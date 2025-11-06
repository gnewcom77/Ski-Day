package skiday.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import skiday.dto.SkiSessionDTO;
import skiday.entity.Resort;
import skiday.entity.SkiSession;
import skiday.entity.User;
import skiday.enums.DayType;
import skiday.repository.ResortRepository;
import skiday.repository.SkiSessionRepository;
import skiday.repository.UserRepository;
import skiday.util.SeasonUtils;

@RestController
@RequestMapping("/sessions")
public class SkiSessionController {
	private final SkiSessionRepository sessions;
	private final UserRepository users;
	private final ResortRepository resorts;

	public SkiSessionController(SkiSessionRepository sessions, UserRepository users, ResortRepository resorts) {
		this.sessions = sessions;
		this.users = users;
		this.resorts = resorts;
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody SkiSessionDTO dto) {
		// Required fields
		if (dto.userId == null || dto.season == null || dto.date == null || dto.type == null) {
			return ResponseEntity.badRequest().body("userId, season, date, and type are required.");
		}

		LocalDate d;
		try {
			d = LocalDate.parse(dto.date); // expects YYYY-MM-DD
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body("date must be in YYYY-MM-DD format.");
		}

		DayType type;
		try {
			type = DayType.valueOf(dto.type.toUpperCase());
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body("type must be RESORT or BACKCOUNTRY.");
		}

		if (!SeasonUtils.dateInSeason(d, dto.season)) {
			int startYear = SeasonUtils.parseSeasonStartYear(dto.season);
			String msg = "Date must be between Nov 1, " + startYear + " and Apr 30, " + (startYear + 1) + " for season "
					+ dto.season + ".";
			return ResponseEntity.badRequest().body(msg);
		}

		User user = users.findById(dto.userId).orElse(null);
		if (user == null)
			return ResponseEntity.badRequest().body("Invalid userId: " + dto.userId);

		SkiSession s = new SkiSession();
		s.setUser(user);
		s.setDate(d);
		s.setSeason(dto.season);
		s.setType(type);

		if (type == DayType.RESORT) {
			if (dto.resortId == null) {
				return ResponseEntity.badRequest().body("RESORT sessions require resortId.");
			}
			Resort resort = resorts.findById(dto.resortId).orElse(null);
			if (resort == null) {
				return ResponseEntity.badRequest().body("Invalid resortId: " + dto.resortId);
			}
			s.setResort(resort);
			s.setFavoriteRun(dto.favoriteRun);
			s.setLiftsUsed(dto.liftsUsed);
			// clear backcountry-only fields
			s.setRegion(null);
			s.setGearUsed(null);
			s.setDogFriendly(null);
		} else { // BACKCOUNTRY
			s.setRegion(dto.region);
			s.setGearUsed(dto.gearUsed);
			s.setDogFriendly(dto.dogFriendly);
			// clear resort-only fields
			s.setResort(null);
			s.setFavoriteRun(null);
			s.setLiftsUsed(null);
		}

		s.setAscent(dto.ascent);
		s.setDescent(dto.descent);
		s.setMiles(dto.miles);
		s.setElapsedMinutes(dto.elapsedMinutes);
		s.setConditions(dto.conditions);
		s.setNotes(dto.notes);

		SkiSession saved = sessions.save(s);
		URI location = URI.create("/sessions/" + saved.getId());
		return ResponseEntity.created(location).body(saved);
	}

	@GetMapping
	public List<SkiSession> all() {
		return sessions.findAll();
	}

	@GetMapping("/season")
	public List<SkiSession> bySeason(@RequestParam String season, @RequestParam(required = false) DayType type) {
		if (type == null) {
			return sessions.findBySeasonOrderByDateDesc(season);
		} else {
			return sessions.findBySeasonAndTypeOrderByDateDesc(season, type);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		return sessions.findById(id).<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody skiday.dto.SkiSessionDTO dto) {
		var existingOpt = sessions.findById(id);
		if (existingOpt.isEmpty())
			return ResponseEntity.notFound().build();
		var s = existingOpt.get();

		if (dto.date != null) {
			try {
				s.setDate(java.time.LocalDate.parse(dto.date));
			} catch (Exception ex) {
				return ResponseEntity.badRequest().body("date must be YYYY-MM-DD if provided.");
			}
		}
		if (dto.season != null) {
			s.setSeason(dto.season);
		}
		if (dto.type != null) {
			try {
				s.setType(skiday.enums.DayType.valueOf(dto.type.toUpperCase()));
			} catch (Exception ex) {
				return ResponseEntity.badRequest().body("type must be RESORT or BACKCOUNTRY if provided.");
			}
		}

		if (s.getDate() != null && s.getSeason() != null) {
			if (!skiday.util.SeasonUtils.dateInSeason(s.getDate(), s.getSeason())) {
				int startYear = skiday.util.SeasonUtils.parseSeasonStartYear(s.getSeason());
				String msg = "Date must be between Nov 1, " + startYear + " and Apr 30, " + (startYear + 1)
						+ " for season " + s.getSeason() + ".";
				return ResponseEntity.badRequest().body(msg);
			}
		}

		if (dto.userId != null) {
			var user = users.findById(dto.userId).orElse(null);
			if (user == null)
				return ResponseEntity.badRequest().body("Invalid userId: " + dto.userId);
			s.setUser(user);
		}
		if (s.getType() == skiday.enums.DayType.RESORT) {
			if (dto.resortId != null) {
				var resort = resorts.findById(dto.resortId).orElse(null);
				if (resort == null)
					return ResponseEntity.badRequest().body("Invalid resortId: " + dto.resortId);
				s.setResort(resort);
			}
			if (dto.favoriteRun != null)
				s.setFavoriteRun(dto.favoriteRun);
			if (dto.liftsUsed != null)
				s.setLiftsUsed(dto.liftsUsed);
			s.setRegion(null);
			s.setGearUsed(null);
			s.setDogFriendly(null);
		} else if (s.getType() == skiday.enums.DayType.BACKCOUNTRY) {
			if (dto.region != null)
				s.setRegion(dto.region);
			if (dto.gearUsed != null)
				s.setGearUsed(dto.gearUsed);
			if (dto.dogFriendly != null)
				s.setDogFriendly(dto.dogFriendly);
			s.setResort(null);
			s.setFavoriteRun(null);
			s.setLiftsUsed(null);
		}

		if (dto.ascent != null)
			s.setAscent(dto.ascent);
		if (dto.descent != null)
			s.setDescent(dto.descent);
		if (dto.miles != null)
			s.setMiles(dto.miles);
		if (dto.elapsedMinutes != null)
			s.setElapsedMinutes(dto.elapsedMinutes);
		if (dto.conditions != null)
			s.setConditions(dto.conditions);
		if (dto.notes != null)
			s.setNotes(dto.notes);

		return ResponseEntity.ok(sessions.save(s));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if (!sessions.existsById(id))
			return ResponseEntity.notFound().build();
		sessions.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}