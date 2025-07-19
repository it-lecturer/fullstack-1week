package sample.app.todo.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import sample.app.todo.api.dto.request.create.CreateTodoBody;

import java.util.UUID;

@Entity
@Table(name = "todo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Todo {
    @Id
    @Column(name = "todo_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();

    @Column(name = "title")
    @Setter
    private String title;

    private Todo(String title) {
        this.title = title;
    }

    // CreateTodoBody로부터 Todo 생성
    public static Todo of(CreateTodoBody request) {
        return new Todo(request.getTitle());
    }

    // title로 직접 생성
    public static Todo of(String title) {
        return new Todo(title);
    }
}
