<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="/public/images/logo-ico-small.svg"/>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"/>
    <!-- Bootstrap CSS -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
    <title>Book Shop</title>
</head>
<style>


    html *::-webkit-scrollbar {
        border-radius: 0;
        width: 8px;
    }

    html *::-webkit-scrollbar {
        border-radius: 0;
        width: 8px;
    }

    html *::-webkit-scrollbar-thumb {
        background-color: rgba(0, 0, 0, .15);
    }

    html *::-webkit-scrollbar-thumb {
        background-color: rgba(0, 0, 0, .15);
    }

    html *::-webkit-scrollbar-track {
        border-radius: 0;
        background-color: rgba(0, 0, 0, 0);
    }

    html *::-webkit-scrollbar-track {
        border-radius: 0;
        background-color: rgba(0, 0, 0, 0);
    }
</style>
<body>
<div class="container-fluid">
    <nav class="navbar navbar-expand-lg row shadow-sm fixed-top px-3"
         style="background-color: #0a3d62">
        <div class="container-fluid">
            <a class="navbar-brand" href="#"> <img alt="logo"
                                                   src="https://media.istockphoto.com/id/1304792520/vi/vec-to/s%C3%A1ch-store-thi%E1%BA%BFt-k%E1%BA%BF-logo-vector-kh%C3%A1i-ni%E1%BB%87m-bi%E1%BB%83u-t%C6%B0%E1%BB%A3ng-logo-book-shop.jpg?s=170667a&w=0&k=20&c=9Vl8kxwSdXh4ZsaR6bBMXRkxkCMD0bg36WOW4Nk19UY="
                                                   width="80" height="80">
            </a>
            <button class="navbar-toggler " type="button"
                    data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
                    aria-controls="navbarNavDropdown" aria-expanded="false"
                    aria-label="Toggle navigation">
                <i class="fa-solid fa-bars fs-2"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link text-white"
                                            aria-current="page" href="/home">Trang Chủ</a></li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link text-white"
                                            aria-current="page" href="/home">Sách Thiếu Nhi</a></li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link text-white"
                                            aria-current="page" href="/home">Sách Văn Học</a></li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link text-white"
                                            aria-current="page" href="/home">Sách Kinh Tế</a></li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link text-white"
                                            aria-current="page" href="/home">Sách Tâm Lý</a></li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link text-white"
                                            aria-current="page" href="/home">Sách Tiểu Thuyết</a></li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link text-white"
                                            aria-current="page" href="/home">Giới Thiệu</a></li>
                </ul>

            </div>
            <div class="d-flex px-4">
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link text-white"
                                            id="dropAccount" role="button" data-bs-toggle="dropdown"
                                            aria-expanded="false" href="#"> <i
                            class="fa-solid fa-user fs-4"></i>
                    </a>
                        <ul class="dropdown-menu" aria-labelledby="dropAccount"
                            style="top: auto; left: auto;">
                            <sec:authorize access="!isAuthenticated()">
                                <li><a class="dropdown-item" href="/login">Login</a></li>
                            </sec:authorize>
                            <sec:authorize access="isAuthenticated()">
                                <li><a class="dropdown-item" href="/logout">Logout</a></li>
                            </sec:authorize>
                            <sec:authorize access="hasAuthority('1') and isAuthenticated()">
                                <li><a class="dropdown-item" href="/admin">Manage Users</a></li>
                            </sec:authorize>
                        </ul>
                    </li>
                    <li class="nav-item"><a class="nav-link text-white"
                                            aria-current="page" href="/user/carts"> <i
                            class="fa-solid fa-cart-shopping fs-4 position-relative"> <c:if
                            test="${ countCart >0}">
										<span
                                                class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger"
                                                style="font-size: 0.5em"> ${ countCart } <span
                                                class="visually-hidden">unread messages</span>
										</span>
                    </c:if>
                    </i>
                    </a></li>

                </ul>
            </div>
        </div>
    </nav>
    <%-- Slider --%>
    <div class="row" style="margin-top: 120px">
        <div id="carouselExampleInterval" class="carousel slide"
             data-bs-ride="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active" data-bs-interval="10000">
                    <img src="https://media.istockphoto.com/id/1221003467/vi/anh/c%E1%BB%ADa-h%C3%A0ng-s%C3%A1ch-b%C3%AAn-ngo%C3%A0i-v%E1%BB%9Bi-s%C3%A1ch-v%C3%A0-s%C3%A1ch-gi%C3%A1o-khoa-trong-tr%C6%B0ng-b%C3%A0y.jpg?s=1024x1024&w=is&k=20&c=iVLvnq17Etn-J06oxCZK0VJ_auyG7VCXRM0748SWpRY="
                         class="d-block w-100 rounded" alt="sale" style="height: 550px">
                </div>
                <div class="carousel-item" data-bs-interval="2000">
                    <img src="https://decopro.vn/wp-content/uploads/2019/05/Sach-trang-tri-hien-dai-1.jpg"
                         class="d-block w-100 rounded" alt="sale" style="height: 550px">
                </div>
                <div class="carousel-item">
                    <img src="https://lh3.googleusercontent.com/zlPsjzN1hhAZ51SAvA-vq_MJkQIi70i-8csgd_QGTEpk-8YYd3onzQdOMyiF4qaCDeGmEkldRWMPrT8aHDBWGPry4TQ-lifXLInCTHa8aiAwbGiq5-8i9NrGorqQ01yZsVlQ2TKYVg=w1560-h683-no"
                         class="d-block w-100 rounded" alt="sale" style="height: 550px">
                </div>
            </div>
            <button class="carousel-control-prev" type="button"
                    data-bs-target="#carouselExampleInterval" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button"
                    data-bs-target="#carouselExampleInterval" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>
    <%-- Main --%>

    <%-- Footer --%>
    <div class="row mt-5 p-4" style="background-color: #0a3d62">
        <div class="col-4 text-center">
            <img alt="logo"
                 src="https://media.istockphoto.com/id/1304792520/vi/vec-to/s%C3%A1ch-store-thi%E1%BA%BFt-k%E1%BA%BF-logo-vector-kh%C3%A1i-ni%E1%BB%87m-bi%E1%BB%83u-t%C6%B0%E1%BB%A3ng-logo-book-shop.jpg?s=170667a&w=0&k=20&c=9Vl8kxwSdXh4ZsaR6bBMXRkxkCMD0bg36WOW4Nk19UY="
                 height="100px" width="100">
            <p style="color: #dcdde1">FPT Polytechnic Shopping</p>
        </div>
        <div class="col-4">
            <p class="text-white fw-bold">INFORMATION</p>
            <p style="color: #dcdde1">About Us</p>
            <p style="color: #dcdde1">Delivery Information</p>
            <p style="color: #dcdde1">Privacy Policy</p>
            <p style="color: #dcdde1">Terms and Conditions</p>
            <p style="color: #dcdde1">Returns</p>
            <p style="color: #dcdde1">Gift Certificates</p>
        </div>
        <div class="col-4">
            <p class="text-white fw-bold">CONTACT US</p>
            <form action="">
                <label for="exampleFormControlInput1" class="form-label"
                       style="color: #dcdde1">Email address</label> <input type="email"
                                                                           class="form-control"
                                                                           id="exampleFormControlInput1"
                                                                           placeholder="name@example.com">
                <button type="button" class="btn btn-primary w-100 mt-2">Send</button>
            </form>
        </div>
        <hr>
        <p class="text-center text-white fw-bold">Nhóm 6</p>
    </div>
</div>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>