package com.example.demo.controller;

import com.example.demo.dto.ReadRealEstateResponse;
import com.example.demo.model.RealEstate;
import com.example.demo.service.UserService;
import com.example.demo.support.EntityConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EntityConverter<RealEstate, ReadRealEstateResponse> toRealEstateResponse;

    //TODO: PreAuthorizer
    @GetMapping("my-objects")
    public List<ReadRealEstateResponse> getMyRealEstates(Principal principal) {
        var realEstates = userService.getMyRealEstates(principal.getName());
        return toRealEstateResponse.convert(realEstates);
    }
}
