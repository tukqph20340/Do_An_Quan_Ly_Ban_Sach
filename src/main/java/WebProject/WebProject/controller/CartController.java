package WebProject.WebProject.controller;


import WebProject.WebProject.entity.Cart;
import WebProject.WebProject.entity.Product;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.entity.Wallet;
import WebProject.WebProject.repository.WalletRepository;
import WebProject.WebProject.service.CartService;
import WebProject.WebProject.service.CookieService;
import WebProject.WebProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    CartService cartService;
    @Autowired
    CookieService cookie;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    ProductService productService;

    @Autowired
    HttpSession session;

    @GetMapping("/cart")
    public String CartView(Model model) throws Exception {
        session.setAttribute("loiSL", null);
        Cookie user_name = cookie.read("user_name");
        Wallet vi = walletRepository.findByUserId(user_name.getValue());
        model.addAttribute("vi", vi);
        User user = (User) session.getAttribute("acc");
        if (user == null) {
            return "redirect:/home";
        } else {
            List<Cart> listCart = cartService.GetAllCartByUser_id(user.getId());

            long Total = 0;
            for (Cart y : listCart) {
                if (y.isSelected()) {
                    Total = Total + y.getCount() * y.getProduct().getPrice();
                }
                if (listCart != null) {
                    model.addAttribute("Total", Total);
                    model.addAttribute("listCart", listCart);

                }
                model.addAttribute("soLuong", listCart.size());
            }


            return "shopping-cart";
        }
    }

    @PutMapping("/updateCartItem")
    @ResponseBody
    public ResponseEntity<String> updateCartItem(@RequestParam("cartItemId") String cartItemId,
                                                 @RequestParam("isSelected") boolean isSelected) {
        try {
            cartService.updateCartItemSelection(cartItemId, isSelected);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating selection status");
        }
    }

    @PutMapping("/updateCartItemQuantity")
    @ResponseBody
    public ResponseEntity<String> updateCartQuantity(@RequestParam("cartItemId") String cartItemId,
                                                     @RequestParam("quantity") Long quantity) {
        try {
            cartService.updateCartItemQuantity(cartItemId, quantity);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating selection status");
        }
    }


    @GetMapping("/deleteCart/{id}")
    public String GetDeleteCart(@PathVariable int id, Model model, HttpServletRequest request) throws Exception {
        String referer = request.getHeader("Referer");
        User user = (User) session.getAttribute("acc");
        if (user == null) {
            return "redirect:" + referer;
        } else {
            System.out.println(id);
            cartService.deleteById(id);
            session.setAttribute("CartDelete", id);
            List<Cart> listCart = cartService.GetAllCartByUser_id(user.getId());
            session.setAttribute("countCart", listCart.size());
            return "redirect:/cart";
        }
    }
//
//	@GetMapping("/deletebyIdUser/{id}")
//	public String GetDelete(@PathVariable Integer user){
//
//	}

    @PostMapping("/updateCart")
    public String UpdateCart(HttpServletRequest request, Model model) throws Exception {
        @SuppressWarnings("unchecked")
        List<Cart> listCart = (List<Cart>) session.getAttribute("listCart");
        int i = 0;
        for (Cart o : listCart) {
//			System.out.println("count"+i);
//			String a=(String) model.getAttribute("count" + i);
            String a = request.getParameter("count" + i);
            int count = Integer.parseInt(a);
            System.out.println(count);
            o.setCount(Long.valueOf(count));
            i++;
        }
        for (Cart o : listCart) {
            cartService.saveCart(o);
        }
        return "redirect:/cart";
    }

    @GetMapping("/addToCart/{id}")
    public String AddToCart(@PathVariable int id, Model model, HttpServletRequest request) throws Exception {
        String referer = request.getHeader("Referer");
        User user = (User) session.getAttribute("acc");
        if (user == null) {
            session.setAttribute("AddToCartErr", "Vui lòng đăng nhập trước khi thực hiện thao tác");
            return "redirect:" + referer;
        } else {
            List<Cart> listCart = cartService.GetAllCartByUser_id(user.getId());
            Product product = productService.getProductById(id);
            int cart = 0;
            for (Cart y : listCart) {
                if (y.getProduct().getId() == id) {
                    cart++;
                }
            }
            if (cart > 0) {
                for (Cart y : listCart) {
                    if (y.getProduct().getId() == id) {
                        y.setCount(y.getCount() + 1);
                        cartService.saveCart(y);
                    }
                }
            } else {
                Cart newCart = new Cart();
                newCart.setCount(Long.valueOf(1));
                newCart.setProduct(product);
                newCart.setUser(user);
                cartService.saveCart(newCart);
            }
            listCart = cartService.GetAllCartByUser_id(user.getId());
            session.setAttribute("countCart", listCart.size());
            return "redirect:" + referer;
        }
    }

    @PostMapping("/addToCart")
    public String AddToCartPost(@ModelAttribute("product_id") int product_id, @ModelAttribute("count") String a,
                                Model model, HttpServletRequest request) throws Exception {
        int count = Integer.parseInt(a);
        String referer = request.getHeader("Referer");
        User user = (User) session.getAttribute("acc");
        if (user == null) {
            session.setAttribute("AddToCartErr", "Vui lòng đăng nhập trước khi thực hiện thao tác");
            return "redirect:" + referer;
        } else {
            List<Cart> listCart = cartService.GetAllCartByUser_id(user.getId());
            Product product = productService.getProductById(product_id);
            int cart = 0;
            for (Cart y : listCart) {
                if (y.getProduct().getId() == product_id) {
                    y.setCount(y.getCount() + count);
                    cartService.saveCart(y);
                    cart++;
                }
            }
            if (cart == 0) {
                Cart newCart = new Cart();
                newCart.setCount(Long.valueOf(count));
                newCart.setProduct(product);
                newCart.setUser(user);
                cartService.saveCart(newCart);
            }
            listCart = cartService.GetAllCartByUser_id(user.getId());
            session.setAttribute("countCart", listCart.size());
            return "redirect:" + referer;
        }

    }

}
