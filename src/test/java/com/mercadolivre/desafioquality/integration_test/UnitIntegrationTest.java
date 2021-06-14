package com.mercadolivre.desafioquality.integration_test;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolivre.desafioquality.entity.Property;
import com.mercadolivre.desafioquality.entity.Room;
import com.mercadolivre.desafioquality.entity.dto.request.PropertyRequestDTO;
import com.mercadolivre.desafioquality.entity.dto.request.RoomRequestDTO;
import com.mercadolivre.desafioquality.entity.dto.response.PropertyResponseDTO;
import com.mercadolivre.desafioquality.entity.dto.response.RoomResponseDTO;
import com.mercadolivre.desafioquality.interface_adapters.controller.PropertyController;
import com.mercadolivre.desafioquality.interface_adapters.controller.RoomController;
import com.mercadolivre.desafioquality.usecase.PropertyService;
import com.mercadolivre.desafioquality.usecase.RoomService;
import com.mercadolivre.desafioquality.utils.PropertyUtilsTest;
import com.mercadolivre.desafioquality.utils.RoomUtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")                             // Habilita profile de teste em controllers
@WebMvcTest(controllers = RoomController.class)     // Habilita teste em controllers
@AutoConfigureMockMvc                               // Configura um objeto
public class UnitIntegrationTest {

    @MockBean
    private RoomService roomService;

    @MockBean
    private PropertyService propertyService;

    // Criar a rota da API
    static String ROOM_API = "/rooms";

    // Injeta a dependencia de Mock no Contexto
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    Property property;
    Room room;

    @BeforeEach
    void setUp(){
        this.property = PropertyUtilsTest.createNewProperty();
        this.room = RoomUtilsTest.createNewRoom();
    }


    @Test
    @DisplayName("A - Deve criar um comodo com sucesso")
    public void createRoomTest() throws Exception {

        RoomRequestDTO requestDTO = RoomUtilsTest.createNewRoomRequestDTO();
        RoomResponseDTO responseDTO = RoomUtilsTest.createNewRoomResponseDTO();

        BDDMockito.given(roomService.saveRoom(Mockito.any(RoomRequestDTO.class))).willReturn(responseDTO);

        // Cria objeto Json
        byte[] json = objectMapper.writeValueAsBytes(requestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ROOM_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("room_name").value(requestDTO.getRoom_name()))
                .andExpect(jsonPath("room_width").value(requestDTO.getRoom_width()))
                .andExpect(jsonPath("room_length").value(requestDTO.getRoom_length()))
                .andExpect(jsonPath("squareMetersRoom").value(10.0));
    }

    @Test
    @DisplayName("B - Deve retornar erro ao tentar criar um comodo com atributos invalidos")
    public void createInvalidPropertyTest() throws Exception {

        RoomRequestDTO requestDTO = RoomUtilsTest.createNewRoomRequestDTO();
        requestDTO.setRoom_name("quarto");
        //requestDTO.setRoom_name("Quarto pppppppppppppppppppsssssssssssssssssssssssssssssssssssssssssssssssssss");
        //requestDTO.setRoom_width(26.0);
        //requestDTO.setRoom_length(36.0);

        // Cria objeto Json
        String json = new ObjectMapper().writeValueAsString(requestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ROOM_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isBadRequest());
    }


}




