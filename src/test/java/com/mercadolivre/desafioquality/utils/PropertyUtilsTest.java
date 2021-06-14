package com.mercadolivre.desafioquality.utils;

import com.mercadolivre.desafioquality.entity.Property;
import com.mercadolivre.desafioquality.entity.Room;
import com.mercadolivre.desafioquality.entity.dto.request.PropertyRequestDTO;
import com.mercadolivre.desafioquality.entity.dto.response.PropertyResponseDTO;
import com.mercadolivre.desafioquality.entity.dto.response.RoomResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class PropertyUtilsTest {

    public static Property createNewProperty() {
        List<Room> roomsGood = new ArrayList<>();
        Room roomA = Room.builder()
                .room_name("Sala")
                .room_width(5.0)
                .room_length(5.0)
                .property("Casa")
                .build();

        Room roomB = Room.builder()
                .room_name("Quarto")
                .room_width(10.0)
                .room_length(5.0)
                .property("Casa")
                .build();

        roomsGood.add(roomA);
        roomsGood.add(roomB);

        Property p = Property.builder()
                .prop_name("Casa")
                .prop_district("Moema")
                .rooms(roomsGood)
                .totalMeters(75.0)
                .build();
        return p;
    }

    public static List<RoomResponseDTO> createNewRoomDTO() {
        List<RoomResponseDTO> roomsGood = new ArrayList<>();
        RoomResponseDTO roomA = RoomResponseDTO.builder()
                .room_name("Sala")
                .room_width(5.0)
                .room_length(5.0)
                .squareMetersRoom(5.0 * 5.0)
                .build();

        RoomResponseDTO roomB = RoomResponseDTO.builder()
                .room_name("Quarto")
                .room_width(10.0)
                .room_length(5.0)
                .squareMetersRoom(10.0 * 5.0)
                .build();

        roomsGood.add(roomA);
        roomsGood.add(roomB);

        return roomsGood;
    }

    public static PropertyRequestDTO createNewPropertyRequestDTO() {

        PropertyRequestDTO p = PropertyRequestDTO.builder()
                .prop_name("Casa")
                .prop_district("Moema")
                .build();
        return p;
    }

    public static PropertyResponseDTO createResponseProperty() {

        PropertyResponseDTO p = PropertyResponseDTO.builder()
                .prop_name("Casa")
                .prop_district("Moema")
                .totalMeters(75.0)
                .valueProperty(607500.0)
                .biggestRoom(createNewRoomDTO().get(1))
                .rooms(createNewRoomDTO())
                .build();
        return p;
    }

    public static PropertyResponseDTO createResponsePropertyEmpty() {

        PropertyResponseDTO p = PropertyResponseDTO.builder()
                .prop_name("Casa")
                .prop_district("Moema")
                .totalMeters(0.0)
                .valueProperty(0.0)
                .biggestRoom(null)
                .rooms(new ArrayList<RoomResponseDTO>())
                .build();
        return p;
    }


}
