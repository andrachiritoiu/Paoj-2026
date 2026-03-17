package com.pao.laboratory03.exercise.service;

import com.pao.laboratory03.exercise.exception.InvalidGradeException;
import com.pao.laboratory03.exercise.exception.InvalidStudentException;
import com.pao.laboratory03.exercise.exception.StudentNotFoundException;
import com.pao.laboratory03.exercise.model.Student;
import com.pao.laboratory03.exercise.model.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentService {
    private static StudentService instance;
    private final List<Student> students = new ArrayList<>();

    private StudentService() {}

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    public void addStudent(String name, int age) {
        if (name == null || name.isBlank()) {
            throw new InvalidStudentException("Numele studentului este invalid");
        }

        if (age < 0 || age > 150) {
            throw new InvalidStudentException("Vârsta studentului este invalidă");
        }

        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                throw new InvalidStudentException("Studentul '" + name + "' există deja");
            }
        }

        students.add(new Student(name, age));
    }

    public Student findByName(String name) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        throw new StudentNotFoundException("Studentul '" + name + "' nu a fost găsit");
    }

    public void addGrade(String studentName, Subject subject, double grade) {
        Student student = findByName(studentName);
        if (grade < 1 || grade > 10) {
            throw new InvalidGradeException("Nota trebuie să fie între 1 și 10");
        }
        student.addGrade(subject, grade);
    }

    public void printAllStudents() {
        if (students.isEmpty()) {
            System.out.println("Nu există studenți.");
            return;
        }
        int i = 1;
        for (Student s : students) {
            System.out.println(i + ". " + s);
            for (Map.Entry<Subject, Double> entry : s.getGrades().entrySet()) {
                System.out.println("   " + entry.getKey().name() + " = " + entry.getValue());
            }
            i++;
        }
    }

    public void printTopStudents() {
        List<Student> sorted = new ArrayList<>(students);
        sorted.sort((a, b) -> Double.compare(b.getAverage(), a.getAverage()));
        System.out.println("=== Top studenți ===");
        int i = 1;
        for (Student s : sorted) {
            System.out.printf("%d. %s — media: %.2f%n", i, s.getName(), s.getAverage());
            i++;
        }
    }

    public Map<Subject, Double> getAveragePerSubject() {
        Map<Subject, Double> result = new HashMap<>();
        for (Subject subject : Subject.values()) {
            double sum = 0;
            int count = 0;
            for (Student s : students) {
                Double grade = s.getGrades().get(subject);
                if (grade != null) {
                    sum += grade;
                    count++;
                }
            }
            if (count > 0) {
                result.put(subject, sum / count);
            }
        }
        return result;
    }
}

