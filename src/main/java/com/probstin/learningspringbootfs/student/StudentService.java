package com.probstin.learningspringbootfs.student;

import com.probstin.learningspringbootfs.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.selectAllStudents();
    }

    public void addStudent(Student student) {
        addStudent(null, student);
    }

    public void addStudent(UUID studentId, Student student) {
        UUID newStudentId = Optional
                .ofNullable(studentId)
                .orElse(UUID.randomUUID());

        // validate email
        String studentEmail = student.getEmail();
        if (!isValidEmail(studentEmail)) {
            throw new ApiRequestException("Provided email: " + studentEmail +  " is not valid");
        }

        // verify that the email is not in use
        if (studentRepository.findExistingEmail(studentEmail)) {
            throw new ApiRequestException("Provided email: " + studentEmail +  " is already in use");
        }

        studentRepository.insertStudent(newStudentId, student);
    }

    private static boolean isValidEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);

        return matcher.find();
    }

}
