package com.mercadolivre.desafioquality.usecase;

import com.mercadolivre.desafioquality.entity.dto.request.PropertyRequestDTO;
import com.mercadolivre.desafioquality.entity.Property;
import com.mercadolivre.desafioquality.entity.Room;
import com.mercadolivre.desafioquality.entity.dto.response.PropertyResponseDTO;
import com.mercadolivre.desafioquality.entity.dto.response.RoomResponseDTO;
import com.mercadolivre.desafioquality.exception.error.BadRequestException;
import com.mercadolivre.desafioquality.exception.error.NotFoundException;
import com.mercadolivre.desafioquality.interface_adapters.repository.DistrictRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PropertyService {

    private List<Property> properties = new ArrayList<>();

    private DistrictRepository districtRepository;

    public PropertyService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    // Conversão de RequestDTO
    public Property convertRequestDTOToEntity(PropertyRequestDTO pDTO){
        Property p = Property.builder()
                .prop_name(pDTO.getProp_name())
                .prop_district(pDTO.getProp_district())
                .build();
        return p;
    }

    public List<PropertyResponseDTO> getAllProperties(){
        List<PropertyResponseDTO> list = new ArrayList<>();
        Double squareMeters = 0.0;

        for(Property p: properties){
            squareMeters += calculateMeters(p.getRooms());
            p.setTotalMeters(squareMeters);
            list.add(convertEntityToResponseDTO(p));
        }
        return list;
    }

    public Double calculateMeters(List<Room> rooms){
        Double squareMeters = 0.0;
        for(Room r: rooms){
            squareMeters += r.getRoom_length() * r.getRoom_width();
        }
        return squareMeters;
    }

    public PropertyResponseDTO saveProperty(PropertyRequestDTO pDTO){
        if(findByName(pDTO.getProp_name()).isPresent()){
            throw new BadRequestException("Name property already exists");
        } else{
            Property p = convertRequestDTOToEntity(pDTO);
            p.setRooms(new ArrayList<>());
            p.setTotalMeters(0.0);
            properties.add(p);
            return convertEntityToResponseDTO(p);
        }
    }

    public Optional<Property> findByName(String name){
        Optional<Property> obj = properties.stream().
                filter(p -> p.getProp_name().equalsIgnoreCase(name)).
                findFirst();
        return obj;
    }

    public Property getProperty(String name) {
        Property p = findByName(name).orElseThrow(()-> new NotFoundException("Name property not found"));
        return p;
    }

    public PropertyResponseDTO modifyProperty(String name, PropertyRequestDTO request) {
        Property propertyByName = findByName(name).orElseThrow(()-> new NotFoundException("Name property not found"));

        propertyByName.setProp_name(request.getProp_name());
        propertyByName.setProp_district(request.getProp_district());

        return convertEntityToResponseDTO(propertyByName);
    }

    public void deleteProperty(String name) {
        Property p = findByName(name).orElseThrow(()-> new NotFoundException("Name property not found"));
        properties.remove(p);
    }

    public PropertyResponseDTO convertEntityToResponseDTO(Property p){
        PropertyResponseDTO pDTO = PropertyResponseDTO
                .builder().prop_name(p.getProp_name())
                .prop_district(p.getProp_district())
                .totalMeters(p.getTotalMeters())
                .rooms(convertListForResponseDTO(p.getRooms()))
                .valueProperty(propertyValue(p))
                .biggestRoom(biggestRoom(p))
                .build();
        return pDTO;
    }

    public List<RoomResponseDTO> convertListForResponseDTO(List<Room> roomList){
        List<RoomResponseDTO> list = new ArrayList<>();
        for(Room r: roomList){
            list.add(convertRoomToResponseDTO(r));
        }
        return list;
    }

    public RoomResponseDTO convertRoomToResponseDTO(Room r){
        RoomResponseDTO rDTO = RoomResponseDTO.builder()
                .room_name(r.getRoom_name())
                .room_length(r.getRoom_length())
                .room_width(r.getRoom_width())
                .squareMetersRoom(calculateMetersRoom(r))
                .build();
        return rDTO;
    }

    public Double calculateMetersRoom(Room r){
        Double squareMeters = 0.0;
        squareMeters = r.getRoom_length() * r.getRoom_width();
        return squareMeters;
    }


    public Double propertyValue(Property p){
        Map<String, Double> districts = districtRepository.getDistricts();
        verifyDistrict(p.getProp_district());
        return p.getTotalMeters() * districts.get(p.getProp_district());

    }

    public RoomResponseDTO biggestRoom(Property p){
        if(p.getRooms().isEmpty()) {
            return null;
        } else{
            RoomResponseDTO dto = convertRoomToResponseDTO(p.getRooms()
                    .stream()
                    .max(Comparator.comparingDouble(pr -> pr.getRoom_width() * pr.getRoom_length())).get());
            return dto;
        }
    }

    public boolean verifyDistrict(String p) {
        Map<String, Double> districts = districtRepository.getDistricts();
        if(districts.containsKey(p)) {
            return true;
        }else{
            throw new NotFoundException("Distric not found in list: "+districts);
        }

    }
}
