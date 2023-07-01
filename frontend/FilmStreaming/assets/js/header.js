"use strict";
let isManager =true;

$(function () {
    checkLogin();
});

function checkLogin(){
    let username = localStorage.getItem("username");
    let avatar = localStorage.getItem("avatar");

    let textNoLogin = '<a href="/login.html">' +
    '<button class="btn btn-primary">Sign in</button>' +
    '</a>'

    let textUserLogin = '<li class="header__navbar-item header__navbar-user">'
    + '<img src="./assets/images/logo.svg" alt="" class="header__navbar-user-img" />'
    + '<span class="header__navbar-user-name">'+ username + '</span>'
    + '<ul class="header__navbar-user-menu"><li class="header__navbar-user-item">'
    + '<a href="">Tài khoản của tôi</a></li><li class="header__navbar-user-item">'
    + '<a href="">Địa chỉ của tôi</a></li><li class="header__navbar-user-item">'
    + '<a href="">Đơn mua</a> </li><li class="header__navbar-user-item header__navbar-user-item--separate">'
    + '<a href="" onclick="logout()">Đăng xuất</a></li> </ul></li>'

    if(localStorage.getItem("token") === null){
     console.log("chưa đăng nhập")
     document.getElementById("user-login").innerHTML = textNoLogin;
    } else {
     console.log("Đã đăng nhập")
     document.getElementById("user-login").innerHTML = textUserLogin;
    }
 }

 function logout(){
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    localStorage.removeItem("fullName");
    localStorage.removeItem("id");
    localStorage.removeItem("role");
    window.location.href = "/";
 }