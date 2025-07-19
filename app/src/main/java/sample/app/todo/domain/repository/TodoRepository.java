package sample.app.todo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sample.app.todo.domain.entity.Todo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TodoRepository {
    Todo save(Todo todo);
    List<Todo> findAll();
    Optional<Todo> findById(UUID id);
    void update(UUID id, Todo todo);
    void deleteById(UUID id);
    void deleteBulk(List<UUID> ids);
}
