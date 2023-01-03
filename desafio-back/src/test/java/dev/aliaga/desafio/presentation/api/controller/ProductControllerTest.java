package dev.aliaga.desafio.presentation.api.controller;

import dev.aliaga.desafio.domain.port.primary.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static dev.aliaga.desafio.util.DataUtils.buildProductEntityMarca1;
import static dev.aliaga.desafio.util.DataUtils.buildProductEntityMarca6;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    private ProductService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetAllProducts() throws Exception {

        willReturn(List.of(buildProductEntityMarca6(), buildProductEntityMarca1())).given(service).getAll();

        MockHttpServletRequestBuilder controllerRequest = get("/api/v1/products")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions resultActions = mockMvc.perform(controllerRequest).andExpect(status().isOk());
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());


        resultActions
                .andExpect(jsonPath("$.[0].description").value("Laptop Gamer YPS Bell"))
                .andExpect(jsonPath("$.[0].price").value(180000L))
                .andExpect(jsonPath("$.[0].brand").value("Marca6"))
                .andExpect(jsonPath("$.[0].id").value(18))
                .andExpect(jsonPath("$.[0].image").value("www.lider.cl/catalogo/images/catalogo_no_photo.jpg"))
                .andExpect(jsonPath("$.[0]._id").value("624b943175d8be9230d537c8"))
                .andExpect(jsonPath("$.[1].description").value("Televisión 54''"))
                .andExpect(jsonPath("$.[1].price").value(80000L))
                .andExpect(jsonPath("$.[1].brand").value("Marca1"))
                .andExpect(jsonPath("$.[1].id").value(1))
                .andExpect(jsonPath("$.[1].image").value("www.lider.cl/catalogo/images/catalogo_no_photo.jpg"))
                .andExpect(jsonPath("$.[1]._id").value("624b943175d8be9230d537a6"));


    }
}