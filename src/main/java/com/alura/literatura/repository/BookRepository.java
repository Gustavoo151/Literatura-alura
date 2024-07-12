package com.alura.literatura.repository;


import com.alura.literatura.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT COUNT(b) FROM Book b WHERE :language MEMBER OF b.languages")
    long countByLanguage(@Param("language") String language);
}
