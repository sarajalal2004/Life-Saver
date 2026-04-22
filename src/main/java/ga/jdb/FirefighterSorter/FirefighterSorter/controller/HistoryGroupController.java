package ga.jdb.FirefighterSorter.FirefighterSorter.controller;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.HistoryGroup;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.requests.UpdateHistoryGroupRequest;
import ga.jdb.FirefighterSorter.FirefighterSorter.service.HistoryGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class HistoryGroupController {
    private HistoryGroupService historyGroupService;

    @Autowired
    public void setHistoryGroupService(HistoryGroupService historyGroupService){
        this.historyGroupService = historyGroupService;
    }

    @GetMapping(path = "/history-groups")
    public List<HistoryGroup> getGroups(){
        return historyGroupService.getGroups();
    }

    @GetMapping(path = "/cases/{caseId}/history-groups")
    public List<HistoryGroup> getGroupsByCase(@PathVariable("caseId") Long caseId){
        return historyGroupService.getGroupsByCase(caseId);
    }

    @GetMapping(path = "/cases/{caseId}/history-groups/{historyGroupId}")
    public HistoryGroup getGroup(@PathVariable("caseId") Long caseId, @PathVariable("historyGroupId") Long historyGroupId){
        return historyGroupService.getGroupByCase(caseId, historyGroupId);
    }

    @PostMapping(path = "/cases/{caseId}/branches/{branchId}/users/{userId}/history-groups")
    public HistoryGroup createGroup(@PathVariable("caseId") Long caseId,
                                    @PathVariable("branchId") Long branchId,
                                    @PathVariable("userId") Long userId,
                                    @RequestBody HistoryGroup historyGroup){
        return historyGroupService.createGroup(caseId, branchId, userId, historyGroup);
    }

    @PutMapping(path = "/history-groups/{historyGroupId}")
    public HistoryGroup updateGroup(@PathVariable("historyGroupId") Long historyGroupId,
                                    @RequestBody UpdateHistoryGroupRequest updateHistoryGroupRequest){
        return historyGroupService.updateGroup(historyGroupId, updateHistoryGroupRequest);
    }

    @DeleteMapping(path = "/cases/{caseId}/branches/{branchId}/users/{userId}/history-groups/{historyGroupId}")
    public HistoryGroup deleteGroup(@PathVariable("caseId") Long caseId,
                                    @PathVariable("branchId") Long branchId,
                                    @PathVariable("userId") Long userId,
                                    @PathVariable("historyGroupId") Long historyGroupId){
        return historyGroupService.deleteGroup(caseId, branchId, userId, historyGroupId);
    }
}
