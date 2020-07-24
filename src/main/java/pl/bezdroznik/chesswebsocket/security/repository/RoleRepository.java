package pl.bezdroznik.chesswebsocket.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.bezdroznik.chesswebsocket.security.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}