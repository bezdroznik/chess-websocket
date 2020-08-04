package pl.bezdroznik.chesswebsocket.security.service;


import pl.bezdroznik.chesswebsocket.security.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}