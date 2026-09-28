package com.esraa_spring_boot.books.controller;

import com.esraa_spring_boot.books.entity.Book;
import org.springframework.web.bind.annotation.*;

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
                new Book("book six", "Bakar", "java")));

    }
//    @GetMapping("/api/books")
//    public List<Book> getBooks(){
//       return  books;
//    }
    @GetMapping("/api/books/{title}")
    public Book getBookByTitle(@PathVariable  String title){
//        for(Book book : books){
//            if(book.getTitle().equalsIgnoreCase(title))
//                return  book;
//        }
        return books.stream().filter(book -> book.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
    }
    /** filtered by category and return list of books**/
    @GetMapping("api/books")
    public List<Book>getBooks(@RequestParam(required = false) String category){
        if(category== null)
            return books;
        return books.stream().filter(book -> book.getCategory().equalsIgnoreCase(category)).toList();
    }
    @PostMapping ("/api/books")
    public void createBook(@RequestBody Book newBook){
//        for(Book book :books){
//            if(book.getTitle().equalsIgnoreCase(newBook.getTitle())){
//                return;
//            }
//        }
//        books.add(newBook);
        boolean isNewBook = books.stream().noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));
        if(isNewBook)books.add(newBook);
    }
}
