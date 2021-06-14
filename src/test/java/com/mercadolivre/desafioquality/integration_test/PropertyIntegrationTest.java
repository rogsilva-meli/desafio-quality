package com.mercadolivre.desafioquality.integration_test;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolivre.desafioquality.entity.dto.request.PropertyRequestDTO;
import com.mercadolivre.desafioquality.entity.dto.response.PropertyResponseDTO;
import com.mercadolivre.desafioquality.interface_adapters.controller.PropertyController;
import com.mercadolivre.desafioquality.usecase.PropertyService;
import com.mercadolivre.desafioquality.utils.PropertyUtilsTest;
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

        PropertyRequestDTO requestDTO = PropertyUtilsTest.createNewPropertyRequestDTO();
        PropertyResponseDTO responseDTO = PropertyUtilsTest.createResponseProperty();
        List<PropertyResponseDTO> listResponse = new ArrayList<>();
        listResponse.add(responseDTO);

        BDDMockito.given(propertyService.getAllProperties()).willReturn(List.of(responseDTO));

        // Cria objeto Json
        String json = new ObjectMapper().writeValueAsString(requestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PROPERTY_API)
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform(request)
                .andExpect(status().isOk());
    }


}




