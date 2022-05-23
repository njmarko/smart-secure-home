package com.example.demo.service;

import com.example.demo.dto.UpdateUsersRealEstates;
import com.example.demo.dto.UserRequest;
import com.example.demo.model.RealEstate;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User findById(Integer id);
    User findByUsername(String username);
    List<User> findAll ();
	User save(UserRequest userRequest);
    List<RealEstate> getMyRealEstates(String username);
    void deleteUser(String username, String issuerName);
    void modifyRole(String username, String roleName, String issuerName);
    List<User> getUsersBellowMyRole(String username);

    void tryLockAccount(String username);

    Page<User> getUsersBellowMyRolePaginated(String name, Pageable pageable);

    void updateRealEstates(Integer userId, UpdateUsersRealEstates usersRealEstates);

    User getUserDetails(Integer id);
}
