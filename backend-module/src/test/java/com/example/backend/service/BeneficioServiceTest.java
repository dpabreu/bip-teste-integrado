package com.example.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.backend.model.Beneficio;
import com.example.backend.repository.BeneficioRepository;
import com.example.backend.request.TransferirBeneficioRequest;
import com.example.backend.response.Response;

/**
 * Classe de testes unitários do serviço {@link BeneficioService}.
 *
 * <p>
 * Esta classe tem como objetivo validar o comportamento do método
 * {@code transfer(TransferirBeneficioRequest)}, responsável pela
 * transferência de valores entre benefícios, garantindo:
 * </p>
 *
 * <ul>
 *   <li>Execução correta da transferência em cenário de sucesso</li>
 *   <li>Validação de saldo insuficiente</li>
 *   <li>Validação de existência dos benefícios de origem e destino</li>
 *   <li>Garantia de que as operações de persistência sejam executadas corretamente</li>
 * </ul>
 *
 * <p>
 * Os testes utilizam {@link org.mockito.Mockito} para simular o acesso
 * à camada de persistência, mantendo o teste isolado da infraestrutura.
 * </p>
 */
@SpringBootTest
@AutoConfigureMockMvc
class BeneficioServiceTest {

	@InjectMocks
    private BeneficioService beneficioService;

    @Mock
    private BeneficioRepository beneficioRepository;

    @Test
    void testaTransferenciaComSucesso() throws Exception {
        Beneficio from = new Beneficio();
        from.setId(1L);
        from.setValor(new BigDecimal("100.00"));

        Beneficio to = new Beneficio();
        to.setId(2L);
        to.setValor(new BigDecimal("50.00"));

        TransferirBeneficioRequest request = new TransferirBeneficioRequest();
        request.setFromId(1L);
        request.setToId(2L);
        request.setQuantia("30,00");

        when(beneficioRepository.findById(1L))
                .thenReturn(Optional.of(from));
        when(beneficioRepository.findById(2L))
                .thenReturn(Optional.of(to));

        when(beneficioRepository.save(any(Beneficio.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Response response = beneficioService.transfer(request);

        assertNotNull(response);
        assertEquals(1, response.getCodigo());
        assertEquals("Transferência realizada com sucesso.", response.getMsg());

        assertEquals(new BigDecimal("70.00"), from.getValor());
        assertEquals(new BigDecimal("80.00"), to.getValor());
        
        verify(beneficioRepository, times(1)).save(from);
        verify(beneficioRepository, times(1)).save(to);        
    }
    
    @Test
    void testaSaldoInsuficiente() {
        Beneficio from = new Beneficio();
        from.setId(1L);
        from.setValor(new BigDecimal("10.00"));

        Beneficio to = new Beneficio();
        to.setId(2L);
        to.setValor(new BigDecimal("50.00"));

        TransferirBeneficioRequest request = new TransferirBeneficioRequest();
        request.setFromId(1L);
        request.setToId(2L);
        request.setQuantia("30,00");

        when(beneficioRepository.findById(1L))
                .thenReturn(Optional.of(from));
        when(beneficioRepository.findById(2L))
                .thenReturn(Optional.of(to));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> beneficioService.transfer(request)
        );

        assertEquals("Saldo insuficiente.", exception.getMessage());

        verify(beneficioRepository, never()).save(any());
    } 
    
    @Test
    void testaBeneficioNaoEncontrado() {
        TransferirBeneficioRequest request = new TransferirBeneficioRequest();
        request.setFromId(1L);

        when(beneficioRepository.findById(1L))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> beneficioService.transfer(request)
        );

        assertEquals("Benefício origem não encontrado", exception.getMessage());
    }    
}
