package com.vti.trangwebxemphimv2.entity.dto;

import com.vti.trangwebxemphimv2.entity.entity.FilmStatus;
import com.vti.trangwebxemphimv2.entity.entity.Genre;
import com.vti.trangwebxemphimv2.entity.entity.TypeFilm;
import lombok.Data;

import javax.persistence.GeneratedValue;

@Data
public class FilmUpdateRequest {

    private int id;

    private String name;

    private String linkFilm;

    private String image;

    private FilmStatus status;

    private int productionYear;

    private String nation;

    private Genre genre;

    private String performer;

    private TypeFilm typeFilm;

    private String duration;

    private double rating;

}
