package sample.app.todo.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import sample.app.todo.api.dto.request.create.CreateTodoBody;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    @Column(name = "completed")
    @Setter
    private boolean completed;

    private String createdAt = ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

    @Setter
    private String updatedAt = ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }


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
