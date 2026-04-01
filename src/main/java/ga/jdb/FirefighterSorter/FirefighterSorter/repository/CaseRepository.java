package ga.jdb.FirefighterSorter.FirefighterSorter.repository;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<Case, Long> {
}
