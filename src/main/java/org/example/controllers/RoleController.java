package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dtos.RoleDTO;
import org.example.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Validated
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        try {
            RoleDTO createdRoleDTO = roleService.createRole(roleDTO.getRoleName(), roleDTO.getDescription());
            return new ResponseEntity<>(createdRoleDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable("id") int id) {
        try {
            RoleDTO foundRoleDTO = roleService.getRoleById(id);
            return new ResponseEntity<>(foundRoleDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ArrayList<RoleDTO>> getAllRoles() {
        try {
            ArrayList<RoleDTO> listRolesDTO = (ArrayList<RoleDTO>) roleService.getAllRoles();

            return new ResponseEntity<>(listRolesDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRoleById(@PathVariable("id") int id, @Valid @RequestBody RoleDTO roleDTO) {
        try {
            RoleDTO updatedRoleDTO = roleService.updateRoleById(id, roleDTO.getRoleName(), roleDTO.getDescription());
            return new ResponseEntity<>(updatedRoleDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{roleId}/users/{userId}")
    public ResponseEntity<Void> assignRoleToUser(@PathVariable("roleId") int roleId, @PathVariable("userId") int userId) {
        try {
            roleService.assignRoleToUser(userId, roleId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleById(@PathVariable("id") int id) {
        try {
            roleService.deleteRoleById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
