package sample.app.todo.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import sample.app.common.validation.ValidUUID;
import sample.app.todo.api.dto.request.create.CreateTodoBody;
import sample.app.todo.api.dto.request.delete.DeleteBulkTodoBody;
import sample.app.todo.api.dto.request.update.UpdateTodoBody;
import sample.app.todo.api.dto.response.TodoResponse;
import sample.app.todo.api.service.TodoService;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@Validated
@Tag(name = "Todo API", description = "할 일 관리를 위한 REST API")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    @Operation(
        summary = "할 일 생성",
        description = "새로운 할 일을 생성합니다. 제목과 설명을 포함한 할 일 정보를 입력받아 저장합니다."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "할 일 생성 성공",
            content = @Content(schema = @Schema(implementation = TodoResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 데이터"
        )
    })
    public ResponseEntity<TodoResponse> createTodo(
        @Parameter(description = "생성할 할 일 정보", required = true)
        @Valid @RequestBody CreateTodoBody request
    ) {
        System.out.println("controller request = " + request);
        TodoResponse response = todoService.createTodo(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
        summary = "할 일 목록 조회",
        description = "모든 할 일 목록을 조회합니다. 완료/미완료 상태와 관계없이 전체 목록을 반환합니다."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "할 일 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = TodoResponse.class))
        )
    })
    public ResponseEntity<List<TodoResponse>> getTodos() {
        List<TodoResponse> response = todoService.getTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "할 일 상세 조회",
        description = "특정 ID의 할 일 상세 정보를 조회합니다."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "할 일 조회 성공",
            content = @Content(schema = @Schema(implementation = TodoResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 UUID 형식"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "할 일을 찾을 수 없음"
        )
    })
    public ResponseEntity<TodoResponse> getTodo(
        @Parameter(description = "조회할 할 일의 UUID", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable("id") @ValidUUID String id
    ) {
        TodoResponse response = todoService.getTodo(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "할 일 수정",
        description = "특정 ID의 할 일 정보를 수정합니다. 제목, 설명, 완료 상태를 변경할 수 있습니다."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "할 일 수정 성공"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 데이터 또는 UUID 형식"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "수정할 할 일을 찾을 수 없음"
        )
    })
    public ResponseEntity<Void> updateTodo(
        @Parameter(description = "수정할 할 일의 UUID", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable("id") @ValidUUID String id,
        @Parameter(description = "수정할 할 일 정보", required = true)
        @Valid @RequestBody UpdateTodoBody request
    ) {
        todoService.updateTodo(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "할 일 삭제",
        description = "특정 ID의 할 일을 삭제합니다."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "할 일 삭제 성공"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 UUID 형식"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "삭제할 할 일을 찾을 수 없음"
        )
    })
    public ResponseEntity<Void> deleteTodo(
        @Parameter(description = "삭제할 할 일의 UUID", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable("id") @ValidUUID String id
    ) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/bulk")
    @Operation(
        summary = "할 일 일괄 삭제",
        description = "여러 개의 할 일을 한 번에 삭제합니다. UUID 목록을 받아서 해당하는 모든 할 일을 삭제합니다."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "할 일 일괄 삭제 성공"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 데이터 또는 UUID 형식"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "삭제할 할 일 중 일부를 찾을 수 없음"
        )
    })
    public ResponseEntity<Void> deleteBulkTodos(
        @Parameter(description = "삭제할 할 일들의 UUID 목록", required = true)
        @Valid @RequestBody DeleteBulkTodoBody request
    ) {
        todoService.deleteBulkTodos(request);
        return ResponseEntity.ok().build();
    }
}
