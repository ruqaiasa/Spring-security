package com.example.SpringSecExpl.controller;

import com.example.SpringSecExpl.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {
    private List<Student> students = List.of(
            new Student(1, "Alice", 85),
            new Student(2, "Bob", 90),
            new Student(3, "Charlie", 78)
    );

    @GetMapping("/students")
    public List<Student> getStudents()
    {
        return students;

    }
    @GetMapping("/csrfToken")
    public CsrfToken getToken(HttpServletRequest r){
        return (CsrfToken) r.getAttribute("_csrf");
    }

    @PostMapping("/addStudent")
    public Student addStudent(@RequestBody Student s)
    {
        students.add(s);
        return s;

    }
}
