package ga.jdb.FirefighterSorter.FirefighterSorter.repository;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.Branch;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.HistoryGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HistoryGroupRepository extends JpaRepository<HistoryGroup, Long> {
    Optional<HistoryGroup> findByIdAndHistoryCaseId(Long id, Long historyCaseId);
    Boolean existsByHistoryCaseIdAndBranchIdAndUserId(Long historyCaseId, Long branchId, Long userId);
    Optional<HistoryGroup> findByIdAndHistoryCaseIdAndBranchIdAndUserId(Long id, Long historyCaseId, Long branchId, Long userId);
    Optional<HistoryGroup> findByHistoryCaseIdAndBranchIdAndUserId(Long historyCaseId, Long branchId, Long userId);

}
