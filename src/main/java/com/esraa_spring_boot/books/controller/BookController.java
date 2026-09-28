package com.esraa_spring_boot.books.controller;

import com.esraa_spring_boot.books.entity.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {
    private final List<Book>books=new ArrayList<>();
    public BookController(){
        intilalizeBooks();
    }
    private void intilalizeBooks() {
        books.addAll(List.of(
                new Book("book one", "esraa", "programming"),
                new Book("book two", "asmaa", "science"),
                new Book("book three", "osama", "java"),
                new Book("book four", "sara", "Phsics"),
                new Book("book five", "asmaa", "science"),
                new Book("book six", "Bakar", "javascript")));

    }
    @GetMapping("/api/books")
    public List<Book> getBooks(){
       return  books;
    }
}
