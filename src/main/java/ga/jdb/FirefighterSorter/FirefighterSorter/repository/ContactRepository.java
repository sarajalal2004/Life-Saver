package ga.jdb.FirefighterSorter.FirefighterSorter.repository;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
