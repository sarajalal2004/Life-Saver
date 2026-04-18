package ga.jdb.FirefighterSorter.FirefighterSorter.service;

import ga.jdb.FirefighterSorter.FirefighterSorter.exception.InformationExistException;
import ga.jdb.FirefighterSorter.FirefighterSorter.exception.InformationNotFoundException;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.ContactType;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.ContactTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactTypeService {
    private ContactTypeRepository contactTypeRepository;
    private UserService userService;

    @Autowired
    public ContactTypeService(ContactTypeRepository contactTypeRepository,
                              UserService userService){
        this.contactTypeRepository = contactTypeRepository;
        this.userService = userService;
    }

    public List<ContactType> getContactTypes(){
        return contactTypeRepository.findAll();
    }

    public ContactType getContactType(Long contactTypeId){
        return contactTypeRepository.findById(contactTypeId).orElseThrow(
                () -> new InformationNotFoundException("Contact type with Id " + contactTypeId + " is not exists")
        );
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ContactType createContactType(ContactType contactType){
        if(contactTypeRepository.existsByMethod(contactType.getMethod()))
            throw new InformationExistException("Contact type with method " + contactType.getMethod() + " is already exists");
        return contactTypeRepository.save(contactType);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ContactType updateContactType(Long contactTypeId,
                                         ContactType contactType){
        ContactType contactTypeObj = contactTypeRepository.findById(contactTypeId).orElseThrow(
                () -> new InformationNotFoundException("Contact type with Id " + contactTypeId + " is not exists")
        );
        if(contactTypeRepository.existsByMethod(contactType.getMethod()) && contactTypeRepository.findByMethod(contactType.getMethod()).get().getId() != contactTypeObj.getId())
            throw new InformationExistException("Contact type with method " + contactType.getMethod() + " is already exists");
        contactTypeObj.setMethod(contactType.getMethod());
        return contactTypeRepository.save(contactTypeObj);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ContactType deleteContactType(Long contactTypeId){
        ContactType contactTypeObj = contactTypeRepository.findById(contactTypeId).orElseThrow(
                () -> new InformationNotFoundException("Contact type with Id " + contactTypeId + " is not exists")
        );
        contactTypeRepository.delete(contactTypeObj);
        return contactTypeObj;
    }
}
