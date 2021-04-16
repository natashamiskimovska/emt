package lab1.emt.service;

import lab1.emt.model.User;

public interface AuthService {

    User login(String username, String password);
}
