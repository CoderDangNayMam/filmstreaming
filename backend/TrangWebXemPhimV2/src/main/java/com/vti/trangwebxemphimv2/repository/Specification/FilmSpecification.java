package com.vti.trangwebxemphimv2.repository.Specification;

import com.vti.trangwebxemphimv2.config.exception.AppException;
import com.vti.trangwebxemphimv2.config.exception.ErrorResponseEnum;
import com.vti.trangwebxemphimv2.entity.dto.SearchFilmRequest;
import com.vti.trangwebxemphimv2.entity.entity.Film;
import com.vti.trangwebxemphimv2.entity.entity.Genre;
import org.springframework.data.jpa.domain.Specification;

public class FilmSpecification {
    public static Specification<Film> buildCondition(SearchFilmRequest request) {
        return Specification.where(buildConditionName(request))
                .and(buildConditionProductionYear(request))
                .and(buildConditionGenre(request))
                .and(buildConditionNation(request))
                .and(buildConditionPerformer(request))
                .and(buildConditionTypeFilm(request));
    }

    public static Specification<Film> buildConditionName(SearchFilmRequest request) {
        if (request.getName() != null && !"".equals(request.getName())) {
            // tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {
                // root: chọn cột, field để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: khai báo loại so sánh dữ liệu (lớn hơn, nhỏ hơn, equal, like...)
                return cri.like(root.get("name"), "%" + request.getName() + "%");
            };
        } else {
            return null;
        }
    }
    public static Specification<Film> buildConditionProductionYear(SearchFilmRequest request) {
        if (request.getProdutionYear()>0) {
            // tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {
                // root: chọn cột, field để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: khai báo loại so sánh dữ liệu (lớn hơn, nhỏ hơn, equal, like...)
                return cri.equal(root.get("productionYear"), request.getProdutionYear());
            };
        } else {
            return null;
        }
    }
    public static Specification<Film> buildConditionGenre(SearchFilmRequest request) {
        if (request.getGenre()!=null && request.getGenre().size()>0){
            return (root, query, cri) -> {
                return root.get("genre").in(request.getGenre());
            };
        } else {
            return null;
        }
    }
    public static Specification<Film> buildConditionNation(SearchFilmRequest request) {
        if (request.getNation() != null && !"".equals(request.getName())) {
            // tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {
                // root: chọn cột, field để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: khai báo loại so sánh dữ liệu (lớn hơn, nhỏ hơn, equal, like...)
                return cri.like(root.get("nation"), "%" + request.getNation() +"%" );
            };
        } else {
            return null;
        }
    }
    public static Specification<Film> buildConditionPerformer(SearchFilmRequest request) {
        if (request.getPerformer() != null && !"".equals(request.getName())) {
            // tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {
                // root: chọn cột, field để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: khai báo loại so sánh dữ liệu (lớn hơn, nhỏ hơn, equal, like...)
                return cri.like(root.get("performer"), "%" + request.getPerformer() + "%");
            };
        } else {
            return null;
        }
    }
    public static Specification<Film> buildConditionTypeFilm(SearchFilmRequest request) {
        if (request.getTypeFilm() != null && !"".equals(request.getTypeFilm())) {
            // tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {
                // root: chọn cột, field để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: khai báo loại so sánh dữ liệu (lớn hơn, nhỏ hơn, equal, like...)
                return cri.equal(root.get("typeFilm"),  request.getTypeFilm());
            };
        } else {
            return null;
        }
    }

}
