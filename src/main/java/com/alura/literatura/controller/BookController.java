package com.alura.literatura.controller;


import com.alura.literatura.entity.Author;
import com.alura.literatura.entity.Book;
import com.alura.literatura.repository.AuthorRepository;
import com.alura.literatura.repository.BookRepository;
import com.alura.literatura.service.GutendexService;
import com.alura.literatura.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GutendexService gutendexService;

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/search")
    public ResponseEntity<Book> searchBookByTitle(@RequestParam String query) {
        try {
            Optional<Book> book = gutendexService.fetchBookByTitle(query);
            return book.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        Author author = book.getAuthor();
        if (author != null) {
            author = authorRepository.save(author);
            book.setAuthor(author);
        }
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.delete(book.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            Book bookToUpdate = book.get();
            bookToUpdate.setTitle(bookDetails.getTitle());
            bookToUpdate.setLanguages(bookDetails.getLanguages());
            bookToUpdate.setDownloadCount(bookDetails.getDownloadCount());

            Author authorDetails = bookDetails.getAuthor();
            if (authorDetails != null) {
                Author author = authorRepository.save(authorDetails);
                bookToUpdate.setAuthor(author);
            }

            Book updatedBook = bookRepository.save(bookToUpdate);
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/authors")
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @GetMapping("/authors/alive")
    public ResponseEntity<List<Author>> getAuthorsAliveInYear(@RequestParam int year) {
        if (year < 0) {
            return ResponseEntity.badRequest().build();
        }
        List<Author> authors = statisticsService.getAuthorsAliveInYear(year);
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/statistics/{language}")
    public ResponseEntity<Long> getBooksCountByLanguage(@PathVariable String language) {
        long count = statisticsService.getBooksCountByLanguage(language);
        return ResponseEntity.ok(count);
    }
}
