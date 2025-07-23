package sample.app.todo.domain.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import sample.app.common.annotation.UUIDv7;
import sample.app.todo.api.dto.request.create.CreateTodoBody;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "todo")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class Todo {
    @Id
    @Column(name = "todo_id")
    private UUID id;

    @Column(name = "title", length = 1000)
    private String title;

    @Column(name = "completed")
    private boolean completed;

    @Column(name = "created_at")
    private String createdAt = ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

    @Column(name = "updated_at")
    private String updatedAt = ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

    @PrePersist
    public void prePersist() {
        this.createdAt = ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }


    private Todo(String title) {
        this.id = UuidCreator.getTimeOrderedEpoch();
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
