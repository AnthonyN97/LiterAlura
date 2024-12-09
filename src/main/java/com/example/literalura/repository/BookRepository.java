package com.example.literalura.repository;

import com.example.literalura.models.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    @Query("SELECT b FROM Book b ")
    List<Book> booksList();

    @Query("SELECT COUNT(b) > 0 FROM Book b WHERE b.title = :title")
    boolean titleExist(@Param("title") String title);

    @Query("SELECT b FROM Book b JOIN Language l ON b.id = l.book.id WHERE l.language = :language")
    List<Book> bookForLanguage(String language);
}
