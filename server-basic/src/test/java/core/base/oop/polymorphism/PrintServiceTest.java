package core.base.oop.polymorphism;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PrintServiceTest {
    @Test
    @DisplayName("캐논 프린터 출력")
    void doCanonPrint() {
        // given
        Printer printer = new CanonPrinter();


        // when
        PrintService printService = new PrintService(printer);
        String result = printService.doPrint();

        // then
        assertThat(result).isEqualTo("캐논 출력물");
    }

    @Test
    @DisplayName("HP 프린터 출력")
    void doHPPrint() {
        // given
        Printer printer = new HPPrinter();


        // when
        PrintService printService = new PrintService(printer);
        String result = printService.doPrint();

        // then
        assertThat(result).isEqualTo("HP 출력물");
    }
}