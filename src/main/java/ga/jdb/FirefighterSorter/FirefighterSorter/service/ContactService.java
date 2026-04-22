package ga.jdb.FirefighterSorter.FirefighterSorter.service;

import ga.jdb.FirefighterSorter.FirefighterSorter.exception.InformationExistException;
import ga.jdb.FirefighterSorter.FirefighterSorter.exception.InformationNotFoundException;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.Case;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.Contact;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.ContactType;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.CaseRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.ContactRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.ContactTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private ContactRepository contactRepository;
    private UserService userService;
    private CaseRepository caseRepository;
    private ContactTypeRepository contactTypeRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository,
                          UserService userService,
                          CaseRepository caseRepository,
                          ContactTypeRepository contactTypeRepository){
        this.contactRepository = contactRepository;
        this.caseRepository = caseRepository;
        this.userService = userService;
        this.contactTypeRepository = contactTypeRepository;
    }

    public List<Contact> getAllContacts(){
        return contactRepository.findAll();
    }

    public List<Contact> getContactsByCase(Long caseId){
        Case caseObj = caseRepository.findById(caseId).orElseThrow(
                () ->  new InformationNotFoundException("case with Id " + caseId + " is not exists")
        );
        return caseObj.getContacts();
    }

    public Contact getContact(Long caseId, Long contactId){
        if(!caseRepository.existsById(caseId))
            throw new InformationNotFoundException("case with Id " + caseId + " is not exists");
        else if (!contactRepository.existsById(contactId))
            throw new InformationNotFoundException("contact with Id " + contactId + " is not exists");
        return contactRepository.findByIdAndContactCaseId(contactId, caseId).orElseThrow(
                () -> new InformationNotFoundException("contact with Id " + contactId + " is not in case with Id" + caseId)
        );
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public Contact createContact(Long caseId, Long contactTypeId, Contact contact){
        Case caseObj = caseRepository.findById(caseId).orElseThrow(
            () -> new InformationNotFoundException("case with Id " + caseId + " is not exists")
        );
        ContactType contactType = contactTypeRepository.findById(contactTypeId).orElseThrow(
            () -> new InformationNotFoundException("contact type with Id " + contactTypeId + " is not exists")
        );
        if(contactRepository.existsByContactCaseIdAndContactTypeIdAndValue(caseId, contactTypeId, contact.getValue()))
            throw new InformationExistException("this contact info already exists");
        contact.setContactCase(caseObj);
        contact.setContactType(contactType);
        return contactRepository.save(contact);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public Contact updateContact(Long caseId, Long contactTypeId, Long contactId,  Contact contact){
        if(!caseRepository.existsById(caseId))
            throw new InformationNotFoundException("case with Id " + caseId + " is not exists");
        else if (!contactTypeRepository.existsById(contactTypeId))
            throw new InformationNotFoundException("contact type with Id " + contactTypeId + " is not exists");
        else if (!contactRepository.existsById(contactId))
            throw new InformationNotFoundException("contact with Id " + contactId + " is not exists");
        Contact contactObj = contactRepository.findByContactCaseIdAndContactTypeIdAndId(caseId, contactTypeId, contactId).orElseThrow(
                () -> new InformationNotFoundException("Contact with Id " + contactId + " is not exist for case" + caseId + " with contact type " + contactTypeId)
        );
        if(contactRepository.existsByContactCaseIdAndContactTypeIdAndValue(caseId, contactTypeId, contact.getValue())
            && !contactRepository.findByContactCaseIdAndContactTypeIdAndValue(caseId, contactTypeId, contact.getValue()).get().getId().equals(contactObj.getId())){
            throw new InformationExistException("Contact with these info is already exist in the current case");
        }
        contactObj.setValue(contact.getValue());
        contactObj.setPriority(contact.getPriority());
        return contactRepository.save(contactObj);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public Contact deleteContact(Long caseId, Long contactTypeId, Long contactId){
        if(!caseRepository.existsById(caseId))
            throw new InformationNotFoundException("case with Id " + caseId + "is not exists");
        else if (!contactTypeRepository.existsById(contactTypeId))
            throw new InformationNotFoundException("contact type with Id " + contactTypeId + "is not exists");
        else if (!contactRepository.existsById(contactId))
            throw new InformationNotFoundException("contact with Id " + contactId + "is not exists");
        Contact contactObj = contactRepository.findByContactCaseIdAndContactTypeIdAndId(caseId, contactTypeId, contactId).orElseThrow(
                () -> new InformationNotFoundException("Contact with Id " + contactId + " is not exist for case " + caseId + " with contact type " + contactTypeId)
        );
        contactRepository.delete(contactObj);
        return contactObj;
    }


}
