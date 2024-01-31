package com.test.address.controller;

import com.test.address.dto.Address;
import com.test.address.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/get-address")
    @Operation(summary = "Get Address", description = "Retrieves the address details.")
    @ApiResponse(responseCode = "200", description = "Address retrieved successfully", content = @Content(schema = @Schema(implementation = Address.class)))
    public ResponseEntity<Address> getAddress(){
        return ResponseEntity.ok(addressService.getAddress());
    }
}
