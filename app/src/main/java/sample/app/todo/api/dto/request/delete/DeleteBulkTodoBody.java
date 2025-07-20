package sample.app.todo.api.dto.request.delete;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class DeleteBulkTodoBody {
    @NotEmpty(message = "삭제할 ID 목록이 필요합니다.")
    private List<@Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", 
                         message = "올바르지 않은 UUID 형식입니다.") String> ids;
}
