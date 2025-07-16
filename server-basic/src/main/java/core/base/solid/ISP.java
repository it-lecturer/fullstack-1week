package core.base.solid;

/**
 * ISP (Interface Segregation Principle) - 인터페이스 분리 원칙
 * 인터페이스 분리원칙 - 클라이언트는 자신이 사용하지 않는 메서드에 의존하지 않아야 한다
 */
public class ISP {
    static class Before {
        interface MultiFunctionPrinter {
            void print();
            void scan();
            void fax();
            void copy();
        }

        class SimplePrinter implements MultiFunctionPrinter {
            @Override
            public void print() {
                System.out.println("Printing...");
            }

            @Override
            public void scan() {
                // 이 프린터는 스캔 기능이 없지만, 인터페이스 때문에 강제로 구현해야 함
                throw new UnsupportedOperationException("Scan not supported.");
            }

            @Override
            public void fax() {
                // 이 프린터는 팩스 기능이 없지만, 인터페이스 때문에 강제로 구현해야 함
                throw new UnsupportedOperationException("Fax not supported.");
            }

            @Override
            public void copy() {
                // 이 프린터는 복사 기능이 없지만, 인터페이스 때문에 강제로 구현해야 함
                throw new UnsupportedOperationException("Copy not supported.");
            }
        }
    }

    static class After {
        interface Printer {
            void print();
        }

        interface Scanner {
            void scan();
        }

        interface FaxMachine {
            void fax();
        }

        interface Copier {
            void copy();
        }

        // 이제 필요한 기능만 구현하면 됩니다.
        class SimplePrinter implements Printer {
            @Override
            public void print() {
                System.out.println("Printing...");
            }
        }

        class PhotoCopier implements Printer, Scanner, Copier { // 팩스 기능은 없음
            @Override
            public void print() {
                System.out.println("Printing from Copier...");
            }
            @Override
            public void scan() {
                System.out.println("Scanning...");
            }
            @Override
            public void copy() {
                System.out.println("Copying...");
            }
        }

        // 모든 기능을 가진 복합기는 모든 인터페이스를 구현
        class AllInOnePrinter implements Printer, Scanner, FaxMachine, Copier {
            @Override
            public void print() { System.out.println("All-in-one Printing..."); }
            @Override
            public void scan() { System.out.println("All-in-one Scanning..."); }
            @Override
            public void fax() { System.out.println("All-in-one Faxing..."); }
            @Override
            public void copy() { System.out.println("All-in-one Copying..."); }
        }
    }
}
