package com.example.demo.repository;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndIsActiveTrue(String username);

    Optional<User> findByIdAndIsActiveTrue(Integer id);

    void deleteOneByUsername(String username);

    @Query("select u from User u join fetch u.roles where u.isActive = true")
    List<User> read();

    User findByUsernameAndIsActiveTrueAndRolesIn(String username, List<Role> rolesBellowMine);

    Page<User> findByIsActiveTrueAndRolesIn(List<Role> rolesBelow, Pageable pageable);

    @Query("select u from User u left join fetch u.realEstates where u.id=:id and u.isActive=true")
    Optional<User> readWithRealEstates(Integer id);
}

