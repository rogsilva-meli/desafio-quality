package com.mercadolivre.desafioquality.utils;

import com.mercadolivre.desafioquality.entity.Property;
import com.mercadolivre.desafioquality.entity.Room;
import com.mercadolivre.desafioquality.entity.dto.request.PropertyRequestDTO;
import com.mercadolivre.desafioquality.entity.dto.request.RoomRequestDTO;
import com.mercadolivre.desafioquality.entity.dto.response.PropertyResponseDTO;
import com.mercadolivre.desafioquality.entity.dto.response.RoomResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class RoomUtilsTest {

    public static RoomResponseDTO createNewRoomResponseDTO() {
        RoomResponseDTO roomResponseDTO = RoomResponseDTO.builder()
                .room_name("Sala")
                .room_width(5.0)
                .room_length(2.0)
                .squareMetersRoom(10.0)
                .build();

        return roomResponseDTO;
    }

    public static Room createNewRoom() {
        Room room = Room.builder()
                .room_name("Sala")
                .room_width(5.0)
                .room_length(2.0)
                .property("Casa")
                .build();

        return room;
    }

    public static RoomRequestDTO createNewRoomRequestDTO() {
        RoomRequestDTO room = RoomRequestDTO.builder()
                .room_name("Sala")
                .room_width(5.0)
                .room_length(2.0)
                .property("Casa")
                .build();

        return room;
    }

}
