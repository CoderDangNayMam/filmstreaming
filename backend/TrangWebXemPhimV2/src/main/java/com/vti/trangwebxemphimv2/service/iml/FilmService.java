package com.vti.trangwebxemphimv2.service.iml;

import com.vti.trangwebxemphimv2.config.exception.AppException;
import com.vti.trangwebxemphimv2.config.exception.ErrorResponseEnum;
import com.vti.trangwebxemphimv2.entity.dto.FilmCreateRequest;
import com.vti.trangwebxemphimv2.entity.dto.FilmUpdateRequest;
import com.vti.trangwebxemphimv2.entity.dto.SearchFilmRequest;
import com.vti.trangwebxemphimv2.entity.entity.Film;
import com.vti.trangwebxemphimv2.entity.entity.TypeFilm;
import com.vti.trangwebxemphimv2.repository.FilmRepository;
import com.vti.trangwebxemphimv2.repository.Specification.FilmSpecification;
import com.vti.trangwebxemphimv2.service.IFilmService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FilmService implements IFilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    @Override
    public Film getById(int id) {
        Optional<Film> optionalFilm = filmRepository.findById(id);
        if (optionalFilm.isPresent()){
            return optionalFilm.get();
        } else {
            throw new AppException(ErrorResponseEnum.NOT_FOUND_FILM);
        }
    }

    @Override
    public Film create(FilmCreateRequest dto) {
        Film film = new Film();
        BeanUtils.copyProperties(dto,film);
        return filmRepository.save(film);
    }

    @Override
    public Film update(FilmUpdateRequest dto) {
        Film film = getById(dto.getId());
        if (film != null){
            BeanUtils.copyProperties(dto,film);
            return filmRepository.save(film);
        } else {
            throw new AppException(ErrorResponseEnum.NOT_FOUND_FILM);
        }
    }

    @Override
    public void deleteById(int id) {
        Optional<Film> optionalFilm = filmRepository.findById(id);
        if (optionalFilm.isPresent()){
            filmRepository.deleteById(id);
        } else {
            throw new AppException(ErrorResponseEnum.NOT_FOUND_FILM);
        }
    }

    @Override
    public Page<Film> search(SearchFilmRequest request) {
        PageRequest pageRequest = null;
        if ("DESC".equals(request.getSortType())){
            pageRequest = PageRequest.of(request.getPage() -1, request.getSize(), Sort.by(request.getSortField()).descending());
        } else {
            pageRequest = PageRequest.of(request.getPage() -1, request.getSize(), Sort.by(request.getSortField()).ascending());
        }
        Specification<Film> condition = FilmSpecification.buildCondition(request);
        return filmRepository.findAll(condition,pageRequest);
    }

    @Override
    public List<Film> findByTypeFilm(TypeFilm typeFilm) {
        if (typeFilm != null){
            return filmRepository.findAllByTypeFilm(typeFilm);
        } else {
            return filmRepository.findAll();
        }
    }
}
