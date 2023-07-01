package com.vti.trangwebxemphimv2.repository;

import com.vti.trangwebxemphimv2.entity.entity.FilmStatus;
import com.vti.trangwebxemphimv2.entity.entity.WatchLater;
import com.vti.trangwebxemphimv2.entity.entity.WatchLaterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchLaterRepository extends JpaRepository<WatchLater, Integer> {
    List<WatchLater> findAllByAccount_IdAndFilmStatus(int accountId, WatchLaterStatus status);
    List<WatchLater> findAllByAccount_Id(int accountId);
    Optional<WatchLater> findAllByAccount_IdAndFilm_Id(int accountId, int filmId);
}
