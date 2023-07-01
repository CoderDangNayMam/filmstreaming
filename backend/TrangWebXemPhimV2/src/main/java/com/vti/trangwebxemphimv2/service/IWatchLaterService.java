package com.vti.trangwebxemphimv2.service;

import com.vti.trangwebxemphimv2.entity.dto.WatchLaterCreateRequest;
import com.vti.trangwebxemphimv2.entity.entity.WatchLater;
import com.vti.trangwebxemphimv2.entity.entity.WatchLaterStatus;

import java.util.List;

public interface IWatchLaterService {
    List<WatchLater> getAll();

    List<WatchLater> findByWatchLaterStatus(WatchLaterStatus watchLaterStatus, int accountId);

    WatchLater create(WatchLaterCreateRequest dto);

    WatchLater watched(int watchLaterId);

    WatchLater cancel(int watchLaterId);

    void deleteById(int id);

    List<WatchLater> getAllByAccountId(int accountId);
}
