package com.mercadolivre.desafioquality.unit_test;


import com.mercadolivre.desafioquality.entity.Property;
import com.mercadolivre.desafioquality.entity.Room;
import com.mercadolivre.desafioquality.entity.dto.request.PropertyRequestDTO;
import com.mercadolivre.desafioquality.entity.dto.response.PropertyResponseDTO;
import com.mercadolivre.desafioquality.entity.dto.response.RoomResponseDTO;
import com.mercadolivre.desafioquality.exception.error.NotFoundException;
import com.mercadolivre.desafioquality.interface_adapters.repository.DistrictRepository;
import com.mercadolivre.desafioquality.usecase.PropertyService;
import com.mercadolivre.desafioquality.usecase.RoomService;
import com.mercadolivre.desafioquality.utils.PropertyUtilsTest;
import com.mercadolivre.desafioquality.utils.RoomUtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class RoomUnitTest {

    @InjectMocks
    RoomService roomService;

    @InjectMocks
    PropertyService propertyService;

    @InjectMocks
    DistrictRepository districtRepository;

    Property property;
    Room room;

    @BeforeEach
    void setUp(){
        this.property = PropertyUtilsTest.createNewProperty();
        this.propertyService = new PropertyService(districtRepository);
        this.room = RoomUtilsTest.createNewRoom();
    }

    @Test
    @DisplayName("A - Verificar o método de calcular metros quadrados do comodo")
    public void calculateSquareMetersRoomTest(){
        Room r = RoomUtilsTest.createNewRoom();
        RoomResponseDTO responseDTO = RoomUtilsTest.createNewRoomResponseDTO();
        Property p = PropertyUtilsTest.createNewProperty();
        //RoomService mock = Mockito.mock(RoomService.class);
        assertEquals(responseDTO.getSquareMetersRoom(), propertyService.calculateMetersRoom(r));
    }

}
