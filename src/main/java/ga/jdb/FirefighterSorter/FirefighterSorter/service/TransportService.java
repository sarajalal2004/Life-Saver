package ga.jdb.FirefighterSorter.FirefighterSorter.service;

import ga.jdb.FirefighterSorter.FirefighterSorter.exception.InformationExistException;
import ga.jdb.FirefighterSorter.FirefighterSorter.exception.InformationNotFoundException;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.Transport;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.BranchRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.TransportRepository;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportService {
    private TransportRepository transportRepository;
    private BranchRepository branchRepository;
    private UserRepository userRepository;

    @Autowired
    private TransportService(TransportRepository transportRepository,
                             UserRepository userRepository,
                             BranchRepository branchRepository){
        this.transportRepository = transportRepository;
        this.userRepository = userRepository;
        this.branchRepository = branchRepository;
    }

    public List<Transport> getAllTransports(){
        return transportRepository.findAll();
    }

    public List<Transport> getTransports(Long branchId){
        if(!branchRepository.existsById(branchId))
            throw new InformationNotFoundException("the branch with ID " + branchId + "is not exists");
        return transportRepository.findByBranchId(branchId);
    }

    public Transport getTransport(Long branchId, Long transportId){
        if(!branchRepository.existsById(branchId))
            throw new InformationNotFoundException("the branch with ID " + branchId + "is not exists");
        else if (!transportRepository.existsById(transportId)) {
            throw new InformationNotFoundException("the transport with ID " + transportId + "is not exists");
        }
        return transportRepository.findByIdAndBranchId(transportId, branchId).orElseThrow(() ->
                new InformationNotFoundException("Transport with ID " + transportId + " is not exist in branch " + branchId)
        );
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public Transport createTransport(Long branchId, Transport transport){
        if(transportRepository.existsByTypeAndRegisterNumber(transport.getType(), transport.getRegisterNumber())){
            throw new InformationExistException("Couldn't add two transports with same type and register number");
        }
        transport.setBranch(
                branchRepository.findById(branchId).orElseThrow(()->
                new InformationNotFoundException("the branch with ID " + branchId + "is not exists"))
        );
        return transportRepository.save(transport);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public Transport updateTransport(Long branchId, Long transportId, Transport transport){
        if(!branchRepository.existsById(branchId))
            throw new InformationNotFoundException("the branch with ID " + branchId + "is not exists");
        else if (!transportRepository.existsById(transportId)) {
            throw new InformationNotFoundException("the transport with ID " + transportId + "is not exists");
        } else if(transportRepository.existsByTypeAndRegisterNumber(transport.getType(), transport.getRegisterNumber())){
            throw new InformationExistException("Couldn't update this type and register number, because other one with same combination exists");
        }
        Transport transportObj = transportRepository.findByIdAndBranchId(transportId, branchId).orElseThrow(() ->
                new InformationNotFoundException("Transport with ID " + transportId + " is not exist in branch " + branchId)
        );
        transportObj.setType(transport.getType());
        transportObj.setRegisterNumber(transport.getRegisterNumber());
        transportObj.setDescription(transport.getDescription());
        transportObj.setCoverageInMeter(transport.getCoverageInMeter());
        transportObj.setSpeed(transport.getSpeed());
        transportObj.setReserved(transport.getReserved());
        return transportRepository.save(transportObj);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public Transport deleteTransport(Long branchId, Long transportId){
        if(!branchRepository.existsById(branchId))
            throw new InformationNotFoundException("the branch with ID " + branchId + "is not exists");
        else if (!transportRepository.existsById(transportId)) {
            throw new InformationNotFoundException("the transport with ID " + transportId + "is not exists");
        }
        Transport transport = transportRepository.findByIdAndBranchId(transportId, branchId).orElseThrow(() ->
                new InformationNotFoundException("Transport with ID " + transportId + " is not exist in branch " + branchId));
        transportRepository.delete(transport);
        return transport;
    }
}
