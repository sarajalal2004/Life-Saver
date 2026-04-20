package ga.jdb.FirefighterSorter.FirefighterSorter.service;

import ga.jdb.FirefighterSorter.FirefighterSorter.exception.InformationNotFoundException;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.Case;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.CaseRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.TypeRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class CaseService {
    private CaseRepository caseRepository;
    private UserService userService;
    private TypeRepository typeRepository;

    public CaseService(CaseRepository caseRepository,
                       UserService userService,
                       TypeRepository typeRepository){
        this.caseRepository = caseRepository;
        this.userService = userService;
        this.typeRepository = typeRepository;
    }

    public List<Case> getCases(){
        return caseRepository.findAll();
    }

    public Case getCase(Long caseId){
        return caseRepository.findById(caseId).orElseThrow(
                () -> new InformationNotFoundException("Case with id " + caseId + " is not exists")
        );
    }

    public Case createCase(Long typeId, Case caseObj){
        if(!typeRepository.existsById(typeId))
            throw new InformationNotFoundException("Type with id " + typeId + " is not exists");
        /// TODO: Add calculation
        caseObj.setStatus(Case.CaseStatus.CREATED);
        return caseRepository.save(caseObj);
    }

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
        caseObj2.setAddress(caseObj.getAddress());
        caseObj2.setCoverageUOM(caseObj.getCoverageUOM());
        caseObj2.setCoverageQuantity(caseObj2.getCoverageQuantity());
        caseObj2.setNotes(caseObj.getNotes());
        caseObj2.setStartedAt(caseObj.getStartedAt());
        caseObj2.setEndedAt(caseObj.getEndedAt());
        caseObj2.setPeopleInside(caseObj.getPeopleInside());

        // TODO: Add calculation

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
        caseObj.setStatus(Case.CaseStatus.CANCELED);
        return caseRepository.save(caseObj);
    }

    public Case startProcessing(Long caseId){
        Case caseObj = caseRepository.findById(caseId).orElseThrow(
                () -> new InformationNotFoundException("Case with id " + caseId + " is not exists")
        );
        caseObj.setStatus(Case.CaseStatus.PROCESSING);
        return caseRepository.save(caseObj);
    }

    public Case completeProcessing(Long caseId){
        Case caseObj = caseRepository.findById(caseId).orElseThrow(
                () -> new InformationNotFoundException("Case with id " + caseId + " is not exists")
        );
        caseObj.setStatus(Case.CaseStatus.COMPLETED);
        return caseRepository.save(caseObj);
    }
}
