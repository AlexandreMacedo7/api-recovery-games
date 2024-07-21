package com.macedo.api_recovery_games.service;

import com.macedo.api_recovery_games.exception.UserNotFoundException;
import com.macedo.api_recovery_games.models.User;
import com.macedo.api_recovery_games.models.dtos.UserDTO;
import com.macedo.api_recovery_games.models.dtos.UserPatchDTO;
import com.macedo.api_recovery_games.models.mapper.UserMapper;
import com.macedo.api_recovery_games.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public UserDTO patchUser(Long id, UserPatchDTO userPatchDTO) {
        Optional<User> optionalUser = Optional.ofNullable(repository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));

        return userMapper.toDTO(fieldUpdate(userPatchDTO, optionalUser));
    }

    public UserDTO getUserById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> userList = repository.findAll();
        return userMapper.toDTOList(userList);
    }

    public void deleteUserById(Long id) throws UserNotFoundException {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public void validateById(Long id) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
    }

    private User fieldUpdate(UserPatchDTO userPatchDTO, Optional<User> optionalUser) {
        User user = optionalUser.get();

        userPatchDTO.name().ifPresent(user::setName);
        userPatchDTO.phone().ifPresent(user::setPhone);

        repository.save(user);
        return user;
    }
}
