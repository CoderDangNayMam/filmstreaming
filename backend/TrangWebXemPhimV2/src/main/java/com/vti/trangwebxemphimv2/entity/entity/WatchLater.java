package com.vti.trangwebxemphimv2.entity.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "watchLater")
public class WatchLater extends BaseEntity{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "accountId") // tên của khoá ngoại trong database
    private Account account;

    @ManyToOne()
    @JoinColumn(name = "film_id") // tên của khoá ngoại trong database
    private Film film;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WatchLaterStatus status;
}
