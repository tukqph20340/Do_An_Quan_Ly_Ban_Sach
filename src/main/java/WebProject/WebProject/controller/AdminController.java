package WebProject.WebProject.controller;

import WebProject.WebProject.entity.*;
import WebProject.WebProject.model.Mail;
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
    BookCoverService bookCoverService;

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


    @GetMapping("/thong-ke-admin-doanh-thu")
    public String DashboardWalletView(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {

            List<Map<String, Object>> resultList = thongKeRepository.getTotalRevenueByDate();
            model.addAttribute("resultList", resultList);


            List<Order> listOrder = orderService.findAll();
            List<Order> listPaymentWithMomo = orderService.findAllByPayment_Method("Payment with momo");
            List<Order> listVi = orderService.findAllByPayment_Method("Ví");
            List<Order> listPaymentOnDelivery = orderService.findAllByPayment_Method("Payment on delivery");
            long TotalMomo = 0;
            long vi = 0;
            long TotalDelivery = 0;
            for (Order y : listPaymentWithMomo) {
                TotalMomo = TotalMomo + y.getTotal();
            }
            for (Order y : listPaymentOnDelivery) {
                TotalDelivery = TotalDelivery + y.getTotal();
            }
            for (Order y : listVi) {
                vi = vi + y.getTotal();
            }
            List<Order> listRecentMomo = orderService.findTop5OrderByPaymentMethod("Payment with momo");
            List<Order> vi1 = orderService.findTop5OrderByPaymentMethod("Ví");
            List<Order> listRecentDelivery = orderService.findTop5OrderByPaymentMethod("Payment on delivery");


            model.addAttribute("vi", vi);
            model.addAttribute("vi1", vi1);
            model.addAttribute("TotalMomo", TotalMomo);
            model.addAttribute("TotalDelivery", TotalDelivery);
            model.addAttribute("TotalOrder", listOrder.size());
            model.addAttribute("listRecentDelivery", listRecentDelivery);
            model.addAttribute("listRecentMomo", listRecentMomo);
            return "thongKeDT.html";
        }
    }

    @GetMapping("/thong-ke-admin-doanh-thu/tim-kiem")
    public String DashboardWalletView1(Model model,
                                       @RequestParam("ngay1") String ngay1,
                                       @RequestParam("ngay2") String ngay2) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            if (ngay1 == null || ngay2 == null) {
                return "redirect:/thong-ke-admin-doanh-thu";
            } else {
                List<Map<String, Object>> resultList = thongKeRepository.getTotalRevenueByDate1(ngay1, ngay2);
                model.addAttribute("resultList", resultList);

                List<Order> listOrder = orderService.findAll();
                List<Order> listPaymentWithMomo = orderService.findAllByPayment_Method("Payment with momo");
                List<Order> listVi = orderService.findAllByPayment_Method("Ví");
                List<Order> listPaymentOnDelivery = orderService.findAllByPayment_Method("Payment on delivery");
                long TotalMomo = 0;
                long vi = 0;
                long TotalDelivery = 0;

                for (Order y : listPaymentWithMomo) {
                    TotalMomo = TotalMomo + y.getTotal();
                }
                for (Order y : listPaymentOnDelivery) {
                    TotalDelivery = TotalDelivery + y.getTotal();
                }
                for (Order y : listVi) {
                    vi = vi + y.getTotal();
                }

                List<Order> listRecentMomo = orderService.findTop5OrderByPaymentMethod("Payment with momo");
                List<Order> vi1 = orderService.findTop5OrderByPaymentMethod("Ví");
                List<Order> listRecentDelivery = orderService.findTop5OrderByPaymentMethod("Payment on delivery");

                model.addAttribute("vi", vi);
                model.addAttribute("vi1", vi1);
                model.addAttribute("TotalMomo", TotalMomo);
                model.addAttribute("TotalDelivery", TotalDelivery);
                model.addAttribute("TotalOrder", listOrder.size());
                model.addAttribute("listRecentDelivery", listRecentDelivery);
                model.addAttribute("listRecentMomo", listRecentMomo);

                return "thongKeDT.html";
            }
        }
    }

    @GetMapping("/san-pham-admin")
    public String DashboardMyProductView(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            List<Category> listCategories = categoryService.findAll();
            Pageable pageable = PageRequest.of(0, 3);
            Page<Product> pageProduct = productService.findAll(pageable);
            model.addAttribute("pageProduct", pageProduct);
            model.addAttribute("listCategories", listCategories);
            return "/admin/sanpham/dashboard-myproducts";
        }
    }

    @GetMapping("/san-pham/sua/{id}")
    public String DashboardMyProductEditView(@PathVariable int id, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            List<Category> listCategories = categoryService.findAll();
            List<Author> authorList = authorService.getAllAuthor();
            List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
            List<Producer> producerList = producerService.getAllProducer();
            Product product = productService.getProductById(id);
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
    }

    @PostMapping("/san-pham/sua")
    public String DashboardMyProductEditHandel(Model model, @ModelAttribute("product_id") int product_id,
                                               @ModelAttribute("product_name") String product_name,
                                               @ModelAttribute("price") String price, @ModelAttribute("availability") String availability,
                                               @ModelAttribute("page_number") String pageNumber, @ModelAttribute("book_size") String bookSize,
                                               @ModelAttribute("year_publication") int yearPublication, @ModelAttribute("language") String language,
                                               @ModelAttribute("category") int category, @ModelAttribute("description") String description,
                                               @ModelAttribute("author") int author, @ModelAttribute("book_cover") int bookCover, @ModelAttribute("producer") int producer,
                                               @ModelAttribute("listImage") MultipartFile[] listImage)
            throws Exception {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            if (listImage != null) {
                Category cate = categoryService.getCategoryById(category);
                Product product = productService.getProductById(product_id);
                Author ath = authorService.getAllAuthorById(author);
                BookCover bc = bookCoverService.getAllBookCoverById(bookCover);
                Producer pr = producerService.getAllProducerById(producer);
//				System.out.println(cate);
//				long millis = System.currentTimeMillis();
//				Date create_at = new java.sql.Date(millis);
//				Product newPro = new Product();
                product.setProduct_Name(product_name);
                product.setPrice(Long.valueOf(price));
                product.setQuantity(Integer.parseInt(availability));
                product.setCategory(cate);
                product.setProducer(pr);
                product.setAuthor(ath);
                product.setBookCover(bc);
                product.setDescription(description);
                product.setBookSize(bookSize);
                product.setLanguage(language);
                product.setPageNumber(pageNumber);
                product.setYearPublication(yearPublication);
                productService.saveProduct(product);
                for (MultipartFile y : listImage) {
                    if (!y.isEmpty()) {
                        String urlImg = cloudinaryService.uploadFile(y);
                        ProductImage img = new ProductImage();
                        img.setProduct(product);
                        img.setUrl_Image(urlImg);
                        productImageService.save(img);
                    }
                }
                session.setAttribute("editProduct", "editProductSuccess");
                return "redirect:/san-pham-admin";
            } else {
                return "redirect:/san-pham/sua/" + product_id;
            }

        }
    }

//    @GetMapping("/dashboard-myproducts/delete-image/{id}")
//    public String DeleteImage(@PathVariable int id, HttpServletRequest request) {
//        String referer = request.getHeader("Referer");
//        productImageService.deleteById(id);
//        return "redirect:" + referer;
//    }

    @GetMapping("/san-pham-admin/{page}")
    public String DashboardMyProductPageView(@PathVariable int page, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            List<Category> listCategories = categoryService.findAll();
            Pageable pageable = PageRequest.of(page, 3);
            Page<Product> pageProduct = productService.findAll(pageable);
            model.addAttribute("pageProduct", pageProduct);
            model.addAttribute("listCategories", listCategories);
            return "/admin/sanpham/dashboard-myproducts";
        }
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
            List<Category> listCategories = categoryService.findAll();
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
            List<Category> listCategories = categoryService.findAll();
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
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            String addProduct = (String) session.getAttribute("addProduct");
            model.addAttribute("addProduct", addProduct);
            session.setAttribute("addProduct", null);
            List<Category> listCategories = categoryService.findAll();
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

    @PostMapping("/add-san-pham")
    public String DashboardAddProductHandel(Model model, @RequestParam("product_name") String product_name,
                                            @RequestParam("price") String price, @RequestParam("availability") String availability,
                                            @RequestParam("page_number") String pageNumber, @RequestParam("book_size") String bookSize,
                                            @RequestParam("year_publication") String yearPublication, @RequestParam("language") String language,
                                            @RequestParam("category") int category, @RequestParam("description") String description,
                                            @RequestParam("author") int author, @RequestParam("book_cover") int bookCover, @RequestParam("producer") int producer,
                                            @RequestParam("listImage") MultipartFile[] listImage) throws Exception {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            if (product_name.isEmpty()) {
                model.addAttribute("loi", "Tên không được để trống");
                if (admin == null) {
                    return "redirect:/signin-admin";
                } else {
                    String addProduct = (String) session.getAttribute("addProduct");
                    model.addAttribute("addProduct", addProduct);
                    session.setAttribute("addProduct", null);
                    List<Category> listCategories = categoryService.findAll();
                    List<Author> authorList = authorService.getAllAuthor();
                    List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                    List<Producer> producerList = producerService.getAllProducer();
                    model.addAttribute("listCategories", listCategories);
                    model.addAttribute("listAuthor", authorList);
                    model.addAttribute("listBookCover", bookCoverList);
                    model.addAttribute("listProducer", producerList);
                    return "/admin/sanpham/dashboard-addproduct";
                }
            } else if (price.isEmpty()) {
                model.addAttribute("loi1", "Giá không được để trống");
                if (admin == null) {
                    return "redirect:/signin-admin";
                } else {
                    String addProduct = (String) session.getAttribute("addProduct");
                    model.addAttribute("addProduct", addProduct);
                    session.setAttribute("addProduct", null);
                    List<Category> listCategories = categoryService.findAll();
                    List<Author> authorList = authorService.getAllAuthor();
                    List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                    List<Producer> producerList = producerService.getAllProducer();
                    model.addAttribute("listCategories", listCategories);
                    model.addAttribute("listAuthor", authorList);
                    model.addAttribute("listBookCover", bookCoverList);
                    model.addAttribute("listProducer", producerList);
                    return "/admin/sanpham/dashboard-addproduct";
                }
            } else if (Integer.valueOf(price) < 1001) {
                model.addAttribute("loi2", "Giá không được nhỏ hơn 1000 VNĐ");
                if (admin == null) {
                    return "redirect:/signin-admin";
                } else {
                    String addProduct = (String) session.getAttribute("addProduct");
                    model.addAttribute("addProduct", addProduct);
                    session.setAttribute("addProduct", null);
                    List<Category> listCategories = categoryService.findAll();
                    List<Author> authorList = authorService.getAllAuthor();
                    List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                    List<Producer> producerList = producerService.getAllProducer();
                    model.addAttribute("listCategories", listCategories);
                    model.addAttribute("listAuthor", authorList);
                    model.addAttribute("listBookCover", bookCoverList);
                    model.addAttribute("listProducer", producerList);
                    return "/admin/sanpham/dashboard-addproduct";
                }
            } else if (availability.isEmpty()) {
                model.addAttribute("loi3", "Số Lượng không được để trống");
                if (admin == null) {
                    return "/admin/sanpham/redirect:/signin-admin";
                } else {
                    String addProduct = (String) session.getAttribute("addProduct");
                    model.addAttribute("addProduct", addProduct);
                    session.setAttribute("addProduct", null);
                    List<Category> listCategories = categoryService.findAll();
                    List<Author> authorList = authorService.getAllAuthor();
                    List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                    List<Producer> producerList = producerService.getAllProducer();
                    model.addAttribute("listCategories", listCategories);
                    model.addAttribute("listAuthor", authorList);
                    model.addAttribute("listBookCover", bookCoverList);
                    model.addAttribute("listProducer", producerList);
                    return "/admin/sanpham/dashboard-addproduct";
                }
            } else if (pageNumber.isEmpty()) {
                model.addAttribute("loi4", "Số trang không được để trống");
                if (admin == null) {
                    return "redirect:/signin-admin";
                } else {
                    String addProduct = (String) session.getAttribute("addProduct");
                    model.addAttribute("addProduct", addProduct);
                    session.setAttribute("addProduct", null);
                    List<Category> listCategories = categoryService.findAll();
                    List<Author> authorList = authorService.getAllAuthor();
                    List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                    List<Producer> producerList = producerService.getAllProducer();
                    model.addAttribute("listCategories", listCategories);
                    model.addAttribute("listAuthor", authorList);
                    model.addAttribute("listBookCover", bookCoverList);
                    model.addAttribute("listProducer", producerList);
                    return "/admin/sanpham/dashboard-addproduct";
                }
            } else if (Integer.valueOf(pageNumber) < 1) {
                model.addAttribute("loi5", "Giá không được nhỏ hơn 1 VNĐ");
                if (admin == null) {
                    return "redirect:/signin-admin";
                } else {
                    String addProduct = (String) session.getAttribute("addProduct");
                    model.addAttribute("addProduct", addProduct);
                    session.setAttribute("addProduct", null);
                    List<Category> listCategories = categoryService.findAll();
                    List<Author> authorList = authorService.getAllAuthor();
                    List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                    List<Producer> producerList = producerService.getAllProducer();
                    model.addAttribute("listCategories", listCategories);
                    model.addAttribute("listAuthor", authorList);
                    model.addAttribute("listBookCover", bookCoverList);
                    model.addAttribute("listProducer", producerList);
                    return "/admin/sanpham/dashboard-addproduct";
                }
            } else if (String.valueOf(yearPublication).isEmpty()) {
                model.addAttribute("loi6", "Năm sản xuất không được để trống");
                if (admin == null) {
                    return "redirect:/signin-admin";
                } else {
                    String addProduct = (String) session.getAttribute("addProduct");
                    model.addAttribute("addProduct", addProduct);
                    session.setAttribute("addProduct", null);
                    List<Category> listCategories = categoryService.findAll();
                    List<Author> authorList = authorService.getAllAuthor();
                    List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                    List<Producer> producerList = producerService.getAllProducer();
                    model.addAttribute("listCategories", listCategories);
                    model.addAttribute("listAuthor", authorList);
                    model.addAttribute("listBookCover", bookCoverList);
                    model.addAttribute("listProducer", producerList);
                    return "/admin/sanpham/dashboard-addproduct";
                }
            } else if (Integer.valueOf(yearPublication) > Integer.valueOf(String.valueOf(hienTai))) {
                model.addAttribute("loi7", "Năm sản xuất không được lớn hơn năm hiện tại");
                if (admin == null) {
                    return "/admin/sanpham/redirect:/signin-admin";
                } else {
                    String addProduct = (String) session.getAttribute("addProduct");
                    model.addAttribute("addProduct", addProduct);
                    session.setAttribute("addProduct", null);
                    List<Category> listCategories = categoryService.findAll();
                    List<Author> authorList = authorService.getAllAuthor();
                    List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                    List<Producer> producerList = producerService.getAllProducer();
                    model.addAttribute("listCategories", listCategories);
                    model.addAttribute("listAuthor", authorList);
                    model.addAttribute("listBookCover", bookCoverList);
                    model.addAttribute("listProducer", producerList);
                    return "/admin/sanpham/dashboard-addproduct";
                }
            } else if (description.isEmpty()) {
                model.addAttribute("loi8", "Mô tả không được để trống");
                if (admin == null) {
                    return "redirect:/signin-admin";
                } else {
                    String addProduct = (String) session.getAttribute("addProduct");
                    model.addAttribute("addProduct", addProduct);
                    session.setAttribute("addProduct", null);
                    List<Category> listCategories = categoryService.findAll();
                    List<Author> authorList = authorService.getAllAuthor();
                    List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                    List<Producer> producerList = producerService.getAllProducer();
                    model.addAttribute("listCategories", listCategories);
                    model.addAttribute("listAuthor", authorList);
                    model.addAttribute("listBookCover", bookCoverList);
                    model.addAttribute("listProducer", producerList);
                    return "/admin/sanpham/dashboard-addproduct";
                }
            } else if (language.isEmpty()) {
                model.addAttribute("loi9", "Ngôn ngữ không được để trống");
                if (admin == null) {
                    return "redirect:/signin-admin";
                } else {
                    String addProduct = (String) session.getAttribute("addProduct");
                    model.addAttribute("addProduct", addProduct);
                    session.setAttribute("addProduct", null);
                    List<Category> listCategories = categoryService.findAll();
                    List<Author> authorList = authorService.getAllAuthor();
                    List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                    List<Producer> producerList = producerService.getAllProducer();
                    model.addAttribute("listCategories", listCategories);
                    model.addAttribute("listAuthor", authorList);
                    model.addAttribute("listBookCover", bookCoverList);
                    model.addAttribute("listProducer", producerList);
                    return "/admin/sanpham/dashboard-addproduct";
                }
            } else if (bookSize.isEmpty()) {
                model.addAttribute("loi10", "Kích thước không được để trống");
                if (admin == null) {
                    return "redirect:/signin-admin";
                } else {
                    String addProduct = (String) session.getAttribute("addProduct");
                    model.addAttribute("addProduct", addProduct);
                    session.setAttribute("addProduct", null);
                    List<Category> listCategories = categoryService.findAll();
                    List<Author> authorList = authorService.getAllAuthor();
                    List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
                    List<Producer> producerList = producerService.getAllProducer();
                    model.addAttribute("listCategories", listCategories);
                    model.addAttribute("listAuthor", authorList);
                    model.addAttribute("listBookCover", bookCoverList);
                    model.addAttribute("listProducer", producerList);
                    return "/admin/sanpham/dashboard-addproduct";
                }
            } else {
                try {
                    Category cate = categoryService.getCategoryById(category);
                    Author ath = authorService.getAllAuthorById(author);
                    BookCover bc = bookCoverService.getAllBookCoverById(bookCover);
                    Producer pr = producerService.getAllProducerById(producer);
                    System.out.println(cate);
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
                    newPro.setCategory(cate);
                    newPro.setAuthor(ath);
                    newPro.setProducer(pr);
                    newPro.setBookCover(bc);
                    newPro.setBookSize(bookSize);
                    newPro.setLanguage(language);
                    newPro.setPageNumber(pageNumber);
                    newPro.setYearPublication(Integer.valueOf(yearPublication));
                    productService.saveProduct(newPro);
                    List<Product> listProducts = productService.getAllProduct();
                    Product newPro1 = listProducts.get(listProducts.size() - 1);
                    for (MultipartFile y : listImage) {
                        String urlImg = cloudinaryService.uploadFile(y);
                        ProductImage img = new ProductImage();
                        img.setProduct(newPro1);
                        img.setUrl_Image(urlImg);
                        productImageService.save(img);

                    }


                    session.setAttribute("addProduct", "addProductSuccess");
                    return "redirect:/san-pham-admin";

                } catch (Exception e) {
                    model.addAttribute("loi11", "Ảnh không được để trống");
                    String addProduct = (String) session.getAttribute("addProduct");
                    model.addAttribute("addProduct", addProduct);
                    session.setAttribute("addProduct", null);
                    List<Category> listCategories = categoryService.findAll();
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

        }

    }
//                if (listImage != null) {
//                    Category cate = categoryService.getCategoryById(category);
//                    Author ath = authorService.getAllAuthorById(author);
//                    BookCover bc = bookCoverService.getAllBookCoverById(bookCover);
//                    Producer pr = producerService.getAllProducerById(producer);
//                    System.out.println(cate);
//                    long millis = System.currentTimeMillis();
//                    Date create_at = new java.sql.Date(millis);
//                    Product newPro = new Product();
//                    newPro.setCreated_At(create_at);
//                    newPro.setDescription(description);
//                    newPro.setIs_Active(1);
//                    newPro.setIs_Selling(1);
//                    newPro.setPrice(Integer.parseInt(price));
//                    newPro.setProduct_Name(product_name);
//                    newPro.setQuantity(Integer.parseInt(availability));
//                    newPro.setSold(0);
//                    newPro.setCategory(cate);
//                    newPro.setAuthor(ath);
//                    newPro.setProducer(pr);
//                    newPro.setBookCover(bc);
//                    newPro.setBookSize(bookSize);
//                    newPro.setLanguage(language);
//                    newPro.setPageNumber(pageNumber);
//                    newPro.setYearPublication(Integer.valueOf(yearPublication));
//                    productService.saveProduct(newPro);
//                    List<Product> listProducts = productService.getAllProduct();
//                    newPro = listProducts.get(listProducts.size() - 1);
//                    for (MultipartFile y : listImage) {
//                        String urlImg = cloudinaryService.uploadFile(y);
//                        ProductImage img = new ProductImage();
//                        img.setProduct(newPro);
//                        img.setUrl_Image(urlImg);
//                        productImageService.save(img);
//                    }
//                    session.setAttribute("addProduct", "addProductSuccess");
//                    return "redirect:/dashboard-addproduct";
//                } else {
//                    model.addAttribute("loi11", "Ảnh không được để trống");
//                    String addProduct = (String) session.getAttribute("addProduct");
//                    model.addAttribute("addProduct", addProduct);
//                    session.setAttribute("addProduct", null);
//                    List<Category> listCategories = categoryService.findAll();
//                    List<Author> authorList = authorService.getAllAuthor();
//                    List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
//                    List<Producer> producerList = producerService.getAllProducer();
//                    model.addAttribute("listCategories", listCategories);
//                    model.addAttribute("listAuthor", authorList);
//                    model.addAttribute("listBookCover", bookCoverList);
//                    model.addAttribute("listProducer", producerList);
//                    return "dashboard-addproduct";
//                }


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
    public String DashboardChangePassword(Model model, @ModelAttribute("current_password") String current_password,
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

    @GetMapping("/dashboard-myproducts/delete-image/{id}")
    public String DashboardAuthor(Model model, @PathVariable() String id) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            List<ProductImage> productImages = productImageService.finalIdSP(Integer.valueOf(id));
            for (ProductImage productImage1 : productImages) {
                productImageService.deleteImg(productImage1);
            }

            List<Category> listCategories = categoryService.findAll();
            List<Author> authorList = authorService.getAllAuthor();
            List<BookCover> bookCoverList = bookCoverService.getAllBookCover();
            List<Producer> producerList = producerService.getAllProducer();
            Product product = productService.getProductById(Integer.valueOf(id));
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
