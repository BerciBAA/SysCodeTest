package com.test.address.service;

import com.test.address.AddressApplication;
import com.test.address.dto.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService{

    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Override
    public Address getAddress() {
        Address address = Address.builder()
                .id(UUID.randomUUID())
                .address("777 Brockton Avenue, Abington MA 2351")
                .build();

        logger.info("Generated address ID: {}", address.getId());
        return address;
    }
}
