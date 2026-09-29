package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeCalculatorTest {

    private static final double DELTA = 0.0001;

    // ---------- letterGrade: хязгаарын утгууд ----------

    @Test
    @DisplayName("90 оноо яг A дүн байх ёстой (хязгаарын тохиолдол)")
    void ninetyIsExactlyA() {
        GradeCalculator calc = new GradeCalculator();      // Arrange
        String grade = calc.letterGrade(90.0);             // Act
        assertEquals("A", grade);                          // Assert
    }

    @Test
    @DisplayName("89.99 оноо B дүн байх ёстой (A-ийн хязгаарын доод талд)")
    void eightyNinePoint99IsB() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(89.99);
        assertEquals("B", grade);
    }

    @Test
    @DisplayName("60 оноо яг D дүн байх ёстой (хязгаарын тохиолдол)")
    void sixtyIsExactlyD() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(60.0);
        assertEquals("D", grade);
    }

    @Test
    @DisplayName("59.99 оноо F дүн байх ёстой (тэнцэхгүй)")
    void fiftyNinePoint99IsF() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(59.99);
        assertEquals("F", grade);
    }

    @Test
    @DisplayName("0 оноо (доод хязгаар) F дүн байх ёстой")
    void zeroIsF() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(0.0);
        assertEquals("F", grade);
    }

    @Test
    @DisplayName("100 оноо (дээд хязгаар) A дүн байх ёстой")
    void hundredIsA() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(100.0);
        assertEquals("A", grade);
    }

    // ---------- letterGrade: буруу оролт ----------

    @Test
    @DisplayName("-1 оноо буруу оролт тул IllegalArgumentException шиднэ")
    void negativeScoreThrows() {
        GradeCalculator calc = new GradeCalculator();
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-1));
    }

    @Test
    @DisplayName("101 оноо буруу оролт тул IllegalArgumentException шиднэ")
    void scoreAbove100Throws() {
        GradeCalculator calc = new GradeCalculator();
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(101));
    }

    // ---------- totalScore ----------

    @Test
    @DisplayName("Бүх оноо дээд хэмжээндээ байвал нийлбэр 100 гарна")
    void totalScoreMaxIs100() {
        GradeCalculator calc = new GradeCalculator();
        double total = calc.totalScore(10, 40, 10, 10, 30);
        assertEquals(100.0, total, DELTA);
    }

    @Test
    @DisplayName("Ирц сөрөг (-5) байвал IllegalArgumentException шиднэ")
    void totalScoreNegativeAttThrows() {
        GradeCalculator calc = new GradeCalculator();
        assertThrows(IllegalArgumentException.class,
                () -> calc.totalScore(-5, 20, 5, 5, 15));
    }

    @Test
    @DisplayName("Лаб 41 (дээд хязгаар 40-өөс хэтэрсэн) бол IllegalArgumentException шиднэ")
    void totalScoreLabAboveMaxThrows() {
        GradeCalculator calc = new GradeCalculator();
        assertThrows(IllegalArgumentException.class,
                () -> calc.totalScore(5, 41, 5, 5, 15));
    }
}
