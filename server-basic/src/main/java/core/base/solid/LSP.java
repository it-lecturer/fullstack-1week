package core.base.solid;

/**
 * 3. LSP (Liskov Substitution Principle) - 리스코프 치환 원칙
 * 자식 클래스는 부모 클래스를 대체할 수 있어야 한다
 */
public class LSP {

    /**
     * Square가 Rectangle 의 규칙을 깨뜨림
     * Rectangle rect = new Square(); 일 때 예상치 못한 결과 발생 가능
     */
    static class Before {
        class Rectangle {
            int width, height;

            public void setWidth(int w) { this.width = w; }
            public void setHeight(int h) { this.height = h; }

            public int getArea() { return width * height; }
        }

        class Square extends Rectangle {
            @Override
            public void setWidth(int w) {
                this.width = this.height = w;
            }

            @Override
            public void setHeight(int h) {
                this.width = this.height = h;
            }
        }
    }

    /**
     * 상속 대신 공통 인터페이스로 분리
     * 자식 클래스가 부모의 계약을 위반하지 않게 하자
     */
    static class After {
        interface Shape {
            int getArea();
        }

        class Rectangle implements Shape {
            int width, height;

            public Rectangle(int w, int h) {
                this.width = w;
                this.height = h;
            }

            @Override
            public int getArea() { return width * height; }
        }

        class Square implements Shape {
            int side;
            public Square(int s) { this.side = s; }

            @Override
            public int getArea() { return side * side; }
        }
    }
}
