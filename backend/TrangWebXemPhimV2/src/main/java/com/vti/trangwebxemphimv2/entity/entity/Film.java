package com.vti.trangwebxemphimv2.entity.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "film")
public class Film extends BaseEntity{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "linkFilm")
    private String linkFilm;

    @Column(name = "`image`")
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "`status`")
    private FilmStatus status;

    @Column(name = "productionYear")
    private int productionYear;

    @Column(name = "nation")
    private String nation;

    @Enumerated(EnumType.STRING)
    @Column(name = "`genre`")
    private Genre genre;

    @Column(name = "performer")
    private String performer;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeFilm")
    private TypeFilm typeFilm;

    @Column(name = "`duration`")
    private String duration;

    @Column(name = "`rating`")
    private double rating;
}
