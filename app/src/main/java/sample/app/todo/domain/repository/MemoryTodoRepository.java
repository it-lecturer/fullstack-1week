package sample.app.todo.domain.repository;

import com.github.f4b6a3.uuid.UuidCreator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import sample.app.common.exception.TodoNotFoundException;
import sample.app.todo.domain.entity.Todo;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
@Primary
public class MemoryTodoRepository implements TodoRepository {
    private static Map<UUID, Todo> store = new HashMap<>();

    @Override
    public Todo save(Todo todo) {
        if (store.containsKey(todo.getId())) {
            Todo existingTodo = store.get(todo.getId());
            existingTodo.setTitle(todo.getTitle());
            existingTodo.setCompleted(todo.isCompleted());
            existingTodo.setUpdatedAt(ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            store.put(todo.getId(), existingTodo);

            return todo;
        }

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
    public void deleteById(UUID id) {
        if (!store.containsKey(id)) {
            throw new TodoNotFoundException(id.toString());
        }
        store.remove(id);
    }


    public void deleteBulk(List<UUID> ids) {
        for (UUID id : ids) {
            if (!store.containsKey(id)) {
                throw new TodoNotFoundException(id.toString());
            }
        }
        ids.forEach(store::remove);

    }

    // 테스트용 헬퍼 메서드
    public void clear() {
        store.clear();
    }

    public int size() {
        return store.size();
    }
}
