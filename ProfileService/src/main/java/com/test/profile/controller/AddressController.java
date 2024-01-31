package com.test.profile.controller;

import com.test.profile.dto.AddressDto;
import com.test.profile.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
@Tag(name = "Address", description = "API endpoints related to Address operations")
public class AddressController {
    private final AddressService addressService;

    @GetMapping("get-address")
    @Operation(summary = "Get Address", description = "Retrieves the address details")
    @ApiResponse(responseCode = "200", description = "Address retrieved successfully", content = @Content(schema = @Schema(implementation = AddressDto.class)))
    public ResponseEntity<AddressDto> getAddress(){
        return ResponseEntity.ok(addressService.getAddress());
    }
}
