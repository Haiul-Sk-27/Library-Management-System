package Library.Management.System.com.example.repository;

import Library.Management.System.com.example.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
