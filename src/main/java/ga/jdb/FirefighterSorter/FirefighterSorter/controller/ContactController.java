package ga.jdb.FirefighterSorter.FirefighterSorter.controller;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.Contact;
import ga.jdb.FirefighterSorter.FirefighterSorter.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class ContactController {
    private ContactService contactService;

    @Autowired
    public void setContactService(ContactService contactService){
        this.contactService = contactService;
    }

    @GetMapping(path = "contacts")
    public List<Contact> getAllContacts(){
        return contactService.getAllContacts();
    }

    @GetMapping(path = "/cases/{caseId}/contacts")
    public List<Contact> getContactsByCase(@PathVariable("caseId") Long caseId){
        return contactService.getContactsByCase(caseId);
    }

    @GetMapping(path = "/cases/{caseId}/contacts/{contactId}")
    public Contact getContactByCase(@PathVariable("caseId") Long caseId, @PathVariable("contactId") Long contactId){
        return contactService.getContact(caseId, contactId);
    }

    @PostMapping(path = "/cases/{caseId}/contactType/{contactTypeId}/contacts")
    public Contact createContact(@PathVariable("caseId") Long caseId,
                                 @PathVariable("contactTypeId") Long contactTypeId,
                                 @RequestBody Contact contact){
        return contactService.createContact(caseId, contactTypeId, contact);
    }

    @PutMapping(path = "/cases/{caseId}/contactType/{contactTypeId}/contacts/{contactId}")
    public Contact updateContact(@PathVariable("caseId") Long caseId,
                                 @PathVariable("contactTypeId") Long contactTypeId,
                                 @PathVariable("contactId") Long contactId,
                              @RequestBody Contact contact){
        return contactService.updateContact(caseId, contactTypeId, contactId, contact);
    }

    @DeleteMapping(path = "/cases/{caseId}/contactType/{contactTypeId}/contacts/{contactId}")
    public Contact deleteContact(@PathVariable("caseId") Long caseId,
                                 @PathVariable("contactTypeId") Long contactTypeId,
                                 @PathVariable("contactId") Long contactId){
        return contactService.deleteContact(caseId, contactTypeId, contactId);
    }
}
