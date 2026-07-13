package api.repository;

import api.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private final List<Student> students = new ArrayList<>();
    private Integer nextId = 1;

    public List<Student> findAll() {
        return students;
    }

    public Student findById(Integer id) {

        for (Student student : students) {

            if (student.getId().equals(id)) {
                return student;
            }

        }

        return null;
    }

    public Student create(Student student) {

        student.setId(nextId++);
        students.add(student);

        return student;
    }

    public Student update(Integer id, Student updatedStudent) {

        Student existingStudent = findById(id);

        if (existingStudent == null) {
            return null;
        }

        existingStudent.setName(updatedStudent.getName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setRegistration(updatedStudent.getRegistration());
        existingStudent.setCourse(updatedStudent.getCourse());

        return existingStudent;
    }

    public boolean delete(Integer id) {

        Student student = findById(id);

        if (student == null) {
            return false;
        }

        students.remove(student);

        return true;
    }

}
