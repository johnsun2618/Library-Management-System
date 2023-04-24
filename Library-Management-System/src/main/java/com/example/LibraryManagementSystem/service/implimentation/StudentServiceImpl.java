package com.example.LibraryManagementSystem.service.implimentation;

import com.example.LibraryManagementSystem.DTO.RequestDto.StudentRequestDto;
import com.example.LibraryManagementSystem.DTO.RequestDto.UpdateStudentMobileRequestDto;
import com.example.LibraryManagementSystem.DTO.ResponseDto.CardResponseDto;
import com.example.LibraryManagementSystem.DTO.ResponseDto.StudentResponseDto;
import com.example.LibraryManagementSystem.DTO.ResponseDto.UpdateStudentMobileResponseDto;
import com.example.LibraryManagementSystem.entity.Card;
import com.example.LibraryManagementSystem.entity.Student;
import com.example.LibraryManagementSystem.enums.CardStatus;
import com.example.LibraryManagementSystem.exceptions.StudentNotFoundException;
import com.example.LibraryManagementSystem.repository.StudentRepository;
import com.example.LibraryManagementSystem.service.StudentService;

public class StudentServiceImpl implements StudentService {

    StudentRepository studentRepository;

    @Override
    public String addStudent(StudentRequestDto studentRequestDto) {
        Student student = new Student();
        student.setAge(studentRequestDto.getAge());
        student.setName(studentRequestDto.getName());
        student.setDepartment(studentRequestDto.getDepartment());
        student.setMobNo(studentRequestDto.getMobNo());

        Card card = new Card();
        card.setCardStatus(CardStatus.ACTIVATED);
        card.setValidTill("2024-01-01");
        card.setStudent(student);

        student.setCard(card);
        studentRepository.save(student);

        return "Student added";
    }

    @Override
    public StudentResponseDto getStudentById(int id) {
        Student student = studentRepository.findById(id).get();

        // prepare response to
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(student.getId());
        studentResponseDto.setName(student.getName());
        studentResponseDto.setDepartment(student.getDepartment());
        studentResponseDto.setAge(student.getAge());
        studentResponseDto.setMobNo(student.getMobNo());

        CardResponseDto cardResponseDto = new CardResponseDto();
        cardResponseDto.setIssueDate(student.getCard().getIssueDate());
        cardResponseDto.setCardStatus(student.getCard().getCardStatus());
        cardResponseDto.setUpdatedOn(student.getCard().getUpdatedOn());
        cardResponseDto.setValidTill(student.getCard().getValidTill());
        cardResponseDto.setId(student.getCard().getId());

        studentResponseDto.setCardResponseDto(cardResponseDto);
        return studentResponseDto;
    }

    @Override
    public UpdateStudentMobileResponseDto updateMobile(UpdateStudentMobileRequestDto updateStudentMobileRequestDto) throws StudentNotFoundException {
        try{
            Student student = studentRepository.findById(updateStudentMobileRequestDto.getId()).get();
            student.setMobNo(updateStudentMobileRequestDto.getMobNo());
            Student updatedStudent = studentRepository.save(student);

            // prepare response dto
            UpdateStudentMobileResponseDto updateStudentMobileResponseDto = new UpdateStudentMobileResponseDto();
            updateStudentMobileResponseDto.setName(updatedStudent.getName());
            updateStudentMobileResponseDto.setMobNo(updateStudentMobileRequestDto.getMobNo());
            return updateStudentMobileResponseDto;
        }
        catch (Exception e){
            throw new StudentNotFoundException("Invalid student id");
        }
    }
}
