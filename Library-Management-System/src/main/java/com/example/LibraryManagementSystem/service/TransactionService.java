package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.DTO.RequestDto.IssueBookRequestDto;
import com.example.LibraryManagementSystem.DTO.ResponseDto.IssueBookResponseDto;

public interface TransactionService {

    public IssueBookResponseDto issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception;
}
