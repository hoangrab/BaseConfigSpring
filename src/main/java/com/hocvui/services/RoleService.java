package com.hocvui.services;

import com.hocvui.entities.Role;
import com.hocvui.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepo roleRepo;
    @Autowired
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public Optional<Role> findByName(String name) {
        return roleRepo.findByName(name);
    }

    public void save(Role role) {
        roleRepo.save(role);
    }
}
