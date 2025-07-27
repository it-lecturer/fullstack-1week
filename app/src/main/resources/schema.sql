CREATE TABLE todo (
                      todo_id CHAR(36) NOT NULL PRIMARY KEY,
                      title VARCHAR(1000),
                      completed BOOLEAN NOT NULL DEFAULT FALSE,
                      created_at VARCHAR(50) NOT NULL,
                      updated_at VARCHAR(50) NOT NULL
);

-- completed 상태 검색을 위한 인덱스 (선택 사항)
CREATE INDEX idx_todo_completed ON todo (completed);