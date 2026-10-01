package com.esraa_spring_boot.books.controller;

import com.esraa_spring_boot.books.entity.Book;
import com.esraa_spring_boot.books.exception.BookNotFoundException;
import com.esraa_spring_boot.books.request.BookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books Rest Api Endpoint", description = "Operations related to books")
public class BookController {
    private final List<Book> books = new ArrayList<>();

    public BookController() {
        intilalizeBooks();
    }

    private void intilalizeBooks() {
        books.addAll(List.of(
                new Book(1, "Computer Science Pro", "Chad Darby", "Computer Science", 5),
                new Book(2, "Java Spring master", "Eric Roby", "Computer Science", 5),
                new Book(3, "Why 1 + 1 Rocks", "Adil A", "Math", 5),
                new Book(4, "How Bears Hibernate", "Bob.B", "Science", 2),
                new Book(5, "A Pirate's Treasure", "Curt.C", "History", 3),
                new Book(6, "Why 2+2 is better", "Dan.D", "Math", 1)));

    }

    //    @GetMapping("/api/books")
//    public List<Book> getBooks()
//       return  books;
//    }
    @Operation(summary = "getting book by id", description = "an endpoint to retrieve specific book by its id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookById(@Parameter(description = "Id of the book to be retrieved") @PathVariable @Min(value = 1) long id) {
//        for(Book book : books){
//            if(book.getTitle().equalsIgnoreCase(title))
//                return  book;
//        }
        return
                books.stream().
                        filter(book -> book.getId() == id).
                        findFirst().
                        orElseThrow(() -> new BookNotFoundException("Book Not Found!" + id));
    }

    /**
     * filtered by category and return list of books
     **/

    @Operation(summary = "Get all books", description = "endpoint for fetching all books")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Book> getBooks(@Parameter(description = "optional query parameter") @RequestParam(required = false) String category) {
        if (category == null)
            return books;
        return books.stream().filter(book -> book.getCategory().equalsIgnoreCase(category)).toList();
    }

    @Operation(summary = "adding new book to the list", description = "an endpoint to add new book to the list of books")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createBook(@Valid @RequestBody BookRequest newBook) {
////        for(Book book :books){
////            if(book.getTitle().equalsIgnoreCase(newBook.getTitle())){
////                return;
////            }
////        }
////        books.add(newBook);
//        boolean isNewBook = books.stream().noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));
//        if(isNewBook)books.add(newBook);

        long id = books.isEmpty() ? 1 : books.getLast().getId() + 1;
        Book book = convertToBook(id, newBook);
        books.add(book);
    }

    @Operation(summary = "updating the details of specific book", description = "an endpoint to update the details of the book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public Book updateBook(@Parameter(description = "id of the book to be updated") @PathVariable @Min(value = 1) long id, @RequestBody BookRequest bookRequest) {
        for (int i = 0; i < books.size(); i++) {
            Book updatedBook = convertToBook(id, bookRequest);
            if (books.get(i).getId() == id) {
                books.set(i, updatedBook);
                return updatedBook;
            }
        }
        throw new BookNotFoundException("Book Not found" + id);
    }

    @Operation(summary = "delete book", description = "endpoint to delete book by its id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@Parameter(description = "Id of the book to be deleted") @PathVariable @Min(value = 1) long id) {
        books.stream().
                filter(book -> book.getId() == id).
                findFirst().
                orElseThrow(() -> new BookNotFoundException("Book Not Found!" + id));
        books.removeIf(book -> book.getId() == id);
    }

    private Book convertToBook(long id, BookRequest newBook) {
        return new Book(id, newBook.getTitle(), newBook.getAuthor(), newBook.getCategory(), newBook.getRating());
    }


}
