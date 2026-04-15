package ga.jdb.FirefighterSorter.FirefighterSorter.repository;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TransportRepository extends JpaRepository<Transport, Long> {
    List<Transport> findByBranchId(Long branchId);
    Optional<Transport> findByIdAndBranchId(Long id, Long branchId);
    Boolean existsByTypeAndRegisterNumber(String type, String registerNumber);
    Optional<Transport> findByTypeAndRegisterNumber(String type, String registerNumber);


}
