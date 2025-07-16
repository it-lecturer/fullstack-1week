package core.base.oop.polymorphism;

public class HPPrinter implements Printer {

    @Override
    public String print() {
        System.out.println("HP 프린터 출력");
        return "HP 출력물";
    }
}
