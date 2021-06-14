package com.mercadolivre.desafioquality.integration_test;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolivre.desafioquality.entity.Property;
import com.mercadolivre.desafioquality.entity.dto.request.PropertyRequestDTO;
import com.mercadolivre.desafioquality.entity.dto.response.PropertyResponseDTO;
import com.mercadolivre.desafioquality.interface_adapters.controller.PropertyController;
import com.mercadolivre.desafioquality.usecase.PropertyService;
import com.mercadolivre.desafioquality.utils.PropertyUtilsTest;
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
@WebMvcTest(controllers = PropertyController.class)     // Habilita teste em controllers
@AutoConfigureMockMvc                               // Configura um objeto
public class PropertyIntegrationTest {

    @MockBean
    private PropertyService propertyService;

    // Criar a rota da API
    static String PROPERTY_API = "/properties";

    // Injeta a dependencia de Mock no Contexto
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    Property property;

    @BeforeEach
    void setUp(){
        this.property = PropertyUtilsTest.createNewProperty();
    }


    @Test
    @DisplayName("A - Deve criar um livro com sucesso")
    public void createPropertyTest() throws Exception {

        PropertyRequestDTO requestDTO = PropertyUtilsTest.createNewPropertyRequestDTO();
        PropertyResponseDTO responseDTO = PropertyUtilsTest.createResponseProperty();

        BDDMockito.given(propertyService.saveProperty(Mockito.any(PropertyRequestDTO.class))).willReturn(responseDTO);

        // Cria objeto Json
        String json = new ObjectMapper().writeValueAsString(requestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PROPERTY_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("prop_name").value(requestDTO.getProp_name()))
                .andExpect(jsonPath("prop_district").value(requestDTO.getProp_district()));
    }

    @Test
    @DisplayName("B - Deve retornar erro ao tentar criar uma propriedade com atributos invalidos")
    public void createInvalidPropertyTest() throws Exception {

        PropertyRequestDTO requestDTO = PropertyUtilsTest.createNewPropertyRequestDTO();
        requestDTO.setProp_name("casa");
        //requestDTO.setProp_name("Casa pppppppppppppppppppsssssssssssssssssssssssssssssssssssssssssssssssssss");
        //requestDTO.setProp_district("qualquer");
        //requestDTO.setProp_district("Moema mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmnnnnnnnnnnnnn");

        // Cria objeto Json
        String json = new ObjectMapper().writeValueAsString(requestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PROPERTY_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("C - Deve retornar uma lista de propriedades")
    public void getAllPropertiesTest() throws Exception {

        PropertyResponseDTO responseDTO = PropertyUtilsTest.createResponseProperty();

        List<PropertyResponseDTO> listResponse = new ArrayList<>();
        listResponse.add(responseDTO);

        BDDMockito.given(propertyService.getAllProperties()).willReturn(List.of(responseDTO));

        // Cria objeto Json
        byte[] json = objectMapper.writeValueAsBytes(listResponse);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PROPERTY_API)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].prop_name").value(responseDTO.getProp_name()))
                .andExpect(jsonPath("$.[0].prop_district").value(responseDTO.getProp_district()))
                .andExpect(jsonPath("$.[0].totalMeters").value(75.0))
                .andExpect(jsonPath("$.[0].valueProperty").value(607500.0))
                .andExpect(jsonPath("$.[0].biggestRoom.room_name").value(responseDTO.getBiggestRoom().getRoom_name()))
                .andExpect(jsonPath("$.[0].biggestRoom.room_width").value(responseDTO.getBiggestRoom().getRoom_width()))
                .andExpect(jsonPath("$.[0].biggestRoom.room_length").value(responseDTO.getBiggestRoom().getRoom_length()))
                .andExpect(jsonPath("$.[0].biggestRoom.squareMetersRoom").value(responseDTO.getBiggestRoom().getSquareMetersRoom()))
                .andExpect(jsonPath("$.[0].rooms.[0].room_name").value(responseDTO.getRooms().get(0).getRoom_name()))
                .andExpect(jsonPath("$.[0].rooms.[0].room_width").value(responseDTO.getRooms().get(0).getRoom_width()))
                .andExpect(jsonPath("$.[0].rooms.[0].room_length").value(responseDTO.getRooms().get(0).getRoom_length()))
                .andExpect(jsonPath("$.[0].rooms.[0].squareMetersRoom").value(responseDTO.getRooms().get(0).getSquareMetersRoom()));
    }

}




