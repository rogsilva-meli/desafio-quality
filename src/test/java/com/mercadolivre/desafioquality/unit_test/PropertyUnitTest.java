package com.mercadolivre.desafioquality.unit_test;


import com.mercadolivre.desafioquality.entity.Property;
import com.mercadolivre.desafioquality.entity.Room;
import com.mercadolivre.desafioquality.entity.dto.request.PropertyRequestDTO;
import com.mercadolivre.desafioquality.entity.dto.response.PropertyResponseDTO;
import com.mercadolivre.desafioquality.entity.dto.response.RoomResponseDTO;
import com.mercadolivre.desafioquality.exception.error.NotFoundException;
import com.mercadolivre.desafioquality.interface_adapters.repository.DistrictRepository;
import com.mercadolivre.desafioquality.usecase.PropertyService;
import com.mercadolivre.desafioquality.utils.PropertyUtilsTest;
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
public class PropertyUnitTest {

    @InjectMocks
    PropertyService propertyService;

    @InjectMocks
    DistrictRepository districtRepository;

    Property property;

    @BeforeEach
    void setUp(){
        this.property = PropertyUtilsTest.createNewProperty();
        this.propertyService = new PropertyService(districtRepository);
    }

    @Test
    @DisplayName("A - Verificar o método de calcular metros quadrados")
    public void calculateSquareMetersTest(){
        Property p = PropertyUtilsTest.createNewProperty();
        PropertyService mock = Mockito.mock(PropertyService.class);
        assertEquals(p.getTotalMeters(), propertyService.calculateMeters(p.getRooms()));
    }

    @Test
    @DisplayName("B - Verificar se o bairro de entrada existe no repositório de bairros.")
    public void verifyIfExistsDistrictsTest(){
        Property p = PropertyUtilsTest.createNewProperty();

        String district = p.getProp_district();

        assertTrue(propertyService.verifyDistrict(district));
    }

    @Test
    @DisplayName("C - Deve lançar exceção ao tentar salvar com um bairro que não existe no repositório de bairros.")
    public void verifyIfInvalidDistrictsTest(){
        Property p = PropertyUtilsTest.createNewProperty();
        String district = "Paraguai";
        assertThrows(NotFoundException.class, ()-> propertyService.verifyDistrict(district));
    }

    @Test
    @DisplayName("D - Verificar se a maior sala foi realmente devolvida.")
    public void verifyIfReturnBiggestRoomTest(){
        Property p = PropertyUtilsTest.createNewProperty();

        List<RoomResponseDTO> list = PropertyUtilsTest.createNewRoomDTO();

        assertEquals(list.get(1), propertyService.biggestRoom(p));
    }

    @Test
    @DisplayName("F - Verificar se a propriedade cadastrada é a mesma devolvida.")
    public void savePropertyTest(){
       PropertyRequestDTO requestDTO = PropertyUtilsTest.createNewPropertyRequestDTO();
       PropertyResponseDTO responseDTO = PropertyUtilsTest.createResponsePropertyEmpty();

       PropertyResponseDTO pSaved = propertyService.saveProperty(requestDTO);

       assertEquals(pSaved, responseDTO);
    }

    @Test
    @DisplayName("G - Verificar a quantidade de metro quadrado por comodo.")
    public void squareMetersRoomTest(){
        List<Room> rooms = PropertyUtilsTest.createNewProperty().getRooms();
        Room roomA = rooms.get(0);
        Room roomB = rooms.get(1);

        assertEquals(25.0, propertyService.calculateMetersRoom(roomA));
        assertEquals(50, propertyService.calculateMetersRoom(roomB));
    }

    @Test
    @DisplayName("H - Verificar a quantidade total de metro quadrado propriedade.")
    public void squareMetersPropertyTest(){
        List<Room> list = PropertyUtilsTest.createNewProperty().getRooms();

        assertEquals(75.0, propertyService.calculateMeters(list));
    }

    @Test
    @DisplayName("I - Verificar o preço total do metro quadrado da propriedade.")
    public void priceSquareMetersPropertyTest(){
        Property p = PropertyUtilsTest.createNewProperty();
        assertEquals(607500.0, propertyService.propertyValue(p));
    }








}
