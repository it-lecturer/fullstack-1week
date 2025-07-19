package sample.app.todo.api.service;

import sample.app.todo.api.dto.request.create.CreateTodoBody;
import sample.app.todo.api.dto.request.delete.DeleteBulkTodoBody;
import sample.app.todo.api.dto.request.update.UpdateTodoBody;
import sample.app.todo.api.dto.response.TodoListResponse;
import sample.app.todo.api.dto.response.TodoResponse;

import java.util.List;
import java.util.UUID;

public interface TodoService {
    /** 할일 삭제 */
    TodoResponse createTodo(CreateTodoBody request);
    /** 할일 목록 조회 */
    List<TodoResponse> getTodos();
    /** 할일 상세 조회 */
    TodoResponse getTodo(String id);
    void updateTodo(String id, UpdateTodoBody request);
    void deleteTodo(String id);
    void deleteBulkTodos(DeleteBulkTodoBody request);
}
