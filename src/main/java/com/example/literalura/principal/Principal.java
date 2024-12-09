package com.example.literalura.principal;

import com.example.literalura.models.*;
import com.example.literalura.repository.AuthorRepository;
import com.example.literalura.repository.BookRepository;
import com.example.literalura.service.ConsumoApi;
import com.example.literalura.service.ConvierteDatos;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/";
    private AuthorRepository authorRepository;
    private ConvierteDatos conversor = new ConvierteDatos();
    private BookRepository bookRepository;

    public Principal(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void muestraElMenu() {
        var opcion = "-1";
        while (!opcion.equals("0")) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                      
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextLine();
            switch (opcion){
                case "1":
                    bookSearchWeb();
                    break;
                case "2":
                    bookListRegistered();
                    break;
                case "3":
                    authorListRegistered();
                    break;
                case "4":
                    findAuthorsAliveInYear();
                    break;
                case "5":
                    bookForLanguage();
                    break;
                case "0":
                    System.out.println("Gracias por utilizar nuestro software.");
                    break;
                default:
                    System.out.println("Opción invalida.");
            }

        }
    }

    private Optional<DatosBook> getDatosResult() {
        System.out.println("Por favor escribir el Titulo del libro que desea Buscar: ");
        var bookTitle = teclado.nextLine();

        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + bookTitle.replace(" ","+"));
        Data result = conversor.obtenerDatos(json, Data.class);

        Optional<DatosBook> bookSearch = result.bookDataList().stream()
                .filter(b -> b.title().toUpperCase().contains(bookTitle.toUpperCase())).findFirst();
        return bookSearch;
    }

    private void bookSearchWeb(){
        Optional<DatosBook> bookSearch = getDatosResult();
        try{
            if (bookSearch.isPresent()){
                DatosBook datosBook = bookSearch.get();
                Book book = new Book(datosBook);
                if (!bookRepository.titleExist(book.getTitle())){
                    try {
                        bookRepository.save(book);
                        System.out.println("------------------------------------------------------------------");
                        System.out.println("------------------------LIBRO-----------------");
                        System.out.println("Titulo: "+ book.getTitle());
                        System.out.println("Numero de Descargas: " + book.getDownloadCount());
                    }catch (DataIntegrityViolationException ex){
                        System.out.println("Error inesperado al guardar el libro: " +ex.getMessage());
                    }
                }else {
                    System.out.println("El libro con titulo: '"+book.getTitle() +"' ya existe." );
                }
                try {
                    List<Author> authors = bookSearch.stream()
                            .flatMap(d -> d.datosAuthorList().stream()
                                    .map(a -> new Author(a))).collect(Collectors.toList());
                    book.setAuthorsList( authors);
                    int i = 0;
                    System.out.println("Autor: " + authors.get(i).getName());
                }catch (DataIntegrityViolationException ex){
                    System.out.println("El Author existe");
                }

                List<Language> languages = bookSearch.stream()
                        .flatMap(l -> l.languageList().stream())
                        .map(a -> new Language(a))
                        .collect(Collectors.toList());
                book.setLanguagesList(languages);
                int i = 0;
                System.out.println("Idioma: "+languages.get(i).getDescription());
                System.out.println("------------------------------------------------------------------");
                bookRepository.save(book);

                System.out.println("------------------------------------------------------------------");
                System.out.println("Se registro correctamente");
                System.out.println("------------------------------------------------------------------");

            }
        }catch (DataIntegrityViolationException ex){
            System.out.println("Error en Registrar Datos. ");
        }
    }

    private void bookListRegistered(){
        List<Book> bookList = bookRepository.booksList();
        System.out.println("---------------Lista de Libros---------------");
        System.out.println(bookList);
    }

    private void authorListRegistered(){
        List<Author> authorList = authorRepository.authorList();
        System.out.println("---------------Lista de Autores---------------");
        System.out.println(authorList);
    }

    private void findAuthorsAliveInYear(){
        System.out.println("Indique el año:");
        var year = teclado.nextInt();
        List<Author> authorLiveList = authorRepository.findAuthorsAliveInYear(year);
        System.out.println("---------------Lista de Autores---------------");
        System.out.println(authorLiveList);
    }

    private void bookForLanguage(){
        System.out.println("Ingrese el Idioma que desea buscar: ");
        System.out.println("""
            es - Español
            en - Ingles
            fr - Frances
            pt - portugues
            """);
        var language = teclado.nextLine();
        List<Book> bookList = bookRepository.bookForLanguage(language);
        System.out.println(bookList);
    }
}
