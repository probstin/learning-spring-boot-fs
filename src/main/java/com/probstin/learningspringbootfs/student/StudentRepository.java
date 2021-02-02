package com.probstin.learningspringbootfs.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Student> selectAllStudents() {
        String sql =
                "SELECT student_id, first_name, last_name, email, gender FROM student";

        return jdbcTemplate.query(sql, mapStudentFromDb());
    }

    @SuppressWarnings("ConstantConditions")
    boolean findExistingEmail(String email) {
        String sql =
                "SELECT EXISTS (SELECT 1 FROM student WHERE email = ?)";

        return jdbcTemplate.queryForObject(sql, ((resultSet, i) -> resultSet.getBoolean(1)), email);
    }

    @SuppressWarnings("UnusedReturnValue")
    int insertStudent(UUID studentId, Student student) {
        String sql =
                "INSERT INTO student (student_id, first_name, last_name, email, gender) VALUES (?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                studentId,
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getGender().name().toUpperCase()
        );
    }

    private RowMapper<Student> mapStudentFromDb() {
        return (resultSet, i) -> {
            String studentIdString = resultSet.getString("student_id");
            UUID studentId = UUID.fromString(studentIdString);
            String genderString = resultSet.getString("gender").toUpperCase();
            Student.Gender gender = Student.Gender.valueOf(genderString);
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String email = resultSet.getString("email");

            return new Student(studentId, firstName, lastName, email, gender);
        };
    }

}
