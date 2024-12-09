package com.example.literalura.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Author> authorsList;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Language> languagesList;

    public List<Language> getLanguagesList(){
        return languagesList;
    }

    public void setLanguagesList(List<Language> languagesList){
        languagesList.forEach(l -> l.setBook(this));
        this.languagesList = languagesList;
    }

    private Double downloadCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthorsList() {
        return authorsList;
    }

    public void setAuthorsList(List<Author> authorsList) {
        authorsList.forEach(a -> a.setBooks(this));
        this.authorsList = authorsList;
    }

    public Double getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = Double.valueOf(downloadCount);
    }

    public Book(
    ){
    }

    public Book(DatosBook datosBook){
        this.title = datosBook.title();
        this.downloadCount = datosBook.download_count();
    }

    @Override
    public String toString () {

        int i = 0;
        return
                "\n ----------------- LIBRO -------------" +
                        "\n Título: " + title +
                        "\n Autor: '" + authorsList.get(i).getName() + '\'' +
                        "\n Idioma: '" + (languagesList.get(i).toString() != null ? languagesList.get(i).getDescription() : "null") + '\'' +
                        "\n Número de descargas: '" + downloadCount + '\'' +
                        "\n-------------------------------------";

    }
}
