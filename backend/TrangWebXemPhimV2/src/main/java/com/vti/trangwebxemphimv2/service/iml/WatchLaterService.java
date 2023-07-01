package com.vti.trangwebxemphimv2.service.iml;


import com.vti.trangwebxemphimv2.config.exception.AppException;
import com.vti.trangwebxemphimv2.config.exception.ErrorResponseEnum;
import com.vti.trangwebxemphimv2.entity.dto.WatchLaterCreateRequest;
import com.vti.trangwebxemphimv2.entity.entity.Account;
import com.vti.trangwebxemphimv2.entity.entity.Film;
import com.vti.trangwebxemphimv2.entity.entity.WatchLater;
import com.vti.trangwebxemphimv2.entity.entity.WatchLaterStatus;
import com.vti.trangwebxemphimv2.repository.AccountRepository;
import com.vti.trangwebxemphimv2.repository.FilmRepository;
import com.vti.trangwebxemphimv2.repository.WatchLaterRepository;
import com.vti.trangwebxemphimv2.service.IWatchLaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WatchLaterService implements IWatchLaterService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private WatchLaterRepository watchLaterRepository;
    @Override
    public List<WatchLater> getAll() {
        return watchLaterRepository.findAll();
    }

    @Override
    public List<WatchLater> findByWatchLaterStatus(WatchLaterStatus status, int accountId) {
        Optional<WatchLater> optionalWatchLater = watchLaterRepository.findById(accountId);
        if (optionalWatchLater.isEmpty()){
            throw new AppException(ErrorResponseEnum.NOT_FOUND_ACCOUNT);
        }
        if (status != null){
            return watchLaterRepository.findAllByAccount_IdAndFilmStatus(accountId,status);
        } else {
            return watchLaterRepository.findAllByAccount_Id(accountId);
        }
    }

    @Override
    public WatchLater create(WatchLaterCreateRequest dto) {
        Optional<Account> optionalAccount = accountRepository.findById(dto.getAccountId());
        Optional<Film> optionalFilm = filmRepository.findById(dto.getFilmId());
        if (watchLaterRepository.findAllByAccount_IdAndFilm_Id(dto.getAccountId(),dto.getFilmId()).isPresent()){
            throw new AppException(ErrorResponseEnum.WATCHLATER_EXISTED);
        }
        if (optionalFilm.isEmpty()){
            throw new AppException(ErrorResponseEnum.NOT_FOUND_FILM);
        }
        if (optionalAccount.isEmpty()){
            throw new AppException(ErrorResponseEnum.NOT_FOUND_ACCOUNT);
        }
        Account account = optionalAccount.get();
        Film film = optionalFilm.get();
        WatchLater watchLater = new WatchLater();
        watchLater.setAccount(account);
        watchLater.setFilm(film);
        watchLater.setStatus(WatchLaterStatus.WATCHLATER);
        return watchLaterRepository.save(watchLater);

    }

    @Override
    public WatchLater watched(int watchLaterId) {
        Optional<WatchLater> optionalWatchLater = watchLaterRepository.findById(watchLaterId);
        if (optionalWatchLater.isPresent()){
            WatchLater watchLater = optionalWatchLater.get();
            watchLater.setStatus(WatchLaterStatus.WATCHED);
            return watchLaterRepository.save(watchLater);
        } else {
            return null;
        }
    }
    @Override
    public WatchLater cancel(int watchLaterId) {
        Optional<WatchLater> optionalWatchLater = watchLaterRepository.findById(watchLaterId);
        if (optionalWatchLater.isPresent()){
            WatchLater watchLater = optionalWatchLater.get();
            watchLater.setStatus(WatchLaterStatus.CANCEL);
            return watchLaterRepository.save(watchLater);
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(int id) {
        Optional<WatchLater> optionalWatchLater = watchLaterRepository.findById(id);
        if (optionalWatchLater.isPresent()){
            watchLaterRepository.deleteById(id);
        }
    }

    @Override
    public List<WatchLater> getAllByAccountId(int accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isEmpty()){
            throw new AppException(ErrorResponseEnum.NOT_FOUND_ACCOUNT);
        }
        return watchLaterRepository.findAllByAccount_Id(accountId);
    }
}
