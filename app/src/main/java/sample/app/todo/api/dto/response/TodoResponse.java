package sample.app.todo.api.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import sample.app.todo.domain.entity.Todo;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoResponse {
    private UUID id;
    private String title;
    private Boolean completed;
    private String createdAt;
    private String updatedAt;

    public TodoResponse(UUID id, String title, Boolean completed, String createdAt, String updatedAt) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static TodoResponse from(Todo todo) {
        return new TodoResponse(todo.getId(), todo.getTitle(), todo.isCompleted(), todo.getCreatedAt(), todo.getUpdatedAt());
    }
}
