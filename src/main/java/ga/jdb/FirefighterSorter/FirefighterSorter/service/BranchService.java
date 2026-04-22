package ga.jdb.FirefighterSorter.FirefighterSorter.service;

import ga.jdb.FirefighterSorter.FirefighterSorter.exception.InformationExistException;
import ga.jdb.FirefighterSorter.FirefighterSorter.exception.InformationNotFoundException;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.Branch;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.BranchRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {
    private BranchRepository branchRepository;
    private UserService userService;

    @Autowired
    public BranchService(BranchRepository branchRepository,
                         UserService userService){
        this.branchRepository = branchRepository;
        this.userService = userService;
    }

    public List<Branch> getBranches(){
        return branchRepository.findAll();
    }

    public Branch getBranch(Long branchId){
        return branchRepository.findById(branchId).orElseThrow(
                () -> new InformationNotFoundException("The branch with id " + branchId + " is not exists"));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public Branch createBranch(Branch branch){
        if(branchRepository.existsByName(branch.getName()))
            throw new InformationExistException("Branch with name " + branch.getName() + " is already exists");
        return branchRepository.save(branch);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public Branch updateBranch(Long branchId, Branch branch){
        Branch branchObj = branchRepository.findById(branchId).orElseThrow(
                () -> new InformationNotFoundException("The branch with id " + branchId + " is not exists")
        );
        if(branchRepository.existsByName(branch.getName())
                && !(branchRepository.findByName(branch.getName()).get().getId() == branchObj.getId()))
            throw new InformationExistException("Couldn't update name to " + branch.getName() + " as other branch have the same name");
        branchObj.setName(branch.getName());
        branchObj.setLatitude(branch.getLatitude());
        branchObj.setLongitude(branch.getLongitude());
        return branchRepository.save(branchObj);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Branch deleteBranch(Long branchId){
         Branch branch = branchRepository.findById(branchId).orElseThrow(
                () -> new InformationNotFoundException("The branch with id " + branchId + " is not exists"));
         branchRepository.delete(branch);
         return branch;
    }

}
