package WebProject.WebProject.controller;

import WebProject.WebProject.entity.Author;
import WebProject.WebProject.entity.AuthorImage;
import WebProject.WebProject.entity.Category;
import WebProject.WebProject.entity.Product;
import WebProject.WebProject.entity.ProductImage;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.service.AuthorImageService;
import WebProject.WebProject.service.AuthorService;
import WebProject.WebProject.service.CloudinaryService;
import WebProject.WebProject.service.impl.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;

@Controller
public class AuthorController {

    @Autowired
    HttpSession session;

    @Autowired
    private AuthorService authorService = new AuthorServiceImpl();

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    AuthorImageService authorImageService;

    @GetMapping("/tac-gia-admin")
    public String DashboardMyAuthorView(Model model) {

//            List<Category> listCategories = categoryService.findAll();
            Pageable pageable = PageRequest.of(0, 4);
            Page<Author> pageAuthor = authorService.findAll(pageable);
            model.addAttribute("pageProduct", pageAuthor);
//            model.addAttribute("listCategories", listCategories);
            return "/admin/tacgia/dashboard-author";

    }

    @GetMapping("dashboard-author/{page}")
    public String DashboardMyAuthorPageView(@PathVariable int page, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
//            List<Category> listCategories = categoryService.findAll();
            Pageable pageable = PageRequest.of(page, 4);
            Page<Author> pageProduct = authorService.findAll(pageable);
            model.addAttribute("pageProduct", pageProduct);
//            model.addAttribute("listCategories", listCategories);
            return "/admin/tacgia/dashboard-author";
        }
    }

    @GetMapping("/add-tac-gia")
    public String DashboardAddAuthorView(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            String addAuthor = (String) session.getAttribute("addAuthor");
            model.addAttribute("addAuthor", addAuthor);
            session.setAttribute("addAuthor", null);
            return "/admin/tacgia/dashboard-addauthor";
        }
    }

    @PostMapping("/add-tac-gia")
    public String DashboardAddAuthorHandel(Model model, @ModelAttribute("name_author") String name_author,
                                           @ModelAttribute("address") String address, @ModelAttribute("description") String description,
                                           @ModelAttribute("listImage") MultipartFile[] listImage) throws Exception {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            if (name_author.isEmpty()) {
                model.addAttribute("loi", "Tên không được nhập trống");
                return "/admin/tacgia/dashboard-addauthor";
            } else if (address.isEmpty()) {
                model.addAttribute("loi1", "Địa chỉ không được nhập trống");
                return "/admin/tacgia/dashboard-addauthor";
            } else if (description.isEmpty()) {
                model.addAttribute("loi2", "Mô tả không được nhập trống");
                return "/admin/tacgia/dashboard-addauthor";
            } else {
                if (listImage != null) {
                    Author author = new Author();
                    author.setNameAuthor(name_author);
                    author.setAddress(address);
                    author.setDescription(description);
                    authorService.saveAuthor(author);
                    List<Author> listAuthor = authorService.getAllAuthor();
                    author = listAuthor.get(listAuthor.size() - 1);
                    for (MultipartFile y : listImage) {
                        String urlImg = cloudinaryService.uploadFile(y);
                        AuthorImage img = new AuthorImage();
                        img.setAuthor(author);
                        img.setUrl_Image(urlImg);
                        authorImageService.save(img);
                    }
                    session.setAttribute("addAuthor", "addAuthorSuccess");
                    return "redirect:/tac-gia-admin";
                } else {
                    return "redirect:/add-tac-gia";
                }

            }
        }
    }

    @GetMapping("/tac-gia/sua/{id}")
    public String DashboardMyAuthorEditView(@PathVariable int id, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Author author = authorService.getAllAuthorById(id);
            model.addAttribute("author", author);
            String editAuthor = (String) session.getAttribute("editAuthor");
            model.addAttribute("editAuthor", editAuthor);
            session.setAttribute("editAuthor", null);
            return "/admin/tacgia/dashboard-author-edit";
        }
    }

    @PostMapping("/tac-gia/sua")
    public String DashboardMyAuthorEditHandel(Model model, @ModelAttribute("author_id") int author_id,
                                              @ModelAttribute("name_author") String name_author,
                                              @ModelAttribute("address") String address, @ModelAttribute("description") String description,
                                              @ModelAttribute("listImage") MultipartFile[] listImage) throws Exception {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            if (name_author.isEmpty()) {
                model.addAttribute("loi", "Tên không được nhập trống");
                return "redirect:/tac-gia/sua/" + author_id;
            } else if (address.isEmpty()) {
                model.addAttribute("loi1", "Địa chỉ không được nhập trống");
                return "redirect:/tac-gia/sua/" + author_id;
            } else if (description.isEmpty()) {
                model.addAttribute("loi2", "Mô tả không được nhập trống");
                return "redirect:/tac-gia/sua/" + author_id;
            } else {
            if (listImage != null) {
                Author author = authorService.getAllAuthorById(author_id);
                author.setNameAuthor(name_author);
                author.setAddress(address);
                author.setDescription(description);
                authorService.saveAuthor(author);
                for (MultipartFile y : listImage) {
                    if (!y.isEmpty()) {
                        String urlImg = cloudinaryService.uploadFile(y);
                        AuthorImage img = new AuthorImage();
                        img.setAuthor(author);
                        img.setUrl_Image(urlImg);
                        authorImageService.save(img);
                    }
                }
                session.setAttribute("editAuthor", "editAuthorSuccess");
                return "redirect:/tac-gia-admin";
            } else {
                return "redirect:/dashboard-author/edit/" + author_id;
            }

        }
        }
    }

    @GetMapping("/dashboard-author/delete-image/{id}")
    public String DeleteImage(@PathVariable int id, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        authorImageService.deleteById(id);
        return "redirect:" + referer;
    }

}
