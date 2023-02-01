package com.driver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {

    private List<Book> bookList;
    private int id;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
    }

    // post request /create-book
    // pass book as request body
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        book.setId(id);
        bookList.add(book);
        id +=1;
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    // getBookById()
    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") String id){
            for(Book book : bookList){
                if(book.getId() == Integer.parseInt(id))
                    return new ResponseEntity<>(book, HttpStatus.ACCEPTED);
            }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // get request /get-all-books
    // getAllBooks()
    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>> getAllBook(){
        return new ResponseEntity<>(bookList, HttpStatus.ACCEPTED);
    }

    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor()
    @GetMapping("/get-books-by-author")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("author") String author){
        List<Book> authorBook =new ArrayList<>();
        for(Book book : bookList){
            if(book.getAuthor().equals(author))
                authorBook.add(book);
        }
        return new ResponseEntity<>(authorBook, HttpStatus.FOUND);
    }
    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()
    @GetMapping("/get-books-by-genre")
    public ResponseEntity<List<Book>>getBooksByGenre(@RequestParam String genre){
        List<Book> booksByGenre = new ArrayList<>();
        for(Book byGenre : bookList){
            if(byGenre.getGenre().equals(genre))
                booksByGenre.add(byGenre);
        }
        return new  ResponseEntity<>(booksByGenre, HttpStatus.ACCEPTED);
    }

    // deleteBookById()
    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    // delete request /delete-all-books
    // deleteAllBooks()
    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity<String> deleteAllBooks(@PathVariable ("id") String id){
        for(Book book : bookList){
            if(book.getId() == Integer.parseInt(id))
                return new ResponseEntity<>("deleted", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("delete-all-books")
    public ResponseEntity<String> deleteAllBooks(){
        bookList.clear();
        return new ResponseEntity<>("All books Deleted", HttpStatus.ACCEPTED);
    }
}
