package ga.jdb.FirefighterSorter.FirefighterSorter.controller;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.Type;
import ga.jdb.FirefighterSorter.FirefighterSorter.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class TypeController {
    private TypeService typeService;

    @Autowired
    public void setTypeService(TypeService typeService){
        this.typeService = typeService;
    }

    @GetMapping(path = "/types")
    public List<Type> getTypes(){
        return typeService.getTypes();
    }

    @GetMapping(path = "/types/{typeId}")
    public Type getType(@PathVariable("typeId") Long typeId){
        return typeService.getType(typeId);
    }

    @PostMapping(path = "/types")
    public Type createType(@RequestBody Type type){
        return typeService.createType(type);
    }

    @PutMapping(path = "/types/{typeId}")
    public Type updateType(@PathVariable("typeId") Long typeId, @RequestBody Type type){
        return typeService.updateType(typeId, type);
    }

    @DeleteMapping(path = "/types/{typeId}")
    public Type deleteType(@PathVariable("typeId") Long typeId){
        return typeService.deleteType(typeId);
    }
}
