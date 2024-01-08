package WebProject.WebProject.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import WebProject.WebProject.entity.Author;
import WebProject.WebProject.entity.Producer;
import WebProject.WebProject.entity.Statistic;
import WebProject.WebProject.entity.Wallet;
import WebProject.WebProject.repository.WalletRepository;
import WebProject.WebProject.repository.BookCoverRepository;
import WebProject.WebProject.service.AuthorService;
import WebProject.WebProject.service.ProducerService;
import WebProject.WebProject.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import WebProject.WebProject.entity.Cart;
import WebProject.WebProject.entity.Category;
import WebProject.WebProject.entity.Product;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.repository.ProductRepository;
import WebProject.WebProject.service.CartService;
import WebProject.WebProject.service.CategoryService;
import WebProject.WebProject.service.CookieService;
import WebProject.WebProject.service.ProductService;
import WebProject.WebProject.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductControler {



	@Autowired
	AuthorService authorController;

	@Autowired
	StatisticService statisticService;
	@Autowired
	ProducerService producerController;


	@Autowired
	ProductService productService;


	@Autowired
	UserService userService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	WalletRepository walletRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	BookCoverRepository bookCoverRepository;

	@Autowired
	CartService cartService;

	@Autowired
	HttpSession session;

	@Autowired
	CookieService cookie;

	@GetMapping(value = {"","/home"})
	public String listStudents(Model model) throws Exception {
		TimeZone vietnamTimeZone = TimeZone.getTimeZone("Asia/Jakarta");

		// Tạo đối tượng SimpleDateFormat với múi giờ của Việt Nam
		SimpleDateFormat vietnamDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vietnamDateFormat.setTimeZone(vietnamTimeZone);

		// Lấy ngày giờ hiện tại theo múi giờ của Việt Nam và định dạng thành chuỗi
		long millis = System.currentTimeMillis();
		String createAt = vietnamDateFormat.format(millis);
		System.out.println(createAt);
		Cookie user_name = cookie.read("user_name");
		Cookie remember = cookie.read("remember");
		String error_momo = (String) session.getAttribute("error_momo");
		String NoSignIn = (String) session.getAttribute("NoSignIn");
		session.setAttribute("NoSignIn", null);
		session.setAttribute("error_momo", null);
		String a = (String) session.getAttribute("NoSignIn");
		System.out.println(a);
		System.out.println(NoSignIn);
		User acc = (User) session.getAttribute("acc");

		if (remember != null) {
			acc = userService.findByIdAndRole(user_name.getValue(), "user");
			session.setAttribute("acc", acc);
			List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
			session.setAttribute("countCart", listCart.size());
		}
		if(acc!=null) {
			List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
			session.setAttribute("countCart", listCart.size());
		}
		if (session.getAttribute("acc") == null)
			session.setAttribute("countCart", "0");
		model.addAttribute("error_momo", error_momo);
		model.addAttribute("NoSignIn", NoSignIn);
		List<Product> Top12ProductBestSellers = productService.findTop12ProductBestSellers();
		List<Product> Top12ProductNewArrivals = productService.findTop12ProductNewArrivals();
		model.addAttribute("Top12ProductBestSellers", Top12ProductBestSellers);
		model.addAttribute("Top12ProductNewArrivals", Top12ProductNewArrivals);
		return "index";
	}


	@GetMapping("/shop")
	public String shop(Model model) throws Exception {
		User acc = (User) session.getAttribute("acc");
		if(acc!=null) {
			List<Cart> listCart = cartService.GetAllCartByUser_id(acc.getId());
			session.setAttribute("countCart", listCart.size());
		}
		if (session.getAttribute("acc") == null)
			session.setAttribute("countCart", "0");
		List<Product> lp = productRepository.findAll();

		Cookie user_name = cookie.read("user_name");
		Wallet vi = walletRepository.findByUserId(user_name.getValue());
		model.addAttribute("vi", vi);
		int TotalPro = lp.size();
		model.addAttribute("TotalPro",TotalPro);
		Pageable pageable = PageRequest.of(0, 12);
		Page<Product> page = productRepository.findAll(pageable);
		List<Category> listCategory = categoryService.getAll();
		String search_input = (String) session.getAttribute("search_input");
		model.addAttribute("listProduct", page);
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("listBookCover", bookCoverRepository.findAll());
		List<Producer> productProducers = producerController.getAllProducer();
		List<Author> author = authorController.getAllAuthor();
		model.addAttribute("listProducer", productProducers);
		model.addAttribute("author", author);
		model.addAttribute("search_input", search_input);
		return "shop";
	}

	@GetMapping("/shop/{id}")
	public String shopPage(Model model, @PathVariable int id) throws Exception {
		Cookie user_name = cookie.read("user_name");
		Wallet vi = walletRepository.findByUserId(user_name.getValue());
		model.addAttribute("vi", vi);
		List<Product> lp = productRepository.findAll();
		int TotalPro = lp.size();
		model.addAttribute("TotalPro",TotalPro);
		Pageable pageable = PageRequest.of(id, 12);
		Page<Product> page = productRepository.findAll(pageable);
		model.addAttribute("listProduct", page);
		List<Producer> productProducers = producerController.getAllProducer();
		List<Author> author = authorController.getAllAuthor();
		model.addAttribute("listProducer", productProducers);
		model.addAttribute("author", author);
		List<Category> listCategory = categoryService.getAll();
		String search_input = (String) session.getAttribute("search_input");
		User user = (User) session.getAttribute("acc");
		if (user != null) {
			model.addAttribute("user_Name", user.getUser_Name());
		}
		if (listCategory != null)
			model.addAttribute("listCategory", listCategory);
		else
			model.addAttribute("listCategory", null);
		model.addAttribute("search_input", search_input);
		return "shop";
	}

	@GetMapping("/productDetail/{id}")
	public String ProductDetailId(@PathVariable int id, Model model) {
		Product product = productService.getProductById(id);
		System.out.println(product);
		if (product != null) {
//			List<Product> relatedProduct = (List<Product>) productService.getProductById(product.getId());
//			model.addAttribute("relatedProduct", relatedProduct);
			List<Statistic> statistics = statisticService.getAllByProductId(id);
			System.out.println(statistics);
			model.addAttribute("statistics", statistics);
			model.addAttribute("product", product);
			return "shop-details";
		} else {
			return "redirect:/home";
		}

	}

	@GetMapping("/admin-detail/{id}")
	public String ProductDetailId1(@PathVariable int id, Model model) {
		Cookie user_name = cookie.read("user_name");
		Wallet vi = walletRepository.findByUserId(user_name.getValue());
		model.addAttribute("vi", vi);
		Product product = productService.getProductById(id);
		model.addAttribute(product);
		return "admin/detail.html";


	}

//	@GetMapping("/productDetail")
//	public String ProductDetail(Model model) {
//		Product product = (Product) session.getAttribute("product");
//
//	}

	@PostMapping("/search")
	public String Search(@ModelAttribute("search-input") String search_input, Model model) throws Exception {
		Cookie user_name = cookie.read("user_name");
		Wallet vi = walletRepository.findByUserId(user_name.getValue());
		model.addAttribute("vi", vi);
		session.setAttribute("search_input", search_input);
		return "redirect:/shop";
	}
	@GetMapping("/search/{id}")
	public String SearchPage(@PathVariable int id, Model model) throws Exception {
		Cookie user_name = cookie.read("user_name");
		Wallet vi = walletRepository.findByUserId(user_name.getValue());
		model.addAttribute("vi", vi);
		List<Category> listCategory = categoryService.getAll();
		String search_input = (String) session.getAttribute("search_input");
		if (search_input != null) {
			Pageable pageable = PageRequest.of(id, 12);
			System.out.println(search_input);
			Page<Product> listProduct = productRepository.findByProduct_NameContaining(search_input, pageable);
			List<Product> listProductAll = productRepository.findByProduct_NameContaining(search_input);
			System.out.println(listProduct);
			int TotalPro = listProductAll.size();
			List<Producer> productProducers = producerController.getAllProducer();
			List<Author> author = authorController.getAllAuthor();
			model.addAttribute("listProducer", productProducers);
			model.addAttribute("author", author);
			model.addAttribute("TotalPro",TotalPro);
			model.addAttribute("search_input", search_input);
			model.addAttribute("listProduct", listProduct);
			model.addAttribute("listCategory", listCategory);
			model.addAttribute("pageSearch", "pageSearch");
			model.addAttribute("noPageable", "search");
			for(Product y :listProduct) {
				System.out.println(y);
			}
			return "shop";
		} else {
			List<Producer> productProducers = producerController.getAllProducer();
			List<Author> author = authorController.getAllAuthor();
			model.addAttribute("listProducer", productProducers);
			model.addAttribute("author", author);
			model.addAttribute("TotalPro",0);
			model.addAttribute("noPageable", "search");
			model.addAttribute("listCategory", listCategory);
			model.addAttribute("search_input", null);
			model.addAttribute("listProduct", null);
			return "shop";
		}
	}

	@GetMapping("blog-details")
	public String blogDetailsView(Model model) {

		Cookie user_name = cookie.read("user_name");
		Wallet vi = walletRepository.findByUserId(user_name.getValue());
		model.addAttribute("vi", vi);
		return "blog-details";
	}
//	@GetMapping("/filterProducts/{id}")
//	@ResponseBody
//	public List<Product> filterProductsByCategory(@PathVariable int id) {
//		return productRepository.findProductByCategory_id(id);
//	}

}
