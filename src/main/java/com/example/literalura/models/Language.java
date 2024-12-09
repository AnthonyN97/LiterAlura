package com.example.literalura.models;

import jakarta.persistence.*;

@Entity
@Table(name = "languages")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String language;
    private String description;
    public Language(){}
    public Language(String datosLanguages){
        this.language = datosLanguages;
        this.description = Language.selectLanguage(datosLanguages);
    }

    @ManyToOne
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String selectLanguage(String text){
        switch (text){
            case "en":
                text = "Inglés";
                break;
            case "es":
                text = "Español";
                break;
            case "fr":
                text = "Francés";
                break;
            case "de":
                text = "Alemán";
                break;
            default:
                text = "Idioma desconocido";
                break;
        }
        return text;
    }

}