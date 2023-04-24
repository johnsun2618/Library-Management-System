package com.example.LibraryManagementSystem.service.implimentation;

import com.example.LibraryManagementSystem.DTO.RequestDto.IssueBookRequestDto;
import com.example.LibraryManagementSystem.DTO.ResponseDto.IssueBookResponseDto;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.Card;
import com.example.LibraryManagementSystem.entity.Transaction;
import com.example.LibraryManagementSystem.enums.CardStatus;
import com.example.LibraryManagementSystem.enums.TransactionStatus;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.example.LibraryManagementSystem.repository.CardRepository;
import com.example.LibraryManagementSystem.repository.TransactionRepository;
import com.example.LibraryManagementSystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class TransactionServiceImpl implements TransactionService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TransactionRepository transactionRepository;

//    @Autowired
//    private JavaMailSender emailSender;

    @Override
    public IssueBookResponseDto issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setIssueOperation(true);

        Card card;
        try{
            card = cardRepository.findById(issueBookRequestDto.getCardId()).get();
        }
        catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Invalid card id!!!");
        }

        transaction.setCard(card);

        Book book;
        try{
            book = bookRepository.findById(issueBookRequestDto.getBookId()).get();
        }
        catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Invalid Book id!!!!");
        }

        transaction.setBook(book);

        if(card.getCardStatus()!= CardStatus.ACTIVATED){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Card is not Active!!!");
        }

        if(book.isIssued()){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Book is not available!!!");
        }

        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        book.setIssued(true);
        book.setCard(card);
        book.getTransactionList().add(transaction);

        card.getBooksIssued().add(book);
        card.getTransactionList().add(transaction);

        cardRepository.save(card); // save card, book and transaction.

        // prepare response dtp;

        IssueBookResponseDto issueBookResponseDto = new IssueBookResponseDto();
        issueBookResponseDto.setBookName(book.getTitle());
        issueBookResponseDto.setTransactionNumber(transaction.getTransactionNumber());
        issueBookResponseDto.setTransactionStatus(transaction.getTransactionStatus());

        String text = "Congrats! " + card.getStudent().getName() +  " You have been issued the book " + book.getTitle();

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("acciojobbackendapril@gmail.com");
//        message.setTo(card.getStudent().getMobNo());
//        message.setSubject("Issue book");
//        message.setText(text);
//        emailSender.send(message);

        return issueBookResponseDto;
    }
}
