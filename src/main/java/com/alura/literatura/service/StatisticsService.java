package com.alura.literatura.service;


import com.alura.literatura.entity.Author;
import com.alura.literatura.repository.AuthorRepository;
import com.alura.literatura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Adjusted StatisticsService class
@Service
public class StatisticsService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository; // Define BookRepository

    public long getBooksCountByLanguage(String language) {
        return bookRepository.countByLanguage(language);
    }

    public List<Author> getAuthorsAliveInYear(int year) {
        return authorRepository.findAuthorsAliveInYear(year);
    }
}
