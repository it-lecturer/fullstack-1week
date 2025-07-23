package sample.app.todo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sample.app.common.exception.TodoNotFoundException;
import sample.app.todo.domain.entity.Todo;

import java.util.List;
import java.util.UUID;

@Repository
// @Primary
public interface SpringDataJpaTodoRepository extends JpaRepository<Todo, UUID>, TodoRepository {
    
    @Override
    @Transactional
    default void deleteBulk(List<UUID> ids) {
        // 존재하지 않는 ID 체크
        for (UUID id : ids) {
            if (!existsById(id)) {
                throw new TodoNotFoundException(id.toString());
            }
        }

        // 일괄 삭제
        deleteAllById(ids);
    }
    
    @Override
    @Transactional
    default void clear() {
        deleteAll();
    }
}
