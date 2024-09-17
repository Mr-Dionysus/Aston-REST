package org.example.servlets;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dtos.RoleDTO;
import org.example.entities.Role;
import org.example.mappers.RoleMapper;
import org.example.services.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class RoleServletTest {
    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private RoleServiceImpl roleService;

    @Mock
    private RoleMapper roleMapper;

    private Gson gson;
    private RoleServlet roleServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gson = new Gson();
        roleServlet = new RoleServlet(roleService, roleMapper);
    }

    @Test
    @DisplayName("Create a Role")
    void doPost() throws IOException {
        int roleId = 1;
        String roleName = "admin";
        String description = "do stuff";

        when(req.getPathInfo()).thenReturn("/" + roleId);

        Role mockRole = new Role(roleId, roleName, description);
        RoleDTO mockRoleDTO = new RoleDTO();
        mockRoleDTO.setRoleName(roleName);
        mockRoleDTO.setDescription(description);

        when(roleService.createRole(roleName, description)).thenReturn(mockRole);
        when(roleMapper.roleToRoleDTO(mockRole)).thenReturn(mockRoleDTO);

        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(gson.toJson(mockRoleDTO))));
        PrintWriter out = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(out);

        roleServlet.doPost(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(resp).setStatus(HttpServletResponse.SC_CREATED);
        verify(out).println(gson.toJson(mockRoleDTO));
    }

    @Test
    @DisplayName("Get a Role")
    void doGet() throws IOException {
        int roleId = 1;
        String roleName = "admin";
        String description = "do stuff";

        when(req.getPathInfo()).thenReturn("/" + roleId);

        Role mockRole = new Role(roleId, roleName, description);
        RoleDTO mockRoleDTO = new RoleDTO();
        mockRoleDTO.setRoleName(roleName);
        mockRoleDTO.setDescription(description);

        when(roleService.getRoleById(roleId)).thenReturn(mockRole);
        when(roleMapper.roleToRoleDTO(mockRole)).thenReturn(mockRoleDTO);

        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(gson.toJson(mockRoleDTO))));
        PrintWriter out = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(out);

        roleServlet.doGet(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(resp).setStatus(HttpServletResponse.SC_OK);
        verify(out).println(gson.toJson(mockRoleDTO));
    }

    @Test
    @DisplayName("Get all Roles")
    void doGetAllRoles() throws IOException {
        int roleId = 1;
        String roleName1 = "admin";
        String description1 = "do stuff";
        Role mockRole1 = new Role(roleId, roleName1, description1);

        int roleId2 = 2;
        String roleName2 = "user";
        String description2 = "use stuff";
        Role mockRole2 = new Role(roleId2, roleName2, description2);

        RoleDTO mockRoleDTO1 = new RoleDTO();
        mockRoleDTO1.setRoleName(roleName1);
        mockRoleDTO1.setDescription(description1);

        RoleDTO mockRoleDTO2 = new RoleDTO();
        mockRoleDTO2.setRoleName(roleName2);
        mockRoleDTO2.setDescription(description2);

        List<Role> listRoles = new ArrayList<>();
        listRoles.add(mockRole1);
        listRoles.add(mockRole2);

        when(roleService.getAllRoles()).thenReturn(listRoles);
        when(roleMapper.roleToRoleDTO(mockRole1)).thenReturn(mockRoleDTO1);
        when(roleMapper.roleToRoleDTO(mockRole2)).thenReturn(mockRoleDTO2);

        List<RoleDTO> listRoleDTOs = new ArrayList<>();
        listRoleDTOs.add(mockRoleDTO1);
        listRoleDTOs.add(mockRoleDTO2);

        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(gson.toJson(listRoleDTOs))));
        PrintWriter out = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(out);

        roleServlet.doGet(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(resp).setStatus(HttpServletResponse.SC_OK);
        verify(out).println(gson.toJson(listRoleDTOs));
    }

    @Test
    @DisplayName("Update a Role")
    void doPut() throws IOException {
        int roleId = 1;
        String roleName = "admin";
        String description = "do stuff";

        when(req.getPathInfo()).thenReturn("/" + roleId);

        Role mockRole = new Role(roleId, roleName, description);
        RoleDTO mockRoleDTO = new RoleDTO();
        mockRoleDTO.setRoleName(roleName);
        mockRoleDTO.setDescription(description);

        when(roleService.updateRoleById(roleId, roleName, description)).thenReturn(mockRole);
        when(roleMapper.roleToRoleDTO(mockRole)).thenReturn(mockRoleDTO);

        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(gson.toJson(mockRoleDTO))));
        PrintWriter out = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(out);

        roleServlet.doPut(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(resp).setStatus(HttpServletResponse.SC_OK);
        verify(out).println(gson.toJson(mockRoleDTO));
    }

    @Test
    @DisplayName("Delete a Role")
    void doDelete() {
        int roleId = 1;
        when(req.getPathInfo()).thenReturn("/" + roleId);

        roleServlet.doDelete(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(resp).setStatus(HttpServletResponse.SC_OK);
    }
}