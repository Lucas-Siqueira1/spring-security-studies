package com.lucas.spring_security_studies.services;
import com.lucas.spring_security_studies.dto.UserRequestDto;
import com.lucas.spring_security_studies.dto.UserResponseDto;
import com.lucas.spring_security_studies.entities.User;
import com.lucas.spring_security_studies.exceptions.ResourceNotFoundException;
import com.lucas.spring_security_studies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserResponseDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(t -> new UserResponseDto(t.getId(), t.getName(), t.getEmail()))
                .toList();
    }

    public UserResponseDto findUserById(Integer id) {
        User obj = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id));
        return new UserResponseDto(obj.getId(), obj.getName(), obj.getEmail());
    }

    public UserResponseDto insert(UserRequestDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        User saved = userRepository.save(user);
        return new UserResponseDto(saved.getId(), saved.getName(), saved.getEmail());
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public UserResponseDto update(Integer id, UserRequestDto newUser) {
        User user = userRepository.getReferenceById(id);
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        User saved = userRepository.save(user);

        return new UserResponseDto(saved.getId(), saved.getName(), saved.getEmail());
    }
}
