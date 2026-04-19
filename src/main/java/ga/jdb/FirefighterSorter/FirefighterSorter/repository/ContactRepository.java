package ga.jdb.FirefighterSorter.FirefighterSorter.repository;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.Contact;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByIdAndContactCaseId(Long id, Long caseId);
    Boolean existsByContactCaseIdAndContactTypeIdAndValue(Long caseId, Long contactType, String value);
    Optional<Contact> findByContactCaseIdAndContactTypeIdAndValue(Long caseId, Long contactType, String value);
    Optional<Contact> findByContactCaseIdAndContactTypeIdAndId(Long caseId, Long contactType, Long id);
}
