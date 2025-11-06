package skiday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import skiday.entity.SkiSession;
import skiday.enums.DayType;

public interface SkiSessionRepository extends JpaRepository<SkiSession, Long> {
	List<SkiSession> findByUserIdAndSeasonOrderByDateDesc(Long userId, String season);
	List<SkiSession> findBySeasonOrderByDateDesc(String season);
    List<SkiSession> findBySeasonAndTypeOrderByDateDesc(String season, DayType type);
    
}
