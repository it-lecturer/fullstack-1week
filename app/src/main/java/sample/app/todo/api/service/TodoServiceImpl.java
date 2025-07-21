package sample.app.todo.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import sample.app.common.exception.TodoNotFoundException;
import sample.app.config.AppConfig;
import sample.app.todo.api.dto.request.create.CreateTodoBody;
import sample.app.todo.api.dto.request.delete.DeleteBulkTodoBody;
import sample.app.todo.api.dto.request.update.UpdateTodoBody;
import sample.app.todo.api.dto.response.TodoResponse;
import sample.app.todo.domain.entity.Todo;
import sample.app.todo.domain.repository.MemoryTodoRepository;
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
        Todo todo = Todo.of(request);

        System.out.println("todo = " + todo);
        System.out.println("request = " + request);
        Todo savedTodo = todoRepository.save(todo);

        System.out.println("저장 후 savedTodo.id = " + savedTodo.getId()); // UUID 값
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
        UUID todoId = UUID.fromString(id);

        return todoRepository.findById(todoId)
                .map(TodoResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found with id: " + id));
    }

    @Override
    public void updateTodo(String id, UpdateTodoBody request) {
        UUID todoId = UUID.fromString(id);

        // 존재 여부 확인
        Todo existingTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException(id));

        // title이 제공된 경우에만 업데이트
        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
            existingTodo.setTitle(request.getTitle());
        }

        // completed가 제공된 경우에만 업데이트
        if (request.getCompleted() != null) {
            existingTodo.setCompleted(request.getCompleted());
        }

        todoRepository.save(existingTodo);
    }

    @Override
    public void deleteTodo(String id) {
        UUID todoId = UUID.fromString(id);

        if (todoRepository.findById(todoId).isEmpty()) {
            throw new TodoNotFoundException(id);
        }

        todoRepository.deleteById(todoId);
    }

    @Override
    public void deleteBulkTodos(DeleteBulkTodoBody request) {
        List<UUID> todoIds = request.getIds().stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());

        todoRepository.deleteBulk(todoIds);
    }
}
