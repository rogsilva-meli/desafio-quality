package com.mercadolivre.desafioquality.interface_adapters;

import com.mercadolivre.desafioquality.entity.dto.request.PropertyRequestDTO;
import com.mercadolivre.desafioquality.entity.Property;
import com.mercadolivre.desafioquality.usecase.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class PropertyController {

    private PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/properties")
    public ResponseEntity<List<Property>> getAll(){
        List<Property> list = propertyService.getAllProperties();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping("/properties")
    public ResponseEntity<Property> create(@RequestBody PropertyRequestDTO pDTO){
        Property property = propertyService.saveProperty(pDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(property);
    }

    @PutMapping("/properties/{name}")
    public ResponseEntity<PropertyRequestDTO> modify(@PathVariable String name, @RequestBody PropertyRequestDTO p){
        PropertyRequestDTO pDTO = propertyService.modifyProperty(name, p);
        return ResponseEntity.status(HttpStatus.OK).body(pDTO);
    }

    @GetMapping("/properties/{name}")
    public ResponseEntity<Property> getProperty(@PathVariable String name){

        Property p = propertyService.getPropertyForRoom(name);

        return ResponseEntity.status(HttpStatus.OK).body(p);
    }

    @DeleteMapping("/properties/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void  delete(@PathVariable String name){
       propertyService.deleteProperty(name);
    }


}
