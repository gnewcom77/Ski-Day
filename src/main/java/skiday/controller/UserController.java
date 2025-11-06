package skiday.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skiday.entity.User;
import skiday.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserRepository repo;

	public UserController(UserRepository repo) {
		this.repo = repo;
	}

	@PostMapping
	public ResponseEntity<User> create(@RequestBody User u) {
		User saved = repo.save(u);
		return ResponseEntity.created(URI.create("/users/" + saved.getId())).body(saved);
	}

	@GetMapping
	public List<User> all() {
		return repo.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> one(@PathVariable Long id) {
		return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User u) {
		return repo.findById(id).map(existing -> {
			// copy only mutable fields (leave id alone)
			existing.setName(u.getName());
			return ResponseEntity.ok(repo.save(existing));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!repo.existsById(id))
			return ResponseEntity.notFound().build();
		repo.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
