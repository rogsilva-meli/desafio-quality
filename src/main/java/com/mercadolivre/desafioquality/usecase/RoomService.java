package com.mercadolivre.desafioquality.usecase;

import com.mercadolivre.desafioquality.entity.dto.request.RoomRequestDTO;
import com.mercadolivre.desafioquality.entity.Property;
import com.mercadolivre.desafioquality.entity.Room;
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

    public RoomRequestDTO convertEntityToRequestDTO(Room r){
        RoomRequestDTO rDTO = RoomRequestDTO.builder()
                .room_name(r.getRoom_name())
                .room_length(r.getRoom_length())
                .room_width(r.getRoom_width())
                .property(r.getProperty())
                .build();

        return rDTO;
    }

    //

    public List<Room> getAllRooms(){
        return rooms;
    }

    public Room saveRoom(RoomRequestDTO rDTO){

        Room r = convertRequestDTOToEntity(rDTO);

        Property property = propertyService.getPropertyForRoom(rDTO.getProperty());

        List<Room> listRooms = property.getRooms();

        r.setRoom_name(rDTO.getRoom_name());
        r.setRoom_length(rDTO.getRoom_length());
        r.setRoom_width(rDTO.getRoom_width());
        r.setProperty(property.getProp_name());
        listRooms.add(r);
        property.setRooms(listRooms);

        return r;
    }

    public Optional<Room> findByName(String name){
        Optional<Room> obj = rooms.stream().
                filter(p -> p.getRoom_name().equalsIgnoreCase(name)).
                findFirst();
        return obj;
    }


    public Room getRoom(String nameProperty, String nameRoom) {
        Property property = propertyService.findByName(nameProperty).orElseThrow(() -> new IndexOutOfBoundsException("Name property not found"));
        Optional<Room> first = property.getRooms().stream().filter(p -> p.getRoom_name().equalsIgnoreCase(nameRoom)).findFirst();
        Room room = first.orElseThrow(() -> new IndexOutOfBoundsException("Name room not found"));
        return room;
    }

    public RoomRequestDTO modifyProperty(String nameProperty, String nameRoom, RoomRequestDTO r) {
        Property property = propertyService.findByName(nameProperty).orElseThrow(() -> new IndexOutOfBoundsException("Name property not found"));
        Optional<Room> first = property.getRooms().stream().filter(p -> p.getRoom_name().equalsIgnoreCase(nameRoom)).findFirst();
        Room room = first.orElseThrow(() -> new IndexOutOfBoundsException("Name room not found"));
        room.setRoom_name(r.getRoom_name());
        room.setRoom_width(r.getRoom_width());
        room.setRoom_length(r.getRoom_length());

        return convertEntityToRequestDTO(room);
    }

    public void deleteProperty(String nameProperty, String nameRoom) {
        Property property = propertyService.findByName(nameProperty).orElseThrow(() -> new IndexOutOfBoundsException("Name property not found"));
        Optional<Room> first = property.getRooms().stream().filter(p -> p.getRoom_name().equalsIgnoreCase(nameRoom)).findFirst();
        Room room = first.orElseThrow(() -> new IndexOutOfBoundsException("Name room not found"));
        List<Room> roomList = property.getRooms();
        roomList.remove(room);
        property.setRooms(roomList);
    }
}