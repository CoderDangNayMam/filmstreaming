package com.vti.trangwebxemphimv2.entity.dto;

import com.vti.trangwebxemphimv2.entity.entity.Genre;
import com.vti.trangwebxemphimv2.entity.entity.TypeFilm;
import lombok.Data;

import java.util.Set;

@Data
public class SearchFilmRequest extends BaseRequest {
    private String name;
    private int produtionYear;
    private Set<Genre> genre;
    private String nation;
    private String performer;
    private TypeFilm typeFilm;
}
