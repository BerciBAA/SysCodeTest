package com.test.profile.service;

import com.test.profile.dto.AddressDto;
import com.test.profile.handler.exceptions.RemoteServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;



@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final WebClient webClient;

    public AddressDto getAddress(){
        logger.info("Requesting address data from remote service");
        ResponseEntity<AddressDto> response = webClient.get()
                .uri("get-address")
                .retrieve()
                .toEntity(AddressDto.class)
                .block();

        if (response == null || response.getBody() == null) {
            logger.error("Failed to retrieve data from the remote service");
            throw new RemoteServiceException("Failed to retrieve the data from the remote service.");
        }
        logger.info("Successfully received response from remote service");
        return response.getBody();
    }
}
