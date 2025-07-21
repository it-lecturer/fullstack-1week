package sample.app.todo.domain.repository;

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
    void deleteById(UUID id);
    void deleteBulk(List<UUID> ids);
    
    // 테스트용 헬퍼 메서드들
    default void clear() {
        // 기본 구현은 아무것도 하지 않음
        // MemoryTodoRepository에서만 실제 구현
    }
    
    default int size() {
        // 기본 구현은 findAll().size()를 반환
        return findAll().size();
    }
}
