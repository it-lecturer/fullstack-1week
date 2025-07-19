package sample.app.todo.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
        return todoRepository.findById(UUID.fromString(id))
                .map(TodoResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found with id: " + id));
    }

    @Override
    public void updateTodo(String id, UpdateTodoBody request) {
        todoRepository.update(UUID.fromString(id), Todo.of(request.getTitle()));
    }

    @Override
    public void deleteTodo(String id) {
        todoRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public void deleteBulkTodos(DeleteBulkTodoBody request) {
        todoRepository.deleteBulk(request.getIds().stream().map(UUID::fromString).collect(Collectors.toList()));
    }
}
