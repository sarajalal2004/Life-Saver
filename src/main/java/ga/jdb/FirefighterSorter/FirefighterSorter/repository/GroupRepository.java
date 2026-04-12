package ga.jdb.FirefighterSorter.FirefighterSorter.repository;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.HistoryGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<HistoryGroup, Long> {
}
