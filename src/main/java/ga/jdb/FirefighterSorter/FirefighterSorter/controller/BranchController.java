package ga.jdb.FirefighterSorter.FirefighterSorter.controller;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.Branch;
import ga.jdb.FirefighterSorter.FirefighterSorter.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class BranchController {
    private BranchService branchService;

    @Autowired
    public void setBranchService(BranchService branchService){
        this.branchService = branchService;
    }

    @GetMapping(path = "/branches")
    public List<Branch> getBranches(){
        return branchService.getBranches();
    }

    @GetMapping(path = "/branches/{branchId}")
    public Branch getBranch(@PathVariable("branchId") Long branchId){
        return branchService.getBranch(branchId);
    }

    @PostMapping(path = "/branches")
    public Branch createBranch(@RequestBody Branch branch){
        return branchService.createBranch(branch);
    }

    @PutMapping(path = "/branches/{branchId}")
    public Branch updateBranch(@PathVariable("branchId") Long branchId, @RequestBody Branch branch){
        return branchService.updateBranch(branchId, branch);
    }

    @DeleteMapping(path = "/branches/{branchId}")
    public Branch deleteBranch(@PathVariable("branchId") Long branchId){
        return branchService.deleteBranch(branchId);
    }

}
