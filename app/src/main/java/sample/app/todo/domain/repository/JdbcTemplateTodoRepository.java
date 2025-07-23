package sample.app.todo.domain.repository;

import sample.app.common.annotation.MainTodoRepository;
import sample.app.todo.domain.entity.Todo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@MainTodoRepository
public class JdbcTemplateTodoRepository implements TodoRepository{
    @Override
    public Todo save(Todo todo) {
        return null;
    }

    @Override
    public List<Todo> findAll() {
        return List.of();
    }

    @Override
    public Optional<Todo> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void deleteBulk(List<UUID> ids) {

    }

    @Override
    public void clear() {
        TodoRepository.super.clear();
    }

    @Override
    public int size() {
        return TodoRepository.super.size();
    }
}
