package com.mercadolivre.desafioquality.usecase;

import com.mercadolivre.desafioquality.entity.dto.request.PropertyRequestDTO;
import com.mercadolivre.desafioquality.entity.Property;
import com.mercadolivre.desafioquality.entity.Room;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    private List<Property> properties = new ArrayList<>();

    // Conversão de RequestDTO
    public Property convertRequestDTOToEntity(PropertyRequestDTO pDTO){
        Property p = Property.builder()
                .prop_name(pDTO.getProp_name())
                .prop_district(pDTO.getProp_district())
                .build();
        return p;
    }

    public PropertyRequestDTO convertEntityToRequestDTO(Property p){
        PropertyRequestDTO pDTO = PropertyRequestDTO
                .builder().prop_name(p.getProp_name())
                .prop_district(p.getProp_district())
                .build();
        return pDTO;
    }

    public List<Property> getAllProperties(){
        Double squareMeters = 0.0;

        for(Property p: properties){
            squareMeters += calculateMeters(p.getRooms());
            p.setTotalMeters(squareMeters);
        }
        return properties;
    }

    public Double calculateMeters(List<Room> rooms){
        Double squareMeters = 0.0;
        for(Room r: rooms){
            squareMeters += r.getRoom_length() * r.getRoom_width();
        }
        return squareMeters;
    }

    public Property saveProperty(PropertyRequestDTO pDTO){
        Property p = convertRequestDTOToEntity(pDTO);
        p.setRooms(new ArrayList<>());
        p.setTotalMeters(0.0);
        properties.add(p);
        return p;
    }

    public Optional<Property> findByName(String name){
        Optional<Property> obj = properties.stream().
                filter(p -> p.getProp_name().equalsIgnoreCase(name)).
                findFirst();
        return obj;
    }


    public Property getPropertyForRoom(String name) {
        Property p = findByName(name).orElseThrow(()-> new IndexOutOfBoundsException("Name property not found"));
        return p;
    }

    public PropertyRequestDTO modifyProperty(String name, PropertyRequestDTO request) {
        Property p = convertRequestDTOToEntity(request);
        Property propertyByName = findByName(name).orElseThrow(()-> new IndexOutOfBoundsException("Name property not found"));

        propertyByName.setProp_name(request.getProp_name());
        propertyByName.setProp_district(request.getProp_district());

        return convertEntityToRequestDTO(propertyByName);
    }

    public void deleteProperty(String name) {
        Property p = findByName(name).orElseThrow(()-> new IndexOutOfBoundsException("Name property not found"));
        properties.remove(p);
    }
}
