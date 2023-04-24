package com.example.LibraryManagementSystem.service.implimentation;

import com.example.LibraryManagementSystem.DTO.ResponseDto.AuthorResponseDto;
import com.example.LibraryManagementSystem.entity.Author;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {


    @Autowired
    AuthorRepository authorRepository;

    @Override
    public String addAuthor(Author author) {
        authorRepository.save(author);
        return "Author Added";
    }

    @Override
    public AuthorResponseDto getByEmail(String email) {
        Author author = authorRepository.findByEmail(email);

        //prepare a response dto
        AuthorResponseDto authorResponseDto = new AuthorResponseDto();
        authorResponseDto.setName(author.getName());
        authorResponseDto.setAge(author.getAge());

        return authorResponseDto;

    }


}
