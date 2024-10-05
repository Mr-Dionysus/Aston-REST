package org.example.connection;

import org.example.db.HikariCP;
import org.example.db.TablesSQL;
import org.example.entities.Post;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestSQL {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void createAllTablesWithTestEntities(Connection connection, HikariCP hikariCP) throws SQLException {
        try (PreparedStatement prepStmtCreateTableUsers = connection.prepareStatement(TablesSQL.CREATE_USERS.getQuery());
             PreparedStatement prepStmtCreateTableRoles = connection.prepareStatement(TablesSQL.CREATE_ROLES.getQuery());
             PreparedStatement prepStmtCreateTablePosts = connection.prepareStatement(TablesSQL.CREATE_POSTS.getQuery());
             PreparedStatement prepStmtCreateTableUsersRoles = connection.prepareStatement(TablesSQL.CREATE_USERS_ROLES.getQuery())
        ) {
            prepStmtCreateTableUsers.executeUpdate();
            prepStmtCreateTableRoles.executeUpdate();
            prepStmtCreateTablePosts.executeUpdate();
            prepStmtCreateTableUsersRoles.executeUpdate();

            String testLogin = "testLogin";
            String testPassword = "testPassword";
            User user = new User(testLogin, testPassword);
            userRepository.save(user);

            String testLogin2 = "testLogin2";
            String testPassword2 = "testPassword2";
            User user2 = new User(testLogin2, testPassword2);
            userRepository.save(user2);

            String testText = "test text";
            int testUserId = 1;
            Post post = new Post(testText);
            postRepository.save(post);

            String expectedRoleName = "admin";
            String expectedDescription = "manage stuff";
            Role role = new Role(expectedRoleName, expectedDescription);
            roleRepository.save(role);
        }
    }
}
