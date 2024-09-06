package com.macedo.api_recovery_games.service;

import com.macedo.api_recovery_games.exception.UserNotFoundException;
import com.macedo.api_recovery_games.models.Rental;
import com.macedo.api_recovery_games.models.User;
import com.macedo.api_recovery_games.models.dtos.rentaldto.RentalDTO;
import com.macedo.api_recovery_games.models.dtos.userdto.UserDTO;
import com.macedo.api_recovery_games.models.dtos.userdto.UserPatchDTO;
import com.macedo.api_recovery_games.models.mapper.RentalMapper;
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
    private final RentalMapper rentalMapper;

    public UserService(UserRepository repository, UserMapper userMapper, RentalMapper rentalMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
        this.rentalMapper = rentalMapper;
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

    public List<RentalDTO> getRentalByUserId(Long id) {
        User user = validateById(id);
        return rentalMapper.toDTOList(user.getRentals());
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

    public User validateById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public void addRental(Rental rental, User user) {
        user.addRental(rental);
        repository.save(user);
    }

    private User fieldUpdate(UserPatchDTO userPatchDTO, Optional<User> optionalUser) {
        User user = optionalUser.get();

        userPatchDTO.name().ifPresent(user::setName);
        userPatchDTO.phone().ifPresent(user::setPhone);

        repository.save(user);
        return user;
    }
}
