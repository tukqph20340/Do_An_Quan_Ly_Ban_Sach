<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="/public/images/logo-ico.svg"/>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"/>
    <!-- Bootstrap CSS -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
    <title>Category</title>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-2 p-4 shadow"
             style="background-color: #0a3d62; height: 100vh">
            <div class="d-flex justify-content-center">
                <a class="navbar-brand" href="#"> <img alt="logo"
                                                       src="https://media.istockphoto.com/id/1304792520/vi/vec-to/s%C3%A1ch-store-thi%E1%BA%BFt-k%E1%BA%BF-logo-vector-kh%C3%A1i-ni%E1%BB%87m-bi%E1%BB%83u-t%C6%B0%E1%BB%A3ng-logo-book-shop.jpg?s=170667a&w=0&k=20&c=9Vl8kxwSdXh4ZsaR6bBMXRkxkCMD0bg36WOW4Nk19UY="
                                                       width="100"
                                                       height="100">
                </a>
            </div>
            <hr>
            <div class="list-group">
                <p class="text-secondary fw-bold">Components</p>
                <a href="/admin/san-pham/hien-thi"
                   class="list-group-item list-group-item-action border-0 "
                   style="background-color: #0a3d62; color: #dcdde1"> <span><i
                        class="fa-solid fa-cart-shopping fs-6 px-1"></i></span> Sách
                </a>
                <a href="/admin/hoa-don-chi-tiet/hien-thi"
                   class="list-group-item list-group-item-action border-0  "
                   style="background-color: #0a3d62; color: #dcdde1"> <span><i
                        class="fa-solid fa-calendar-check fs-6 px-1"></i></span> Hóa Đơn Chi Tiết
                </a><a href="/admin/giay-phep/hien-thi"
                       class="list-group-item list-group-item-action border-0  "
                       style="background-color: #0a3d62; color: #dcdde1"> <span>
							<i class="fa-solid fa-money-check-dollar fs-6 px-1">
                            </i>
					</span> Giấy Phép
            </a><a href="/admin/ban-quyen/hien-thi"
                   class="list-group-item list-group-item-action border-0  "
                   style="background-color: #0a3d62; color: #dcdde1"> <span><i
                    class="fa-solid fa-truck-fast fs-6 px-1"></i></span> Bản Quyền
            </a>
                <a href="/admin/khuyen-mai/hien-thi"
                   class="list-group-item list-group-item-action border-0 "
                   style="background-color: #0a3d62; color: #dcdde1"> <span><i
                        class="fa-solid fa-cart-shopping fs-6 px-1"></i></span> Khuyến mãi
                </a>
                <p class="text-secondary fw-bold">Extras</p>
                <a href="/admin/chuc-vu/hien-thi" class="list-group-item list-group-item-action border-0"
                   style="background-color: #0a3d62; color: #dcdde1"> <span><i
                        class="fa-solid fa-gauge-high fs-6 px-1"></i></span> Chức Vụ
                </a> <a href="/admin/nguoi-dung/hien-thi"
                        class="list-group-item list-group-item-action border-0"
                        style="background-color: #0a3d62; color: #dcdde1"> <span><i
                    class="fa-solid fa-user fs-6 px-1"></i></span> Người Dùng
            </a>
                <a href="/admin/dia-chi/hien-thi"
                   class="list-group-item list-group-item-action border-0  "
                   style="background-color: #0a3d62; color: #dcdde1"> <span>
							<i class="fa-solid fa-money-check-dollar fs-6 px-1">
                            </i>
					</span>Địa Chỉ
                </a> <a href="/admin/khach-hang/hien-thi"
                        class="list-group-item list-group-item-action border-0 "
                        style="background-color: #0a3d62; color: #dcdde1"> <span><i
                    class="fa-solid fa-share-nodes fs-6 px-1"></i></span> Khách Hàng
            </a>
                <a href="/admin/tac-gia/hien-thi" class="list-group-item list-group-item-action border-0"
                   style="background-color: #0a3d62; color: #dcdde1"> <span><i
                        class="fa-solid fa-gauge-high fs-6 px-1"></i></span> Tác Giả
                </a> <a href="/admin/nha-cung-cap/hien-thi"
                        class="list-group-item list-group-item-action border-0"
                        style="background-color: #0a3d62; color: #dcdde1"> <span><i
                    class="fa-solid fa-user fs-6 px-1"></i></span> Nhà Cung Cấp
            </a> <a href="/admin/nha-xuat-ban/hien-thi"
                    class="list-group-item list-group-item-action border-0 "
                    style="background-color: #0a3d62; color: #dcdde1"> <span><i
                    class="fa-solid fa-cart-shopping fs-6 px-1"></i></span> Nhà Xuất Bản
            </a><a href="/admin/the-loai/hien-thi"
                   class="list-group-item list-group-item-action border-0 "
                   style="background-color: #0a3d62; color: #dcdde1"> <span><i
                    class="fa-solid fa-cart-shopping fs-6 px-1"></i></span> Thể Loại
            </a>

            </div>
        </div>
        <div class="col-10 px-0">
            <nav class="navbar navbar-expand-lg shadow-sm"
                 style="background-color: #ffffff">
                <div class="container-fluid">
                    <div class="collapse navbar-collapse" id="navbarText">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item"><a class="nav-link active"
                                                    aria-current="page" href="/home/hien-thi">Home</a></li>
                            <li class="nav-item"><a class="nav-link" href="/home/truyen-tranh">Truyện Tranh</a>
                            </li>
                            <li class="nav-item"><a class="nav-link" href="/home/tieng-viet">Sách Tiếng Việt</a>
                            </li>
                            <li class="nav-item"><a class="nav-link" href="/home/tieng-anh">Sách Tiếng Anh</a>
                            </li>
                        </ul>
                        <span class="navbar-text" id="dropAccount" role="button"
                              data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fa-solid fa-user fs-4"></i>
							</span>
                        <ul class="dropdown-menu" aria-labelledby="dropAccount"
                            style="left: auto; right: 10px">
                            <li><a class="dropdown-item" href="/logout">Log out</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
            <div class="px-4 p-3">
                <div class="modal-body">
                    <form action="/admin/the-loai/update/${detail.idTL}" method="post">
                        <div class="row">
                            <input type="hidden" class="form-control" name="id" value="${detail.idTL}"/>
                            <div class="col-12">
                                <label>Mã Chức Vụ</label>
                                <input type="text" class="form-control" name="maTL" value="${detail.maTL}"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <label>Tên</label>
                                <input type="text" class="form-control" name="ten" value="${detail.ten}"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <label>Mô Tả</label>
                                <input type="text" class="form-control" name="moTa" value="${detail.moTa}"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <label>Trạng Thái</label>
                                <input type="text" class="form-control" name="trangThai" value="${detail.trangThai}"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="row mt-4">
                                    <button class="btn btn-success col-1 m-3" type="submit">
                                        Update
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
<script>
    var option = {
        animation: true,
        delay: 5000
    }
    document.getElementById("liveToastBtn").onclick = function () {
        var myAlert = document.getElementById("liveToast");
        var bsAlert = new bootstrap.Toast(myAlert, option);
        bsAlert.show();
    }
</script>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>