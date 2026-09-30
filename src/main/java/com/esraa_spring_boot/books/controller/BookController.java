package com.esraa_spring_boot.books.controller;

import com.esraa_spring_boot.books.entity.Book;
import com.esraa_spring_boot.books.request.BookRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final List<Book>books=new ArrayList<>();
    public BookController(){
        intilalizeBooks();
    }
    private void intilalizeBooks() {
        books.addAll(List.of(
                new Book(1,"Computer Science Pro", "Chad Darby", "Computer Science", 5),
                new Book(2,"Java Spring master", "Eric Roby", "Computer Science", 5),
                new Book(3,"Why 1 + 1 Rocks", "Adil A", "Math", 5),
                new Book(4,"How Bears Hibernate", "Bob.B", "Science", 2),
                new Book(5,"A Pirate's Treasure", "Curt.C", "History", 3),
                new Book(6,"Why 2+2 is better", "Dan.D", "Math", 1)));

    }
//    @GetMapping("/api/books")
//    public List<Book> getBooks(){
//       return  books;
//    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable  @Min(value = 1) long id){
//        for(Book book : books){
//            if(book.getTitle().equalsIgnoreCase(title))
//                return  book;
//        }
        return books.stream().filter(book -> book.getId()==id).findFirst().orElse(null);
    }
    /** filtered by category and return list of books**/
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Book>getBooks(@RequestParam(required = false) String category){
        if(category== null)
            return books;
        return books.stream().filter(book -> book.getCategory().equalsIgnoreCase(category)).toList();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createBook( @Valid @RequestBody BookRequest newBook){
////        for(Book book :books){
////            if(book.getTitle().equalsIgnoreCase(newBook.getTitle())){
////                return;
////            }
////        }
////        books.add(newBook);
//        boolean isNewBook = books.stream().noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));
//        if(isNewBook)books.add(newBook);

        long id=books.isEmpty()?1:books.getLast().getId()+1;
            Book book = convertToBook(id , newBook);
        books.add(book);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateBook(@PathVariable @Min(value = 1) long id , @RequestBody BookRequest bookRequest){
        for(int i = 0 ; i < books.size(); i++){
            Book updatedBook = convertToBook(id , bookRequest);
            if(books.get(i).getId()==id){
                books.set(i , updatedBook);
                return;
            }
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable @Min(value = 1) long id){
        books.removeIf(book -> book.getId()==id);
    }
    private Book convertToBook(long id , BookRequest newBook){
        return  new Book(id ,newBook.getTitle() , newBook.getAuthor(), newBook.getCategory(), newBook.getRating());
    }
}
