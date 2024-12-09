package com.example.literalura.repository;

import com.example.literalura.models.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author,Long> {

    @Query("SELECT a FROM Author a")
    List<Author> authorList();

    @Query("SELECT a FROM Author a WHERE a.death_year> :year")
    List<Author> findAuthorsAliveInYear(int year);

}