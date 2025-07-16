package core.base.oop.quiz;

public class QuizAnswer1 {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        // 유효성 검증도 가능해짐
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어있을 수 없습니다.");
        }
        this.name = name;
    }
}
