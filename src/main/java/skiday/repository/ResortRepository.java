package skiday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import skiday.entity.Resort;

public interface ResortRepository extends JpaRepository<Resort, Long> {
}