package com.mercadolivre.desafioquality.usecase;

import com.mercadolivre.desafioquality.entity.dto.request.RoomRequestDTO;
import com.mercadolivre.desafioquality.entity.Property;
import com.mercadolivre.desafioquality.entity.Room;
import com.mercadolivre.desafioquality.entity.dto.response.RoomResponseDTO;
import com.mercadolivre.desafioquality.exception.error.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private List<Room> rooms = new ArrayList<>();
    private PropertyService propertyService;

    public RoomService(PropertyService propertyService) {
        this.propertyService = propertyService;
    }


    // Conversão de RequestDTO
    public Room convertRequestDTOToEntity(RoomRequestDTO rDTO){
        Property p = propertyService.getPropertyForRoom(rDTO.getProperty());

        Room r = Room.builder()
                .room_name(rDTO.getRoom_name())
                .room_length(rDTO.getRoom_length())
                .room_width(rDTO.getRoom_width())
                .property(p.getProp_name())
                .build();
        return r;
    }

    public RoomResponseDTO convertEntityToResponseDTO(Room r){
        RoomResponseDTO rDTO = RoomResponseDTO.builder()
                .room_name(r.getRoom_name())
                .room_length(r.getRoom_length())
                .room_width(r.getRoom_width())
                .squareMetersRoom(propertyService.calculateMetersRoom(r))
                .build();
        return rDTO;
    }

    public List<RoomResponseDTO> getAllRooms(){
        List<RoomResponseDTO> list = new ArrayList<>();
        for(Room r: rooms){
            list.add(convertEntityToResponseDTO(r));
        }
        return list;
    }

    public RoomResponseDTO saveRoom(RoomRequestDTO rDTO){

        Room r = convertRequestDTOToEntity(rDTO);

        Property property = propertyService.getPropertyForRoom(rDTO.getProperty());

        List<Room> listRooms = property.getRooms();

        r.setRoom_name(rDTO.getRoom_name());
        r.setRoom_length(rDTO.getRoom_length());
        r.setRoom_width(rDTO.getRoom_width());
        r.setProperty(property.getProp_name());
        listRooms.add(r);
        property.setRooms(listRooms);

        return convertEntityToResponseDTO(r);
    }

    public RoomResponseDTO getRoom(String nameProperty, String nameRoom) {
        Property property = propertyService.findByName(nameProperty).orElseThrow(() -> new NotFoundException("Name property not found"));
        Optional<Room> first = property.getRooms().stream().filter(p -> p.getRoom_name().equalsIgnoreCase(nameRoom)).findFirst();
        Room room = first.orElseThrow(() -> new NotFoundException("Name room not found"));

        return convertEntityToResponseDTO(room);
    }

    public RoomResponseDTO modify(String nameProperty, String nameRoom, RoomRequestDTO r) {
        Property property = propertyService.findByName(nameProperty).orElseThrow(() -> new NotFoundException("Name property not found"));
        Optional<Room> first = property.getRooms().stream().filter(p -> p.getRoom_name().equalsIgnoreCase(nameRoom)).findFirst();
        Room room = first.orElseThrow(() -> new NotFoundException("Name room not found"));
        room.setRoom_name(r.getRoom_name());
        room.setRoom_width(r.getRoom_width());
        room.setRoom_length(r.getRoom_length());

        return convertEntityToResponseDTO(room);
    }

    public void delete(String nameProperty, String nameRoom) {
        Property property = propertyService.findByName(nameProperty).orElseThrow(() -> new NotFoundException("Name property not found"));
        Optional<Room> first = property.getRooms().stream().filter(p -> p.getRoom_name().equalsIgnoreCase(nameRoom)).findFirst();
        Room room = first.orElseThrow(() -> new NotFoundException("Name room not found"));
        List<Room> roomList = property.getRooms();
        roomList.remove(room);
        property.setRooms(roomList);
    }
}
