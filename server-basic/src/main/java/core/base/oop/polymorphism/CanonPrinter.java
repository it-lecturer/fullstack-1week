package core.base.oop.polymorphism;


public class CanonPrinter implements Printer {

    @Override
    public String print() {
        System.out.println("캐논 프린터 출력");
        return "캐논 출력물";
    }
}