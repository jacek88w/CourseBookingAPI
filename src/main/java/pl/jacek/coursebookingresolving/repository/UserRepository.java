package pl.jacek.coursebookingresolving.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pl.jacek.coursebookingresolving.entity.User;

@Repository
@Component
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
