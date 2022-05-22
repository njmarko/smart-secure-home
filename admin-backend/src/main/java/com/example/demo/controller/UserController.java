package com.example.demo.controller;

import com.example.demo.dto.ReadRealEstateResponse;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.RealEstate;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.support.EntityConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final EntityConverter<User, UserResponse> toUserResponse;

    @PreAuthorize("hasAuthority('READ_MY_REAL_ESTATES')")
    @GetMapping("my-real-estates")
    public List<ReadRealEstateResponse> getMyRealEstates(Principal principal) {
        var realEstates = userService.getMyRealEstates(principal.getName());
        return toRealEstateResponse.convert(realEstates);
    }

    @PreAuthorize("hasAuthority('CREATE_REAL_ESTATE')")
    @GetMapping("bellow-my-role")
    public List<UserResponse> getUsersBellowMyRole(Principal principal) {
        var users = userService.getUsersBellowMyRole(principal.getName());
        return toUserResponse.convert(users);
    }

    @PreAuthorize("hasAuthority('MODIFY_USER_ROLE')")
    @PostMapping("{username}/modifyRole/{roleName}")
    public void modifyRole(@PathVariable String roleName, @PathVariable String username, Principal principal){
        userService.modifyRole(username, roleName, principal.getName());
    }

    @PreAuthorize("hasAuthority('DELETE_USERS')")
    @DeleteMapping("{username}")
    public void deleteUser(@PathVariable String username, Principal principal){
        userService.deleteUser(username, principal.getName());
    }
}
