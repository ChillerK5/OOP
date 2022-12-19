package ru.nsu.kbagryantsev;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class CreditBookTest {
    @Nested
    @DisplayName("Subject test")
    class SubjectTest {
        Teacher seminarian = new Teacher("Alexander", "Raish", "Vladimirovich");
        Teacher anonymous = new Teacher("John", "Galt");
        Subject successiveAdding;
        Subject listAdding;

        @BeforeEach
        @Test
        @DisplayName("Instantiating via successive single adds")
        void successiveAdding() {
            successiveAdding = new Subject("Operational systems")
                    .addTeacher("Dmitriy", "Irtegov", "Valentinovich")
                    .addTeacher("John", "Galt")
                    .addTeacher(anonymous);
            int sampleSize = successiveAdding.getTeachers().size();
            int modelSize = 2;
            Assertions.assertEquals(modelSize, sampleSize);
        }

        @BeforeEach
        @Test
        @DisplayName("Instantiating via list adding")
        void listAdding() {
            Set<Teacher> data = Set.of(seminarian, anonymous);
            Set<Teacher> teachers = new HashSet<>(data);
            listAdding = new Subject("OOP", teachers);
            int sample = listAdding.getTeachers().size();
            int model = 2;
            Assertions.assertEquals(model, sample);
        }

        @Test
        @DisplayName("Getting subject name")
        void getName() {
            String sample = listAdding.getName();
            String model = "OOP";
            Assertions.assertEquals(model, sample);
            sample = successiveAdding.getName();
            model = "Operational systems";
            Assertions.assertEquals(model, sample);
        }

        @Test
        @DisplayName("Getting subject teachers")
        void getTeachers() {
            Collection<Teacher> teachers = listAdding.getTeachers();
            Assertions.assertTrue(teachers.contains(seminarian));
            Assertions.assertTrue(teachers.contains(anonymous));
        }

        @Test
        @DisplayName("Removing single teacher")
        void removeSingleTeacher() {
            listAdding.removeTeacher(anonymous);
            int sample = listAdding.getTeachers().size();
            int model = 1;
            Assertions.assertEquals(model, sample);
        }

        @Test
        @DisplayName("Removing all teachers and even more")
        void removeMultiTeacher() {
            listAdding.removeTeacher(anonymous).removeTeacher(seminarian);
            int sample = listAdding.getTeachers().size();
            int model = 0;
            Assertions.assertEquals(model, sample);
            Executable extraAction = () -> listAdding.removeTeacher(anonymous);
            Assertions.assertDoesNotThrow(extraAction);
            sample = listAdding.getTeachers().size();
            Assertions.assertEquals(model, sample);
        }
    }

    @Nested
    @DisplayName("Term test")
    class TermTest {
        Teacher lecturer = new Teacher("Dmitriy", "Irtegov", "Valentinovich");
        Teacher seminarian = new Teacher("Ivan", "Bukshev", "Evgenievich");
        Subject operationalSystems = new Subject("Operational systems")
                .addTeacher(lecturer)
                .addTeacher(seminarian);

        Subject pythonBasics = new Subject("Python basics")
                .addTeacher(lecturer);

        Term term = new Term();

        @Test
        @DisplayName("Adding several records")
        void addingRecords() {
            term
                    .addRecord(new Record(operationalSystems, lecturer, 3))
                    .addRecord(new Record(pythonBasics, lecturer, 5));
            int sample = term.getRecords().size();
            int model = 2;
            Assertions.assertEquals(model, sample);
        }

        @Test
        @DisplayName("Adding an illegal access record")
        void addingIllegalRecord() {
            term
                    .addRecord(new Record(operationalSystems, lecturer, 3))
                    .addRecord(new Record(pythonBasics, lecturer, 5));
            Record illegalRecord = new Record(pythonBasics, seminarian, 5);
            Executable illegalAccess = () -> term.addRecord(illegalRecord);
            assertThrows(IllegalArgumentException.class, illegalAccess);
        }

        @Test
        @DisplayName("Adding a duplicate subject record")
        void addingDuplicateRecord() {
            term
                    .addRecord(new Record(operationalSystems, lecturer, 3))
                    .addRecord(new Record(pythonBasics, lecturer, 5));
            Record duplicateRecord;
            duplicateRecord = new Record(operationalSystems, seminarian, 5);
            Executable duplicateAccess = () -> term.addRecord(duplicateRecord);
            assertThrows(IllegalArgumentException.class, duplicateAccess);
        }

        @Test
        @DisplayName("Deleting a single record")
        void deleteRecord() {
            Record osMark = new Record(operationalSystems, lecturer, 3);
            term
                    .addRecord(osMark)
                    .addRecord(new Record(pythonBasics, lecturer, 5));
            term.deleteRecord(osMark);
            int sample = term.getRecords().size();
            int model = 1;
            Assertions.assertEquals(model, sample);
        }
    }

    @Nested
    @DisplayName("Credit book test")
    class MainTest {
        Teacher lecturer = new Teacher("Dmitriy", "Irtegov", "Valentinovich");
        Teacher seminarian = new Teacher("Ivan", "Bukshev", "Evgenievich");
        Subject operationalSystems = new Subject("Operational systems")
                .addTeacher(lecturer)
                .addTeacher(seminarian);
        Subject pythonBasics = new Subject("Python basics")
                .addTeacher(lecturer);
        Subject iosDevelopment = new Subject("iOs development")
                .addTeacher(seminarian);
        CreditBook creditBook = new CreditBook();

        @Test
        @DisplayName("Adding a single term")
        void addingSingleTerm() {
            creditBook.addTerm();
            int sample = creditBook.getTerms().size();
            int model = 2;
            Assertions.assertEquals(model, sample);
        }

        @Test
        @DisplayName("Getting current term, when there is only one present")
        void gettingSingleTerm() {
            Assertions.assertDoesNotThrow(creditBook::getCurrentTerm);
        }

        @Test
        @DisplayName("Getting current term when there are plenty")
        void gettingMultipleTerm() {
            creditBook.addTerm();
            Term model = creditBook.addTerm();
            Term sample = creditBook.getCurrentTerm();
            Assertions.assertEquals(model, sample);
        }

        @Test
        @DisplayName("Adding several records")
        void addingRecord() {
            Record osMark = new Record(operationalSystems, lecturer, 3);
            creditBook.addRecord(osMark);
            creditBook.addRecord(pythonBasics, lecturer, 5);
            Term current = creditBook.getCurrentTerm();
            Assertions.assertTrue(current.getRecords().contains(osMark));
            boolean hasLastRecord = current
                    .getRecords()
                    .stream()
                    .anyMatch(r -> r.subject() == pythonBasics);
            Assertions.assertTrue(hasLastRecord);
        }

        @Test
        @DisplayName("Getting credit book's ID")
        void gettingId() {
            Assertions.assertDoesNotThrow(creditBook::getIdentity);
        }

        @Test
        @DisplayName("Qualifying work interactions")
        void actionsQualifyingWork() {
            creditBook.setQualifyingWork(5);
            int model = 5;
            int sample = creditBook.getQualifyingWork();
            Assertions.assertEquals(model, sample);
        }

        @Test
        @DisplayName("Mean grade of several terms")
        void calculatingTotal() {
            creditBook.addRecord(operationalSystems, lecturer, 3);
            creditBook.addRecord(pythonBasics, lecturer, 4);
            creditBook.addTerm();
            creditBook.addRecord(iosDevelopment, seminarian, 4);
            creditBook.addRecord(operationalSystems, lecturer, 5);
            creditBook.addRecord(pythonBasics, lecturer, 3);
            double model = 3.8;
            double sample = creditBook.averageGrade();
            Assertions.assertEquals(model, sample);
        }

        @Test
        @DisplayName("Mean grade with no records")
        void calculatingIllegal() {
            Executable illegalDivision = creditBook::averageGrade;
            Assertions.assertThrows(ArithmeticException.class, illegalDivision);
        }

        @Test
        @DisplayName("Extra grant for several semesters")
        void extraGrant() {
            creditBook.addRecord(operationalSystems, lecturer, 3);
            creditBook.addRecord(iosDevelopment, seminarian, 5);
            Term current = creditBook.getCurrentTerm();
            Assertions.assertFalse(creditBook.extraGrant(current));
            creditBook.addTerm();
            creditBook.addRecord(operationalSystems, lecturer, 5);
            creditBook.addRecord(pythonBasics, lecturer, 5);
            current = creditBook.getCurrentTerm();
            Assertions.assertTrue(creditBook.extraGrant(current));
        }

        @Test
        @DisplayName("Excellent diploma is not allowed for having sats")
        void hasSats() {
            creditBook.addRecord(operationalSystems, lecturer, 3);
            creditBook.addRecord(pythonBasics, lecturer, 5);
            creditBook.addTerm();
            creditBook.addRecord(iosDevelopment, seminarian, 5);
            creditBook.addRecord(operationalSystems, lecturer, 5);
            creditBook.addRecord(pythonBasics, lecturer, 5);
            creditBook.setQualifyingWork(5);
            Assertions.assertFalse(creditBook.excellentDiploma());
        }

        @Test
        @DisplayName("Excellent diploma is not allowed for bad qualifying work")
        void notQualified() {
            creditBook.addRecord(operationalSystems, lecturer, 5);
            creditBook.addRecord(pythonBasics, lecturer, 5);
            creditBook.addTerm();
            creditBook.addRecord(iosDevelopment, seminarian, 5);
            creditBook.addRecord(operationalSystems, lecturer, 5);
            creditBook.addRecord(pythonBasics, lecturer, 5);
            creditBook.setQualifyingWork(3);
            Assertions.assertFalse(creditBook.excellentDiploma());
        }

        @Test
        @DisplayName("Excellent diploma is not allowed for too few excellents")
        void fewExcellents() {
            creditBook.addRecord(operationalSystems, lecturer, 5);
            creditBook.addRecord(pythonBasics, lecturer, 4);
            creditBook.addTerm();
            creditBook.addRecord(iosDevelopment, seminarian, 4);
            creditBook.addRecord(operationalSystems, lecturer, 4);
            creditBook.addRecord(pythonBasics, lecturer, 4);
            creditBook.setQualifyingWork(5);
            Assertions.assertFalse(creditBook.excellentDiploma());
        }

        @Test
        @DisplayName("Excellent diploma is allowed")
        void excellentDiploma() {
            creditBook.addRecord(operationalSystems, lecturer, 5);
            creditBook.addRecord(pythonBasics, lecturer, 5);
            creditBook.addTerm();
            creditBook.addRecord(iosDevelopment, seminarian, 5);
            creditBook.addRecord(operationalSystems, lecturer, 5);
            creditBook.addRecord(pythonBasics, lecturer, 5);
            creditBook.setQualifyingWork(5);
            Assertions.assertTrue(creditBook.excellentDiploma());
        }
    }
}