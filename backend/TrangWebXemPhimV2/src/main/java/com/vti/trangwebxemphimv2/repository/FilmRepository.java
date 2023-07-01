package com.vti.trangwebxemphimv2.repository;

import com.vti.trangwebxemphimv2.entity.entity.Film;
import com.vti.trangwebxemphimv2.entity.entity.TypeFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Film> {
    List<Film> findAllByTypeFilm(TypeFilm typeFilm);
}
