package com.test.profile;

import com.test.profile.dto.AddressDto;
import com.test.profile.handler.exceptions.RemoteServiceException;
import com.test.profile.service.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AddressServiceImplTest {
    @Mock
    private WebClient webClient;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    void getAddressShouldReturnAddress() {
        AddressDto expectedAddress = new AddressDto(UUID.randomUUID(), "123 Test Street");
        when(responseSpec.toEntity(AddressDto.class)).thenReturn(Mono.just(ResponseEntity.ok(expectedAddress)));

        AddressDto actualAddress = addressService.getAddress();

        assertNotNull(actualAddress);
        assertEquals(expectedAddress.getId(), actualAddress.getId());
        assertEquals(expectedAddress.getAddress(), actualAddress.getAddress());
    }

    @Test
    void getAddressShouldThrowRemoteServiceExceptionWhenResponseIsEmpty() {
        when(responseSpec.toEntity(AddressDto.class)).thenReturn(Mono.empty());

        assertThrows(RemoteServiceException.class, () -> addressService.getAddress());
    }

}
