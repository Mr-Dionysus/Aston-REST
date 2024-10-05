package org.example.services;

import org.example.dtos.UserDTO;
import org.example.entities.User;
import org.example.exceptions.*;
import org.example.mappers.UserMapper;
import org.example.mappers.UserMapperImpl;
import org.example.repositories.UserRepository;
import org.example.validators.UserValidator;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper = new UserMapperImpl();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUser(String login, String password) {
        UserValidator.login(login);
        UserValidator.password(password);
        User user = new User(login, password);

        User createdUser = userRepository.save(user);
        UserValidator.createdUser(createdUser, login);
        UserDTO createdUserDTO = userMapper.userToUserDTO(createdUser);

        return createdUserDTO;
    }

    @Override
    public UserDTO getUserById(int userId) {
        UserValidator.userId(userId);

        User foundUser = userRepository.findById(userId)
                                       .get();
                UserValidator.foundUser(foundUser, userId);
        UserDTO foundUserDTO = userMapper.userToUserDTO(foundUser);

        return foundUserDTO;
    }

    @Override
    public UserDTO getUserByIdWithoutHisRoles(int userId) {
        UserValidator.userId(userId);

        User foundUser = userRepository.findByIdWithoutRoles(userId);
        UserValidator.foundUser(foundUser, userId);
        UserDTO foundUserDTO = userMapper.userToUserDTO(foundUser);

        return foundUserDTO;
    }

    @Override
    public UserDTO updateUserById(int userId, String newLogin, String newPassword) {
        UserValidator.userId(userId);
        UserValidator.login(newLogin);
        UserValidator.password(newPassword);

        User user = userRepository.findById(userId)
                                  .get();
        user.setLogin(newLogin);
        user.setPassword(newPassword);
        User updatedUser = userRepository.save(user);
        UserValidator.foundUser(updatedUser, userId);
        UserDTO updatedUserDTO = userMapper.userToUserDTO(updatedUser);

        return updatedUserDTO;
    }

    @Override
    public void deleteUserById(int userId) {
        UserValidator.userId(userId);

        if (this.getUserById(userId) == null) {
            throw new UserNotFoundException("Error while deleting the User. User with ID '" + userId + "' can't be found");
        }

        userRepository.deleteById(userId);
    }
}
