package sample.app.todo.api.dto.request.delete;

import lombok.Data;

import java.util.List;

@Data
public class DeleteBulkTodoBody {
    private List<String> ids;
}
