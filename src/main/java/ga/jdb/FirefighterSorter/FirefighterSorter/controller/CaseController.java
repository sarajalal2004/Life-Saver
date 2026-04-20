package ga.jdb.FirefighterSorter.FirefighterSorter.controller;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.Case;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.Type;
import ga.jdb.FirefighterSorter.FirefighterSorter.service.CaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class CaseController {
    private CaseService caseService;

    public void setCaseService(CaseService caseService){
        this.caseService = caseService;
    }

    @GetMapping("/cases")
    public List<Case> getCases(){
        return caseService.getCases();
    }

    @GetMapping("/cases/{caseId}")
    public Case getCase(@PathVariable("caseId") Long caseId){
        return caseService.getCase(caseId);
    }

    @PostMapping("/types/{typeId}/cases")
    public Case createCase(@PathVariable("typeId") Long typeId, @RequestBody Case caseObj){
        return caseService.createCase(typeId, caseObj);
    }

    @PutMapping("/types/{typeId}/cases/{caseId}")
    public Case updateCase(@PathVariable("typeId") Long typeId,
                           @PathVariable("caseId") Long caseId,
                           @RequestBody Case caseObj){
        return caseService.updateCase(typeId, caseId, caseObj);
    }

    @DeleteMapping("/types/{typeId}/cases/{caseId}")
    public Case deleteCase(@PathVariable("typeId") Long typeId,
                           @PathVariable("caseId") Long caseId){
        return caseService.deleteCase(typeId, caseId);
    }

    @PatchMapping("/cases/{caseId}/start")
    public Case startProcessing(@PathVariable Long caseId){
        return caseService.startProcessing(caseId);
    }

    @PatchMapping("/cases/{caseId}/complete")
    public Case completeProcessing(@PathVariable Long caseId){
        return caseService.completeProcessing(caseId);
    }


}
