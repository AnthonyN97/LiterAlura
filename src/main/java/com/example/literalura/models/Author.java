package com.example.literalura.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String name;
    private Integer birth_year;
    private Integer death_year;

    @ManyToOne
    private Book book;

    public Book getBooks() {
        return book;
    }

    public void setBooks(Book book) {
        this.book = book;
    }

    public Author(){}

    public Author(DatosAuthor dataAuthor){
        this.name = dataAuthor.name();
        this.birth_year = dataAuthor.birth_year();
        this.death_year = dataAuthor.death_year();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public void setDeath_year(Integer death_year) {
        this.death_year = death_year;
    }

    @Override
    public String toString(){
        return  "\n ----------------- Autores y sus respectivos Libros -------------"+
                "\n Nombre: "+ name +
                "\n Fecha de Nacimiento: "+ birth_year +
                "\n Fecha de Fallecimiento: "+ death_year+
                "\n Libros: "+book.getTitle()+
                "\n ";
    }



}