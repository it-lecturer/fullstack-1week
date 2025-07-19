package sample.app.todo.domain.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import sample.app.todo.domain.entity.Todo;

import java.util.*;

@Repository
@Primary
public class MemoryTodoRepository implements TodoRepository {
    private static Map<UUID, Todo> store = new HashMap<>();

    @Override
    public Todo save(Todo todo) {
        store.put(todo.getId(), todo);
        return todo;
    }

    @Override
    public List<Todo> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Todo> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void update(UUID id, Todo todo) {
        if (store.containsKey(id)) {
            // 기존 Todo의 ID를 유지하면서 내용만 업데이트
            Todo existingTodo = store.get(id);
            existingTodo.setTitle(todo.getTitle());
            store.put(id, existingTodo);
        } else {
            throw new IllegalArgumentException("Todo not found with id: " + id);
        }
    }


    @Override
    public void deleteById(UUID id) {
        if (!store.containsKey(id)) {
            throw new IllegalArgumentException("Todo not found with id: " + id);
        }
        store.remove(id);
    }

    @Override
    public void deleteBulk(List<UUID> ids) {
        ids.forEach(this::deleteById);
    }
}
