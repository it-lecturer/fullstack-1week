package sample.app.todo.api.dto.request.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateTodoBody {
    @NotBlank(message = "할일 제목은 필수입니다.")
    @Size(min = 1, max = 100, message = "할일 제목은 1자 이상 100자 이하여야 합니다.")
    private String title;
}
