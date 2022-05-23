package com.example.demo.controller;

import com.example.demo.dto.CreateRealEstateRequest;
import com.example.demo.dto.ReadRealEstateResponse;
import com.example.demo.model.RealEstate;
import com.example.demo.service.RealEstateService;
import com.example.demo.support.EntityConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/real-estates")
@RequiredArgsConstructor
public class RealEstateController {
    private final RealEstateService realEstateService;
    private final EntityConverter<RealEstate, ReadRealEstateResponse> toResponse;

    @PreAuthorize("hasAuthority('CREATE_REAL_ESTATE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReadRealEstateResponse create(@Valid @RequestBody CreateRealEstateRequest request) {
        var realEstate = realEstateService.create(request);
        return toResponse.convert(realEstate);
    }

    @PreAuthorize("hasAuthority('ADD_REAL_ESTATE_TO_USER')")
    @GetMapping
    public List<ReadRealEstateResponse> read() {
        var realEstates = realEstateService.read();
        return toResponse.convert(realEstates);
    }
}
