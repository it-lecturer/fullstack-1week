package sample.app.todo.api.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import sample.app.common.exception.TodoNotFoundException;
import sample.app.todo.api.dto.request.create.CreateTodoBody;
import sample.app.todo.api.dto.request.delete.DeleteBulkTodoBody;
import sample.app.todo.api.dto.request.update.UpdateTodoBody;
import sample.app.todo.api.dto.response.TodoResponse;
import sample.app.todo.domain.repository.TodoRepository;
import sample.app.todo.domain.repository.MemoryTodoRepository;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = TodoServiceTest.TestConfig.class)
@DisplayName("TodoService 테스트")
class TodoServiceTest {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;

    static class TestConfig {
        @Bean
        public TodoRepository todoRepository() {
            return new MemoryTodoRepository();
        }

        @Bean
        public TodoService todoService(TodoRepository todoRepository) {
            return new TodoServiceImpl(todoRepository);
        }
    }

    @BeforeEach
    void setUp() {
        // 각 테스트 전에 저장소 초기화
        todoRepository.clear();
    }

    @Nested
    @DisplayName("할일 생성 테스트")
    class CreateTodoTest {

        @Test
        @DisplayName("정상적인 할일 생성")
        void createTodo_Success() {
            // Given
            CreateTodoBody request = new CreateTodoBody();
            request.setTitle("새로운 할일");

            // When
            TodoResponse response = todoService.createTodo(request);

            // Then
            assertNotNull(response);
            assertNotNull(response.getId());
            assertEquals("새로운 할일", response.getTitle());
            assertFalse(response.getCompleted());
            assertNotNull(response.getCreatedAt());
            assertNotNull(response.getUpdatedAt());

            // 실제로 저장되었는지 확인
            assertEquals(1, todoRepository.size());
        }

        @Test
        @DisplayName("여러 할일 생성")
        void createMultipleTodos() {
            // Given
            List<String> titles = Arrays.asList("할일 1", "할일 2", "할일 3");

            // When
            List<TodoResponse> responses = new ArrayList<>();
            for (String title : titles) {
                CreateTodoBody request = new CreateTodoBody();
                request.setTitle(title);
                responses.add(todoService.createTodo(request));
            }

            // Then
            assertEquals(3, responses.size());
            assertEquals(3, todoRepository.size());

            Set<UUID> uniqueIds = responses.stream()
                    .map(TodoResponse::getId)
                    .collect(Collectors.toSet());
            assertEquals(3, uniqueIds.size()); // 모든 ID가 고유한지 확인
        }
    }

    @Nested
    @DisplayName("할일 목록 조회 테스트")
    class GetTodosTest {

        @Test
        @DisplayName("빈 목록 조회")
        void getTodos_EmptyList() {
            // When
            List<TodoResponse> responses = todoService.getTodos();

            // Then
            assertTrue(responses.isEmpty());
        }

        @Test
        @DisplayName("전체 할일 목록 조회 - 데이터 있음")
        void getTodos_WithData() {
            // Given - 미리 3개의 할일 생성
            createTestTodos(Arrays.asList("할일 1", "할일 2", "할일 3"));

            // When
            List<TodoResponse> responses = todoService.getTodos();

            // Then
            assertEquals(3, responses.size());

            Set<String> titles = responses.stream()
                    .map(TodoResponse::getTitle)
                    .collect(Collectors.toSet());
            assertTrue(titles.contains("할일 1"));
            assertTrue(titles.contains("할일 2"));
            assertTrue(titles.contains("할일 3"));
        }
    }

    @Nested
    @DisplayName("할일 상세 조회 테스트")
    class GetTodoTest {

        @Test
        @DisplayName("정상적인 할일 조회")
        void getTodo_Success() {
            // Given
            TodoResponse created = createTestTodo("조회 테스트 할일");

            // When
            TodoResponse response = todoService.getTodo(created.getId().toString());

            // Then
            assertNotNull(response);
            assertEquals(created.getId(), response.getId());
            assertEquals("조회 테스트 할일", response.getTitle());
            assertFalse(response.getCompleted());
        }

        @Test
        @DisplayName("존재하지 않는 할일 조회")
        void getTodo_NotFound() {
            // Given
            String nonExistentId = UUID.randomUUID().toString();

            // When & Then
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> todoService.getTodo(nonExistentId)
            );

            assertTrue(exception.getMessage().contains("Todo not found with id"));
        }

        @Test
        @DisplayName("잘못된 UUID 형식")
        void getTodo_InvalidUUID() {
            // Given
            String invalidId = "invalid-uuid";

            // When & Then
            assertThrows(IllegalArgumentException.class, () -> todoService.getTodo(invalidId));
        }
    }

    @Nested
    @DisplayName("할일 수정 테스트")
    class UpdateTodoTest {

        @Test
        @DisplayName("제목과 완료 상태 모두 수정")
        void updateTodo_TitleAndCompleted() {
            // Given
            TodoResponse created = createTestTodo("수정 전 제목");
            UpdateTodoBody request = new UpdateTodoBody();
            request.setTitle("수정된 제목");
            request.setCompleted(true);

            // When
            todoService.updateTodo(created.getId().toString(), request);

            // Then
            TodoResponse updated = todoService.getTodo(created.getId().toString());
            assertEquals("수정된 제목", updated.getTitle());
            assertTrue(updated.getCompleted());
            assertNotEquals(created.getUpdatedAt(), updated.getUpdatedAt());
        }

        @Test
        @DisplayName("제목만 수정")
        void updateTodo_TitleOnly() {
            // Given
            TodoResponse created = createTestTodo("수정 전 제목");
            UpdateTodoBody request = new UpdateTodoBody();
            request.setTitle("제목만 수정");
            request.setCompleted(null);

            // When
            todoService.updateTodo(created.getId().toString(), request);

            // Then
            TodoResponse updated = todoService.getTodo(created.getId().toString());
            assertEquals("제목만 수정", updated.getTitle());
            assertFalse(updated.getCompleted()); // 기존 값 유지
        }

        @Test
        @DisplayName("완료 상태만 수정")
        void updateTodo_CompletedOnly() {
            // Given
            TodoResponse created = createTestTodo("완료 상태 테스트");
            UpdateTodoBody request = new UpdateTodoBody();
            request.setTitle(null);
            request.setCompleted(true);

            // When
            todoService.updateTodo(created.getId().toString(), request);

            // Then
            TodoResponse updated = todoService.getTodo(created.getId().toString());
            assertEquals("완료 상태 테스트", updated.getTitle()); // 기존 값 유지
            assertTrue(updated.getCompleted());
        }

        @Test
        @DisplayName("빈 제목으로 수정 시도")
        void updateTodo_EmptyTitle() {
            // Given
            TodoResponse created = createTestTodo("원래 제목");
            UpdateTodoBody request = new UpdateTodoBody();
            request.setTitle("   "); // 공백만 있는 문자열
            request.setCompleted(true);

            // When
            todoService.updateTodo(created.getId().toString(), request);

            // Then
            TodoResponse updated = todoService.getTodo(created.getId().toString());
            assertEquals("원래 제목", updated.getTitle()); // 기존 제목 유지
            assertTrue(updated.getCompleted()); // completed는 수정됨
        }

        @Test
        @DisplayName("존재하지 않는 할일 수정")
        void updateTodo_NotFound() {
            // Given
            String nonExistentId = UUID.randomUUID().toString();
            UpdateTodoBody request = new UpdateTodoBody();
            request.setTitle("수정된 제목");

            // When & Then
            assertThrows(TodoNotFoundException.class,
                    () -> todoService.updateTodo(nonExistentId, request));
        }
    }

    @Nested
    @DisplayName("할일 삭제 테스트")
    class DeleteTodoTest {

        @Test
        @DisplayName("정상적인 할일 삭제")
        void deleteTodo_Success() {
            // Given
            TodoResponse created = createTestTodo("삭제될 할일");
            assertEquals(1, todoRepository.size());

            // When
            todoService.deleteTodo(created.getId().toString());

            // Then
            assertEquals(0, todoRepository.size());
            assertThrows(IllegalArgumentException.class,
                    () -> todoService.getTodo(created.getId().toString()));
        }

        @Test
        @DisplayName("존재하지 않는 할일 삭제")
        void deleteTodo_NotFound() {
            // Given
            String nonExistentId = UUID.randomUUID().toString();

            // When & Then
            assertThrows(TodoNotFoundException.class,
                    () -> todoService.deleteTodo(nonExistentId));
        }

        @Test
        @DisplayName("여러 할일 중 하나만 삭제")
        void deleteTodo_OneOfMany() {
            // Given
            List<TodoResponse> created = createTestTodos(Arrays.asList("할일 1", "할일 2", "할일 3"));
            assertEquals(3, todoRepository.size());

            // When
            todoService.deleteTodo(created.get(1).getId().toString()); // "할일 2" 삭제

            // Then
            assertEquals(2, todoRepository.size());

            List<TodoResponse> remaining = todoService.getTodos();
            Set<String> remainingTitles = remaining.stream()
                    .map(TodoResponse::getTitle)
                    .collect(Collectors.toSet());

            assertTrue(remainingTitles.contains("할일 1"));
            assertFalse(remainingTitles.contains("할일 2"));
            assertTrue(remainingTitles.contains("할일 3"));
        }
    }

    @Nested
    @DisplayName("할일 일괄 삭제 테스트")
    class DeleteBulkTodosTest {

        @Test
        @DisplayName("정상적인 일괄 삭제")
        void deleteBulkTodos_Success() {
            // Given
            List<TodoResponse> created = createTestTodos(Arrays.asList("할일 1", "할일 2", "할일 3", "할일 4"));
            assertEquals(4, todoRepository.size());

            List<String> idsToDelete = created.subList(0, 2).stream()
                    .map(todo -> todo.getId().toString())
                    .collect(Collectors.toList());

            DeleteBulkTodoBody request = new DeleteBulkTodoBody();
            request.setIds(idsToDelete);

            // When
            todoService.deleteBulkTodos(request);

            // Then
            assertEquals(2, todoRepository.size());

            List<TodoResponse> remaining = todoService.getTodos();
            Set<String> remainingTitles = remaining.stream()
                    .map(TodoResponse::getTitle)
                    .collect(Collectors.toSet());

            assertTrue(remainingTitles.contains("할일 3"));
            assertTrue(remainingTitles.contains("할일 4"));
        }

        @Test
        @DisplayName("빈 목록으로 일괄 삭제")
        void deleteBulkTodos_EmptyList() {
            // Given
            createTestTodos(Arrays.asList("할일 1", "할일 2"));
            int initialSize = todoRepository.size();

            DeleteBulkTodoBody request = new DeleteBulkTodoBody();
            request.setIds(Collections.emptyList());

            // When
            todoService.deleteBulkTodos(request);

            // Then
            assertEquals(initialSize, todoRepository.size());
        }

        @Test
        @DisplayName("존재하지 않는 ID 포함 일괄 삭제")
        void deleteBulkTodos_WithNonExistentIds() {
            // Given
            List<TodoResponse> created = createTestTodos(Arrays.asList("할일 1", "할일 2"));

            List<String> idsToDelete = Arrays.asList(
                    created.get(0).getId().toString(), // 존재하는 ID
                    UUID.randomUUID().toString(),      // 존재하지 않는 ID
                    created.get(1).getId().toString()  // 존재하는 ID
            );

            DeleteBulkTodoBody request = new DeleteBulkTodoBody();
            request.setIds(idsToDelete);

            // When, Then
            // 존재하는 할일들은 삭제되고, 한개라도 존재하지 않으면 TodoNotFoundException 나온다
            Assertions.assertThatThrownBy(() -> todoService.deleteBulkTodos(request)).isInstanceOf(TodoNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("통합 시나리오 테스트")
    class IntegrationScenarioTest {

        @Test
        @DisplayName("할일 생성 -> 조회 -> 수정 -> 삭제 전체 시나리오")
        void fullLifecycleScenario() {
            // 1. 생성
            CreateTodoBody createRequest = new CreateTodoBody();
            createRequest.setTitle("시나리오 테스트 할일");
            TodoResponse created = todoService.createTodo(createRequest);

            assertNotNull(created);
            assertEquals("시나리오 테스트 할일", created.getTitle());
            assertFalse(created.getCompleted());

            // 2. 조회
            TodoResponse found = todoService.getTodo(created.getId().toString());
            assertEquals(created.getId(), found.getId());
            assertEquals(created.getTitle(), found.getTitle());

            // 3. 수정
            UpdateTodoBody updateRequest = new UpdateTodoBody();
            updateRequest.setTitle("수정된 할일");
            updateRequest.setCompleted(true);

            todoService.updateTodo(created.getId().toString(), updateRequest);

            TodoResponse updated = todoService.getTodo(created.getId().toString());
            assertEquals("수정된 할일", updated.getTitle());
            assertTrue(updated.getCompleted());

            // 4. 삭제
            todoService.deleteTodo(created.getId().toString());

            assertThrows(IllegalArgumentException.class,
                    () -> todoService.getTodo(created.getId().toString()));
            assertEquals(0, (todoRepository).size());
        }

        @Test
        @DisplayName("대량 데이터 처리 시나리오")
        void bulkDataScenario() {
            // Given - 대량 데이터 생성
            int count = 100;
            List<String> titles = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                titles.add("대량 테스트 할일 " + i);
            }

            // When - 대량 생성
            List<TodoResponse> created = createTestTodos(titles);

            // Then
            assertEquals(count, created.size());
            assertEquals(count, todoService.getTodos().size());

            // 일부 완료 처리
            for (int i = 0; i < count / 2; i++) {
                UpdateTodoBody request = new UpdateTodoBody();
                request.setCompleted(true);
                todoService.updateTodo(created.get(i).getId().toString(), request);
            }

            // 검증
            List<TodoResponse> all = todoService.getTodos();
            long completedCount = all.stream().mapToLong(todo -> todo.getCompleted() ? 1 : 0).sum();
            assertEquals(count / 2, completedCount);

            // 일괄 삭제
            List<String> idsToDelete = created.subList(0, count / 4).stream()
                    .map(todo -> todo.getId().toString())
                    .collect(Collectors.toList());

            DeleteBulkTodoBody deleteRequest = new DeleteBulkTodoBody();
            deleteRequest.setIds(idsToDelete);
            todoService.deleteBulkTodos(deleteRequest);

            // 최종 검증
            assertEquals(count - count / 4, todoService.getTodos().size());
        }
    }


    // 테스트 헬퍼 메서드들
    private TodoResponse createTestTodo(String title) {
        CreateTodoBody request = new CreateTodoBody();
        request.setTitle(title);
        return todoService.createTodo(request);
    }

    private List<TodoResponse> createTestTodos(List<String> titles) {
        return titles.stream()
                .map(this::createTestTodo)
                .collect(Collectors.toList());
    }
}