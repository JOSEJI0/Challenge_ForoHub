package com.example.alura.ForoHub.service;

import com.example.alura.ForoHub.exceptions.PermissionException;
import com.example.alura.ForoHub.model.authorization.Permission;
import com.example.alura.ForoHub.repository.PermissionRepository;
import com.example.alura.ForoHub.util.PermissionUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paghttp.request.PermissionRequest;
import paghttp.response.PermissionResponse;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional
    public PermissionResponse savePermission(PermissionRequest permissionRequest) {
        Permission permission = permissionRepository.save(PermissionUtil.toPermission(permissionRequest));
        return PermissionUtil.toPermissionResponse(permission);
    }

    public PermissionResponse getPermission(String id) throws PermissionException {
       Permission permission = permissionRepository.findById(id).orElse(null);
       if (permission == null){
           throw new PermissionException("Permission no encontrado");
       }
       return PermissionUtil.toPermissionResponse(permission);
    }

    public List<Permission> getAllPermissions() {return  permissionRepository.findByEnable(true);}

    @Transactional
    public void deletePermission(String id) throws PermissionException {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new PermissionException("Permission no encontrado"));
        permission.setEnable(false);
    }

    @Transactional
    public PermissionResponse updatePermission(String id, PermissionRequest permissionRequest) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new RuntimeException("Permission not found"));
        permission.setName(permissionRequest.getName());
        return PermissionUtil.toPermissionResponse(permission);
    }
}
