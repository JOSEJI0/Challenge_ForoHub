package com.example.alura.ForoHub.util;

import com.example.alura.ForoHub.model.authorization.Permission;
import paghttp.request.PermissionRequest;
import paghttp.response.PermissionResponse;

public class PermissionUtil {

    public static PermissionResponse toPermissionResponse(Permission permission){
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .build();
    }

    public static Permission toPermission(PermissionRequest permissionRequest){
        return Permission.builder()
                .name(permissionRequest.getName())
                .build();
    }
}