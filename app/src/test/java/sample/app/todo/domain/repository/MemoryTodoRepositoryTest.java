
package sample.app.todo.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import sample.app.common.exception.TodoNotFoundException;
import sample.app.todo.domain.entity.Todo;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MemoryTodoRepository 테스트")
class MemoryTodoRepositoryTest {

    private MemoryTodoRepository repository;  // MemoryTodoRepository로 타입 변경
    private Todo testTodo1;
    private Todo testTodo2;
    private Todo testTodo3;

    @BeforeEach
    void setUp() {
        repository = new MemoryTodoRepository();
        repository.clear(); // 각 테스트마다 깨끗한 상태로 시작

        // 테스트용 Todo 생성
        testTodo1 = Todo.of("첫 번째 할일");
        testTodo2 = Todo.of("두 번째 할일");
        testTodo3 = Todo.of("세 번째 할일");
    }

    @Nested
    @DisplayName("save() 테스트")
    class SaveTest {

        @Test
        @DisplayName("새로운 할일 저장 성공")
        void save_Success() {
            // When
            Todo saved = repository.save(testTodo1);

            // Then
            assertNotNull(saved);
            assertNotNull(saved.getId());  // ID가 자동 생성됨
            assertEquals(testTodo1.getTitle(), saved.getTitle());
            assertEquals(testTodo1.isCompleted(), saved.isCompleted());
            assertEquals(1, repository.size());
        }

        @Test
        @DisplayName("여러 개의 할일 저장")
        void save_Multiple() {
            // When
            Todo saved1 = repository.save(testTodo1);
            Todo saved2 = repository.save(testTodo2);
            Todo saved3 = repository.save(testTodo3);

            // Then
            assertEquals(3, repository.size());

            // 모든 Todo가 저장되었는지 확인
            assertTrue(repository.findById(saved1.getId()).isPresent());
            assertTrue(repository.findById(saved2.getId()).isPresent());
            assertTrue(repository.findById(saved3.getId()).isPresent());
        }

        @Test
        @DisplayName("동일한 ID로 재저장 시 업데이트")
        void save_UpdateExisting() {
            // Given
            Todo saved = repository.save(testTodo1);
            assertEquals(1, repository.size());
            UUID originalId = saved.getId();

            // When - 같은 ID를 가진 Todo를 수정 후 재저장
            saved.setTitle("수정된 제목");
            saved.setCompleted(true);
            Todo updated = repository.save(saved);

            // Then
            assertEquals(1, repository.size()); // 사이즈는 그대로
            assertEquals(originalId, updated.getId()); // ID는 그대로
            assertEquals("수정된 제목", updated.getTitle()); // 제목 변경됨
            assertTrue(updated.isCompleted()); // 완료 상태 변경됨
        }

        @Test
        @DisplayName("null Todo 저장 시 NullPointerException")
        void save_NullTodo() {
            // When & Then
            assertThrows(NullPointerException.class, () -> repository.save(null));
        }
    }

    @Nested
    @DisplayName("findAll() 테스트")
    class FindAllTest {

        @Test
        @DisplayName("빈 저장소에서 빈 리스트 반환")
        void findAll_Empty() {
            // When
            List<Todo> todos = repository.findAll();

            // Then
            assertNotNull(todos);
            assertTrue(todos.isEmpty());
            assertEquals(0, todos.size());
        }

        @Test
        @DisplayName("저장된 모든 할일 조회")
        void findAll_WithData() {
            // Given
            Todo saved1 = repository.save(testTodo1);
            Todo saved2 = repository.save(testTodo2);
            Todo saved3 = repository.save(testTodo3);

            // When
            List<Todo> todos = repository.findAll();

            // Then
            assertEquals(3, todos.size());

            // ID로 확인
            Set<UUID> foundIds = new HashSet<>();
            todos.forEach(todo -> foundIds.add(todo.getId()));

            assertTrue(foundIds.contains(saved1.getId()));
            assertTrue(foundIds.contains(saved2.getId()));
            assertTrue(foundIds.contains(saved3.getId()));
        }

        @Test
        @DisplayName("반환된 리스트 수정이 원본에 영향 없음")
        void findAll_ListIsolation() {
            // Given
            repository.save(testTodo1);
            repository.save(testTodo2);

            // When
            List<Todo> todos1 = repository.findAll();
            List<Todo> todos2 = repository.findAll();

            // Then
            assertEquals(2, todos1.size());
            assertEquals(2, todos2.size());

            // 리스트를 수정해도 다른 리스트나 저장소에 영향 없음
            todos1.clear();
            assertEquals(0, todos1.size());
            assertEquals(2, todos2.size());
            assertEquals(2, repository.size());
        }
    }

    @Nested
    @DisplayName("findById() 테스트")
    class FindByIdTest {

        @Test
        @DisplayName("존재하는 ID로 조회 성공")
        void findById_Exists() {
            // Given
            Todo saved = repository.save(testTodo1);

            // When
            Optional<Todo> found = repository.findById(saved.getId());

            // Then
            assertTrue(found.isPresent());
            assertEquals(saved.getId(), found.get().getId());
            assertEquals(saved.getTitle(), found.get().getTitle());
            assertEquals(saved.isCompleted(), found.get().isCompleted());
        }

        @Test
        @DisplayName("존재하지 않는 ID로 조회 시 빈 Optional 반환")
        void findById_NotExists() {
            // Given
            UUID nonExistentId = UUID.randomUUID();

            // When
            Optional<Todo> found = repository.findById(nonExistentId);

            // Then
            assertFalse(found.isPresent());
            assertTrue(found.isEmpty());
        }

        @Test
        @DisplayName("null ID로 조회 시 빈 Optional 반환")
        void findById_NullId() {
            // When
            Optional<Todo> found = repository.findById(null);

            // Then
            assertFalse(found.isPresent());
        }

        @Test
        @DisplayName("여러 Todo 중 특정 ID로 조회")
        void findById_MultipleData() {
            // Given
            Todo saved1 = repository.save(testTodo1);
            Todo saved2 = repository.save(testTodo2);
            Todo saved3 = repository.save(testTodo3);

            // When
            Optional<Todo> found = repository.findById(saved2.getId());

            // Then
            assertTrue(found.isPresent());
            assertEquals(saved2.getId(), found.get().getId());
            assertEquals(saved2.getTitle(), found.get().getTitle());
        }
    }

    @Nested
    @DisplayName("save()로 업데이트 테스트")  // update() -> save()로 변경
    class UpdateTest {

        @Test
        @DisplayName("존재하는 할일 업데이트 성공")
        void save_UpdateExisting() throws InterruptedException {
            // Given
            Todo saved = repository.save(testTodo1);
            String originalCreatedAt = saved.getCreatedAt();
            String originalUpdatedAt = saved.getUpdatedAt();
            UUID originalId = saved.getId();

            // 시간 차이를 위해 잠시 대기
            Thread.sleep(10);

            // 기존 Todo 수정
            saved.setTitle("업데이트된 제목");
            saved.setCompleted(true);

            // When
            Todo updated = repository.save(saved);  // save()로 업데이트

            // Then
            Optional<Todo> foundTodo = repository.findById(originalId);
            assertTrue(foundTodo.isPresent());

            Todo updatedTodo = foundTodo.get();
            assertEquals(originalId, updatedTodo.getId()); // ID는 그대로
            assertEquals("업데이트된 제목", updatedTodo.getTitle()); // 제목 변경
            assertTrue(updatedTodo.isCompleted()); // 완료 상태 변경
            assertEquals(originalCreatedAt, updatedTodo.getCreatedAt()); // 생성일은 그대로
            assertNotEquals(originalUpdatedAt, updatedTodo.getUpdatedAt()); // 수정일은 변경
        }

        @Test
        @DisplayName("존재하지 않는 ID로 새로운 할일 생성")
        void save_CreateNew() {
            // Given
            Todo newTodo = Todo.of("새로운 할일");

            // When
            Todo saved = repository.save(newTodo);

            // Then
            assertNotNull(saved.getId());
            assertEquals("새로운 할일", saved.getTitle());
            assertFalse(saved.isCompleted());
            assertEquals(1, repository.size());
        }

        @Test
        @DisplayName("부분 업데이트 - 제목과 완료 상태 변경")
        void save_PartialUpdate() {
            // Given
            testTodo1.setCompleted(true); // 초기값 설정
            Todo saved = repository.save(testTodo1);

            // 기존 Todo 수정
            saved.setTitle("제목만 변경");
            saved.setCompleted(false); // 다른 값으로 설정

            // When
            repository.save(saved);  // save()로 업데이트

            // Then
            Optional<Todo> updated = repository.findById(saved.getId());
            assertTrue(updated.isPresent());

            Todo updatedTodo = updated.get();
            assertEquals("제목만 변경", updatedTodo.getTitle());
            assertFalse(updatedTodo.isCompleted()); // completed도 업데이트됨
        }
    }

    @Nested
    @DisplayName("deleteById() 테스트")
    class DeleteByIdTest {

        @Test
        @DisplayName("존재하는 할일 삭제 성공")
        void deleteById_Success() {
            // Given
            Todo saved1 = repository.save(testTodo1);
            Todo saved2 = repository.save(testTodo2);
            assertEquals(2, repository.size());

            // When
            repository.deleteById(saved1.getId());

            // Then
            assertEquals(1, repository.size());
            assertFalse(repository.findById(saved1.getId()).isPresent());
            assertTrue(repository.findById(saved2.getId()).isPresent());
        }

        @Test
        @DisplayName("존재하지 않는 할일 삭제 시 예외 발생")
        void deleteById_NotExists() {
            // Given
            UUID nonExistentId = UUID.randomUUID();

            // When & Then
            TodoNotFoundException exception = assertThrows(
                    TodoNotFoundException.class,
                    () -> repository.deleteById(nonExistentId)
            );

            assertNotNull(exception.getMessage());
        }

        @Test
        @DisplayName("마지막 할일 삭제 후 빈 저장소")
        void deleteById_LastItem() {
            // Given
            Todo saved = repository.save(testTodo1);
            assertEquals(1, repository.size());

            // When
            repository.deleteById(saved.getId());

            // Then
            assertEquals(0, repository.size());
            assertTrue(repository.findAll().isEmpty());
        }

        @Test
        @DisplayName("동일한 ID 중복 삭제 시 예외 발생")
        void deleteById_DuplicateDelete() {
            // Given
            Todo saved = repository.save(testTodo1);

            // When
            repository.deleteById(saved.getId());

            // Then
            assertThrows(
                    TodoNotFoundException.class,
                    () -> repository.deleteById(saved.getId())
            );
        }
    }

    @Nested
    @DisplayName("deleteBulk() 테스트")
    class DeleteBulkTest {

        @Test
        @DisplayName("여러 할일 일괄 삭제 성공")
        void deleteBulk_Success() {
            // Given
            Todo saved1 = repository.save(testTodo1);
            Todo saved2 = repository.save(testTodo2);
            Todo saved3 = repository.save(testTodo3);
            assertEquals(3, repository.size());

            List<UUID> idsToDelete = Arrays.asList(saved1.getId(), saved3.getId());

            // When
            repository.deleteBulk(idsToDelete);

            // Then
            assertEquals(1, repository.size());
            assertFalse(repository.findById(saved1.getId()).isPresent());
            assertTrue(repository.findById(saved2.getId()).isPresent());
            assertFalse(repository.findById(saved3.getId()).isPresent());
        }

        @Test
        @DisplayName("존재하지 않는 ID 포함 시 예외 발생")
        void deleteBulk_NonExistentId() {
            // Given
            Todo saved1 = repository.save(testTodo1);
            Todo saved2 = repository.save(testTodo2);

            UUID nonExistentId = UUID.randomUUID();
            List<UUID> idsToDelete = Arrays.asList(saved1.getId(), nonExistentId, saved2.getId());

            // When & Then
            TodoNotFoundException exception = assertThrows(
                    TodoNotFoundException.class,
                    () -> repository.deleteBulk(idsToDelete)
            );

            // 예외 발생 시 아무것도 삭제되지 않음 (트랜잭션 개념)
            assertEquals(2, repository.size());
            assertTrue(repository.findById(saved1.getId()).isPresent());
            assertTrue(repository.findById(saved2.getId()).isPresent());
        }

        @Test
        @DisplayName("빈 리스트로 일괄 삭제")
        void deleteBulk_EmptyList() {
            // Given
            repository.save(testTodo1);
            repository.save(testTodo2);
            int originalSize = repository.size();

            // When
            repository.deleteBulk(Collections.emptyList());

            // Then
            assertEquals(originalSize, repository.size()); // 변화 없음
            assertTrue(repository.findById(testTodo1.getId()).isPresent());
            assertTrue(repository.findById(testTodo2.getId()).isPresent());
        }

        @Test
        @DisplayName("전체 할일 일괄 삭제")
        void deleteBulk_All() {
            // Given
            Todo saved1 = repository.save(testTodo1);
            Todo saved2 = repository.save(testTodo2);
            Todo saved3 = repository.save(testTodo3);

            List<UUID> allIds = Arrays.asList(saved1.getId(), saved2.getId(), saved3.getId());

            // When
            repository.deleteBulk(allIds);

            // Then
            assertEquals(0, repository.size());
            assertTrue(repository.findAll().isEmpty());
        }

        @Test
        @DisplayName("중복된 ID가 포함된 일괄 삭제")
        void deleteBulk_DuplicateIds() {
            // Given
            Todo saved1 = repository.save(testTodo1);
            Todo saved2 = repository.save(testTodo2);

            List<UUID> idsWithDuplicates = Arrays.asList(
                    saved1.getId(),
                    saved2.getId(),
                    saved1.getId() // 중복
            );

            // When
            repository.deleteBulk(idsWithDuplicates);

            // Then
            assertEquals(0, repository.size()); // 모두 삭제됨
        }
    }

    @Nested
    @DisplayName("clear() 테스트")
    class ClearTest {

        @Test
        @DisplayName("저장소 전체 초기화")
        void clear_Success() {
            // Given
            Todo saved1 = repository.save(testTodo1);
            Todo saved2 = repository.save(testTodo2);
            Todo saved3 = repository.save(testTodo3);
            assertEquals(3, repository.size());

            // When
            repository.clear();

            // Then
            assertEquals(0, repository.size());
            assertTrue(repository.findAll().isEmpty());
            assertFalse(repository.findById(saved1.getId()).isPresent());
            assertFalse(repository.findById(saved2.getId()).isPresent());
            assertFalse(repository.findById(saved3.getId()).isPresent());
        }

        @Test
        @DisplayName("빈 저장소에 clear() 호출")
        void clear_EmptyRepository() {
            // Given
            assertEquals(0, repository.size());

            // When
            repository.clear();

            // Then
            assertEquals(0, repository.size());
            assertTrue(repository.findAll().isEmpty());
        }

        @Test
        @DisplayName("clear() 후 새로운 데이터 저장")
        void clear_ThenSaveNew() {
            // Given
            Todo saved1 = repository.save(testTodo1);
            repository.clear();

            // When
            Todo saved2 = repository.save(testTodo2);

            // Then
            assertEquals(1, repository.size());
            assertFalse(repository.findById(saved1.getId()).isPresent());
            assertTrue(repository.findById(saved2.getId()).isPresent());
        }
    }

    @Nested
    @DisplayName("size() 테스트")
    class SizeTest {

        @Test
        @DisplayName("빈 저장소의 크기는 0")
        void size_Empty() {
            // When & Then
            assertEquals(0, repository.size());
        }

        @Test
        @DisplayName("데이터 추가에 따른 크기 변화")
        void size_AddData() {
            // Given & When & Then
            assertEquals(0, repository.size());

            repository.save(testTodo1);
            assertEquals(1, repository.size());

            repository.save(testTodo2);
            assertEquals(2, repository.size());

            repository.save(testTodo3);
            assertEquals(3, repository.size());
        }

        @Test
        @DisplayName("데이터 삭제에 따른 크기 변화")
        void size_DeleteData() {
            // Given
            Todo saved1 = repository.save(testTodo1);
            Todo saved2 = repository.save(testTodo2);
            Todo saved3 = repository.save(testTodo3);
            assertEquals(3, repository.size());

            // When & Then
            repository.deleteById(saved1.getId());
            assertEquals(2, repository.size());

            repository.deleteBulk(Arrays.asList(saved2.getId(), saved3.getId()));
            assertEquals(0, repository.size());
        }

        @Test
        @DisplayName("동일한 ID 재저장 시 크기 유지")
        void size_OverwriteSame() {
            // Given
            Todo saved = repository.save(testTodo1);
            assertEquals(1, repository.size());

            // When - 동일한 Todo 재저장 (업데이트)
            saved.setTitle("수정된 제목");
            repository.save(saved);

            // Then
            assertEquals(1, repository.size()); // 크기 변화 없음
        }
    }

    @Nested
    @DisplayName("통합 시나리오 테스트")
    class IntegrationTest {

        @Test
        @DisplayName("전체 기능 통합 시나리오")
        void fullIntegrationScenario() {
            // 1. 초기 상태 확인
            assertEquals(0, repository.size());
            assertTrue(repository.findAll().isEmpty());

            // 2. 데이터 저장
            Todo saved1 = repository.save(testTodo1);
            Todo saved2 = repository.save(testTodo2);
            Todo saved3 = repository.save(testTodo3);
            assertEquals(3, repository.size());

            // 3. 조회 테스트
            List<Todo> all = repository.findAll();
            assertEquals(3, all.size());
            assertTrue(repository.findById(saved2.getId()).isPresent());

            // 4. 업데이트 테스트 (save로 업데이트)
            saved1.setTitle("업데이트된 할일");
            saved1.setCompleted(true);
            repository.save(saved1);  // save()로 업데이트

            Todo updated = repository.findById(saved1.getId()).get();
            assertEquals("업데이트된 할일", updated.getTitle());
            assertTrue(updated.isCompleted());

            // 5. 개별 삭제
            repository.deleteById(saved2.getId());
            assertEquals(2, repository.size());
            assertFalse(repository.findById(saved2.getId()).isPresent());

            // 6. 일괄 삭제
            repository.deleteBulk(Arrays.asList(saved1.getId(), saved3.getId()));
            assertEquals(0, repository.size());

            // 7. 최종 상태 확인
            assertTrue(repository.findAll().isEmpty());
        }

        @Test
        @DisplayName("대량 데이터 처리 시나리오")
        void bulkDataScenario() {
            // Given - 대량 데이터 생성
            int count = 1000;
            List<Todo> todos = new ArrayList<>();
            List<UUID> evenIds = new ArrayList<>();
            List<UUID> oddIds = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                Todo todo = Todo.of("대량 테스트 " + i);
                Todo saved = repository.save(todo);
                todos.add(saved);

                if (i % 2 == 0) {
                    evenIds.add(saved.getId());
                } else {
                    oddIds.add(saved.getId());
                }
            }

            // When & Then - 전체 조회
            assertEquals(count, repository.size());
            assertEquals(count, repository.findAll().size());

            // 짝수 번째 일괄 삭제
            repository.deleteBulk(evenIds);
            assertEquals(count / 2, repository.size());

            // 나머지 개별 삭제
            for (UUID id : oddIds) {
                repository.deleteById(id);
            }

            // 최종 확인
            assertEquals(0, repository.size());
            assertTrue(repository.findAll().isEmpty());
        }
    }
}