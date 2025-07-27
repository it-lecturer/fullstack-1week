package sample.app.todo.domain.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import sample.app.common.annotation.MainTodoRepository;
import sample.app.todo.domain.entity.Todo;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class JdbcTemplateTodoRepository implements TodoRepository{

    private final JdbcTemplate template;

    public JdbcTemplateTodoRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Todo> todoRowMapper = (ResultSet rs, int rowNum) -> {
        Todo todo = new Todo();
        todo.setId(UUID.fromString(rs.getString("todo_id")));
        todo.setTitle(rs.getString("title"));
        todo.setCompleted(rs.getBoolean("completed"));
        return todo;
    };

    @Override
    public Todo save(Todo todo) {
        String sql = "INSERT INTO todo (todo_id, title, completed, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        
        template.update(sql,
            todo.getId().toString(),
            todo.getTitle(),
            todo.isCompleted(),
            todo.getCreatedAt(),
            todo.getUpdatedAt()
        );
        
        return todo;
    }

    @Override
    public List<Todo> findAll() {
        String sql = "SELECT todo_id, title, completed FROM todo ORDER BY created_at DESC";
        return template.query(sql, todoRowMapper);
    }

    @Override
    public Optional<Todo> findById(UUID id) {
        String sql = "SELECT todo_id, title, completed FROM todo WHERE todo_id = ?";
        List<Todo> results = template.query(sql, todoRowMapper, id.toString());
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM todo WHERE todo_id = ?";
        template.update(sql, id.toString());
    }

    @Override
    public void deleteBulk(List<UUID> ids) {
        if (ids.isEmpty()) {
            return;
        }
        
        String placeholders = String.join(",", java.util.Collections.nCopies(ids.size(), "?"));
        String sql = "DELETE FROM todo WHERE todo_id IN (" + placeholders + ")";
        
        String[] idStrings = ids.stream()
            .map(UUID::toString)
            .toArray(String[]::new);
            
        template.update(sql, (Object[]) idStrings);
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM todo";
        template.update(sql);
    }

    @Override
    public int size() {
        String sql = "SELECT COUNT(*) FROM todo";
        return template.queryForObject(sql, Integer.class);
    }
}
