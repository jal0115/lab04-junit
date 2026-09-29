package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    // ---------- Parameterized тестүүд ----------

    @ParameterizedTest(name = "{0} оноо -> {1} дүн")
    @DisplayName("Ердийн утгуудын үсгэн дүн")
    @CsvSource({"95,A", "85,B", "75,C", "65,D", "30,F"})
    void typicalGrades(double score, String expected) {
        GradeCalculator calc = new GradeCalculator();
        String actual = calc.letterGrade(score);
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "{0} оноо -> {1} дүн")
    @DisplayName("Дүнгийн хязгаарууд")
    @CsvSource({"100,A", "95,A", "90,A", "89.99,B", "80,B", "79.99,C",
                "70,C", "69.99,D", "60,D", "59.99,F", "0,F"})
    void letterGradeBoundaries(double score, String expected) {
        GradeCalculator calc = new GradeCalculator();
        String actual = calc.letterGrade(score);
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "{0} оноо буруу оролт")
    @DisplayName("Хязгаараас гарсан оноонууд exception шиднэ")
    @ValueSource(doubles = {-1, -0.01, 100.01, 101, 1000})
    void invalidScoresThrow(double score) {
        GradeCalculator calc = new GradeCalculator();
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(score));
    }

    @ParameterizedTest(name = "{0}+{1}+{2}+{3}+{4} = {5}")
    @DisplayName("totalScore зөв нийлбэр тооцно")
    @CsvSource({"10,40,10,10,30,100",
                "0,0,0,0,0,0",
                "8,32,7,8,20,75",
                "5,20,5,5,15,50"})
    void totalScoreValid(double att, double lab, double q1, double q2,
                         double exam, double expected) {
        GradeCalculator calc = new GradeCalculator();
        double actual = calc.totalScore(att, lab, q1, q2, exam);
        assertEquals(expected, actual, DELTA);
    }

    @ParameterizedTest(name = "буруу: {0},{1},{2},{3},{4}")
    @DisplayName("totalScore буруу оролтод exception шиднэ")
    @CsvSource({"-5,20,5,5,15",
                "11,20,5,5,15",
                "5,41,5,5,15",
                "5,20,11,5,15",
                "5,20,5,11,15",
                "5,20,5,5,31",
                "5,20,5,5,-1"})
    void totalScoreInvalidThrows(double att, double lab, double q1,
                                 double q2, double exam) {
        GradeCalculator calc = new GradeCalculator();
        assertThrows(IllegalArgumentException.class,
                () -> calc.totalScore(att, lab, q1, q2, exam));
    }
}
