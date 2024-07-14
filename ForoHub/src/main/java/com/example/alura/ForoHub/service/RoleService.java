package com.example.alura.ForoHub.service;

import com.example.alura.ForoHub.exceptions.RoleException;
import com.example.alura.ForoHub.model.authorization.Permission;
import com.example.alura.ForoHub.model.authorization.Role;
import com.example.alura.ForoHub.repository.PermissionRepository;
import com.example.alura.ForoHub.repository.RoleRepository;
import com.example.alura.ForoHub.util.RoleUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paghttp.request.RoleRequest;
import paghttp.response.RoleResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional
    public RoleResponse createRole(RoleRequest roleRequest) {
        List<Permission> permissions = new ArrayList<>();
        roleRequest.getPermissions().forEach(permissionRequest ->
                permissionRepository.findByNameAndEnable(permissionRequest.getName(), true).ifPresent(permissions::add)
        );
        Role role = roleRepository.save(Role.builder()
                .name(roleRequest.getName())
                .permissions(permissions)
                .build());
        return RoleUtil.toRoleResponse(role);
    }

    @Transactional
    public RoleResponse updateRole(String id, RoleRequest roleRequest) throws RoleException {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new RoleException("Role not found")
        );
        if (!role.isEnable())
            throw new RoleException("Role is not enable");
        List<Permission> permissions = new ArrayList<>();
        roleRequest.getPermissions().forEach(permissionRequest ->
                permissionRepository.findByNameAndEnable(permissionRequest.getName(), true).ifPresent(permissions::add)
        );
        role.setName(roleRequest.getName());
        role.setPermissions(permissions);
        roleRepository.save(role);
        return RoleUtil.toRoleResponse(role);
    }

    @Transactional
    public RoleResponse deleteRole(String id) throws RoleException {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new RoleException("Role not found")
        );
        if (!role.isEnable())
            throw new RoleException("Role is not enable");
        role.setEnable(false);
        roleRepository.save(role);
        return RoleUtil.toRoleResponse(role);
    }

    public RoleResponse getRoleByID(String id) throws RoleException {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new RoleException("Role not found")
        );
        if (!role.isEnable())
            throw new RoleException("Role is not enable");
        return RoleUtil.toRoleResponse(role);
    }

    public List<RoleResponse> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleResponse> roleResponses = new ArrayList<>();
        roles.forEach(role -> {
            if (role.isEnable())
                roleResponses.add(RoleUtil.toRoleResponse(role));
        });
        return roleResponses;
    }
}