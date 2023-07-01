package com.vti.trangwebxemphimv2.entity.dto;

import com.vti.trangwebxemphimv2.entity.entity.FilmStatus;
import com.vti.trangwebxemphimv2.entity.entity.Genre;
import com.vti.trangwebxemphimv2.entity.entity.TypeFilm;
import lombok.Data;


@Data
public class FilmCreateRequest {

    private String name;

    private String linkFilm;

    private String image;

    private int productionYear;

    private FilmStatus status;

    private String nation;

    private Genre genre;

    private String performer;

    private TypeFilm typeFilm;

    private String duration;

    private double rating;
}
