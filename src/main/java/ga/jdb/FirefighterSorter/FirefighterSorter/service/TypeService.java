package ga.jdb.FirefighterSorter.FirefighterSorter.service;

import ga.jdb.FirefighterSorter.FirefighterSorter.exception.InformationExistException;
import ga.jdb.FirefighterSorter.FirefighterSorter.exception.InformationNotFoundException;
import ga.jdb.FirefighterSorter.FirefighterSorter.model.Type;
import ga.jdb.FirefighterSorter.FirefighterSorter.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    private UserService userService;
    private TypeRepository typeRepository;

    @Autowired
    public TypeService(UserService userService,
                       TypeRepository typeRepository){
        this.userService = userService;
        this.typeRepository = typeRepository;
    }

    public List<Type> getTypes(){
        return typeRepository.findAll();
    }

    public Type getType(Long typeId){
        return typeRepository.findById(typeId).orElseThrow(
                () -> new InformationNotFoundException("Type with Id " + typeId + " is not exist")
        );
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public Type createType(Type type){
        if(typeRepository.existsByName(type.getName()))
            throw new InformationExistException("Type with name " + type.getName() + " is already exists");
        return typeRepository.save(type);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public Type updateType(Long typeId, Type type){
        Type typeObj = typeRepository.findById(typeId).orElseThrow(
                () -> new InformationNotFoundException("Type with id " + typeId + " is not exits")
        );
        if(typeRepository.existsByName(type.getName()) && typeRepository.findByName(type.getName()).get().getId() != typeId)
            throw new InformationExistException("Couldn't update as type with name " + type.getName() + " is already exits");
        typeObj.setName(type.getName());
        typeObj.setPriority(type.getPriority());
        return typeRepository.save(typeObj);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Type deleteType(Long typeId){
        Type typeObj = typeRepository.findById(typeId).orElseThrow(
                () -> new InformationNotFoundException("Type with id " + typeId + " is not exits")
        );
        typeRepository.delete(typeObj);
        return typeObj;
    }

}
