package com.deiz0n.makeorder.api.controllers.exceptions;

import com.deiz0n.makeorder.domain.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HandleControllerTest {

    public static final UUID ID = UUID.randomUUID();
    public static final Instant DATA = Instant.now();

    @InjectMocks
    private HandleController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenHandleResourceNotFoundExceptionThenReturnNotFound() {
        ResponseEntity<Error> response = controller.handleResourceNotFoundException(new ResourceNotFoundException(String.format("O pedido com o id: %s não foi encontrado", ID.toString())),
                                                                                    new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody().getData());

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Error.class, response.getBody().getClass());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());

        assertEquals("Recurso não encontrado", response.getBody().getTitle());
        assertEquals(String.format("O pedido com o id: %s não foi encontrado", ID.toString()), response.getBody().getDetail());
    }
}