"use strict";

let filmName = "";
let productionYear = 0;
let nation = "";
let genre = [];
let performer = "";
let typeFilm = null;

let rating = "";
let linkFilm = "";
let image = "";
let status = [];
let duration = "";

let isManagerFilm = true;
let token = "";
let pageSize = 4;
let pageNumber = 1;
let sortField = "id";
let sortType = "DESC";

let baseUrlFilm = "http://localhost:3333/api/v1/film";
let baseUrlWatchLater = "http://localhost:3333/api/v1/watch-later";

$(function () {
  buildManager();
  getListFilmV2();
  buildPagination();
});

function buildManager() {
  if ("USER" === localStorage.getItem("role") || localStorage.getItem("role") === null) {
    isManagerFilm = false;
  } else {
    isManagerFilm = true;
    // $("#button-add").empty();
    $("#button-add").empty().append(`<button type="button" class="btn base-font base-shoppe-bg" style=" width: 80%;" 
                                onclick="addFilm()">Thêm mới</button>`);
    $("admin-delete").empty().append()
  }
}

function addFilm() {
  document.getElementById("myModal").style.display = "block";
}

function fillterApply() {
  getFilmName();
  getListFilmV2();
  getListWatchLater();
}

function getFilmName() {
  filmName = document.getElementById("film_name_search").value;
}
var input = document.getElementById("film_name_search");

input.addEventListener("keypress", function (event) {
  if (event.key === "Enter") {
    event.preventDefault();
    document.getElementById("search_button").click();
  }
});

function SearchFilmRequest(filmName,
  productionYear, nation, genre, performer, typeFilm,
  pageSize, pageNumber, sortBy, sortType) {

  this.name = filmName;
  this.productionYear = productionYear;
  this.nation = nation;
  this.genre = genre;
  this.performer = performer;
  this.typeFilm = typeFilm;

  this.size = pageSize;
  this.page = pageNumber;
  this.sortField = sortBy;
  this.sortType = sortType;
};

function CreateFilmRequest(id, filmName, linkFilm, image, productionYear, status
  , nation, genre, performer, typeFilm,
  duration, rating) {

  this.id = id;
  this.name = filmName;
  this.linkFilm = linkFilm;
  this.image = image;
  this.productionYear = productionYear;
  this.status = status;
  this.nation = nation;
  this.genre = genre;
  this.performer = performer;
  this.typeFilm = typeFilm;
  this.duration = duration;
  this.rating = rating;
};

function getListFilmV2() {
  let request = new SearchFilmRequest(filmName,
    productionYear, nation, genre, performer, typeFilm, pageSize, pageNumber, sortField, sortType)
  //   ------------------------------------- API TÌM KIẾM FILM -------------------------------------
  $.ajax({
    url: baseUrlFilm + "/search",
    type: "POST",
    beforeSend: function (xhr) {
      xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
    },
    contentType: "application/json",
    data: JSON.stringify(request),
    error: function (err) {
      console.log(err)
      confirm(err.responseJSON.message)
    },

    success: function (data) {
      fillData(data.content);
      buildPagination(data.number + 1, data.totalPages);
    }
  });
};

function buildPagination(number, totalPages) {
  if (number === 1) {
    $("#pagination").empty().append(`<li class="pagination-item">
                              <a class="pagination-item__link">
                              <ion-icon name="chevron-back-outline"></ion-icon>
                              </a></li>`);
  } else {
    $("#pagination").empty().append(`<li class="pagination-item">
                              <a href="#" class="pagination-item__link " onclick="prePage()">
                              <ion-icon name="chevron-back-outline"></ion-icon>
                              </a></li>`);
  }

  for (let index = 1; index <= totalPages; index++) {
    if (number === (index)) {
      $('#pagination').append(`<li class="pagination-item pagination-item--active">
                                <a href="" class="pagination-item__link" onclick="chosePage(`+ index + `)">` + index + `</a>
                              </li>`);
    } else {
      $('#pagination').append(`<li class="pagination-item">
                                <a href="" class="pagination-item__link" onclick="chosePage(`+ index + `)">` + index + `</a>
                              </li>`);
    }
  }

  if (number === totalPages) {
    $("#pagination").append(`<li class="pagination-item">
                              <a class="pagination-item__link "">
                              <ion-icon name="chevron-forward-outline"></ion-icon>
                              </a></li>`);
  } else {
    $("#pagination").append(`<li class="pagination-item">
                              <a href="#" class="pagination-item__link " onclick="nextPage()">
                              <ion-icon name="chevron-forward-outline"></ion-icon>
                              </a></li>`);
  }


};

function chosePage(page) {
  event.preventDefault()
  pageNumber = page;
  getListFilmV2();
};
function prePage() {
  event.preventDefault()
  pageNumber--;
  getListFilmV2();
};

function nextPage() {
  event.preventDefault()
  pageNumber++;
  getListFilmV2();
};


function editFilm(id, filmName, linkFilm, image, productionYear, status
  , nation, genre, performer, typeFilm,
  duration, rating) {
  document.getElementById('id').value = id;
  document.getElementById('filmName').value = filmName;
  document.getElementById('linkFilm').value = linkFilm;
  document.getElementById('image').value = image;
  document.getElementById('productionYear').value = productionYear;
  document.getElementById('statusFilm').value = status;
  document.getElementById('nation').value = nation;
  document.getElementById('genre').value = genre;
  document.getElementById('performer').value = performer;
  document.getElementById('typeFilm').value = typeFilm;
  document.getElementById('duration').value = duration;
  document.getElementById('rating').value = rating;

  document.getElementById('myModal').style.display = "block";
};


function confirmDeleteFilm(id) {
  document.getElementById('modalConfirmDelete').style.display = "block";
  document.getElementById('id').value = id;
};

function deleteFilm() {
  let id = document.getElementById('id').value;
  console.log(id)
  //   ------------------------------------- API XOÁ SẢN PHẨM -------------------------------------
  $.ajax({
    url: baseUrlWatchLater + "/" + id,
    type: "DELETE",
    beforeSend: function (xhr) {
      xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
    },
    contentType: "application/json",
    error: function (err) {
      console.log(err)
      confirm(err.responseJSON.message)
    },
    success: function (data) {
      getListWatchLater();
      document.getElementById('modalConfirmDelete').style.display = "none";
    }
  })
};

function saveFilm() {
  // Lấy các thông tin cần thiết trên form
  const id = document.getElementById('id').value;
  const filmName = document.getElementById('filmName').value;
  const linkFilm = document.getElementById('linkFilm').value;
  const image = document.getElementById('image').value;
  const productionYear = document.getElementById('productionYear').value;
  const status = document.getElementById('statusFilm').value;
  const nation = document.getElementById('nation').value;
  const genre = document.getElementById('genre').value;
  const performer = document.getElementById('performer').value;
  const typeFilm = document.getElementById('typeFilm').value;
  const duration = document.getElementById('duration').value;
  const rating = document.getElementById('rating').value;

  // Tạo 1 request
  let request = new CreateFilmRequest(id, filmName, linkFilm, image, productionYear, status
    , nation, genre, performer, typeFilm,
    duration, rating);
  let url = (id === "") ? (baseUrlFilm + "/create") : (baseUrlFilm + "/update");
  let type = (id === "") ? "POST" : "PUT";

  //   ------------------------------------- API UPDATE, THÊM MỚI FILM -------------------------------------
  $.ajax({
    url: url,
    type: type,
    beforeSend: function (xhr) {
      xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
    },
    contentType: "application/json",
    data: JSON.stringify(request),
    error: function (err) {
      console.log(err)
      confirm(err.responseJSON.message)
    },
    success: function (data) {
      document.getElementById('myModal').style.display = "none";
      getListFilmV2();
    }
  });
};

function sortproductionYear(type) {
  sortField = "productionYear";
  sortType = type;
  getListFilmV2();
};

function sortRating() {
  sortField = "rating";
  sortType = "DESC";
  getListFilmV2();
};

function getTypeFilm(type) {
  typeFilm = type;
  getListFilmV2();
}

function showWatchLater(){
  $('#main').load('./watch-later.html')
  getListWatchLater();
}

function fillData(data) {
  $("#filmappending").empty();
  data.forEach(function (element) {
    let text = `<li>
      <div class="movie-card">
        <a href="#film-detail" onclick="findFilmByID(`+ element.id + `)">
          <figure class="card-banner">
            <img src="`+ element.image + `" alt="` + element.name + ` movie poster" onclick="findFilmByID(` + element.id + `)">
          </figure>
        </a>
        <div class="title-wrapper">
          <a onclick="findFilmByID(`+ element.id + `)">
            <h3 class="card-title">`+ element.name + `</h3>
          </a>
          <time datetime="2022">`+ element.productionYear + `</time>
        </div>
        <div class="card-meta">
          <div class="badge badge-outline">` + element.status + `</div>
          <div class="duration">
            <ion-icon name="time-outline"></ion-icon>
            <time datetime="PT137M">`+ element.duration + `</time>
          </div>
          <div class="rating">
            <ion-icon name="star"></ion-icon>
            <data>`+ element.rating + `</data>
          </div>
        </div>

        ${isManagerFilm
        ? `<br>
          <div class="card-meta"> 
            <button class="btn btn-primary" style="color: aliceblue;" onclick="editFilm('${element.id}', '${element.name}', '${element.linkFilm}', '${element.image}', '${element.productionYear}', '${element.status}', '${element.nation}', '${element.genre}', '${element.performer}', '${element.typeFilm}', '${element.duration}', '${element.rating}')">Update</button>
            <button class="btn btn-primary" style="color: aliceblue;" onclick="confirmDeleteFilm(' ${element.id} ')">Delete</button>
          
          </div>`
        : `<button class="btn btn-primary" style="color: aliceblue;" onclick="addToWatchLater('${localStorage.id} ',' ${element.id} ')">Add to Watch Later</button>`
      }

      </div>
    </li>`
    $("#filmappending").append(text)
  })
};


function CreateWatchLater(accountId, filmId) {
  this.accountId = accountId;
  this.filmId = filmId;
};

function addToWatchLater(accountId, id) {
  accountId = localStorage.getItem("id");
  let request = new CreateWatchLater(accountId, id);

  //   ------------------------------------- API THÊM VÀO XEM SAU -------------------------------------
  $.ajax({
    url: "http://localhost:3333/api/v1/watch-later/create",
    type: "POST",
    beforeSend: function (xhr) {
      xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
    },
    contentType: "application/json",
    data: JSON.stringify(request),
    error: function (err) {
      console.log(err)
      confirm(err.responseJSON.message)
    },
    success: function (data) {
      getListFilmV2();
      console.log(accountId, id)
      getListWatchLater();
    }
  });
};

function getListWatchLater() {
  $.ajax({
    url: "http://localhost:3333/api/v1/watch-later/get-all/" + localStorage.getItem("id"),
    type: 'GET',
    contentType: 'application/json', 
    error: function (err) {
      console.log(err)
      alert(err.responseJSON)
    },
    success: function (data) {
      fillDataWatchLater(data)
    }
  });
};
function fillDataWatchLater(data) {
  $("#watchLater").empty();
  data.forEach(function(element) {
    let text = `<li>
      <div class="movie-card">
        <a href="#film-detail" onclick="findFilmByID(`+ element.film.id + `)">
          <figure class="card-banner">
            <img src="`+ element.film.image + `" alt="` + element.film.name + ` movie poster" onclick="findFilmByID(` + element.film.id + `)">
          </figure>
        </a>
        <div class="title-wrapper">
          <a onclick="findFilmByID(`+ element.film.id + `)">
            <h3 class="card-title">`+ element.film.name + `</h3>
          </a>
          <time datetime="2022">`+ element.film.productionYear + `</time>
        </div>
        <div class="card-meta">
          <div class="badge badge-outline">` + element.film.status + `</div>
          <div class="duration">
            <ion-icon name="time-outline"></ion-icon>
            <time datetime="PT137M">`+ element.film.duration + `</time>
          </div>
          <div class="rating">
            <ion-icon name="star"></ion-icon>
            <data>`+ element.film.rating + `</data>
          </div>
        </div>
        <br>
            <button class="btn btn-primary" style="color: aliceblue;" onclick="confirmDeleteFilm(`+ element.id + `)">Delete</button>

      </div>
    </li>`
    $("#watchLater").append(text)
  })
};

function watched(watchLaterId) {
  $.ajax({
    url: "http://localhost:3333/api/v1/watch-later/watched/" + watchLaterId,
    type: 'POST',
    contentType: 'application/json',
    error: function (err) {
      console.log(err)
      alert(err.responseJSON)
    },
    success: function (data) {
      getListWatchLater();
    }
  });
};

function cancel(watchLaterId) {
  $.ajax({
    url: "http://localhost:3333/api/v1/watch-later/" + watchLaterId,
    type: 'DELETE',
    contentType: 'application/json',
    error: function (err) {
      console.log(err)
      alert(err.responseJSON)
    },
    success: function (data) {
      getListWatchLater();
    }
  });
};

function fillFilmDetail(film) {
  $("#main").empty();
  var result = $('#main').load('./movie-details.html', 
                function () {
                    $("#title-details").text(film.name);
                    $("#image-details").attr("src",film.image);
                    $("#status-details").text(film.status);
                    $("#genre-details").text(film.genre);
                    $("#productionYear-details").text(film.productionYear);
                    $("#duration-details").text(film.duration);
                    $("#sumary-details").text(film.sumary);
                    iframe(film);
                     } ); 
};


function findFilmByID(id) {
  $.ajax({
    url: "http://localhost:3333/api/v1/film/" + id,
    type: 'GET',
    contentType: 'application/json',
    error: function (err) {
      console.log(err)
      alert(err.responseJSON)
    },
    success: function (data) {
      fillFilmDetail(data);
    }
  });
};

function iframe(film) {
  console.log(film)
  $("#iframe_film").empty();
  $("#iframe_film").append(`<iframe width="942" height="530" src="`+film.linkFilm + `" title="`+film.name +
  `" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>`);
};

function filmModal() {
  document.getElementById("watchFilm").style.display = "block";
}

// https://www.youtube.com/embed/bd-lNeFKmhA
// https://www.youtube.com/watch?v=bd-lNeFKmhA