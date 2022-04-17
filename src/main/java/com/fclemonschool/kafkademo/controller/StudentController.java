package com.fclemonschool.kafkademo.controller;

import com.fclemonschool.kafkademo.model.Student;
import com.fclemonschool.kafkademo.service.StudentProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
public class StudentController {
    private StudentProducerService studentProducerService;

    @Autowired
    public void setStudentProducerService(StudentProducerService studentProducerService) {
        this.studentProducerService = studentProducerService;
    }

    @PostMapping("/publish")
    public String publishMessage(@RequestBody Student student) {
        studentProducerService.sendStudent(student);
        return "Published Successfully";
    }
}
