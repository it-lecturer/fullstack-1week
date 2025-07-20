package sample.app.todo.api.dto.request.delete;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import sample.app.common.validation.ValidUUID;

import java.util.List;

@Data
public class DeleteBulkTodoBody {
    @NotEmpty(message = "삭제할 ID 목록이 필요합니다.")
    @NotNull
    private List<@ValidUUID String> ids;
}
