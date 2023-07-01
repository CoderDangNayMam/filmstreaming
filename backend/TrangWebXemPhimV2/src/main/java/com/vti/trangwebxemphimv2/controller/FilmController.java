package com.vti.trangwebxemphimv2.controller;

import com.vti.trangwebxemphimv2.config.Annotation.FilmIDExists;
import com.vti.trangwebxemphimv2.entity.dto.FilmCreateRequest;
import com.vti.trangwebxemphimv2.entity.dto.FilmUpdateRequest;
import com.vti.trangwebxemphimv2.entity.dto.SearchFilmRequest;
import com.vti.trangwebxemphimv2.entity.entity.Film;
import com.vti.trangwebxemphimv2.entity.entity.TypeFilm;
import com.vti.trangwebxemphimv2.service.IFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/film")
@CrossOrigin("*")
@Validated
public class FilmController {
    @Autowired
    private IFilmService filmService;

    @GetMapping("/get-all")
    public List<Film> getAll() {
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable @FilmIDExists int id) {
        return filmService.getById(id);
    }

    @PostMapping("/create")
    public Film create(@RequestBody FilmCreateRequest dto) {
        return filmService.create(dto);
    }

    @PutMapping("/update")
    public Film update(@RequestBody @Valid FilmUpdateRequest dto) {
        return filmService.update(dto);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public String deleteById(@PathVariable int id) {
        filmService.deleteById(id);
        return "Đã xoá thành công";
    }
    @PostMapping("/search")
    public Page<Film> search(@RequestBody SearchFilmRequest request){
        return filmService.search(request);
    }

    @GetMapping("/type-film")
    public List<Film> findAllByTypeFilm(@RequestParam TypeFilm typeFilm){
        return filmService.findByTypeFilm(typeFilm);
    }
}
