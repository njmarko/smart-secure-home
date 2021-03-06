package com.example.demo.service.impl;

import java.util.List;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Role findById(Long id) {
    return this.roleRepository.findById(id).orElse(null);
  }

  @Override
  public List<Role> findByName(String name) {
    return this.roleRepository.findByName(name);
  }

  @Override
  public Role findByNameAndPriorityLessThanEqual(String name, Integer priority) {
    return this.roleRepository.findByNameAndPriorityLessThanEqual(name, priority);
  }

  @Override
  public List<Role> getRolesBellowPriority(Integer myRoleLevel) {
    return roleRepository.findBellowPriority(myRoleLevel);
  }


}
