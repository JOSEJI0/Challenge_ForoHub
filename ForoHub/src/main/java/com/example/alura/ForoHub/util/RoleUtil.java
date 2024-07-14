package com.example.alura.ForoHub.util;

import com.example.alura.ForoHub.model.authorization.Permission;
import com.example.alura.ForoHub.model.authorization.Role;
import com.example.alura.ForoHub.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import paghttp.request.RoleRequest;
import paghttp.response.RoleResponse;

import java.util.ArrayList;
import java.util.List;

public class RoleUtil {

    @Autowired
    private static PermissionRepository permissionRepository;

    public static RoleResponse toRoleResponse(Role role){
        return RoleResponse.builder()
                .name(role.getName())
                .permissions(
                        role.getPermissions().stream()
                                .map(PermissionUtil::toPermissionResponse)
                                .toList()
                )
                .build();
    }

    public static Role toRole(RoleRequest roleRequest){
        List<Permission> permissions = new ArrayList<>();
        roleRequest.getPermissions().forEach(permissionRequest ->
                permissionRepository.findByNameAndEnable(permissionRequest.getName(),true).ifPresent(permissions::add)
        );
        return Role.builder()
                .name(roleRequest.getName())
                .permissions(permissions)
                .build();
    }
}
