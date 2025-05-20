package com.erico.desafio.itau.transaction.controller;

import com.erico.desafio.itau.transaction.TransactionService;
import com.erico.desafio.itau.transaction.dto.TransactionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.binder.http.HttpRequestTags;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    TransactionService transactionService;

    @Test
    @DisplayName("Dado um DTO de transações deve retornar 201 Created ao criar a transação")
    void givenDto_whenCreateTransaction_thenReturn201Created() throws Exception {
        TransactionRequest request = new TransactionRequest(BigDecimal.valueOf(200.00), OffsetDateTime.now());
        Mockito.when(transactionService.addTransaction(Mockito.eq(request))).thenReturn(Mockito.any(UUID.class));

        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/transacao").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.LOCATION))
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
}