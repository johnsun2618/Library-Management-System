package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.DTO.RequestDto.StudentRequestDto;
import com.example.LibraryManagementSystem.DTO.RequestDto.UpdateStudentMobileRequestDto;
import com.example.LibraryManagementSystem.DTO.ResponseDto.StudentResponseDto;
import com.example.LibraryManagementSystem.DTO.ResponseDto.UpdateStudentMobileResponseDto;
import com.example.LibraryManagementSystem.entity.Student;
import com.example.LibraryManagementSystem.exceptions.StudentNotFoundException;

public interface StudentService {

    public String addStudent(StudentRequestDto studentRequestDto);

    public StudentResponseDto getStudentById(int id);

    UpdateStudentMobileResponseDto updateMobile(UpdateStudentMobileRequestDto updateStudentMobileRequestDto) throws StudentNotFoundException;
}
