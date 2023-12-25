//package WebProject.WebProject.controller;
//
//import java.util.List;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import WebProject.WebProject.entity.Author;
//import WebProject.WebProject.entity.BookCover;
//import WebProject.WebProject.entity.Cart;
//import WebProject.WebProject.entity.Producer;
//import WebProject.WebProject.entity.User;
//import WebProject.WebProject.entity.Wallet;
//import WebProject.WebProject.repository.WalletRepository;
//import WebProject.WebProject.service.AuthorService;
//import WebProject.WebProject.service.CartService;
//import WebProject.WebProject.service.CookieService;
//import WebProject.WebProject.service.ProducerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import WebProject.WebProject.entity.Category;
//import WebProject.WebProject.entity.Product;
//import WebProject.WebProject.repository.ProductRepository;
//import WebProject.WebProject.service.CategoryService;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class CategoryController {
//
//    @Autowired
//    AuthorService authorController;
//    @Autowired
//    CookieService cookie;
//
//    @Autowired
//    WalletRepository walletRepository;
//
//    @Autowired
//    CartService cartService;
//    @Autowired
//    ProducerService producerController;
//
//    @Autowired
//    CategoryService categoryService;
//
//    @Autowired
//    ProductRepository productRepository;
//
//    @Autowired
//    HttpSession session;
//
//    @GetMapping("/category/{id}")
//    public String category(@PathVariable int id, Model model) throws Exception {
//        User acc = (User) session.getAttribute("acc");
//        if(acc!=null) {
//            List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
//            session.setAttribute("countCart", listCart.size());
//        }
//        if (session.getAttribute("acc") == null)
//            session.setAttribute("countCart", "0");
//        Cookie user_name = cookie.read("user_name");
//        Wallet vi = walletRepository.findByUserId(user_name.getValue());
//        model.addAttribute("vi", vi);
//        Pageable pageable = PageRequest.of(0, 12);
//        Page<Product> page = productRepository.findAllByCategory_id(id, pageable);
//        Category category = categoryService.getCategoryById(id);
//        List<Category> listCategory = categoryService.findAll();
//        List<Product> listProduct = null;
//        if (category != null) {
//            listProduct = category.getProduct();
//        }
//        int TotalPro = listProduct.size();
//        List<Producer> productProducers = producerController.getAllProducer();
//        List<Author> author = authorController.getAllAuthor();
//        model.addAttribute("listProducer", productProducers);
//        model.addAttribute("author", author);
//        model.addAttribute("TotalPro", TotalPro);
//        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("search_input", null);
//        model.addAttribute("listProduct", page);
//        model.addAttribute("idCate", id);
//        model.addAttribute("noPageable", "category");
//        return "shop";
//    }
//
//
//    @GetMapping("/author/{id}")
//    public String author(@PathVariable int id, Model model) throws Exception {
//        Cookie user_name = cookie.read("user_name");
//        User acc = (User) session.getAttribute("acc");
//        if(acc!=null) {
//            List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
//            session.setAttribute("countCart", listCart.size());
//        }
//        if (session.getAttribute("acc") == null)
//            session.setAttribute("countCart", "0");
//        Wallet vi = walletRepository.findByUserId(user_name.getValue());
//        model.addAttribute("vi", vi);
//        Pageable pageable = PageRequest.of(0, 12);
//        Page<Product> page = productRepository.findAllByAuthorId(id, pageable);
//        Author category = authorController.getAllAuthorById(id);
//        List<Category> listCategory = categoryService.findAll();
//        List<Product> listProduct = null;
//        if (category != null) {
//            listProduct = category.getProducts();
//        }
//        int TotalPro = listProduct.size();
//        List<Producer> productProducers = producerController.getAllProducer();
//        List<Author> author = authorController.getAllAuthor();
//        model.addAttribute("listProducer", productProducers);
//        model.addAttribute("author", author);
//        model.addAttribute("TotalPro", TotalPro);
//        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("search_input", null);
//        model.addAttribute("listProduct", page);
//        model.addAttribute("idCate", id);
//        model.addAttribute("noPageable", "author");
//        return "shop";
//    }
//
//
//    @GetMapping("/producer/{id}")
//    public String producer(@PathVariable int id, Model model) throws Exception {
//        Cookie user_name = cookie.read("user_name");
//        Wallet vi = walletRepository.findByUserId(user_name.getValue());
//        model.addAttribute("vi", vi);
//        Pageable pageable = PageRequest.of(0, 12);
//        Page<Product> page = productRepository.findAllByProducerId(id, pageable);
//        Producer category = producerController.getAllProducerById(id);
//        List<Category> listCategory = categoryService.findAll();
//        List<Product> listProduct = null;
//        if (category != null) {
//            listProduct = category.getProducts();
//        }
//        int TotalPro = listProduct.size();
//        User acc = (User) session.getAttribute("acc");
//        if(acc!=null) {
//            List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
//            session.setAttribute("countCart", listCart.size());
//        }
//        if (session.getAttribute("acc") == null)
//            session.setAttribute("countCart", "0");
//        List<Producer> productProducers = producerController.getAllProducer();
//        List<Author> author = authorController.getAllAuthor();
//        model.addAttribute("listProducer", productProducers);
//        model.addAttribute("author", author);
//        model.addAttribute("TotalPro", TotalPro);
//        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("search_input", null);
//        model.addAttribute("listProduct", page);
//        model.addAttribute("idCate", id);
//        model.addAttribute("noPageable", "producer");
//        return "shop";
//    }
//
//    @GetMapping("/0den50")
//    public String tu0den50( Model model) throws Exception {
//        Cookie user_name = cookie.read("user_name");
//        Wallet vi = walletRepository.findByUserId(user_name.getValue());
//        model.addAttribute("vi", vi);
//        Pageable pageable = PageRequest.of(0, 12);
//        long a = Long.parseLong(String.valueOf(Integer.valueOf(0)));
//        long b= Long.parseLong(String.valueOf(Integer.valueOf(50000)));
//        Page<Product> page = productRepository.findAllByPriceBetween(pageable,a,b);
//        List<Category> listCategory = categoryService.findAll();
//        List<Product> listProduct = null;
//        if (page != null) {
//            listProduct = page.getContent();
//        }
//        int TotalPro = listProduct.size();
//        User acc = (User) session.getAttribute("acc");
//        if(acc!=null) {
//            List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
//            session.setAttribute("countCart", listCart.size());
//        }
//        if (session.getAttribute("acc") == null)
//            session.setAttribute("countCart", "0");
//        List<Producer> productProducers = producerController.getAllProducer();
//        List<Author> author = authorController.getAllAuthor();
//        model.addAttribute("listProducer", productProducers);
//        model.addAttribute("author", author);
//        model.addAttribute("TotalPro", TotalPro);
//        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("search_input", null);
//        model.addAttribute("listProduct", page);
//        model.addAttribute("noPageable", "0den50");
//        return "shop-price";
//    }
//
//    @GetMapping("/50den100")
//    public String tu50den100( Model model) throws Exception {
//        Cookie user_name = cookie.read("user_name");
//        Wallet vi = walletRepository.findByUserId(user_name.getValue());
//        model.addAttribute("vi", vi);
//        User acc = (User) session.getAttribute("acc");
//        if(acc!=null) {
//            List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
//            session.setAttribute("countCart", listCart.size());
//        }
//        if (session.getAttribute("acc") == null)
//            session.setAttribute("countCart", "0");
//        Pageable pageable = PageRequest.of(0, 12);
//        Page<Product> page = productRepository.findAllByPriceBetween(pageable,50000,100000);
//        List<Category> listCategory = categoryService.findAll();
//        List<Product> listProduct = null;
//        if (page != null) {
//            listProduct = page.getContent();
//        }
//        int TotalPro = listProduct.size();
//        List<Producer> productProducers = producerController.getAllProducer();
//        List<Author> author = authorController.getAllAuthor();
//        model.addAttribute("listProducer", productProducers);
//        model.addAttribute("author", author);
//        model.addAttribute("TotalPro", TotalPro);
//        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("search_input", null);
//        model.addAttribute("listProduct", page);
//        model.addAttribute("noPageable", "50den100");
//        return "shop-price";
//    }
//
//    @GetMapping("/50den100/{p}")
//    public String tu50den1001( @PathVariable int p,Model model) throws Exception {
//        Cookie user_name = cookie.read("user_name");
//        Wallet vi = walletRepository.findByUserId(user_name.getValue());
//        model.addAttribute("vi", vi);
//        User acc = (User) session.getAttribute("acc");
//        if(acc!=null) {
//            List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
//            session.setAttribute("countCart", listCart.size());
//        }
//        if (session.getAttribute("acc") == null)
//            session.setAttribute("countCart", "0");
//        Pageable pageable = PageRequest.of(p, 12);
//        Page<Product> page = productRepository.findAllByPriceBetween(pageable,50000,100000);
//        List<Category> listCategory = categoryService.findAll();
//        List<Product> listProduct = null;
//        if (page != null) {
//            listProduct = page.getContent();
//        }
//        int TotalPro = listProduct.size();
//        List<Producer> productProducers = producerController.getAllProducer();
//        List<Author> author = authorController.getAllAuthor();
//        model.addAttribute("listProducer", productProducers);
//        model.addAttribute("author", author);
//        model.addAttribute("TotalPro", TotalPro);
//        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("search_input", null);
//        model.addAttribute("listProduct", page);
//        model.addAttribute("noPageable", "50den100");
//        return "shop-price";
//    }
//
//
//
//    @GetMapping("/100den200")
//    public String tu100den200( Model model) throws Exception {
//        Cookie user_name = cookie.read("user_name");
//        Wallet vi = walletRepository.findByUserId(user_name.getValue());
//        model.addAttribute("vi", vi);
//        User acc = (User) session.getAttribute("acc");
//        if(acc!=null) {
//            List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
//            session.setAttribute("countCart", listCart.size());
//        }
//        if (session.getAttribute("acc") == null)
//            session.setAttribute("countCart", "0");
//        Pageable pageable = PageRequest.of(0, 12);
//        Page<Product> page = productRepository.findAllByPriceBetween(pageable,100000,200000);
//        List<Category> listCategory = categoryService.findAll();
//        List<Product> listProduct = null;
//        if (page != null) {
//            listProduct = page.getContent();
//        }
//        int TotalPro = listProduct.size();
//        List<Producer> productProducers = producerController.getAllProducer();
//        List<Author> author = authorController.getAllAuthor();
//        model.addAttribute("listProducer", productProducers);
//        model.addAttribute("author", author);
//        model.addAttribute("TotalPro", TotalPro);
//        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("search_input", null);
//        model.addAttribute("listProduct", page);
//        model.addAttribute("noPageable", "100den200");
//        return "shop-price";
//    }
//    @GetMapping("/100den200/{p}")
//    public String tu100den200( Model model,@PathVariable int p) throws Exception {
//        Cookie user_name = cookie.read("user_name");
//        Wallet vi = walletRepository.findByUserId(user_name.getValue());
//        model.addAttribute("vi", vi);
//        User acc = (User) session.getAttribute("acc");
//        if(acc!=null) {
//            List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
//            session.setAttribute("countCart", listCart.size());
//        }
//        if (session.getAttribute("acc") == null)
//            session.setAttribute("countCart", "0");
//        Pageable pageable = PageRequest.of(p, 12);
//        Page<Product> page = productRepository.findAllByPriceBetween(pageable,100000,200000);
//        List<Category> listCategory = categoryService.findAll();
//        List<Product> listProduct = null;
//        if (page != null) {
//            listProduct = page.getContent();
//        }
//        int TotalPro = listProduct.size();
//        List<Producer> productProducers = producerController.getAllProducer();
//        List<Author> author = authorController.getAllAuthor();
//        model.addAttribute("listProducer", productProducers);
//        model.addAttribute("author", author);
//        model.addAttribute("TotalPro", TotalPro);
//        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("search_input", null);
//        model.addAttribute("listProduct", page);
//        model.addAttribute("noPageable", "100den200");
//        return "shop-price";
//    }
//    @GetMapping("/200den500")
//    public String tu200den500( Model model) throws Exception {
//        Cookie user_name = cookie.read("user_name");
//        Wallet vi = walletRepository.findByUserId(user_name.getValue());
//        model.addAttribute("vi", vi);
//        User acc = (User) session.getAttribute("acc");
//        if(acc!=null) {
//            List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
//            session.setAttribute("countCart", listCart.size());
//        }
//        if (session.getAttribute("acc") == null)
//            session.setAttribute("countCart", "0");
//        Pageable pageable = PageRequest.of(0, 12);
//        Page<Product> page = productRepository.findAllByPriceBetween(pageable,200000,500000);
//        List<Category> listCategory = categoryService.findAll();
//        List<Product> listProduct = null;
//        if (page != null) {
//            listProduct = page.getContent();
//        }
//        int TotalPro = listProduct.size();
//        List<Producer> productProducers = producerController.getAllProducer();
//        List<Author> author = authorController.getAllAuthor();
//        model.addAttribute("listProducer", productProducers);
//        model.addAttribute("author", author);
//        model.addAttribute("TotalPro", TotalPro);
//        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("search_input", null);
//        model.addAttribute("listProduct", page);
//        model.addAttribute("noPageable", "200den500");
//        return "shop-price";
//    }
//
//    @GetMapping("/200den500/{p}")
//    public String tu200den500( Model model,@PathVariable int p) throws Exception {
//        Cookie user_name = cookie.read("user_name");
//        Wallet vi = walletRepository.findByUserId(user_name.getValue());
//        model.addAttribute("vi", vi);
//        User acc = (User) session.getAttribute("acc");
//        if(acc!=null) {
//            List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
//            session.setAttribute("countCart", listCart.size());
//        }
//        if (session.getAttribute("acc") == null)
//            session.setAttribute("countCart", "0");
//        Pageable pageable = PageRequest.of(p, 12);
//        Page<Product> page = productRepository.findAllByPriceBetween(pageable,200000,500000);
//        List<Category> listCategory = categoryService.findAll();
//        List<Product> listProduct = null;
//        if (page != null) {
//            listProduct = page.getContent();
//        }
//        int TotalPro = listProduct.size();
//        List<Producer> productProducers = producerController.getAllProducer();
//        List<Author> author = authorController.getAllAuthor();
//        model.addAttribute("listProducer", productProducers);
//        model.addAttribute("author", author);
//        model.addAttribute("TotalPro", TotalPro);
//        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("search_input", null);
//        model.addAttribute("listProduct", page);
//        model.addAttribute("noPageable", "200den500");
//        return "shop-price";
//    }
//
//
//    @GetMapping("/500den1000")
//    public String tu500den1000( Model model) throws Exception {
//        Cookie user_name = cookie.read("user_name");
//        Wallet vi = walletRepository.findByUserId(user_name.getValue());
//        model.addAttribute("vi", vi);
//
//        User acc = (User) session.getAttribute("acc");
//        if(acc!=null) {
//            List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
//            session.setAttribute("countCart", listCart.size());
//        }
//        if (session.getAttribute("acc") == null)
//            session.setAttribute("countCart", "0");
//        Pageable pageable = PageRequest.of(0, 12);
//        Page<Product> page = productRepository.findByPrice(pageable);
//        List<Category> listCategory = categoryService.findAll();
//        List<Product> listProduct = null;
//        if (page != null) {
//            listProduct = page.getContent();
//        }
//        int TotalPro = listProduct.size();
//        List<Producer> productProducers = producerController.getAllProducer();
//        List<Author> author = authorController.getAllAuthor();
//        model.addAttribute("listProducer", productProducers);
//        model.addAttribute("author", author);
//        model.addAttribute("TotalPro", TotalPro);
//        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("search_input", null);
//        model.addAttribute("listProduct", page);
//        model.addAttribute("noPageable", "500den1000");
//        return "shop-price.html";
//    }
//
//    @GetMapping("/500den1000/{p}")
//    public String tu500den1000( Model model,@PathVariable int p) throws Exception {
//        Cookie user_name = cookie.read("user_name");
//        Wallet vi = walletRepository.findByUserId(user_name.getValue());
//        model.addAttribute("vi", vi);
//
//        User acc = (User) session.getAttribute("acc");
//        if(acc!=null) {
//            List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
//            session.setAttribute("countCart", listCart.size());
//        }
//        if (session.getAttribute("acc") == null)
//            session.setAttribute("countCart", "0");
//        Pageable pageable = PageRequest.of(p, 12);
//        Page<Product> page = productRepository.findByPrice(pageable);
//        List<Category> listCategory = categoryService.findAll();
//        List<Product> listProduct = null;
//        if (page != null) {
//            listProduct = page.getContent();
//        }
//        int TotalPro = listProduct.size();
//        List<Producer> productProducers = producerController.getAllProducer();
//        List<Author> author = authorController.getAllAuthor();
//        model.addAttribute("listProducer", productProducers);
//        model.addAttribute("author", author);
//        model.addAttribute("TotalPro", TotalPro);
//        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("search_input", null);
//        model.addAttribute("listProduct", page);
//        model.addAttribute("noPageable", "500den1000");
//        return "shop-price.html";
//    }
//
//
//
//
//    @GetMapping("/category/{id}/{p}")
//    public String categoryPage(@PathVariable int id, @PathVariable int p, Model model, HttpServletRequest request) throws Exception {
//        Cookie user_name = cookie.read("user_name");
//        Wallet vi = walletRepository.findByUserId(user_name.getValue());
//        model.addAttribute("vi", vi);
//        User acc = (User) session.getAttribute("acc");
//        if(acc!=null) {
//            List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
//            session.setAttribute("countCart", listCart.size());
//        }
//        if (session.getAttribute("acc") == null)
//            session.setAttribute("countCart", "0");
//
//        String referer = request.getHeader("Referer");
//        Pageable pageable = PageRequest.of(p, 12);
//        Page<Product> page = productRepository.findAllByCategory_id(id, pageable);
//        Category category = categoryService.getCategoryById(id);
//        List<Category> listCategory = categoryService.findAll();
//        List<Product> listProduct = null;
//        if (category != null) {
//            listProduct = category.getProduct();
//        }
//
//        List<Producer> productProducers = producerController.getAllProducer();
//        List<Author> author = authorController.getAllAuthor();
//        model.addAttribute("listProducer", productProducers);
//        model.addAttribute("author", author);
//        int TotalPro = listProduct.size();
//        model.addAttribute("TotalPro", TotalPro);
//        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("search_input", null);
//        model.addAttribute("listProduct", page);
//        model.addAttribute("referer", referer);
//        model.addAttribute("idCate", id);
////		model.addAttribute("noPageable", "noPageable");
//        model.addAttribute("noPageable", "category");
//        return "shop";
//    }
//
//
//
//
//    @GetMapping("/producer/{id}/{p}")
//    public String producer(@PathVariable int id,@PathVariable int p, Model model) throws Exception {
//        Cookie user_name = cookie.read("user_name");
//        Wallet vi = walletRepository.findByUserId(user_name.getValue());
//        model.addAttribute("vi", vi);
//        Pageable pageable = PageRequest.of(p, 12);
//        Page<Product> page = productRepository.findAllByProducerId(id, pageable);
//        Producer category = producerController.getAllProducerById(id);
//        List<Category> listCategory = categoryService.findAll();
//        List<Product> listProduct = null;
//        if (category != null) {
//            listProduct = category.getProducts();
//        }
//        int TotalPro = listProduct.size();
//        User acc = (User) session.getAttribute("acc");
//        if(acc!=null) {
//            List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
//            session.setAttribute("countCart", listCart.size());
//        }
//        if (session.getAttribute("acc") == null)
//            session.setAttribute("countCart", "0");
//        List<Producer> productProducers = producerController.getAllProducer();
//        List<Author> author = authorController.getAllAuthor();
//        model.addAttribute("listProducer", productProducers);
//        model.addAttribute("author", author);
//        model.addAttribute("TotalPro", TotalPro);
//        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("search_input", null);
//        model.addAttribute("listProduct", page);
//        model.addAttribute("idCate", id);
//        model.addAttribute("noPageable", "producer");
//        return "shop";
//    }
//
//    @GetMapping("/author/{id}/{p}")
//    public String author1(@PathVariable int id, @PathVariable int p, Model model) throws Exception {
//        Cookie user_name = cookie.read("user_name");
//        User acc = (User) session.getAttribute("acc");
//        if(acc!=null) {
//            List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
//            session.setAttribute("countCart", listCart.size());
//        }
//        if (session.getAttribute("acc") == null)
//            session.setAttribute("countCart", "0");
//        Wallet vi = walletRepository.findByUserId(user_name.getValue());
//        model.addAttribute("vi", vi);
//        Pageable pageable = PageRequest.of(p, 12);
//        Page<Product> page = productRepository.findAllByAuthorId(id, pageable);
//        Author category = authorController.getAllAuthorById(id);
//        List<Category> listCategory = categoryService.findAll();
//        List<Product> listProduct = null;
//        if (category != null) {
//            listProduct = category.getProducts();
//        }
//        int TotalPro = listProduct.size();
//        List<Producer> productProducers = producerController.getAllProducer();
//        List<Author> author = authorController.getAllAuthor();
//        model.addAttribute("listProducer", productProducers);
//        model.addAttribute("author", author);
//        model.addAttribute("TotalPro", TotalPro);
//        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("search_input", null);
//        model.addAttribute("listProduct", page);
//        model.addAttribute("idCate", id);
//        model.addAttribute("noPageable", "author");
//        return "shop";
//    }
//
//
//    @GetMapping("/the-loai-admin")
//    public String category1(Model model) {
//        User admin = (User) session.getAttribute("admin");
//        if (admin == null) {
//            return "redirect:/signin-admin";
//        } else {
//            Pageable pageable = PageRequest.of(0, 5);
//            Page<Category> categories = categoryService.finadALL(pageable);
//            model.addAttribute("pageProduct", categories);
//            return "/admin/theloai/the-loai.html";
//        }
//    }
//
//    @GetMapping("/the-loai-admin/{page}")
//    public String category2(Model model, @PathVariable int page) {
//        User admin = (User) session.getAttribute("admin");
//        if (admin == null) {
//            return "redirect:/signin-admin";
//        } else {
//            Pageable pageable = PageRequest.of(page, 5);
//            Page<Category> categories = categoryService.finadALL(pageable);
//            model.addAttribute("pageProduct", categories);
//            return "/admin/theloai/the-loai.html";
//        }
//    }
//
//    @GetMapping("/add-the-loai")
//    public String category3() {
//
//
//        return "/admin/theloai/add-the-loai.html";
//
//    }
//
//    @PostMapping("/add-the-loai")
//    public String category4(@RequestParam("tenTheLoai") String ten, Model model) {
//        if (ten.isEmpty()) {
//            model.addAttribute("loi", "Tên Không Được Để Trống");
//            return "/admin/theloai/add-the-loai.html";
//        } else {
//            Category category = new Category();
//            category.setCategory_Name(ten);
//            categoryService.saveCategory(category);
//            return "redirect:/the-loai-admin";
//        }
//    }
//    @GetMapping("/the-loai/sua/{id}")
//    public String category5(@PathVariable() String id,Model model) {
//        Category category = categoryService.getCategoryById(Integer.valueOf(id));
//        model.addAttribute("detail",category);
//        return "/admin/theloai/update-the-loai.html";
//
//    }
//
//    @PostMapping("/the-loai/sua/{id}")
//    public String category6(@PathVariable() String id,@RequestParam("tenTheLoai") String ten, Model model) {
//        if (ten.isEmpty()) {
//            model.addAttribute("loi", "Tên Không Được Để Trống");
//            Category category = categoryService.getCategoryById(Integer.valueOf(id));
//            model.addAttribute("detail",category);
//            return "/admin/theloai/update-the-loai.html";
//        } else {
//            Category category =categoryService.getCategoryById(Integer.valueOf(id));
//            category.setCategory_Name(ten);
//            categoryService.saveCategory(category);
//            return "redirect:/the-loai-admin";
//        }
//    }
//}
