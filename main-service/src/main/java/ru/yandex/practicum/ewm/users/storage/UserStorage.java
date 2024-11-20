package ru.yandex.practicum.ewm.users.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.ewm.users.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserStorage {

    private final UserRepository userRepository;

    public List<User> getAllUsers(List<Long> ids, int from, int size) {

        PageRequest pageRequest = PageRequest.of(from / size, size);
        return new ArrayList<>(userRepository.findByIds(ids, pageRequest));
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
