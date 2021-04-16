package lab1.emt.service.impl;

import lab1.emt.model.User;
import lab1.emt.model.exceptions.InvalidArgumentsException;
import lab1.emt.model.exceptions.InvalidUserCredentialsException;
import lab1.emt.repository.UserRepository;
import lab1.emt.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username,
                password).orElseThrow(InvalidUserCredentialsException::new);
    }

}
