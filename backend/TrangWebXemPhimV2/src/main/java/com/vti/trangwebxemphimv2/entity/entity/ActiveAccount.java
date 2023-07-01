package com.vti.trangwebxemphimv2.entity.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "activeAccount")
public class ActiveAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "uuid")
    private String uuid;

    @ManyToOne()
    @JoinColumn(name = "userId")
    private Account account;
}
