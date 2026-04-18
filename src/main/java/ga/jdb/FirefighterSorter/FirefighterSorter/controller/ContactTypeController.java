package ga.jdb.FirefighterSorter.FirefighterSorter.controller;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.ContactType;
import ga.jdb.FirefighterSorter.FirefighterSorter.service.ContactTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class ContactTypeController {
    private ContactTypeService contactTypeService;

    @Autowired
    public void setContactTypeService(ContactTypeService contactTypeService){
        this.contactTypeService = contactTypeService;
    }

    @GetMapping(path = "/contact-types")
    public List<ContactType> getContactTypes(){
        return contactTypeService.getContactTypes();
    }

    @GetMapping(path = "/contact-types/{contactTypeId}")
    public ContactType getContactType(@PathVariable("contactTypeId") Long contactTypeId){
        return contactTypeService.getContactType(contactTypeId);
    }

    @PostMapping(path = "/contact-types")
    public ContactType createContactType(@RequestBody ContactType contactType){
        return contactTypeService.createContactType(contactType);
    }

    @PutMapping(path = "/contact-types/{contactTypeId}")
    public ContactType updateContactType(@PathVariable("contactTypeId") Long contactTypeId,
                                       @RequestBody ContactType contactType){
        return contactTypeService.updateContactType(contactTypeId, contactType);
    }

    @DeleteMapping(path = "/contact-types/{contactTypeId}")
    public ContactType deleteContactType(@PathVariable("contactTypeId") Long contactTypeId){
        return contactTypeService.deleteContactType(contactTypeId);
    }
}
