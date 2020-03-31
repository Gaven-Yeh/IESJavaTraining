package com.gaven.mission5.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaven.mission5.business.service.ProductService;
import com.gaven.mission5.data.assembler.ProductModelAssembler;
import com.gaven.mission5.data.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {ProductService.class, ProductController.class})
@WebMvcTest(ProductController.class)
@ExtendWith(SpringExtension.class)
public class ProductControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    static ObjectMapper mapper = new ObjectMapper();

    @MockBean
    ProductService productService;

    @Test
    public void newProductTest() throws Exception {
        Product product = new Product((long) 5, "Classical", "Guitar", "Cordoba", 3);
        EntityModel<Product> entityModel = new EntityModel<>(product,
                linkTo(methodOn(ProductController.class).one(product.getProduct_id())).withSelfRel(),
                linkTo(methodOn(ProductController.class).all()).withRel("products"));
        ResponseEntity responseEntity = ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel.getContent());

        Mockito.when(productService.newProduct(product)).thenReturn(responseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product)))
                .andExpect(status().isCreated()).andReturn();
    }
}
