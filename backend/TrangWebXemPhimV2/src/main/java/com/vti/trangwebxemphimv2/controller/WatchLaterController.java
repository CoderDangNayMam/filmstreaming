package com.vti.trangwebxemphimv2.controller;

import com.vti.trangwebxemphimv2.entity.dto.WatchLaterCreateRequest;
import com.vti.trangwebxemphimv2.entity.entity.WatchLater;
import com.vti.trangwebxemphimv2.entity.entity.WatchLaterStatus;
import com.vti.trangwebxemphimv2.service.iml.WatchLaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/watch-later")
@CrossOrigin("*")
@Validated
public class WatchLaterController {
    @Autowired
    private WatchLaterService watchLaterService;

    @GetMapping("/get-all")
    public List<WatchLater> getAll(){
        return watchLaterService.getAll();
    }

    @GetMapping("/get-by-status")
    public List<WatchLater> findByWatchLaterStatus(@RequestParam(required = false) WatchLaterStatus status, @RequestParam int accountId ){
        return watchLaterService.findByWatchLaterStatus(status, accountId);
    }
    @PostMapping("/create")
    public WatchLater create(@RequestBody WatchLaterCreateRequest dto){
        return watchLaterService.create(dto);
    }

    @PostMapping("/watched/{watchLaterId}")
    public WatchLater watched(@PathVariable int watchLaterId){
        return watchLaterService.watched(watchLaterId);
    }
    @PostMapping("/cancel/{watchLaterId}")
    public WatchLater cancel(@PathVariable int watchLaterId){
        return watchLaterService.cancel(watchLaterId);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id){
        watchLaterService.deleteById(id);
    }

    @GetMapping("/get-all/{id}")
    public List<WatchLater> getAllByAccountId(@PathVariable int id){
        return watchLaterService.getAllByAccountId(id);
    }
}
