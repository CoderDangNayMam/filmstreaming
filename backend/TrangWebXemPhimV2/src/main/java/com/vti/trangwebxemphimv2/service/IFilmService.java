package com.vti.trangwebxemphimv2.service;

import com.vti.trangwebxemphimv2.entity.dto.FilmCreateRequest;
import com.vti.trangwebxemphimv2.entity.dto.FilmUpdateRequest;
import com.vti.trangwebxemphimv2.entity.dto.SearchFilmRequest;
import com.vti.trangwebxemphimv2.entity.entity.Film;
import com.vti.trangwebxemphimv2.entity.entity.TypeFilm;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IFilmService {
    List<Film> getAll();

    Film getById(int id);

    Film create(FilmCreateRequest dto);

    Film update(FilmUpdateRequest dto);

    void deleteById(int id);

    Page<Film> search(SearchFilmRequest request);

    List<Film> findByTypeFilm(TypeFilm typeFilm);
}
