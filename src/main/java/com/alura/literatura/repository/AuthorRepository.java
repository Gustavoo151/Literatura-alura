package com.alura.literatura.repository;

import com.alura.literatura.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a WHERE (:year BETWEEN a.birthYear AND a.deathYear) OR (:year >= a.birthYear AND a.deathYear IS NULL)")
    List<Author> findAuthorsAliveInYear(@Param("year") int year);
}