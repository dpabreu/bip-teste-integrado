package com.example.backend.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.backend.model.Beneficio;
import com.example.backend.response.BeneficioResponse;
import com.example.backend.service.BeneficioService;

@SpringBootTest
@AutoConfigureMockMvc
public class BeneficioControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BeneficioService beneficioService;
	
	@Test
	public void testListarBeneficios() throws Exception {
		
		Beneficio beneficio1 = new Beneficio(1L, "Beneficio A", "Descrição A", new BigDecimal(100.00));
		Beneficio beneficio2 = new Beneficio(2L, "Beneficio B", "Descrição B", new BigDecimal(1000.00));
		
		BeneficioResponse resp1 = new BeneficioResponse(beneficio1);
		BeneficioResponse resp2 = new BeneficioResponse(beneficio2);
		
		List<BeneficioResponse> mockBeneficios = Arrays.asList(resp1, resp2);
        when(beneficioService.readAll()).thenReturn(mockBeneficios);		
	    
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/beneficios/listaBeneficios")
	           .accept(MediaType.APPLICATION_JSON))
	           .andExpect(status().isOk())
	           .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	           .andExpect(jsonPath("$[0].id", is(1)))
               .andExpect(jsonPath("$[0].nome", is("Beneficio A")))
               .andExpect(jsonPath("$[1].id", is(2)))
               .andExpect(jsonPath("$[1].nome", is("Beneficio B")));
	}
	
}
