package WebProject.WebProject.controller;

import WebProject.WebProject.entity.Author;
import WebProject.WebProject.entity.AuthorImage;
import WebProject.WebProject.entity.Producer;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.service.AuthorImageService;
import WebProject.WebProject.service.AuthorService;
import WebProject.WebProject.service.CloudinaryService;
import WebProject.WebProject.service.CookieService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@Controller
public class AuthorController {

    @Autowired
    HttpSession session;

    @Autowired
    AuthorService authorService;

    @Autowired
    CookieService cookieService;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    AuthorImageService authorImageService;

    @GetMapping("/tac-gia-admin")
    public String DashboardMyAuthorView(Model model, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo) {

        Page<Author> pageAu = authorService.findAll(pageNo, 5);
        model.addAttribute("timKiem", null);
        model.addAttribute("pageAuthor", pageAu.getContent());
        model.addAttribute("pageAu", pageAu.getTotalPages());
        model.addAttribute("pageNumber", pageNo);
        model.addAttribute("Cate", new Author());
        return "/admin/tacgia/dashboard-author";

    }


    @GetMapping("/add-tac-gia")
    public String DashboardAddAuthorView(Model model) {
        return "/admin/tacgia/dashboard-addauthor";

    }

    @PostMapping("/add-tac-gia")
    public String DashboardAddAuthorHandel(Model model, @RequestParam("name_author") String name_author,
                                           @RequestParam("date") String date,
                                           @RequestParam("diaChi") String diaChi,
                                           @RequestParam("quocGia") String quocGia,
                                           @RequestParam("description") String description,
                                           @RequestParam("listImage") MultipartFile[] listImage) throws Exception {

        long millis = System.currentTimeMillis();
        Date createAt = new java.sql.Date(millis);
        if (name_author.trim().isEmpty()) {
            model.addAttribute("loi", "Tên không được nhập trống");
            return "/admin/tacgia/dashboard-addauthor";
        } else if (date.trim().isEmpty()) {
            model.addAttribute("loi1", "Ngày sinh không được nhập trống");
            return "/admin/tacgia/dashboard-addauthor";
        } else if (diaChi.trim().isEmpty()) {
            model.addAttribute("loi2", "Địa chỉ không được nhập trống");
            return "/admin/tacgia/dashboard-addauthor";
        } else if (quocGia.trim().isEmpty()) {
            model.addAttribute("loi4", "Quốc gia không được nhập trống");
            return "/admin/tacgia/dashboard-addauthor";
        } else if (description.trim().isEmpty()) {
            model.addAttribute("loi5", "Mô tả không được nhập trống");
            return "/admin/tacgia/dashboard-addauthor";
        } else if (Date.valueOf(date).after(createAt)) {
            model.addAttribute("loi6", "Ngày sinh không được lớn hơn hiện tại");
            return "/admin/tacgia/dashboard-addauthor";
        } else {
            try {
                for (MultipartFile y : listImage) {
                    String urlImg = cloudinaryService.uploadFile(y);
                    AuthorImage img = new AuthorImage();


                    Author author = new Author();
                    author.setNameAuthor(name_author);
                    author.setDate_of_birth(Date.valueOf(date));
                    author.setAddress(diaChi);
                    author.setNation(quocGia);
                    author.setDescription(description);
                    authorService.saveAuthor(author);
                    Author listAuthor = authorService.ten(name_author);
                    img.setAuthor(listAuthor);
                    img.setUrl_Image(urlImg);
                    authorImageService.save(img);


                }
                return "redirect:/tac-gia-admin";

            } catch (Exception e) {
                model.addAttribute("loi7", "Ảnh không được để trống");
                return "/admin/tacgia/dashboard-addauthor";

            }
        }
    }

    @GetMapping("/tac-gia/sua/{id}")
    public String DashboardMyAuthorEditView(@PathVariable int id, Model model) {

        Author author = authorService.getAllAuthorById(id);
        model.addAttribute("detail", author);
        if (author.getAuthorList().isEmpty()) {
            model.addAttribute("a", null);
        }
        if (author.getAuthorList().size() == 1) {
            model.addAttribute("a", true);
        } else {
            model.addAttribute("a", false);
        }
        Cookie cookie = cookieService.create("idTacGia",String.valueOf(id),1);
        return "/admin/tacgia/dashboard-author-edit";

    }

    @PostMapping("/tac-gia/sua/{id}")
    public String DashboardMyAuthorEditHandel(Model model, @PathVariable() Integer id,
                                              @RequestParam("name_author") String name_author,
                                              @RequestParam("date") String date,
                                              @RequestParam("diaChi") String diaChi,
                                              @RequestParam("quocGia") String quocGia,
                                              @RequestParam("description") String description,
                                              @ModelAttribute("listImage") MultipartFile[] listImage) throws Exception {

        long millis = System.currentTimeMillis();
        Date createAt = new java.sql.Date(millis);
        if (name_author.trim().isEmpty()) {
            model.addAttribute("loi", "Tên không được nhập trống");
            Author author = authorService.getAllAuthorById(id);
            model.addAttribute("detail", author);
            if (author.getAuthorList().isEmpty()) {
                model.addAttribute("a", null);
            } else {
                model.addAttribute("a", "a");
            }

            return "/admin/tacgia/dashboard-author-edit";
        } else if (date.trim().isEmpty()) {
            model.addAttribute("loi1", "Ngày sinh không được nhập trống");
            Author author = authorService.getAllAuthorById(id);
            model.addAttribute("detail", author);
            if (author.getAuthorList().isEmpty()) {
                model.addAttribute("a", null);
            } else {
                model.addAttribute("a", "a");
            }

            return "/admin/tacgia/dashboard-author-edit";
        } else if (diaChi.trim().isEmpty()) {
            model.addAttribute("loi2", "Địa chỉ không được nhập trống");
            Author author = authorService.getAllAuthorById(id);
            model.addAttribute("detail", author);
            if (author.getAuthorList().isEmpty()) {
                model.addAttribute("a", null);
            } else {
                model.addAttribute("a", "a");
            }

            return "/admin/tacgia/dashboard-author-edit";
        } else if (quocGia.trim().isEmpty()) {
            model.addAttribute("loi4", "Quốc gia không được nhập trống");
            Author author = authorService.getAllAuthorById(id);
            model.addAttribute("detail", author);
            if (author.getAuthorList().isEmpty()) {
                model.addAttribute("a", null);
            } else {
                model.addAttribute("a", "a");
            }

            return "/admin/tacgia/dashboard-author-edit";
        } else if (description.trim().isEmpty()) {
            model.addAttribute("loi5", "Mô tả không được nhập trống");
            Author author = authorService.getAllAuthorById(id);
            model.addAttribute("detail", author);
            if (author.getAuthorList().isEmpty()) {
                model.addAttribute("a", null);
            } else {
                model.addAttribute("a", "a");
            }

            return "/admin/tacgia/dashboard-author-edit";
        } else if (Date.valueOf(date).after(createAt)) {
            model.addAttribute("loi6", "Ngày sinh không được lớn hơn hiện tại");
            Author author = authorService.getAllAuthorById(id);
            model.addAttribute("detail", author);
            if (author.getAuthorList().isEmpty()) {
                model.addAttribute("a", null);
            } else {
                model.addAttribute("a", "a");
            }

            return "/admin/tacgia/dashboard-author-edit";
        } else {
            try {
                for (MultipartFile y : listImage) {
                    String urlImg = cloudinaryService.uploadFile(y);
                    AuthorImage img = new AuthorImage();


                    Author author = authorService.getAllAuthorById(id);
                    author.setNameAuthor(name_author);
                    author.setDate_of_birth(Date.valueOf(date));
                    author.setAddress(diaChi);
                    author.setNation(quocGia);
                    author.setDescription(description);
                    authorService.saveAuthor(author);
                    Author listAuthor = authorService.ten(name_author);
                    img.setAuthor(listAuthor);
                    img.setUrl_Image(urlImg);
                    authorImageService.save(img);


                }
                return "redirect:/tac-gia-admin";

            } catch (Exception e) {
                model.addAttribute("loi7", "Ảnh không được để trống");
                Author author = authorService.getAllAuthorById(id);
                model.addAttribute("detail", author);
                if (author.getAuthorList().isEmpty()) {
                    model.addAttribute("a", null);
                } else {
                    model.addAttribute("a", "a");
                }

                return "/admin/tacgia/dashboard-author-edit";

            }

        }
    }

    @GetMapping("/xoa-anh/tac-gia/{id}")
    public String DeleteImage(@PathVariable int id, HttpServletRequest request) {
        authorImageService.deleteById(id);
        Cookie cookie = cookieService.read("idTacGia");
        return "redirect:/tac-gia/sua/"+ cookie.getValue();
    }

}
