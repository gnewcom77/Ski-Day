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

import skiday.entity.Resort;
import skiday.repository.ResortRepository;

@RestController
@RequestMapping("/resorts")
public class ResortController {
	private final ResortRepository repo;

	public ResortController(ResortRepository repo) {
		this.repo = repo;
	}

	@PostMapping
    public ResponseEntity<Resort> create(@RequestBody Resort r) {
        Resort saved = repo.save(r);
        return ResponseEntity.created(URI.create("/resorts/" + saved.getId())).body(saved);
    }

    // READ ALL
    @GetMapping
    public List<Resort> all() {
        return repo.findAll();
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Resort> one(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Resort> update(@PathVariable Long id, @RequestBody Resort r) {
        return repo.findById(id)
                .map(existing -> {
                    // copy only mutable fields (leave id alone)
                    existing.setName(r.getName());
                    existing.setRegion(r.getRegion());
                    existing.setState(r.getState());
                    return ResponseEntity.ok(repo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}