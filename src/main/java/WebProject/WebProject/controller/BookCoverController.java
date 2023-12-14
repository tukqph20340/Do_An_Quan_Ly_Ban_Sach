package WebProject.WebProject.controller;

import WebProject.WebProject.entity.BookCover;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.service.BookCoverService;
import WebProject.WebProject.service.impl.BookCoverServiceImpl;
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

import javax.servlet.http.HttpSession;

@Controller
public class BookCoverController {
    @Autowired
    HttpSession session;

    @Autowired
    private BookCoverService bookCoverService = new BookCoverServiceImpl();

    @GetMapping("/bia-admin")
    public String DashboardMyBookCoverView(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Pageable pageable = PageRequest.of(0, 5);
            Page<BookCover> pageBookCover = bookCoverService.findAll(pageable);
            model.addAttribute("pageProduct", pageBookCover);
            return "/admin/bia/dashboard-bookcover";
        }
    }

    @GetMapping("dashboard-bookcover/{page}")
    public String DashboardMyBookCoverPageView(@PathVariable int page, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
//            List<Category> listCategories = categoryService.findAll();
            Pageable pageable = PageRequest.of(page, 5);
            Page<BookCover> pageProduct = bookCoverService.findAll(pageable);
            model.addAttribute("pageProduct", pageProduct);
//            model.addAttribute("listCategories", listCategories);
            return "/admin/bia/dashboard-bookcover";
        }
    }

    @GetMapping("/add-bia-sach")
    public String DashboardAddBookCoverView(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            String addBookCover = (String) session.getAttribute("addBookCover");
            model.addAttribute("addBookCover", addBookCover);
            session.setAttribute("addBookCover", null);
            return "/admin/bia/dashboard-add-bookcover";
        }
    }

    @PostMapping("/add-bia-sach")
    public String DashboardAddBookCoverHandel(Model model, @ModelAttribute("cover_type") String cover_type,
                                              @ModelAttribute("description") String description) throws Exception {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            if (cover_type.isEmpty()) {
                model.addAttribute("loi", "Tên không được nhập trống");
                return "/admin/bia/dashboard-add-bookcover";
            } else if (description.isEmpty()) {
                model.addAttribute("loi1", "Mô tả không được nhập trống");
                return "/admin/bia/dashboard-add-bookcover";
            } else {
                BookCover bookCover = new BookCover();
                bookCover.setCoverType(cover_type);
                bookCover.setDescription(description);
                bookCoverService.saveBookCover(bookCover);
                session.setAttribute("addBookCover", "addBookCoverSuccess");
                return "redirect:/bia-admin";
            }
        }
    }

    @GetMapping("/bia-sach/sua/{id}")
    public String DashboardMyBookCoverEditView(@PathVariable int id, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            BookCover bookCover = bookCoverService.getAllBookCoverById(id);
            model.addAttribute("bookCover", bookCover);
            String editBookCover = (String) session.getAttribute("editBookCover");
            model.addAttribute("editBookCover", editBookCover);
            session.setAttribute("editBookCover", null);
            return "/admin/bia/dashboard-bookcover-edit";
        }
    }

    @PostMapping("/bia-sach/sua")
    public String DashboardMyBookCoverEditHandel(Model model, @ModelAttribute("bookCover_id") int bookCover_id,
                                                @ModelAttribute("cover_type") String cover_type,
                                                @ModelAttribute("description") String description) throws Exception {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            BookCover bookCover = bookCoverService.getAllBookCoverById(bookCover_id);
            bookCover.setCoverType(cover_type);
            bookCover.setDescription(description);
            bookCoverService.saveBookCover(bookCover);

            session.setAttribute("editBookCover", "editBookCoverSuccess");
            return "redirect:/bia-admin";
        }

    }
}
