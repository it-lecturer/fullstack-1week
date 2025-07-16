package core.base.oop.quiz;

public class QuizAnswer2 {
    static abstract class Animal {
        public abstract void sound();
    }

    static class Dog extends Animal {
        @Override
        public void sound() {
            System.out.println("멍멍");
        }
    }

    static class Cat extends Animal {
        @Override
        public void sound() {
            System.out.println("야옹");
        }
    }

    public class AnimalSoundTest {
        public static void main(String[] args) {
            Animal dog = new Dog();
            Animal cat = new Cat();

            dog.sound(); // 멍멍
            cat.sound(); // 야옹
        }
    }
}
