package pl.bezdroznik.chesswebsocket.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.bezdroznik.chesswebsocket.security.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}