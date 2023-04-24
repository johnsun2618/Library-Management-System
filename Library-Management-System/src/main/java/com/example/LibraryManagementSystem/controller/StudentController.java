package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.DTO.RequestDto.StudentRequestDto;
import com.example.LibraryManagementSystem.DTO.RequestDto.UpdateStudentMobileRequestDto;
import com.example.LibraryManagementSystem.DTO.ResponseDto.StudentResponseDto;
import com.example.LibraryManagementSystem.DTO.ResponseDto.UpdateStudentMobileResponseDto;
import com.example.LibraryManagementSystem.entity.Student;
import com.example.LibraryManagementSystem.exceptions.StudentNotFoundException;
import com.example.LibraryManagementSystem.repository.StudentRepository;
import com.example.LibraryManagementSystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/add")
    public String addStudent(@RequestBody StudentRequestDto studentRequestDto){

        return studentService.addStudent(studentRequestDto);
    }

    @PutMapping("/update_mobile")
    public UpdateStudentMobileResponseDto updateMobNo(@RequestBody UpdateStudentMobileRequestDto updateStudentMobRequestDto) throws StudentNotFoundException {

        return studentService.updateMobile(updateStudentMobRequestDto);
    }

    @GetMapping("/get_student")
    public StudentResponseDto getStudent(@RequestParam("id") int id){
        return studentService.getStudentById(id);
    }
}
