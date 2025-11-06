package skiday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import skiday.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
