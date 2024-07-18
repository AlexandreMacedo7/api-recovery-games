package com.macedo.api_recovery_games.service;

import com.macedo.api_recovery_games.exception.UserNotFoundException;
import com.macedo.api_recovery_games.models.User;
import com.macedo.api_recovery_games.models.dtos.UserDTO;
import com.macedo.api_recovery_games.models.mapper.UserMapper;
import com.macedo.api_recovery_games.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    public UserService(UserRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDTO saveUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        repository.save(user);
        return userMapper.toDTO(user);
    }

    public UserDTO getUserById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> userList = repository.findAll();
        return userMapper.toDTOList(userList);
    }
}
