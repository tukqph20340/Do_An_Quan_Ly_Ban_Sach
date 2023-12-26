package WebProject.WebProject.controller;

import WebProject.WebProject.entity.BookCover;
import WebProject.WebProject.entity.Producer;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.service.BookCoverService;
import WebProject.WebProject.service.ProducerService;
import WebProject.WebProject.service.impl.BookCoverServiceImpl;
import WebProject.WebProject.service.impl.ProducerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BookCoverController {
    @Autowired
    HttpSession session;

    @Autowired
    private BookCoverService bookCoverService = new BookCoverServiceImpl();

    @GetMapping("/bia-admin")
    public String DashboardBookCoverView(Model model, @Param("name") String name, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, BookCover cover) {
        Page<BookCover> bookCoverPage = bookCoverService.findAll(pageNo, 5);
        List<BookCover> list = this.bookCoverService.getAllBookCover();
        if(name != null){
            list = this.bookCoverService.seach(name);
        }
        model.addAttribute("name", list);
        System.out.println(list);
        model.addAttribute("pageProduct", bookCoverPage.getContent());
        model.addAttribute("pageProductPage", bookCoverPage.getTotalPages());
        model.addAttribute("pageNumber", pageNo);
        model.addAttribute("cover", new BookCover());

        return "/admin/bia/dashboard-bookcover";

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
    public String DashboardAddProducerHandel(Model model, @RequestParam("cover_type") String coverType,
                                             @RequestParam("desciption") String desciption) throws Exception {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            if (coverType.trim().isEmpty()) {
                model.addAttribute("loi", "Loại bìa không được để trống");
                return "/admin/bia/dashboard-add-bookcover";
            }else if (desciption.trim().isEmpty()) {
                model.addAttribute("loi6", "Mô tả không được để trống");
                return "/admin/bia/dashboard-add-bookcover";
            } else {
                BookCover bookCover = new BookCover();
                bookCover.setCover_type(coverType);
                bookCover.setDesciption(desciption);
                bookCoverService.saveBook(bookCover);
                return "redirect:/bia-admin";
            }
        }
    }

    @GetMapping("/edit-bia-sach/{id}")
    public String DashboardMyProducerEditView(@PathVariable int id, Model model) {
        BookCover bookCover = bookCoverService.getAllBookCoverById(id);
        model.addAttribute("detail", bookCover);
        return "/admin/bia/dashboard-bookcover-edit";

    }

    @GetMapping("/bia-sach/{id}")
    public String DashboardMyProducerDetailView(@PathVariable int id, Model model) {
        BookCover bookCover = bookCoverService.getAllBookCoverById(id);
        model.addAttribute("detail", bookCover);
        return "/admin/bia/detail-bookcover";

    }

    @PostMapping("/sua-bia-sach/{id}")
    public String DashboardMyCategoryEditHandel(Model model, @PathVariable("id") int id
            , @RequestParam("cover_type") String coverType, @RequestParam("desciption") String desciption) throws Exception {

        if (coverType.trim().isEmpty()) {
            model.addAttribute("loi", "Tên không được để trống");
            BookCover bookCover = bookCoverService.getAllBookCoverById(id);
            model.addAttribute("detail", bookCover);
            return "/admin/bia/dashboard-add-bookcover";
        } else if (desciption.trim().isEmpty()) {
            model.addAttribute("loi6", "Mô tả không được để trống");
            return "/admin/bia/dashboard-add-bookcover";
        }
        else {
            BookCover category = bookCoverService.getAllBookCoverById(id);
            category.setCover_type(coverType);
            category.setDesciption(desciption);
            bookCoverService.saveBook(category);
            return "redirect:/bia-admin";
        }

    }

}