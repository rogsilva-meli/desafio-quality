package com.mercadolivre.desafioquality.interface_adapters.controller;

import com.mercadolivre.desafioquality.entity.dto.request.PropertyRequestDTO;
import com.mercadolivre.desafioquality.entity.Property;
import com.mercadolivre.desafioquality.entity.dto.response.PropertyResponseDTO;
import com.mercadolivre.desafioquality.usecase.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class PropertyController {

    private PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/properties")
    public ResponseEntity<List<PropertyResponseDTO>> getAll(){
        List<PropertyResponseDTO> list = propertyService.getAllProperties();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping("/properties")
    public ResponseEntity<PropertyResponseDTO> create(@Valid @RequestBody PropertyRequestDTO pDTO){
        PropertyResponseDTO property = propertyService.saveProperty(pDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(property);
    }

    @PutMapping("/properties/{name}")
    public ResponseEntity<PropertyResponseDTO> modify(@PathVariable String name, @Valid @RequestBody PropertyRequestDTO p){
        PropertyResponseDTO pDTO = propertyService.modifyProperty(name, p);
        return ResponseEntity.status(HttpStatus.OK).body(pDTO);
    }

    @GetMapping("/properties/{name}")
    public ResponseEntity<PropertyResponseDTO> getProperty(@PathVariable String name){

        Property p = propertyService.getPropertyForRoom(name);
        PropertyResponseDTO responseDTO = propertyService.convertEntityToResponseDTO(p);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/properties/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void  delete(@PathVariable String name){
       propertyService.deleteProperty(name);
    }


}
