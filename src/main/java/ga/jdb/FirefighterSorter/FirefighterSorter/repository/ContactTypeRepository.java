package ga.jdb.FirefighterSorter.FirefighterSorter.repository;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {
    boolean existsByMethod(String method);
    Optional<ContactType> findByMethod(String method);
}
