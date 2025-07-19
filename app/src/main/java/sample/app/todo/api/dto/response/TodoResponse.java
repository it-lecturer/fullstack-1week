package sample.app.todo.api.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import sample.app.todo.domain.entity.Todo;

import java.util.UUID;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoResponse {
    private UUID id;
    private String title;

    public TodoResponse(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

    public static TodoResponse from(Todo todo) {
        return new TodoResponse(todo.getId(), todo.getTitle());
    }
}
