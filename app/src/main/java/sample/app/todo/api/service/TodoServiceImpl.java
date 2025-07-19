package sample.app.todo.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sample.app.common.exception.InvalidRequestException;
import sample.app.common.exception.TodoNotFoundException;
import sample.app.todo.api.dto.request.create.CreateTodoBody;
import sample.app.todo.api.dto.request.delete.DeleteBulkTodoBody;
import sample.app.todo.api.dto.request.update.UpdateTodoBody;
import sample.app.todo.api.dto.response.TodoResponse;
import sample.app.todo.domain.entity.Todo;
import sample.app.todo.domain.repository.TodoRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public TodoResponse createTodo(CreateTodoBody request) {

        // 입력 검증
        if (request == null || !StringUtils.hasText(request.getTitle())) {
            throw new InvalidRequestException("할일 제목은 필수입니다.");
        }

        Todo todo = Todo.of(request);

        System.out.println("todo = " + todo);
        System.out.println("request = " + request);
        Todo savedTodo = todoRepository.save(todo);
        return TodoResponse.from(savedTodo);
    }

    @Override
    public List<TodoResponse> getTodos() {
        return todoRepository.findAll().stream()
                .map(TodoResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public TodoResponse getTodo(String id) {

        UUID todoId;
        try {
            todoId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("올바르지 않은 ID 형식입니다: " + id);
        }

        return todoRepository.findById(todoId)
                .map(TodoResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found with id: " + id));
    }

    @Override
    public void updateTodo(String id, UpdateTodoBody request) {
        // 입력 검증
        if (request == null || !StringUtils.hasText(request.getTitle())) {
            throw new InvalidRequestException("할일 제목은 필수입니다.");
        }

        UUID todoId;
        try {
            todoId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("올바르지 않은 ID 형식입니다: " + id);
        }

        // 존재 여부 확인
        if (todoRepository.findById(todoId).isEmpty()) {
            throw new TodoNotFoundException(id);
        }


        todoRepository.update(todoId, Todo.of(request.getTitle()));
    }

    @Override
    public void deleteTodo(String id) {
        UUID todoId;
        try {
            todoId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("올바르지 않은 ID 형식입니다: " + id);
        }

        if (todoRepository.findById(todoId).isEmpty()) {
            throw new TodoNotFoundException(id);
        }

        todoRepository.deleteById(todoId);
    }

    @Override
    public void deleteBulkTodos(DeleteBulkTodoBody request) {
        if (request == null || request.getIds() == null || request.getIds().isEmpty()) {
            throw new InvalidRequestException("삭제할 ID 목록이 필요합니다.");
        }

        List<UUID> todoIds;
        try {
            todoIds = request.getIds().stream()
                    .map(UUID::fromString)
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("올바르지 않은 ID 형식이 포함되어 있습니다.");
        }

        todoRepository.deleteBulk(request.getIds().stream().map(UUID::fromString).collect(Collectors.toList()));
    }
}
