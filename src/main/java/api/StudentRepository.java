package api;

import api.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private final List<Student> students = new ArrayList<>();

    private Integer nextId = 1;

    public StudentRepository() {

        create(new Student(
                null,
                "Lucas Almeida",
                "lucas@email.com",
                "20250001",
                "Computer Science"
        ));

        create(new Student(
                null,
                "Emma Johnson",
                "emma@email.com",
                "20250002",
                "Software Engineering"
        ));

    }

    public List<Student> findAll() {
        return students;
    }

    public Student findById(Integer id) {

        for (Student student : students) {

            if (student.getId().equals(id))
                return student;

        }

        return null;

    }

    public Student create(Student student) {

        student.setId(nextId++);

        students.add(student);

        return student;

    }

    public Student update(Integer id, Student updatedStudent) {

        Student student = findById(id);

        if (student == null)
            return null;

        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        student.setRegistration(updatedStudent.getRegistration());
        student.setCourse(updatedStudent.getCourse());

        return student;

    }

    public boolean delete(Integer id) {

        Student student = findById(id);

        if (student == null)
            return false;

        students.remove(student);

        return true;

    }

}
