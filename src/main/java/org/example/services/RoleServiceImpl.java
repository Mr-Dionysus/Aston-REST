package org.example.services;

import jakarta.transaction.Transactional;
import org.example.dtos.RoleDTO;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.exceptions.*;
import org.example.mappers.RoleMapper;
import org.example.mappers.RoleMapperImpl;
import org.example.repositories.RoleRepository;
import org.example.repositories.UserRepository;
import org.example.validators.RoleValidator;
import org.example.validators.UserValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper = new RoleMapperImpl();
    private final UserRepository userRepository;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RoleDTO createRole(String roleName, String description) {
        RoleValidator.roleName(roleName);
        RoleValidator.description(description);

        Role role = new Role(roleName, description);
        Role createdRole = roleRepository.save(role);
        RoleValidator.createdRole(createdRole, roleName);
        RoleDTO createdRoleDTO = roleMapper.roleToRoleDTO(createdRole);

        return createdRoleDTO;
    }

    @Override
    public RoleDTO getRoleById(int roleId) {
        RoleValidator.roleId(roleId);

        Role foundRole = roleRepository.findById(roleId)
                                       .isPresent() ? roleRepository.findById(roleId)
                                                                    .get() : null;
        RoleValidator.foundRole(foundRole, roleId);
        RoleDTO foundRoleDTO = roleMapper.roleToRoleDTO(foundRole);

        return foundRoleDTO;
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> listFoundRoles = roleRepository.findAll();
        RoleValidator.listFoundRoles(listFoundRoles);
        List<RoleDTO> listFoundRolesDTO = new ArrayList<>();

        for (Role role : listFoundRoles) {
            RoleDTO roleDTO = roleMapper.roleToRoleDTO(role);
            listFoundRolesDTO.add(roleDTO);
        }

        return listFoundRolesDTO;
    }

    @Override
    public RoleDTO updateRoleById(int roleId, String newRoleName, String newDescription) {
        RoleValidator.roleId(roleId);
        RoleValidator.roleName(newRoleName);
        RoleValidator.description(newDescription);

        Role role = roleRepository.findById(roleId)
                                  .isPresent() ? roleRepository.findById(roleId)
                                                               .get() : null;

        RoleValidator.foundRole(role, roleId);
        role.setRoleName(newRoleName);
        role.setDescription(newDescription);
        Role updatedRole = roleRepository.save(role);
        RoleValidator.foundRole(updatedRole, roleId);
        RoleDTO updatedRoleDTO = roleMapper.roleToRoleDTO(updatedRole);

        return updatedRoleDTO;
    }

    @Override
    public void deleteRoleById(int roleId) {
        RoleValidator.roleId(roleId);

        if (this.getRoleById(roleId) == null) {
            throw new RoleNotFoundException("Error while deleting the Role. Role with ID '" + roleId + "' " + "can't be found");
        }

        Role role = roleRepository.findById(roleId)
                                  .isPresent() ? roleRepository.findById(roleId)
                                                               .get() : null;
        RoleValidator.foundRole(role, roleId);
        List<User> users = role.getUsers();

        for (User user : users) {
            user.getRoles()
                .remove(role);
            userRepository.save(user);
        }

        roleRepository.deleteById(roleId);

    }

    @Override
    @Transactional
    public void assignRoleToUser(int userId, int roleId) {
        UserValidator.userId(userId);
        RoleValidator.roleId(roleId);

        User user = userRepository.findById(userId)
                                  .isPresent() ? userRepository.findById(userId).get() : null;
        Role role = roleRepository.findById(roleId)
                                  .isPresent() ? roleRepository.findById(roleId)
                                                               .get() : null;
        UserValidator.foundUser(user, userId);
        RoleValidator.foundRole(role, roleId);

        List<Role> updatedRoles = new ArrayList<>(user.getRoles());
        updatedRoles.add(role);
        user.setRoles(updatedRoles);
        userRepository.save(user);
    }
}
