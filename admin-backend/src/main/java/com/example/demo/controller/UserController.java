package com.example.demo.controller;

import com.example.demo.dto.ReadRealEstateResponse;
import com.example.demo.dto.UpdateUsersRealEstates;
import com.example.demo.dto.UserDetailsResponse;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.RealEstate;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.support.EntityConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EntityConverter<RealEstate, ReadRealEstateResponse> toRealEstateResponse;
    private final EntityConverter<User, UserResponse> toUserResponse;
    private final EntityConverter<User, UserDetailsResponse> toUserDetailsResponse;

    @PreAuthorize("hasAuthority('READ_MY_REAL_ESTATES')")
    @GetMapping("my-real-estates")
    public List<ReadRealEstateResponse> getMyRealEstates(Principal principal) {
        List<RealEstate> realEstates = userService.getMyRealEstates(principal.getName());
        return toRealEstateResponse.convert(realEstates);
    }

    @PreAuthorize("hasAuthority('ADD_REAL_ESTATE_TO_USER')")
    @GetMapping("/{id}/details")
    public UserDetailsResponse readUserDetails(@PathVariable("id") Integer id) {
        User user = userService.getUserDetails(id);
        return toUserDetailsResponse.convert(user);
    }

    @PreAuthorize("hasAuthority('ADD_REAL_ESTATE_TO_USER')")
    @PutMapping("/{id}/real-estates")
    public void modifyUsersRealEstates(@PathVariable("id") Integer id, @Valid @RequestBody UpdateUsersRealEstates usersRealEstates) {
        userService.updateRealEstates(id, usersRealEstates);
    }

    @PreAuthorize("hasAuthority('CREATE_REAL_ESTATE')")
    @GetMapping("bellow-my-role")
    public List<UserResponse> getUsersBellowMyRole(Principal principal) {
        List<User> users = userService.getUsersBellowMyRole(principal.getName());
        return toUserResponse.convert(users);
    }

    @PreAuthorize("hasAuthority('READ_USERS')")
    @GetMapping("bellow-my-role-paginated")
    public Page<UserResponse> getUsersBellowMyRolePaginated(Principal principal, @PageableDefault Pageable pageable) {
        Page<User> users = userService.getUsersBellowMyRolePaginated(principal.getName(), pageable);
        return users.map(toUserResponse::convert);
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
