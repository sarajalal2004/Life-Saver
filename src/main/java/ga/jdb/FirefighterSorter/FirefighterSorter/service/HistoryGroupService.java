package ga.jdb.FirefighterSorter.FirefighterSorter.service;

import ga.jdb.FirefighterSorter.FirefighterSorter.exception.BadRequestException;
import ga.jdb.FirefighterSorter.FirefighterSorter.exception.InformationExistException;
import ga.jdb.FirefighterSorter.FirefighterSorter.exception.InformationNotFoundException;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.Branch;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.Case;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.HistoryGroup;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.User;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.requests.UpdateHistoryGroupRequest;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.BranchRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.CaseRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.HistoryGroupRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistoryGroupService {
    private HistoryGroupRepository historyGroupRepository;
    private UserService userService;
    private CaseRepository caseRepository;
    private BranchRepository branchRepository;
    private UserRepository userRepository;

    @Autowired
    public HistoryGroupService(HistoryGroupRepository historyGroupRepository,
                               UserService userService,
                               CaseRepository caseRepository,
                               BranchRepository branchRepository,
                               UserRepository userRepository){
        this.historyGroupRepository = historyGroupRepository;
        this.userService = userService;
        this.caseRepository = caseRepository;
        this.branchRepository = branchRepository;
        this.userRepository = userRepository;
    }

    public List<HistoryGroup> getGroups(){
        return historyGroupRepository.findAll();
    }

    public List<HistoryGroup> getGroupsByCase(Long caseId){
        return caseRepository.findById(caseId).orElseThrow(
                () -> new InformationNotFoundException("Case with id " + caseId + " is not exists")
        ).getHistoryGroups();
    }

    public HistoryGroup getGroupByCase(Long caseId, Long historyGroupId){
        if(!caseRepository.existsById(caseId))
            throw new InformationNotFoundException("Case with id " + caseId + " is not exists");
        else if(!historyGroupRepository.existsById(historyGroupId))
            throw new InformationNotFoundException("History Group with id " + historyGroupId + " is not exists");
        return historyGroupRepository.findByIdAndHistoryCaseId(historyGroupId, caseId).orElseThrow(
                () -> new InformationNotFoundException("group with id " + historyGroupId + " is not included in case with id " + caseId)
        );
    }

    public HistoryGroup createGroup(Long caseId, Long branchId, Long userId, HistoryGroup historyGroup){
        Case caseObj = caseRepository.findById(caseId).orElseThrow(
            () -> new InformationNotFoundException("Case with id " + caseId + " is not exists")
        );
        Branch branch = branchRepository.findById(branchId).orElseThrow(
            () -> new InformationNotFoundException("Branch with id " + branchId + " is not exists")
        );
        User user = userRepository.findById(userId).orElseThrow(
            () -> new InformationNotFoundException("user with id " + userId + " is not exists")
        );
        if(historyGroupRepository.existsByHistoryCaseIdAndBranchIdAndUserId(caseId, branchId, userId))
            throw new InformationExistException("user is already assigned to this case");
        historyGroup.setHistoryCase(caseObj);
        historyGroup.setBranch(branch);
        historyGroup.setUser(user);
        historyGroup.getHistoryCase().setStatus(Case.CaseStatus.ASSIGNED);
        return historyGroupRepository.save(historyGroup);
    }

    public HistoryGroup updateGroup(Long historyGroupId, UpdateHistoryGroupRequest request){
        Case caseObj = caseRepository.findById(request.getCaseId()).orElseThrow(
                () ->  new InformationNotFoundException("Case with id " + request.getCaseId() + " is not exists")
        );
        Branch branch = branchRepository.findById(request.getBranchId()).orElseThrow(
                () ->  new InformationNotFoundException("Branch with id " + request.getBranchId() + " is not exists")
        );
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () ->  new InformationNotFoundException("User with id " + request.getUserId() + " is not exists")
        );
        HistoryGroup historyGroup = historyGroupRepository.findById(historyGroupId).orElseThrow(
                () ->  new InformationNotFoundException("Group with id " + historyGroupId + " is not exists")
        );
        if(historyGroupRepository.existsByHistoryCaseIdAndBranchIdAndUserId(request.getCaseId(), request.getBranchId(), request.getUserId()) &&
        !historyGroupRepository.findByHistoryCaseIdAndBranchIdAndUserId(request.getCaseId(), request.getBranchId(), request.getUserId()).get().getId().equals(historyGroupId))
            throw new InformationExistException("user with this combination already exists");

        historyGroup.setHistoryCase(caseObj);
        historyGroup.setBranch(branch);
        historyGroup.setUser(user);

        return historyGroupRepository.save(historyGroup);
    }

    public HistoryGroup deleteGroup(Long caseId, Long branchId, Long userId, Long historyGroupId){
        if(!caseRepository.existsById(caseId))
            throw new InformationNotFoundException("Case with id " + caseId + " is not exists");
        else if(!branchRepository.existsById(branchId))
            throw new InformationNotFoundException("Branch with id " + branchId + " is not exists");
        else if(!userRepository.existsById(userId))
            throw new InformationNotFoundException("user with id " + userId + " is not exists");
        else if(!historyGroupRepository.existsById(historyGroupId))
            throw new InformationNotFoundException("Group with id " + historyGroupId + " is not exists");
        HistoryGroup group= historyGroupRepository.findByIdAndHistoryCaseIdAndBranchIdAndUserId(historyGroupId, caseId, branchId, userId).orElseThrow(
                () -> new InformationNotFoundException("this group combination is not exists")
        );
        Case caseObj = group.getHistoryCase();

        if(caseObj.getStatus().equals(Case.CaseStatus.ASSIGNED)){
            if(caseObj.getHistoryGroups().size() == 1){
                caseObj.setStatus(Case.CaseStatus.CREATED);
            }
            historyGroupRepository.delete(group);
        } else if(!caseObj.getStatus().equals(Case.CaseStatus.CREATED))
            throw new BadRequestException("The user couldn't be deleted from case already started or cancelled");
        caseRepository.save(caseObj);

        return group;
    }
}
