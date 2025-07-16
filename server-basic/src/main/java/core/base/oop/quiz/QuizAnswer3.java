package core.base.oop.quiz;

public class QuizAnswer3 {
    public interface Printer {
        void print();
    }

    static class HPPrinter implements Printer {
        public void print() {
            System.out.println("HP 프린터 출력");
        }
    }

    static class CanonPrinter implements Printer {
        public void print() {
            System.out.println("캐논 프린터 출력");
        }
    }

    static class PrintService {
        private final Printer printer;

        public PrintService(Printer printer) {
            this.printer = printer;
        }

        public void doPrint() {
            printer.print();
        }
    }

    public class PrintMain {
        public static void main(String[] args) {
            Printer printer = new HPPrinter();
            PrintService service = new PrintService(printer);
            service.doPrint(); // HP 프린터 출력

            printer = new CanonPrinter();
            service = new PrintService(printer);
            service.doPrint(); // 캐논 프린터 출력
        }
    }
}
