package ru.yandex.practicum.ewm.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.ewm.users.dto.NewUserRequest;
import ru.yandex.practicum.ewm.users.dto.UserDto;
import ru.yandex.practicum.ewm.users.mapper.UserMapper;
import ru.yandex.practicum.ewm.users.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public List<UserDto> getAllUsers(List<Long> ids, Integer from, Integer size) {
        return new ArrayList<>(userStorage.getAllUsers(ids, from, size).stream()
                .map(UserMapper::userToDto)
                .collect(Collectors.toList()));
    }

    public UserDto addUser(NewUserRequest newUserRequest) {
        return UserMapper.userToDto(
                userStorage.addUser(
                        UserMapper.requestToUser(newUserRequest)
                )
        );
    }

    public void deleteUser(Long id) {
        userStorage.deleteUser(id);
    }
}
