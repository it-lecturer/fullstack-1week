package core.base.oop.quiz;

import core.base.oop.polymorphism.HPPrinter;

public class Quiz3 {
    public class PrintService {
        private final HPPrinter printer = new HPPrinter(); // 구현체에 강하게 결합

        public void doPrint() {
            printer.print();
        }
    }
}
