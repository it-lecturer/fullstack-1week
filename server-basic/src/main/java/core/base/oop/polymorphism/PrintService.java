package core.base.oop.polymorphism;

public class PrintService {
    private final Printer printer;

    public PrintService(Printer printer) {
        this.printer = printer;
    }

    public String doPrint() {
        return printer.print();
    }
}
