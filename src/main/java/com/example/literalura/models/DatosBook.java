package com.example.literalura.models;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosBook(@JsonAlias("title") String title,
                         @JsonAlias("authors") List<DatosAuthor> datosAuthorList,
                         @JsonAlias("languages") List<String> languageList,
                         @JsonAlias("download_count") Double download_count
){
}
