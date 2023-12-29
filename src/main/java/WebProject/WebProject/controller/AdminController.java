package WebProject.WebProject.controller;

import WebProject.WebProject.entity.*;
import WebProject.WebProject.model.Mail;
import WebProject.WebProject.repository.ProductAuthorRepository;
import WebProject.WebProject.repository.ProductCategoryRepository;
import WebProject.WebProject.repository.ProductImageRepository;
import WebProject.WebProject.repository.ProductRepository;
import WebProject.WebProject.repository.ThongKeRepository;
import WebProject.WebProject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    OrderService orderService;
    @Autowired
    ThongKeRepository thongKeRepository;
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    Order_ItemService order_ItemService;

    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    MailService mailService;

    @Autowired
    HttpSession session;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    AuthorService authorService;

    @Autowired
    ProducerService producerService;


    @Autowired
    ProductRepository productRepository;

    @Autowired
    BookCoverService bookCoverService;

    @Autowired
    ProductAuthorRepository productAuthorRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    CookieService cookieService;

    java.util.Date date = new java.util.Date();
    SimpleDateFormat fm = new SimpleDateFormat("yyyy");
    String hienTai = fm.format(date);

    @GetMapping("signin-admin")
    public String SignInAdminView(Model model) {
        String err_sign_admin = (String) session.getAttribute("err_sign_admin");
        model.addAttribute("err_sign_admin", err_sign_admin);
        session.setAttribute("err_sign_admin", null);
        return "signin-admin";
    }

    @PostMapping("signin-admin")
    public String SignInAdminHandel(@ModelAttribute("login-name") String login_name,
                                    @ModelAttribute("pass") String pass, Model model) throws Exception {
        User admin = userService.findByIdAndRole(login_name, "admin");
        if (admin == null) {
            session.setAttribute("err_sign_admin", "UserName or Password is not correct!");
            return "redirect:/signin-admin";
        } else {
            String decodedValue = new String(Base64.getDecoder().decode(admin.getPassword()));
            if (!decodedValue.equals(pass)) {
                session.setAttribute("err_sign_admin", "UserName or Password is not correct!");
                return "redirect:/signin-admin";
            } else {
                session.setAttribute("admin", admin);
                return "redirect:/home-admin";
            }
        }

    }

    @GetMapping("dang-xuat")
    public String LogOutAdmin(Model model) {
        session.setAttribute("admin", null);
        return "redirect:/signin-admin";
    }

//    @GetMapping("dashboard")
//    public String DashboardView(Model model) {
//        User admin = (User) session.getAttribute("admin");
//        if (admin == null) {
//            return "redirect:/signin-admin";
//        } else {
//            List<Order> listOrder = orderService.findAll();
//            List<Product> listProduct = productService.getAllProduct();
//            List<User> listUser = userService.findAll();
//            List<Category> listCategory = categoryService.findAll();
//
//            List<Order> recentOrders = orderService.findTop5RecentOrder();
//            List<String> recentUser = orderService.findTop5RecentCustomer();
//            List<User> recentCustomer = new ArrayList<>();
//            for (String y : recentUser) {
//                recentCustomer.add(userService.findByIdAndRole(y, "user"));
//            }
//            model.addAttribute("Total_Order", listOrder.size());
//            model.addAttribute("Total_Product", listProduct.size());
//            model.addAttribute("Total_User", listUser.size());
//            model.addAttribute("Total_Category", listCategory.size());
//            model.addAttribute("recentOrders", recentOrders);
//            model.addAttribute("recentCustomer", recentCustomer);
//            return "dashboard";
//        }
//    }

    @GetMapping("/dashboard-invoice/{id}")
    public String InvoiceView(@PathVariable int id, Model model, HttpServletRequest request) {
        Order order = orderService.findById(id);
        List<Order_Item> listOrder_Item = order_ItemService.getAllByOrder_Id(order.getId());
        model.addAttribute("listOrder_Item", listOrder_Item);
        model.addAttribute("order", order);
        return "dashboard-invoice";
    }


//    @GetMapping("/thong-ke-admin-doanh-thu")
//    public String DashboardWalletView(Model model) {
//        User admin = (User) session.getAttribute("admin");
//        if (admin == null) {
//            return "redirect:/signin-admin";
//        } else {
//
//            List<Map<String, Object>> resultList = thongKeRepository.getTotalRevenueByDate();
//            model.addAttribute("resultList", resultList);
//
//
//            List<Order> listOrder = orderService.findAll();
//            List<Order> listPaymentWithMomo = orderService.findAllByPayment_Method("Payment with momo");
//            List<Order> listVi = orderService.findAllByPayment_Method("Ví");
//            List<Order> listPaymentOnDelivery = orderService.findAllByPayment_Method("Payment on delivery");
//            long TotalMomo = 0;
//            long vi = 0;
//            long TotalDelivery = 0;
//            for (Order y : listPaymentWithMomo) {
//                TotalMomo = TotalMomo + y.getTotal();
//            }
//            for (Order y : listPaymentOnDelivery) {
//                TotalDelivery = TotalDelivery + y.getTotal();
//            }
//            for (Order y : listVi) {
//                vi = vi + y.getTotal();
//            }
//            List<Order> listRecentMomo = orderService.findTop5OrderByPaymentMethod("Payment with momo");
//            List<Order> vi1 = orderService.findTop5OrderByPaymentMethod("Ví");
//            List<Order> listRecentDelivery = orderService.findTop5OrderByPaymentMethod("Payment on delivery");
//
//
//            model.addAttribute("vi", vi);
//            model.addAttribute("vi1", vi1);
//            model.addAttribute("TotalMomo", TotalMomo);
//            model.addAttribute("TotalDelivery", TotalDelivery);
//            model.addAttribute("TotalOrder", listOrder.size());
//            model.addAttribute("listRecentDelivery", listRecentDelivery);
//            model.addAttribute("listRecentMomo", listRecentMomo);
//            return "thongKeDT.html";
//        }
//    }
//
//    @GetMapping("/thong-ke-admin-doanh-thu/tim-kiem")
//    public String DashboardWalletView1(Model model,
//                                       @RequestParam("ngay1") String ngay1,
//                                       @RequestParam("ngay2") String ngay2) {
//        User admin = (User) session.getAttribute("admin");
//        if (admin == null) {
//            return "redirect:/signin-admin";
//        } else {
//            if (ngay1 == null || ngay2 == null) {
//                return "redirect:/thong-ke-admin-doanh-thu";
//            } else {
//                List<Map<String, Object>> resultList = thongKeRepository.getTotalRevenueByDate1(ngay1, ngay2);
//                model.addAttribute("resultList", resultList);
//
//                List<Order> listOrder = orderService.findAll();
//                List<Order> listPaymentWithMomo = orderService.findAllByPayment_Method("Payment with momo");
//                List<Order> listVi = orderService.findAllByPayment_Method("Ví");
//                List<Order> listPaymentOnDelivery = orderService.findAllByPayment_Method("Payment on delivery");
//                long TotalMomo = 0;
//                long vi = 0;
//                long TotalDelivery = 0;
//
//                for (Order y : listPaymentWithMomo) {
//                    TotalMomo = TotalMomo + y.getTotal();
//                }
//                for (Order y : listPaymentOnDelivery) {
//                    TotalDelivery = TotalDelivery + y.getTotal();
//                }
//                for (Order y : listVi) {
//                    vi = vi + y.getTotal();
//                }
//
//                List<Order> listRecentMomo = orderService.findTop5OrderByPaymentMethod("Payment with momo");
//                List<Order> vi1 = orderService.findTop5OrderByPaymentMethod("Ví");
//                List<Order> listRecentDelivery = orderService.findTop5OrderByPaymentMethod("Payment on delivery");
//
//                model.addAttribute("vi", vi);
//                model.addAttribute("vi1", vi1);
//                model.addAttribute("TotalMomo", TotalMomo);
//                model.addAttribute("TotalDelivery", TotalDelivery);
//                model.addAttribute("TotalOrder", listOrder.size());
//                model.addAttribute("listRecentDelivery", listRecentDelivery);
//                model.addAttribute("listRecentMomo", listRecentMomo);
//
//                return "thongKeDT.html";
//            }
//        }
//    }

    @GetMapping("/san-pham-admin")
    public String DashboardMyProductView(Model model, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo) {

        Page<Product> pageProduct = productService.findAll(pageNo, 5);
        for (Product product : pageProduct) {
            if (product.getProductImage().isEmpty()) {
                model.addAttribute("img", null);
            } else {
                model.addAttribute("img", "img");
            }
        }
        model.addAttribute("pageProduct", pageProduct.getContent());
        model.addAttribute("pageProductPage", pageProduct.getTotalPages());
        model.addAttribute("pageNumber", pageNo);
        model.addAttribute("Cate", new Product());
        model.addAttribute("a", null);
        return "/admin/sanpham/dashboard-myproducts";
    }

    @GetMapping("/san-pham/sua/{id}")
    public String DashboardMyProductEditView(@PathVariable int id, Model model) {
Cookie cookie =cookieService.create("idSP",String.valueOf(id),1);

        List<Category> listCategories = categoryService.getAll();
        List<Author> authorList = authorService.getAllAuthor();
        List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
        List<Producer> producerList = producerService.getAllProducer();

        // Lấy thông tin sản phẩm
        Product product = productService.getProductById(id);

        // Kiểm tra xem sản phẩm có tồn tại không

        // Lấy danh sách ProductCategory dựa trên sản phẩm
        List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

        List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
        List<ProductImage> productImages = productImageService.finalIdSP(product.getId());


        if (listProductCategories.size() == 1) {
            model.addAttribute("a", null);
        } else {
            model.addAttribute("a", "a");

        }

        if (productImages.size() == 1) {
            model.addAttribute("c", null);
        } else {
            model.addAttribute("c", "a");
        }

        if (productAuthorList.size() == 1) {
            model.addAttribute("b", null);
        } else {
            model.addAttribute("b", "a");
        }

        model.addAttribute("productImages", productImages);

        model.addAttribute("productAuthorList", productAuthorList);
        model.addAttribute("listProductCategories", listProductCategories);
        model.addAttribute("product", product);
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("listAuthor", authorList);
        model.addAttribute("listBookCover", bookCoverList);
        model.addAttribute("listProducer", producerList);

        String editProduct = (String) session.getAttribute("editProduct");
        model.addAttribute("editProduct", editProduct);
        session.setAttribute("editProduct", null);
        return "/admin/sanpham/dashboard-my-products-edit";

    }

    @GetMapping("/san-pham-all/{id}")
    public String DashboardMyProductEditViewALL(@PathVariable int id, Model model) {


        // Lấy thông tin sản phẩm
        Product product = productService.getProductById(id);

        // Kiểm tra xem sản phẩm có tồn tại không
        if (product == null) {
            // Xử lý khi sản phẩm không tồn tại, có thể chuyển hướng hoặc thông báo lỗi
            return "redirect:/error-page";
        }

        // Lấy danh sách ProductCategory dựa trên sản phẩm
        List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

        List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
        List<ProductImage> productImages = productImageService.finalIdSP(product.getId());


        if (listProductCategories.isEmpty()) {
            model.addAttribute("a", null);
        } else {
            model.addAttribute("a", "a");

        }

        if (productImages.isEmpty()) {
            model.addAttribute("c", null);
        } else {
            model.addAttribute("c", "a");
        }

        if (productAuthorList.isEmpty()) {
            model.addAttribute("b", null);
        } else {
            model.addAttribute("b", "a");
        }

        model.addAttribute("productImages", productImages);

        model.addAttribute("productAuthorList", productAuthorList);
        model.addAttribute("listProductCategories", listProductCategories);
        model.addAttribute("product", product);


        String editProduct = (String) session.getAttribute("editProduct");
        model.addAttribute("editProduct", editProduct);
        session.setAttribute("editProduct", null);
        return "/admin/sanpham/detail-san-pham.html";

    }


    @PostMapping("/san-pham/sua/{id}")
    public String DashboardUpdateProductHandel(Model model, @PathVariable() int id,
                                               @RequestParam("product_name") String product_name,
                                               @RequestParam("price") String price,
                                               @RequestParam("availability") String availability,
                                               @RequestParam("page_number") String pageNumber,
                                               @RequestParam("book_size") String bookSize,
                                               @RequestParam("year_publication") String yearPublication,
                                               @RequestParam("language") String language,
//                                            @RequestParam("category")  List<String> category
                                               @RequestParam("description") String description,
//                                            @RequestParam("author") List<Author> author,
                                               @RequestParam("book_cover") int bookCover,
                                               @RequestParam("producer") int producer,
                                               @RequestParam("listImage") MultipartFile[] listImage
            , HttpServletRequest request
    ) throws Exception {


        try {
            if (product_name.isEmpty()) {
                model.addAttribute("loi", "Tên Sản Phẩm Không Được Để Trống");

                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();

                // Lấy thông tin sản phẩm
                Product product = productService.getProductById(id);

                // Kiểm tra xem sản phẩm có tồn tại không


                // Lấy danh sách ProductCategory dựa trên sản phẩm
                List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

                List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
                List<ProductImage> productImages = productImageService.finalIdSP(product.getId());

                if (listProductCategories.size() == 1) {
                    model.addAttribute("a", null);
                } else {
                    model.addAttribute("a", "a");

                }

                if (productImages.size() == 1) {
                    model.addAttribute("c", null);
                } else {
                    model.addAttribute("c", "a");
                }

                if (productAuthorList.size() == 1) {
                    model.addAttribute("b", null);
                } else {
                    model.addAttribute("b", "a");
                }

                model.addAttribute("productImages", productImages);

                model.addAttribute("productAuthorList", productAuthorList);
                model.addAttribute("listProductCategories", listProductCategories);
                model.addAttribute("product", product);
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);

                String editProduct = (String) session.getAttribute("editProduct");
                model.addAttribute("editProduct", editProduct);
                session.setAttribute("editProduct", null);
                return "/admin/sanpham/dashboard-my-products-edit";
            } else if (price.isEmpty()) {
                model.addAttribute("loi1", "Giá Sản Phẩm Không Được Để Trống");

                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();

                // Lấy thông tin sản phẩm
                Product product = productService.getProductById(id);

                // Kiểm tra xem sản phẩm có tồn tại không


                // Lấy danh sách ProductCategory dựa trên sản phẩm
                List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

                List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
                List<ProductImage> productImages = productImageService.finalIdSP(product.getId());
                if (listProductCategories.size() == 1) {
                    model.addAttribute("a", null);
                } else {
                    model.addAttribute("a", "a");

                }

                if (productImages.size() == 1) {
                    model.addAttribute("c", null);
                } else {
                    model.addAttribute("c", "a");
                }

                if (productAuthorList.size() == 1) {
                    model.addAttribute("b", null);
                } else {
                    model.addAttribute("b", "a");
                }
                model.addAttribute("productImages", productImages);

                model.addAttribute("productAuthorList", productAuthorList);
                model.addAttribute("listProductCategories", listProductCategories);
                model.addAttribute("product", product);
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);

                String editProduct = (String) session.getAttribute("editProduct");
                model.addAttribute("editProduct", editProduct);
                session.setAttribute("editProduct", null);
                return "/admin/sanpham/dashboard-my-products-edit";

            } else if (Long.valueOf(price) < 1000) {
                model.addAttribute("loi2", "Giá Sản Phẩm Từ 1000 VNĐ Chở Lên");

                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();

                // Lấy thông tin sản phẩm
                Product product = productService.getProductById(id);

                // Kiểm tra xem sản phẩm có tồn tại không


                // Lấy danh sách ProductCategory dựa trên sản phẩm
                List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

                List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
                List<ProductImage> productImages = productImageService.finalIdSP(product.getId());
                if (listProductCategories.size() == 1) {
                    model.addAttribute("a", null);
                } else {
                    model.addAttribute("a", "a");

                }

                if (productImages.size() == 1) {
                    model.addAttribute("c", null);
                } else {
                    model.addAttribute("c", "a");
                }

                if (productAuthorList.size() == 1) {
                    model.addAttribute("b", null);
                } else {
                    model.addAttribute("b", "a");
                }
                model.addAttribute("productImages", productImages);

                model.addAttribute("productAuthorList", productAuthorList);
                model.addAttribute("listProductCategories", listProductCategories);
                model.addAttribute("product", product);
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);

                String editProduct = (String) session.getAttribute("editProduct");
                model.addAttribute("editProduct", editProduct);
                session.setAttribute("editProduct", null);
                return "/admin/sanpham/dashboard-my-products-edit";
            } else if (availability.isEmpty()) {
                model.addAttribute("loi3", "Số Lượng Không Được Để Trống");

                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();

                // Lấy thông tin sản phẩm
                Product product = productService.getProductById(id);

                // Kiểm tra xem sản phẩm có tồn tại không


                // Lấy danh sách ProductCategory dựa trên sản phẩm
                List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

                List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
                List<ProductImage> productImages = productImageService.finalIdSP(product.getId());

                if (listProductCategories.size() == 1) {
                    model.addAttribute("a", null);
                } else {
                    model.addAttribute("a", "a");

                }

                if (productImages.size() == 1) {
                    model.addAttribute("c", null);
                } else {
                    model.addAttribute("c", "a");
                }

                if (productAuthorList.size() == 1) {
                    model.addAttribute("b", null);
                } else {
                    model.addAttribute("b", "a");
                }

                model.addAttribute("productImages", productImages);

                model.addAttribute("productAuthorList", productAuthorList);
                model.addAttribute("listProductCategories", listProductCategories);
                model.addAttribute("product", product);
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);

                String editProduct = (String) session.getAttribute("editProduct");
                model.addAttribute("editProduct", editProduct);
                session.setAttribute("editProduct", null);
                return "/admin/sanpham/dashboard-my-products-edit";

            } else if (Integer.valueOf(availability) < 1) {
                model.addAttribute("loi4", "Số Lượng Phải Lớn Hơn 0");

                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();

                // Lấy thông tin sản phẩm
                Product product = productService.getProductById(id);

                // Kiểm tra xem sản phẩm có tồn tại không

                // Lấy danh sách ProductCategory dựa trên sản phẩm
                List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

                List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
                List<ProductImage> productImages = productImageService.finalIdSP(product.getId());


                if (listProductCategories.size() == 1) {
                    model.addAttribute("a", null);
                } else {
                    model.addAttribute("a", "a");

                }

                if (productImages.size() == 1) {
                    model.addAttribute("c", null);
                } else {
                    model.addAttribute("c", "a");
                }

                if (productAuthorList.size() == 1) {
                    model.addAttribute("b", null);
                } else {
                    model.addAttribute("b", "a");
                }

                model.addAttribute("productImages", productImages);

                model.addAttribute("productAuthorList", productAuthorList);
                model.addAttribute("listProductCategories", listProductCategories);
                model.addAttribute("product", product);
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);

                String editProduct = (String) session.getAttribute("editProduct");
                model.addAttribute("editProduct", editProduct);
                session.setAttribute("editProduct", null);
                return "/admin/sanpham/dashboard-my-products-edit";

            } else if (pageNumber.isEmpty()) {
                model.addAttribute("loi5", "Số Trang Không Được Để Trống");

                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();

                // Lấy thông tin sản phẩm
                Product product = productService.getProductById(id);

                // Kiểm tra xem sản phẩm có tồn tại không


                // Lấy danh sách ProductCategory dựa trên sản phẩm
                List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

                List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
                List<ProductImage> productImages = productImageService.finalIdSP(product.getId());

                if (listProductCategories.size() == 1) {
                    model.addAttribute("a", null);
                } else {
                    model.addAttribute("a", "a");

                }

                if (productImages.size() == 1) {
                    model.addAttribute("c", null);
                } else {
                    model.addAttribute("c", "a");
                }

                if (productAuthorList.size() == 1) {
                    model.addAttribute("b", null);
                } else {
                    model.addAttribute("b", "a");
                }

                model.addAttribute("productImages", productImages);

                model.addAttribute("productAuthorList", productAuthorList);
                model.addAttribute("listProductCategories", listProductCategories);
                model.addAttribute("product", product);
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);

                String editProduct = (String) session.getAttribute("editProduct");
                model.addAttribute("editProduct", editProduct);
                session.setAttribute("editProduct", null);
                return "/admin/sanpham/dashboard-my-products-edit";

            } else if (Integer.valueOf(pageNumber) < 1) {
                model.addAttribute("loi6", "Số Trang Phải Lớn Hơn 0");

                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();

                // Lấy thông tin sản phẩm
                Product product = productService.getProductById(id);

                // Kiểm tra xem sản phẩm có tồn tại không


                // Lấy danh sách ProductCategory dựa trên sản phẩm
                List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

                List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
                List<ProductImage> productImages = productImageService.finalIdSP(product.getId());

                if (listProductCategories.size() == 1) {
                    model.addAttribute("a", null);
                } else {
                    model.addAttribute("a", "a");

                }

                if (productImages.size() == 1) {
                    model.addAttribute("c", null);
                } else {
                    model.addAttribute("c", "a");
                }

                if (productAuthorList.size() == 1) {
                    model.addAttribute("b", null);
                } else {
                    model.addAttribute("b", "a");
                }

                model.addAttribute("productImages", productImages);

                model.addAttribute("productAuthorList", productAuthorList);
                model.addAttribute("listProductCategories", listProductCategories);
                model.addAttribute("product", product);
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);

                String editProduct = (String) session.getAttribute("editProduct");
                model.addAttribute("editProduct", editProduct);
                session.setAttribute("editProduct", null);
                return "/admin/sanpham/dashboard-my-products-edit";

            } else if (bookSize.isEmpty()) {
                model.addAttribute("loi7", "Kích Thước Không Được Để Trống");

                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();

                // Lấy thông tin sản phẩm
                Product product = productService.getProductById(id);

                // Kiểm tra xem sản phẩm có tồn tại không


                // Lấy danh sách ProductCategory dựa trên sản phẩm
                List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

                List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
                List<ProductImage> productImages = productImageService.finalIdSP(product.getId());

                if (listProductCategories.size() == 1) {
                    model.addAttribute("a", null);
                } else {
                    model.addAttribute("a", "a");

                }

                if (productImages.size() == 1) {
                    model.addAttribute("c", null);
                } else {
                    model.addAttribute("c", "a");
                }

                if (productAuthorList.size() == 1) {
                    model.addAttribute("b", null);
                } else {
                    model.addAttribute("b", "a");
                }

                model.addAttribute("productImages", productImages);

                model.addAttribute("productAuthorList", productAuthorList);
                model.addAttribute("listProductCategories", listProductCategories);
                model.addAttribute("product", product);
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);

                String editProduct = (String) session.getAttribute("editProduct");
                model.addAttribute("editProduct", editProduct);
                session.setAttribute("editProduct", null);
                return "/admin/sanpham/dashboard-my-products-edit";

            } else if (yearPublication.isEmpty()) {
                model.addAttribute("loi8", "Năm Sản Xuất Không Được Để Trống");

                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();

                // Lấy thông tin sản phẩm
                Product product = productService.getProductById(id);

                // Kiểm tra xem sản phẩm có tồn tại không


                // Lấy danh sách ProductCategory dựa trên sản phẩm
                List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

                List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
                List<ProductImage> productImages = productImageService.finalIdSP(product.getId());

                if (listProductCategories.size() == 1) {
                    model.addAttribute("a", null);
                } else {
                    model.addAttribute("a", "a");

                }

                if (productImages.size() == 1) {
                    model.addAttribute("c", null);
                } else {
                    model.addAttribute("c", "a");
                }

                if (productAuthorList.size() == 1) {
                    model.addAttribute("b", null);
                } else {
                    model.addAttribute("b", "a");
                }

                model.addAttribute("productImages", productImages);

                model.addAttribute("productAuthorList", productAuthorList);
                model.addAttribute("listProductCategories", listProductCategories);
                model.addAttribute("product", product);
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);

                String editProduct = (String) session.getAttribute("editProduct");
                model.addAttribute("editProduct", editProduct);
                session.setAttribute("editProduct", null);
                return "/admin/sanpham/dashboard-my-products-edit";

            } else if (Integer.valueOf(yearPublication) < 0 || Integer.valueOf(yearPublication) > 2024) {
                model.addAttribute("loi9", "Năm Sản Xuất Không Nhỏ Hơn 0 Và Lớn Hơn Năm Hiện Tại");

                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();

                // Lấy thông tin sản phẩm
                Product product = productService.getProductById(id);

                // Kiểm tra xem sản phẩm có tồn tại không

                // Lấy danh sách ProductCategory dựa trên sản phẩm
                List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

                List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
                List<ProductImage> productImages = productImageService.finalIdSP(product.getId());

                if (listProductCategories.size() == 1) {
                    model.addAttribute("a", null);
                } else {
                    model.addAttribute("a", "a");

                }

                if (productImages.size() == 1) {
                    model.addAttribute("c", null);
                } else {
                    model.addAttribute("c", "a");
                }

                if (productAuthorList.size() == 1) {
                    model.addAttribute("b", null);
                } else {
                    model.addAttribute("b", "a");
                }

                model.addAttribute("productImages", productImages);

                model.addAttribute("productAuthorList", productAuthorList);
                model.addAttribute("listProductCategories", listProductCategories);
                model.addAttribute("product", product);
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);

                String editProduct = (String) session.getAttribute("editProduct");
                model.addAttribute("editProduct", editProduct);
                session.setAttribute("editProduct", null);
                return "/admin/sanpham/dashboard-my-products-edit";

            } else if (language.isEmpty()) {

                model.addAttribute("loi10", "Ngôn Ngữ Không Được Để Trống");

                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();

                // Lấy thông tin sản phẩm
                Product product = productService.getProductById(id);

                // Kiểm tra xem sản phẩm có tồn tại không

                // Lấy danh sách ProductCategory dựa trên sản phẩm
                List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

                List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
                List<ProductImage> productImages = productImageService.finalIdSP(product.getId());


                if (listProductCategories.size() == 1) {
                    model.addAttribute("a", null);
                } else {
                    model.addAttribute("a", "a");

                }

                if (productImages.size() == 1) {
                    model.addAttribute("c", null);
                } else {
                    model.addAttribute("c", "a");
                }

                if (productAuthorList.size() == 1) {
                    model.addAttribute("b", null);
                } else {
                    model.addAttribute("b", "a");
                }

                model.addAttribute("productImages", productImages);

                model.addAttribute("productAuthorList", productAuthorList);
                model.addAttribute("listProductCategories", listProductCategories);
                model.addAttribute("product", product);
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);

                String editProduct = (String) session.getAttribute("editProduct");
                model.addAttribute("editProduct", editProduct);
                session.setAttribute("editProduct", null);
                return "/admin/sanpham/dashboard-my-products-edit";


            } else if (description.isEmpty()) {

                model.addAttribute("loi11", "Mô Tả Không Được Để Trống");

                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();

                // Lấy thông tin sản phẩm
                Product product = productService.getProductById(id);

                // Kiểm tra xem sản phẩm có tồn tại không


                // Lấy danh sách ProductCategory dựa trên sản phẩm
                List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

                List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
                List<ProductImage> productImages = productImageService.finalIdSP(product.getId());

                if (listProductCategories.size() == 1) {
                    model.addAttribute("a", null);
                } else {
                    model.addAttribute("a", "a");

                }

                if (productImages.size() == 1) {
                    model.addAttribute("c", null);
                } else {
                    model.addAttribute("c", "a");
                }

                if (productAuthorList.size() == 1) {
                    model.addAttribute("b", null);
                } else {
                    model.addAttribute("b", "a");
                }

                model.addAttribute("productImages", productImages);

                model.addAttribute("productAuthorList", productAuthorList);
                model.addAttribute("listProductCategories", listProductCategories);
                model.addAttribute("product", product);
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);

                String editProduct = (String) session.getAttribute("editProduct");
                model.addAttribute("editProduct", editProduct);
                session.setAttribute("editProduct", null);
                return "/admin/sanpham/dashboard-my-products-edit";


            } else {


//                    Category cate = categoryService.getAllCategoryById(category);
//                    List<Author> ath = (List<Author>) authorService.getAllAuthorById(author);
                BookCover bc = bookCoverService.getAllBookCoverById(bookCover);
                Producer pr = producerService.getAllProducerById(producer);
//                    System.out.println(cate);
                long millis = System.currentTimeMillis();
                Date create_at = new java.sql.Date(millis);
                Product newPro = productService.getProductById(Integer.valueOf(id));
                newPro.setCreated_At(create_at);
                newPro.setDescription(description);
                newPro.setIs_Active(1);
                newPro.setIs_Selling(1);
                newPro.setPrice(Long.valueOf(price));
                newPro.setProduct_Name(product_name);
                newPro.setQuantity(Integer.parseInt(availability));
                newPro.setSold(0);
//                    newPro.setCategory(cate);
//                    newPro.setAuthor(ath);
                newPro.setProducer(pr);
                newPro.setBookCover(bc);
                newPro.setBookSize(bookSize);
                newPro.setLanguage(language);
                newPro.setPageNumber(pageNumber);
                newPro.setYearPublication(Integer.valueOf(yearPublication));

                Producer producer1 = new Producer();
                producer1.setId(producer);
                newPro.setProducer(producer1);


                BookCover bookCover1 = new BookCover();
                bookCover1.setId(bookCover);

                newPro.setBookCover(bookCover1);


                productService.saveProduct(newPro);

                Product newPro1 = productService.getProductById(Integer.valueOf(id));

                String[] author = request.getParameterValues("author");
                String[] categories = request.getParameterValues("category");

                if (categories == null || categories.length == 0) {
                    if (author == null || author.length == 0) {
                        return "redirect:/san-pham-admin";
                    } else {
//                 Lặp qua danh sách tác giả và thêm vào bảng product_author
                        List<ProductAuthor> list = productAuthorRepository.findByProductId(id);
                        for (String a : author) {
                            for (ProductAuthor lista : list) {
                                if (lista.getAuthor().getId() == Integer.valueOf(a)) {
                                    Author author1 = authorService.getAllAuthorById(Integer.valueOf(a));
                                    model.addAttribute("loiTacGia", author1.getNameAuthor() + "  Đã Tồn Tại");
                                    List<Category> listCategories = categoryService.getAll();
                                    List<Author> authorList = authorService.getAllAuthor();
                                    List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                                    List<Producer> producerList = producerService.getAllProducer();

                                    // Lấy thông tin sản phẩm
                                    Product product = productService.getProductById(id);

                                    // Kiểm tra xem sản phẩm có tồn tại không


                                    // Lấy danh sách ProductCategory dựa trên sản phẩm
                                    List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

                                    List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
                                    List<ProductImage> productImages = productImageService.finalIdSP(product.getId());

                                    if (listProductCategories.size() == 1) {
                                        model.addAttribute("a", null);
                                    } else {
                                        model.addAttribute("a", "a");

                                    }

                                    if (productImages.size() == 1) {
                                        model.addAttribute("c", null);
                                    } else {
                                        model.addAttribute("c", "a");
                                    }

                                    if (productAuthorList.size() == 1) {
                                        model.addAttribute("b", null);
                                    } else {
                                        model.addAttribute("b", "a");
                                    }

                                    model.addAttribute("productImages", productImages);

                                    model.addAttribute("productAuthorList", productAuthorList);
                                    model.addAttribute("listProductCategories", listProductCategories);
                                    model.addAttribute("product", product);
                                    model.addAttribute("listCategories", listCategories);
                                    model.addAttribute("listAuthor", authorList);
                                    model.addAttribute("listBookCover", bookCoverList);
                                    model.addAttribute("listProducer", producerList);

                                    Cookie cookie = cookieService.create("idSP", String.valueOf(id), 1);

                                    String editProduct = (String) session.getAttribute("editProduct");
                                    model.addAttribute("editProduct", editProduct);
                                    session.setAttribute("editProduct", null);
                                    return "/admin/sanpham/dashboard-my-products-edit";
                                }

                            }
                            ProductAuthor productAuthor = new ProductAuthor();
                            productAuthor.setProduct(newPro1);
                            Author author1 = authorService.getAllAuthorById(Integer.valueOf(a));
                            productAuthor.setAuthor(author1);
                            productAuthorRepository.save(productAuthor);

                        }
                    }
                }
                else {
                    List<ProductCategory> list = productCategoryRepository.findByProductId(id);
                    for (String category : categories) {
                        for (ProductCategory lista : list) {
                            if (lista.getCategory().getId() == Integer.valueOf(category)) {
                                Category author1 = categoryService.getAllCategoryById(Integer.valueOf(category));
                                model.addAttribute("loiTheLoai", author1.getCategory_Name() + "  Đã Tồn Tại");
                                List<Category> listCategories = categoryService.getAll();
                                List<Author> authorList = authorService.getAllAuthor();
                                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                                List<Producer> producerList = producerService.getAllProducer();

                                // Lấy thông tin sản phẩm
                                Product product = productService.getProductById(id);

                                // Kiểm tra xem sản phẩm có tồn tại không


                                // Lấy danh sách ProductCategory dựa trên sản phẩm
                                List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

                                List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
                                List<ProductImage> productImages = productImageService.finalIdSP(product.getId());

                                if (listProductCategories.size() == 1) {
                                    model.addAttribute("a", null);
                                } else {
                                    model.addAttribute("a", "a");

                                }

                                if (productImages.size() == 1) {
                                    model.addAttribute("c", null);
                                } else {
                                    model.addAttribute("c", "a");
                                }

                                if (productAuthorList.size() == 1) {
                                    model.addAttribute("b", null);
                                } else {
                                    model.addAttribute("b", "a");
                                }

                                model.addAttribute("productImages", productImages);

                                model.addAttribute("productAuthorList", productAuthorList);
                                model.addAttribute("listProductCategories", listProductCategories);
                                model.addAttribute("product", product);
                                model.addAttribute("listCategories", listCategories);
                                model.addAttribute("listAuthor", authorList);
                                model.addAttribute("listBookCover", bookCoverList);
                                model.addAttribute("listProducer", producerList);

                                Cookie cookie = cookieService.create("idSP", String.valueOf(id), 1);

                                String editProduct = (String) session.getAttribute("editProduct");
                                model.addAttribute("editProduct", editProduct);
                                session.setAttribute("editProduct", null);
                                return "/admin/sanpham/dashboard-my-products-edit";
                            }
                            else {
                                ProductCategory productCategory = new ProductCategory();
                                productCategory.setProduct(newPro1);
                                Category category1 = categoryService.getAllCategoryById(Integer.valueOf(category));
                                productCategory.setCategory(category1);
                                productCategoryRepository.save(productCategory);
                            }
                        }

                        if (author == null || author.length == 0) {
                            return "redirect:/san-pham-admin";
                        } else {
//                 Lặp qua danh sách tác giả và thêm vào bảng product_author
                            List<ProductAuthor> listTG = productAuthorRepository.findByProductId(id);
                            for (String a : author) {
                                for (ProductAuthor lista : listTG) {
                                    if (lista.getAuthor().getId() == Integer.valueOf(a)) {
                                        Author author1 = authorService.getAllAuthorById(Integer.valueOf(a));
                                        model.addAttribute("loiTacGia", author1.getNameAuthor() + "  Đã Tồn Tại");
                                        List<Category> listCategories = categoryService.getAll();
                                        List<Author> authorList = authorService.getAllAuthor();
                                        List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                                        List<Producer> producerList = producerService.getAllProducer();

                                        // Lấy thông tin sản phẩm
                                        Product product = productService.getProductById(id);

                                        // Kiểm tra xem sản phẩm có tồn tại không


                                        // Lấy danh sách ProductCategory dựa trên sản phẩm
                                        List<ProductCategory> listProductCategories = productCategoryRepository.findByProduct(product);

                                        List<ProductAuthor> productAuthorList = productAuthorRepository.findByProductId(product.getId());
                                        List<ProductImage> productImages = productImageService.finalIdSP(product.getId());

                                        if (listProductCategories.size() == 1) {
                                            model.addAttribute("a", null);
                                        } else {
                                            model.addAttribute("a", "a");

                                        }

                                        if (productImages.size() == 1) {
                                            model.addAttribute("c", null);
                                        } else {
                                            model.addAttribute("c", "a");
                                        }

                                        if (productAuthorList.size() == 1) {
                                            model.addAttribute("b", null);
                                        } else {
                                            model.addAttribute("b", "a");
                                        }

                                        model.addAttribute("productImages", productImages);

                                        model.addAttribute("productAuthorList", productAuthorList);
                                        model.addAttribute("listProductCategories", listProductCategories);
                                        model.addAttribute("product", product);
                                        model.addAttribute("listCategories", listCategories);
                                        model.addAttribute("listAuthor", authorList);
                                        model.addAttribute("listBookCover", bookCoverList);
                                        model.addAttribute("listProducer", producerList);

                                        Cookie cookie = cookieService.create("idSP", String.valueOf(id), 1);

                                        String editProduct = (String) session.getAttribute("editProduct");
                                        model.addAttribute("editProduct", editProduct);
                                        session.setAttribute("editProduct", null);
                                        return "/admin/sanpham/dashboard-my-products-edit";
                                    }

                                }
                                ProductAuthor productAuthor = new ProductAuthor();
                                productAuthor.setProduct(newPro1);
                                Author author1 = authorService.getAllAuthorById(Integer.valueOf(a));
                                productAuthor.setAuthor(author1);
                                productAuthorRepository.save(productAuthor);

                            }
                        }
                    }

                }
                    for (MultipartFile y : listImage) {
                        String urlImg = cloudinaryService.uploadFile(y);
                        ProductImage img = new ProductImage();
                        img.setProduct(newPro1);
                        img.setUrl_Image(urlImg);
                        productImageService.save(img);
                    }
                    return "redirect:/san-pham-admin";

            }
        } catch (Exception e) {
            return "redirect:/san-pham-admin";
        }
    }

    @GetMapping("/dashboard-myproducts/delete-image/{id}")
    public String DeleteImage(@PathVariable int id, HttpServletRequest request) {
        Cookie cookie = cookieService.read("idSP");
        productImageService.deleteImg(id);
        return "redirect:/san-pham/sua/" + cookie.getValue();
    }

    @GetMapping("/tac-gia/delete/{id}")
    public String Deleteaue(@PathVariable long id, Model model, HttpServletRequest request) {
        Cookie cookie = cookieService.read("idSP");

        productAuthorRepository.deleteById(id);
        return "redirect:/san-pham/sua/" + cookie.getValue();
    }

    @GetMapping("/the-loai/delete/{id}")
    public String Deletethe(@PathVariable int id, Model model, HttpServletRequest request) {
        Cookie cookie = cookieService.read("idSP");

        productCategoryRepository.deleteById((long) id);

        return "redirect:/san-pham/sua/" + cookie.getValue();
    }


    @PostMapping("/dashboard-myproduct/search")
    public String DashboardMyproductSearch(@ModelAttribute("search-input") String search_input,
                                           @ModelAttribute("category-selected") int category_selected, Model model) {


//            List<Category> listCategories = categoryService.findAll();
//            Pageable pageable = PageRequest.of(0, 3);
//            Page<Product> pageProduct = productService.findAll(pageable);
//            model.addAttribute("pageProduct", pageProduct);
//            model.addAttribute("listCategories", listCategories);
//            return "/admin/sanpham/dashboard-myproducts";


        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Page<Product> pageProduct = null;
            Pageable pageable = PageRequest.of(0, 3);
            if (category_selected > 0) {
                pageProduct = productService.findByProduct_NameAndCategory_idContaining(search_input, category_selected,
                        pageable);
            } else {
                pageProduct = productService.findByProduct_NameContaining(search_input, pageable);
            }
            List<Category> listCategories = categoryService.getAll();
            String nameCategory = null;
            if (category_selected == 0) {
                nameCategory = null;
            } else {
                for (Category y : listCategories) {
                    if (y.getId() == category_selected) {
                        nameCategory = y.getCategory_Name();
                    }
                }
            }
            System.out.println(nameCategory);
            model.addAttribute("pageProduct", pageProduct);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("search_dashboard", "search_dashboard");
            model.addAttribute("search_input", search_input);
            model.addAttribute("nameCategory", nameCategory);
            session.setAttribute("search_input_dashboard", search_input);
            session.setAttribute("category_selected", category_selected);
            return "/admin/sanpham/dashboard-myproducts";
        }
    }

    @GetMapping("/dashboard-myproduct/search/{page}")
    public String DashboardMyproductSearchPage(@PathVariable int page, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            String search_input = (String) session.getAttribute("search_input_dashboard");
            int category_selected = (int) session.getAttribute("category_selected");
//			int category_selected = 0;
            Page<Product> pageProduct = null;
            Pageable pageable = PageRequest.of(page, 3);
            if (category_selected > 0) {
                pageProduct = productService.findByProduct_NameAndCategory_idContaining(search_input, category_selected,
                        pageable);
            } else {
                pageProduct = productService.findByProduct_NameContaining(search_input, pageable);
            }
            List<Category> listCategories = categoryService.getAll();
            model.addAttribute("pageProduct", pageProduct);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("search_dashboard", "search_dashboard");
            model.addAttribute("search_input", search_input);
            model.addAttribute("category_selected", category_selected);
            session.setAttribute("search_input_dashboard", search_input);
            return "/admin/sanpham/dashboard-myproducts";
        }
    }

    @GetMapping("/add-san-pham")
    public String DashboardAddProductView(Model model) {
        String addProduct = (String) session.getAttribute("addProduct");
        model.addAttribute("addProduct", addProduct);
        session.setAttribute("addProduct", null);
        List<Category> listCategories = categoryService.getAll();
        List<Author> authorList = authorService.getAllAuthor();
        List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
        List<Producer> producerList = producerService.getAllProducer();
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("listAuthor", authorList);
        model.addAttribute("listBookCover", bookCoverList);
        model.addAttribute("listProducer", producerList);
        return "/admin/sanpham/dashboard-addproduct";

    }

    @PostMapping("/add-san-pham")
    public String DashboardAddProductHandel(Model model,
                                            @RequestParam("product_name") String product_name,
                                            @RequestParam("price") String price,
                                            @RequestParam("availability") String availability,
                                            @RequestParam("page_number") String pageNumber,
                                            @RequestParam("book_size") String bookSize,
                                            @RequestParam("year_publication") String yearPublication,
                                            @RequestParam("language") String language,
//                                            @RequestParam("category")  List<String> category
                                            @RequestParam("description") String description,
//                                            @RequestParam("author") List<Author> author,
                                            @RequestParam("book_cover") int bookCover,
                                            @RequestParam("producer") int producer,
                                            @RequestParam("listImage") MultipartFile[] listImage
            , HttpServletRequest request
    ) throws Exception {


        try {
            if (product_name.isEmpty()) {
                model.addAttribute("loi", "Tên Sản Phẩm Không Được Để Trống");
                String addProduct = (String) session.getAttribute("addProduct");
                model.addAttribute("addProduct", addProduct);
                session.setAttribute("addProduct", null);
                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);
                return "/admin/sanpham/dashboard-addproduct";
            } else if (price.isEmpty()) {
                model.addAttribute("loi1", "Giá Sản Phẩm Không Được Để Trống");
                String addProduct = (String) session.getAttribute("addProduct");
                model.addAttribute("addProduct", addProduct);
                session.setAttribute("addProduct", null);
                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);
                return "/admin/sanpham/dashboard-addproduct";

            } else if (Long.valueOf(price) < 1000) {
                model.addAttribute("loi2", "Giá Sản Phẩm Từ 1000 VNĐ Chở Lên");
                String addProduct = (String) session.getAttribute("addProduct");
                model.addAttribute("addProduct", addProduct);
                session.setAttribute("addProduct", null);
                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);
                return "/admin/sanpham/dashboard-addproduct";
            } else if (availability.isEmpty()) {
                model.addAttribute("loi3", "Số Lượng Không Được Để Trống");
                String addProduct = (String) session.getAttribute("addProduct");
                model.addAttribute("addProduct", addProduct);
                session.setAttribute("addProduct", null);
                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);
                return "/admin/sanpham/dashboard-addproduct";

            } else if (Integer.valueOf(availability) < 1) {
                model.addAttribute("loi4", "Số Lượng Phải Lớn Hơn 0");
                String addProduct = (String) session.getAttribute("addProduct");
                model.addAttribute("addProduct", addProduct);
                session.setAttribute("addProduct", null);
                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);
                return "/admin/sanpham/dashboard-addproduct";

            } else if (pageNumber.isEmpty()) {
                model.addAttribute("loi5", "Số Trang Không Được Để Trống");
                String addProduct = (String) session.getAttribute("addProduct");
                model.addAttribute("addProduct", addProduct);
                session.setAttribute("addProduct", null);
                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);
                return "/admin/sanpham/dashboard-addproduct";

            } else if (Integer.valueOf(pageNumber) < 1) {
                model.addAttribute("loi6", "Số Trang Phải Lớn Hơn 0");
                String addProduct = (String) session.getAttribute("addProduct");
                model.addAttribute("addProduct", addProduct);
                session.setAttribute("addProduct", null);
                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);
                return "/admin/sanpham/dashboard-addproduct";

            } else if (bookSize.isEmpty()) {
                model.addAttribute("loi7", "Kích Thước Không Được Để Trống");
                String addProduct = (String) session.getAttribute("addProduct");
                model.addAttribute("addProduct", addProduct);
                session.setAttribute("addProduct", null);
                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);
                return "/admin/sanpham/dashboard-addproduct";

            } else if (yearPublication.isEmpty()) {
                model.addAttribute("loi8", "Năm Sản Xuất Không Được Để Trống");
                String addProduct = (String) session.getAttribute("addProduct");
                model.addAttribute("addProduct", addProduct);
                session.setAttribute("addProduct", null);
                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);
                return "/admin/sanpham/dashboard-addproduct";

            } else if (Integer.valueOf(yearPublication) < 0 || Integer.valueOf(yearPublication) > 2024) {
                model.addAttribute("loi9", "Năm Sản Xuất Không Nhỏ Hơn 0 Và Lớn Hơn Năm Hiện Tại");
                String addProduct = (String) session.getAttribute("addProduct");
                model.addAttribute("addProduct", addProduct);
                session.setAttribute("addProduct", null);
                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);
                return "/admin/sanpham/dashboard-addproduct";

            } else if (language.isEmpty()) {

                model.addAttribute("loi10", "Ngôn Ngữ Không Được Để Trống");
                String addProduct = (String) session.getAttribute("addProduct");
                model.addAttribute("addProduct", addProduct);
                session.setAttribute("addProduct", null);
                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);
                return "/admin/sanpham/dashboard-addproduct";


            } else if (description.isEmpty()) {

                model.addAttribute("loi11", "Mô Tả Không Được Để Trống");
                String addProduct = (String) session.getAttribute("addProduct");
                model.addAttribute("addProduct", addProduct);
                session.setAttribute("addProduct", null);
                List<Category> listCategories = categoryService.getAll();
                List<Author> authorList = authorService.getAllAuthor();
                List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                List<Producer> producerList = producerService.getAllProducer();
                model.addAttribute("listCategories", listCategories);
                model.addAttribute("listAuthor", authorList);
                model.addAttribute("listBookCover", bookCoverList);
                model.addAttribute("listProducer", producerList);
                return "/admin/sanpham/dashboard-addproduct";


            } else {
                for (MultipartFile y : listImage) {
                    String urlImg = cloudinaryService.uploadFile(y);
                    ProductImage img = new ProductImage();
//                    Category cate = categoryService.getAllCategoryById(category);
//                    List<Author> ath = (List<Author>) authorService.getAllAuthorById(author);
                    BookCover bc = bookCoverService.getAllBookCoverById(bookCover);
                    Producer pr = producerService.getAllProducerById(producer);
//                    System.out.println(cate);
                    long millis = System.currentTimeMillis();
                    Date create_at = new java.sql.Date(millis);
                    Product newPro = new Product();
                    newPro.setCreated_At(create_at);
                    newPro.setDescription(description);
                    newPro.setIs_Active(1);
                    newPro.setIs_Selling(1);
                    newPro.setPrice(Long.valueOf(price));
                    newPro.setProduct_Name(product_name);
                    newPro.setQuantity(Integer.parseInt(availability));
                    newPro.setSold(0);
//                    newPro.setCategory(cate);
//                    newPro.setAuthor(ath);
                    newPro.setProducer(pr);
                    newPro.setBookCover(bc);
                    newPro.setBookSize(bookSize);
                    newPro.setLanguage(language);
                    newPro.setPageNumber(pageNumber);
                    newPro.setYearPublication(Integer.valueOf(yearPublication));


                    Producer producer1 = producerService.getAllProducerById(producer);
                    newPro.setProducer(producer1);


                    BookCover bookCover1 = bookCoverService.getAllBookCoverById(bookCover);

                    newPro.setBookCover(bookCover1);

                    productService.saveProduct(newPro);
                    List<Product> listProducts = productService.getAllProduct();
                    Product newPro1 = listProducts.get(listProducts.size() - 1);


                    img.setProduct(newPro1);
                    img.setUrl_Image(urlImg);
                    productImageService.save(img);


                    String[] categories = request.getParameterValues("category");

                    if (categories == null || categories.length == 0) {
                        List<ProductImage> list = productImageRepository.findByProduct(newPro1);
                        for (ProductImage a : list) {
                            productImageRepository.delete(a);
                        }
                        productRepository.delete(newPro1);
                        model.addAttribute("loi12", "Không Được Để Trống Thể Loại");
                        String addProduct = (String) session.getAttribute("addProduct");
                        model.addAttribute("addProduct", addProduct);
                        session.setAttribute("addProduct", null);
                        List<Category> listCategories = categoryService.getAll();
                        List<Author> authorList = authorService.getAllAuthor();
                        List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                        List<Producer> producerList = producerService.getAllProducer();
                        model.addAttribute("listCategories", listCategories);
                        model.addAttribute("listAuthor", authorList);
                        model.addAttribute("listBookCover", bookCoverList);
                        model.addAttribute("listProducer", producerList);
                        return "/admin/sanpham/dashboard-addproduct";
                    } else {
                        for (String category : categories) {
                            ProductCategory productCategory = new ProductCategory();
                            productCategory.setProduct(newPro1);
                            Category category1 = categoryService.getAllCategoryById(Integer.valueOf(category));
                            productCategory.setCategory(category1);
                            productCategoryRepository.save(productCategory);
                        }
                    }
                    String[] author = request.getParameterValues("author");
                    if (author == null || author.length == 0) {
                        List<ProductImage> list = productImageRepository.findByProduct(newPro1);
                        for (ProductImage a : list) {
                            productImageRepository.delete(a);
                        }
                        productRepository.delete(newPro1);
                        model.addAttribute("loi13", "Không Được Để Trống Tác Giả");
                        String addProduct = (String) session.getAttribute("addProduct");
                        model.addAttribute("addProduct", addProduct);
                        session.setAttribute("addProduct", null);
                        List<Category> listCategories = categoryService.getAll();
                        List<Author> authorList = authorService.getAllAuthor();
                        List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                        List<Producer> producerList = producerService.getAllProducer();
                        model.addAttribute("listCategories", listCategories);
                        model.addAttribute("listAuthor", authorList);
                        model.addAttribute("listBookCover", bookCoverList);
                        model.addAttribute("listProducer", producerList);
                        return "/admin/sanpham/dashboard-addproduct";
                    } else {
//                 Lặp qua danh sách tác giả và thêm vào bảng product_author
                        for (String a : author) {
                            ProductAuthor productAuthor = new ProductAuthor();
                            productAuthor.setProduct(newPro1);
                            Author author1 = authorService.getAllAuthorById(Integer.valueOf(a));
                            productAuthor.setAuthor(author1);
                            productAuthorRepository.save(productAuthor);
                        }
                    }


                }
                session.setAttribute("addProduct", "addProductSuccess");
                return "redirect:/san-pham-admin";
            }
        } catch (Exception e) {
            model.addAttribute("loiimg", "Ảnh không được để trống");
            String addProduct = (String) session.getAttribute("addProduct");
            model.addAttribute("addProduct", addProduct);
            session.setAttribute("addProduct", null);
            List<Category> listCategories = categoryService.getAll();
            List<Author> authorList = authorService.getAllAuthor();
            List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
            List<Producer> producerList = producerService.getAllProducer();
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("listAuthor", authorList);
            model.addAttribute("listBookCover", bookCoverList);
            model.addAttribute("listProducer", producerList);
            return "/admin/sanpham/dashboard-addproduct";
        }
    }


    @GetMapping("dashboard-myprofile")
    public String DashboardMyProfile(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            String error_change_pass = (String) session.getAttribute("error_change_pass");
            String ChangePassSuccess = (String) session.getAttribute("ChangePassSuccess");
            String messageChangeProfile = (String) session.getAttribute("messageChangeProfile");
            model.addAttribute("messageChangeProfile", messageChangeProfile);
            model.addAttribute("error_change_pass", error_change_pass);
            model.addAttribute("ChangePassSuccess", ChangePassSuccess);
            session.setAttribute("error_change_pass", null);
            session.setAttribute("ChangePassSuccess", null);
            session.setAttribute("messageChangeProfile", null);
            model.addAttribute("admin", admin);
            return "dashboard-my-profile";
        }
    }

    @PostMapping("/dashboard-myprofile/changepassword")
    public String DashboardChangePassword(Model model, @ModelAttribute("current_password") String
            current_password,
                                          @ModelAttribute("new_password") String new_password,
                                          @ModelAttribute("confirm_password") String confirm_password, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            String decodedValue = new String(Base64.getDecoder().decode(admin.getPassword()));
            if (!decodedValue.equals(current_password)) {
                session.setAttribute("error_change_pass", "Current Password not correct!");
                return "redirect:/dashboard-myprofile";
            } else {

                if (!new_password.equals(confirm_password)) {
                    session.setAttribute("error_change_pass", "Confirm New Password not valid!");
                    return "redirect:/dashboard-myprofile";
                } else {
                    String encodedValue = Base64.getEncoder().encodeToString(new_password.getBytes());
                    admin.setPassword(encodedValue);
                    userService.saveUser(admin);
                    session.setAttribute("admin", admin);
                }
            }
            session.setAttribute("ChangePassSuccess", "ChangePassSuccess");
            return "redirect:/signin-admin";
        }
    }

    @PostMapping("/dashboard-myprofile/changeProfile")
    public String ChangeProfile(Model model, @ModelAttribute("avatar") MultipartFile avatar,
                                @ModelAttribute("fullname") String fullname, @ModelAttribute("phone") String phone,
                                @ModelAttribute("email") String email) throws IOException {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            if (!avatar.isEmpty()) {
                String url = cloudinaryService.uploadFile(avatar);
                admin.setAvatar(url);
            }
            admin.setUser_Name(fullname);
            admin.setEmail(email);
            admin.setPhone_Number(phone);
            userService.saveUser(admin);
            session.setAttribute("admin", admin);
            session.setAttribute("messageChangeProfile", "Change Success.");
            return "redirect:/dashboard-myprofile";
        }
    }


    @GetMapping("/dashboard-orders1")
    public String DashboardOrderView(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("id")));
            Page<Order> pageOrder = orderService.trangThaiDonHang(pageable, "1");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders";
        }
    }

    @GetMapping("/dashboard-orders1/{page}")
    public String DashboardOrderPageView(@PathVariable int page, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Order.desc("id")));
            Page<Order> pageOrder = orderService.trangThaiDonHang(pageable, "1");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders";
        }
    }

    @GetMapping("/dashboard-orders2")
    public String DashboardOrderView2(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("id")));
            Page<Order> pageOrder = orderService.trangThaiDonHang(pageable, "2");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders2";
        }
    }

    @GetMapping("/dashboard-orders2/{page}")
    public String DashboardOrderPageView2(@PathVariable int page, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Order.desc("id")));
            Page<Order> pageOrder = orderService.trangThaiDonHang(pageable, "2");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders2";
        }
    }

    //
    @GetMapping("/dashboard-orders3")
    public String DashboardOrderView3(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("id")));
            Page<Order> pageOrder = orderService.trangThaiDonHang(pageable, "3");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders3";
        }
    }

    @GetMapping("/dashboard-orders3/{page}")
    public String DashboardOrderPageView3(@PathVariable int page, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Order.desc("id")));
            Page<Order> pageOrder = orderService.trangThaiDonHang(pageable, "3");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders3";
        }
    }

    //
    @GetMapping("/dashboard-orders4")
    public String DashboardOrderView4(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("id")));
            Page<Order> pageOrder = orderService.trangThaiDonHang(pageable, "4");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders4";
        }
    }

    @GetMapping("/dashboard-orders4/{page}")
    public String DashboardOrderPageView4(@PathVariable int page, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Order.desc("id")));
            Page<Order> pageOrder = orderService.trangThaiDonHang(pageable, "4");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders4";
        }
    }

    //
    @GetMapping("/dashboard-orders9")
    public String DashboardOrderView5(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("id")));
            Page<Order> pageOrder = orderService.trangThaiDonHang(pageable, "5");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders5";
        }
    }

    @GetMapping("/dashboard-orders9/{page}")
    public String DashboardOrderPageView5(@PathVariable int page, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Order.desc("id")));
            Page<Order> pageOrder = orderService.trangThaiDonHang(pageable, "5");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders5";
        }
    }

    @GetMapping("/dashboard-orders6")
    public String DashboardOrderView6(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("id")));
            Page<Order> pageOrder = orderService.trangThaiDonHang(pageable, "6");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders6";
        }
    }

    @GetMapping("/dashboard-orders6/{page}")
    public String DashboardOrderPageView6(@PathVariable int page, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Order.desc("id")));
            Page<Order> pageOrder = orderService.trangThaiDonHang(pageable, "6");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders6";
        }
    }

    @GetMapping("/dashboard-orders7")
    public String DashboardOrderView7(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("id")));
            Page<Order> pageOrder = orderService.trangThaiDonHang(pageable, "7");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders7";
        }
    }

    @GetMapping("/dashboard-orders7/{page}")
    public String DashboardOrderPageView7(@PathVariable int page, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Order.desc("id")));
            Page<Order> pageOrder = orderService.trangThaiDonHang(pageable, "7");
            model.addAttribute("pageOrder", pageOrder);
            return "dashboard-orders7";
        }
    }

    @PostMapping("/send-message")
    public String SendMessage(Model model, @ModelAttribute("message") String message,
                              @ModelAttribute("email") String email, HttpServletRequest request) throws Exception {
        String referer = request.getHeader("Referer");
        System.out.println(message);
        System.out.println(email);
        Mail mail = new Mail();
        mail.setMailFrom("nguyentrunganhnta43@gmail.com");
        mail.setMailTo(email);
        mail.setMailSubject("Nhà sách Opacarophile");
        mail.setMailContent(message);
        mailService.sendEmail(mail);
        return "redirect:" + referer;
    }

    @GetMapping("/delete-order/{id}")
    public String DeleteOrder(@PathVariable int id, Model model, HttpServletRequest request) throws Exception {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            String referer = request.getHeader("Referer");
            Order order = orderService.findById(id);
            System.out.println(order);
            if (order != null) {
                for (Order_Item y : order.getOrder_Item()) {
                    order_ItemService.deleteById(y.getId());
                }
                orderService.deleteById(id);
            }
            return "redirect:" + referer;
        }
    }

    @GetMapping("/dashboard-orders/edit/{id}")
    public String updateOrder(@PathVariable int id, Model model, HttpServletRequest request) {
        User admin = (User) session.getAttribute("admin");
        long millis = System.currentTimeMillis();
        Date createAt = new java.sql.Date(millis);
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            String referer = request.getHeader("Referer");
            Order order = orderService.findById(id);
            if ("1".equals(order.getActiveOrder().getId())) {
                Mail mail1 = new Mail();
                mail1.setMailFrom("nguyentrunganhnta43@gmail.com");
                mail1.setMailTo(order.getEmail());
                mail1.setMailSubject("Nhà sách Opacarophile");
                mail1.setMailContent("Đơn hàng của bạn đã được xác nhận và đang chờ lấy hàng");

                mailService.sendEmail(mail1);
                order.setActiveOrder(ActiveOrder.builder().id("2").build());
                order.setConfirmDate(createAt);
            } else if ("2".equals(order.getActiveOrder().getId())) {
                Mail mail1 = new Mail();
                mail1.setMailFrom("nguyentrunganhnta43@gmail.com");
                mail1.setMailTo(order.getEmail());
                mail1.setMailSubject("Nhà sách Opacarophile");
                mail1.setMailContent("Lấy hàng thành công, đơn hàng của bạn đang được giao cho bên vận chuyển");
                mailService.sendEmail(mail1);
                order.setActiveOrder(ActiveOrder.builder().id("3").build());
                order.setPickupDate(createAt);
            } else if ("3".equals(order.getActiveOrder().getId())) {
                Mail mail1 = new Mail();
                mail1.setMailFrom("nguyentrunganhnta43@gmail.com");
                mail1.setMailTo(order.getEmail());
                mail1.setMailSubject("Nhà sách Opacarophile");
                mail1.setMailContent("Bên vẫn chuyển đã nhận đơn hàng, đang trên đường giao ");
                mailService.sendEmail(mail1);
                order.setActiveOrder(ActiveOrder.builder().id("4").build());
                order.setDeliveryDate(createAt);
            }
            orderService.saveOrder(order);
            return "redirect:" + referer;
        }
    }

    @GetMapping("/dashboard-orders-active4/edit/{id}")
    public String updateOrderActive4(@PathVariable int id, Model model,
                                     @ModelAttribute("active") String active, HttpServletRequest request) {
        User admin = (User) session.getAttribute("admin");
        TimeZone vietnamTimeZone = TimeZone.getTimeZone("Asia/Jakarta");

        // Tạo đối tượng SimpleDateFormat với múi giờ của Việt Nam
        SimpleDateFormat vietnamDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        vietnamDateFormat.setTimeZone(vietnamTimeZone);

        // Lấy ngày giờ hiện tại theo múi giờ của Việt Nam và định dạng thành chuỗi
        long millis = System.currentTimeMillis();
        String createAt = vietnamDateFormat.format(millis);

        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            String referer = request.getHeader("Referer");
            Order order = orderService.findById(id);
            if ("4".equals(order.getActiveOrder().getId())) {
                order.setActiveOrder(ActiveOrder.builder().id("5").build());
                order.setDeliveryFailedDate(createAt);
                Mail mail = new Mail();
                mail.setMailFrom("nguyentrunganhnta43@gmail.com");
                mail.setMailTo(order.getEmail());
                mail.setMailSubject("Nhà sách Opacarophile");
                mail.setMailContent("Đơn hàng với mã đơn: HD" + order.getId() + " bạn đặt đã bị hủy do người đặt không nhận hàng. Nếu bạn còn quan tâm tới nhà Sách Oparophile vui lòng truy cập website của cửa hàng. Xin chân trọng cảm ơn!");
                mailService.sendEmail(mail);
            }
            orderService.saveOrder(order);
            return "redirect:" + referer;
        }
    }

    @GetMapping("/dashboard-orders-active5/edit/{id}")
    public String updateOrderActive5(@PathVariable int id, Model model,
                                     @ModelAttribute("active") String active, HttpServletRequest request) {
        User admin = (User) session.getAttribute("admin");
        long millis = System.currentTimeMillis();
        Date createAt = new java.sql.Date(millis);
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            String referer = request.getHeader("Referer");
            Order order = orderService.findById(id);
            if ("4".equals(order.getActiveOrder().getId())) {
                Mail mail = new Mail();
                mail.setMailFrom("nguyentrunganhnta43@gmail.com");
                mail.setMailTo(order.getEmail());
                mail.setMailSubject("Nhà sách Opacarophile");
                mail.setMailContent("Đơn hàng với mã đơn: HD" + order.getId() + " đơn hàng đã giao thành công. Nếu bạn còn quan tâm tới nhà Sách Oparophile vui lòng truy cập website của cửa hàng. Xin chân trọng cảm ơn!");
                mailService.sendEmail(mail);
                order.setActiveOrder(ActiveOrder.builder().id("6").build());
            }
            order.setDeliverySuccesfulDate(createAt);
            orderService.saveOrder(order);
            return "redirect:" + referer;
        }
    }


}
