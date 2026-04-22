package ga.jdb.FirefighterSorter.FirefighterSorter.service;

import ga.jdb.FirefighterSorter.FirefighterSorter.exception.BadRequestException;
import ga.jdb.FirefighterSorter.FirefighterSorter.exception.InformationNotFoundException;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.Branch;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.Case;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.Type;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.response.TomTomResponse;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.BranchRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.CaseRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.TypeRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class CaseService {
    private CaseRepository caseRepository;
    private UserService userService;
    private TypeRepository typeRepository;
    private TomTomService tomTomService;
    private BranchRepository branchRepository;

    public CaseService(CaseRepository caseRepository,
                       UserService userService,
                       TypeRepository typeRepository,
                       TomTomService tomTomService,
                       BranchRepository branchRepository){
        this.caseRepository = caseRepository;
        this.userService = userService;
        this.typeRepository = typeRepository;
        this.tomTomService = tomTomService;
        this.branchRepository = branchRepository;
    }

    public List<Case> getCases() throws InterruptedException {
        return refreshCases(caseRepository.findAll());
    }

    public Case getCase(Long caseId){
        return refreshCase(caseRepository.findById(caseId).orElseThrow(
                () -> new InformationNotFoundException("Case with id " + caseId + " is not exists")
        ));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public Case createCase(Long typeId, Case caseObj){
        Type type = typeRepository.findById(typeId).orElseThrow(
            () -> new InformationNotFoundException("Type with id " + typeId + " is not exists")
        );
        caseObj.setType(type);
        calculateCaseData(caseObj);
        caseObj.setStatus(Case.CaseStatus.CREATED);
        return caseRepository.save(caseObj);
    }


    public Case refreshCase(Case caseObj){
        calculateCaseData(caseObj);
        return caseRepository.save(caseObj);
    }

    public List<Case> refreshCases(List<Case> cases) throws InterruptedException {
        for(Case caseObj : cases){
            calculateCaseData(caseObj);
            // delay because the api not accepting too many calls in same time
            Thread.sleep(50);
        }
        return caseRepository.saveAll(cases);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public Case updateCase(Long typeId, Long caseId, Case caseObj){
        if(!typeRepository.existsById(typeId))
            throw new InformationNotFoundException("Type with id " + typeId + " is not exists");
        if(!caseRepository.existsById(caseId))
            throw new InformationNotFoundException("Case with id " + caseId + " is not exists");
        Case caseObj2 = caseRepository.findByIdAndTypeId(caseId, typeId).orElseThrow(
                () -> new InformationNotFoundException("Case with Id " + caseId + " not exists for type " + typeId)
        );
        caseObj2.setName(caseObj.getName());
        caseObj2.setDescription(caseObj.getDescription());
        caseObj2.setLatitude(caseObj.getLatitude());
        caseObj2.setLongitude(caseObj.getLongitude());
        caseObj2.setCoverageInMeter(caseObj.getCoverageInMeter());
        caseObj2.setNotes(caseObj.getNotes());
        caseObj2.setStartedAt(caseObj.getStartedAt());
        caseObj2.setEndedAt(caseObj.getEndedAt());
        caseObj2.setPeopleInside(caseObj.getPeopleInside());

        calculateCaseData(caseObj2);
        return caseRepository.save(caseObj2);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public Case deleteCase(Long typeId, Long caseId){
        if(!typeRepository.existsById(typeId))
            throw new InformationNotFoundException("Type with id " + typeId + " is not exists");
        if(!caseRepository.existsById(caseId))
            throw new InformationNotFoundException("Case with id " + caseId + " is not exists");
        Case caseObj = caseRepository.findByIdAndTypeId(caseId, typeId).orElseThrow(
                () -> new InformationNotFoundException("Case with Id " + caseId + " not exists for type " + typeId)
        );
        if(!caseObj.getStatus().equals(Case.CaseStatus.CREATED) && !caseObj.getStatus().equals(Case.CaseStatus.ASSIGNED))
            throw new BadRequestException("Only created and assigned cases could be cancelled");
        caseObj.setStatus(Case.CaseStatus.CANCELED);
        return caseRepository.save(caseObj);
    }

    public Case startProcessing(Long caseId){
        Case caseObj = caseRepository.findById(caseId).orElseThrow(
                () -> new InformationNotFoundException("Case with id " + caseId + " is not exists")
        );
        if(!caseObj.getStatus().equals(Case.CaseStatus.ASSIGNED))
            throw new BadRequestException("Only assigned status could be changed to processing");
        caseObj.setStatus(Case.CaseStatus.PROCESSING);
        return caseRepository.save(caseObj);
    }

    public Case completeProcessing(Long caseId){
        Case caseObj = caseRepository.findById(caseId).orElseThrow(
                () -> new InformationNotFoundException("Case with id " + caseId + " is not exists")
        );
        if(!caseObj.getStatus().equals(Case.CaseStatus.PROCESSING))
            throw new BadRequestException("Only processing status could be marked as completed");
        caseObj.setStatus(Case.CaseStatus.COMPLETED);
        caseObj.setEndedAt(LocalDateTime.now());
        return caseRepository.save(caseObj);
    }

    private Case calculateCaseData(Case caseObj){
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ReentrantLock lock = new ReentrantLock();
        List<Branch> branches = branchRepository.findAll();
        Map<Branch, TomTomResponse> branchesComparing = new HashMap<>();
        for(Branch branch : branches){
            executor.submit(() -> {
                TomTomResponse tomTomResponse = tomTomService.getTomTomResponse(
                        tomTomService.RoutingInfo(
                                caseObj.getLatitude(), caseObj.getLongitude(),
                                branch.getLatitude(), branch.getLongitude(),
                                true
                        )
                );
                lock.lock();
                branchesComparing.put(branch,tomTomResponse);
                lock.unlock();
            });
        }
        Map.Entry<Branch, TomTomResponse> bestBranchInfo= branchesComparing.entrySet().stream().min(Comparator.comparingDouble(entry -> entry.getValue().getTravelTimeInSeconds())).orElse(null);

        if(bestBranchInfo != null){
            caseObj.setCalculatedTime(bestBranchInfo.getValue().getTravelTimeInSeconds());
            caseObj.setCalculatedDistance(bestBranchInfo.getValue().getLengthInMeters());
            caseObj.setSuggestedBranch(bestBranchInfo.getKey());
        }

        // calculate priority
        // people inside have 30% - from 1 to 5
        // type priority give 30%
        // coverage in addition to start at with respect to time will take 40% - more far will take more time

        Double priority = (caseObj.getPeopleInside() ? 30.0 : 0.0) + ((6 - caseObj.getType().getPriority()) / 5.0 * 30.0);

        if(caseObj.getCalculatedTime() != null)
            priority += (1 - (1.0 / (caseObj.getCoverageInMeter() * Duration.between(caseObj.getStartedAt(), LocalDateTime.now()).toMinutes() * caseObj.getCalculatedTime()))) * 40.0;
        else
            priority += (1 - (1.0 / (caseObj.getCoverageInMeter() * Duration.between(caseObj.getStartedAt(), LocalDateTime.now()).toMinutes()))) * 40.0;

        caseObj.setCalculatedPriority(priority);
        return caseObj;
    }
}
