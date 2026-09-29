# Лаборатори №4: Нэгжийн тест — JUnit 5

**Оюутан:** Г. Лувсанжал
**Код:** B242270016

## Хувилбарууд

```
java version "23.0.1" 2024-10-15
Java(TM) SE Runtime Environment (build 23.0.1+11-39)
Java HotSpot(TM) 64-Bit Server VM (build 23.0.1+11-39, mixed mode, sharing)
```

```
Apache Maven 3.9.9
Maven home: /usr/share/maven
Java version: 23.0.1, vendor: Oracle Corporation, runtime: /usr/lib/jvm/jdk-23
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "6.12.107+deb13-amd64", arch: "amd64", family: "unix"
```

(pom.xml дотор `maven.compiler.release=17` тохируулсан тул JDK 23-оор Java 17 түвшинд compile хийгдэнэ.)

## Үр дүн

- Тестийн методын тоо: 16 (11 `@Test` + 5 `@ParameterizedTest`)
- `results/mvn-test.txt`-ийн `Tests run`: 43 (Failures: 0, Errors: 0, Skipped: 0), BUILD SUCCESS
- Мутаци (`score >= 90` → `score > 90`): `results/mvn-test-mutant.txt`-д Failures: 2, BUILD FAILURE. Унасан тестүүд: `ninetyIsExactlyA`, `letterGradeBoundaries[3]` (90,A тохиолдол) — хоёулаа `expected: <A> but was: <B>`

## Дүгнэлт

Энэ лабораторид Maven төслийг quickstart archetype-ээр үүсгэж, JUnit 5.10.2 болон maven-surefire-plugin 3.2.5-ыг тохируулсан. GradeCalculator классын letterGrade ба totalScore методуудыг хэрэгжүүлж, 11 энгийн тест болон 5 parameterized тест бичсэн бөгөөд Surefire нь параметрчилсэн тестийн мөр бүрийг тусдаа тест гэж тоолсноор нийт 43 тест ажилласан. Хязгаарын утгууд (90, 89.99, 60, 59.99, 0, 100), буруу оролт (-1, 101, сөрөг оноо, дээд хязгаараас хэтэрсэн лаб оноо) зэргийг тусгайлан шалгасан. Мутаци хийхдээ letterGrade доторх `score >= 90` нөхцөлийг `score > 90` болгож дараад тест ажиллуулахад 43-аас зөвхөн 2 тест унасан: `ninetyIsExactlyA` болон `letterGradeBoundaries`-ийн `90,A` тохиолдол, хоёулаа `expected: <A> but was: <B>` гэсэн мэдээлэлтэй унасан. Хамгийн сонирхолтой нь: 95→A шалгасан `typicalGrades` тест мутантад огт мэдрэгдээгүй, учир нь 95 нь `>` болон `>=`-ийн аль алинд A өгдөг тул зөвхөн яг 90-ийг шалгасан хязгаарын тестүүд л энэ нарийн алдааг барьж чадсан. Энэ нь ердийн (typical) утгаар тестлэх нь хангалтгүй, хязгаарын утгаар тестлэх нь чухал болохыг практикт харуулсан жишээ юм. Мутацийн дараа кодыг эргүүлэн `score >= 90` болгож засаад mvn test ажиллуулахад дахин 43 тест бүгд ногоон, BUILD SUCCESS болсон.
