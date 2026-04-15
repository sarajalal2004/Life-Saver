package ga.jdb.FirefighterSorter.FirefighterSorter.controller;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.Transport;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.TransportRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class TransportController {
    private TransportService transportService;

    @Autowired
    public void setTransportService(TransportService transportService){
        this.transportService = transportService;
    }

    @GetMapping(path = "/transports")
    public List<Transport> getAllTransports(){
        return transportService.getAllTransports();
    }

    @GetMapping(path = "/branches/{branchId}/transports")
    public List<Transport> getTransports(@PathVariable("branchId") Long branchId){
        return transportService.getTransports(branchId);
    }

    @GetMapping(path = "/branches/{branchId}/transports/{transportId}")
    public Transport getTransport(@PathVariable("branchId") Long branchId,
                                  @PathVariable("transportId") Long transportId){
        return transportService.getTransport(branchId, transportId);
    }

    @PostMapping(path = "/branches/{branchId}/transports")
    public Transport createTransport(@PathVariable("branchId") Long branchId, @RequestBody Transport transport){
        return transportService.createTransport(branchId, transport);
    }

    @PutMapping(path = "/branches/{branchId}/transports/{transportId}")
    public Transport updateTransport(@PathVariable("branchId") Long branchId,
                                     @PathVariable("transportId") Long transportId,
                                     @RequestBody Transport transport){
        return transportService.updateTransport(branchId, transportId, transport);
    }

    @DeleteMapping(path = "/branches/{branchId}/transports/{transportId}")
    public Transport deleteTransport(@PathVariable("branchId") Long branchId,
                                     @PathVariable("transportId") Long transportId){
        return transportService.deleteTransport(branchId, transportId);
    }

    //TODO: transfer transport functionality
    //TODO: reservation

}
