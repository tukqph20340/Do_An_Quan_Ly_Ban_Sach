package WebProject.WebProject.controller;

import WebProject.WebProject.entity.Category;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.service.CategoryService;
import WebProject.WebProject.service.impl.CategoryServiceImpl;
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

import javax.servlet.http.HttpSession;

@Controller
public class CategoryController {

    @Autowired
    HttpSession session;

    @Autowired
    private CategoryService categoryService = new CategoryServiceImpl();

    @GetMapping("/the-loai-admin")
    public String DashboardMyCategoryView(Model model, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo) {
        Page<Category> pageCategory = categoryService.findAll(pageNo, 5);
        model.addAttribute("pageCategory", pageCategory.getContent());
        model.addAttribute("pageCategorypage", pageCategory.getTotalPages());
        model.addAttribute("pageNumber", pageNo);
        model.addAttribute("Cate", new Category());
        return "/admin/theloai/dashboard-category";

    }

//    @GetMapping("/phan-trang/{page}")
//    public String DashboardMyProducerPageView(@PathVariable int page, Model model) {
//        User admin = (User) session.getAttribute("admin");
//        if (admin == null) {
//            return "redirect:/signin-admin";
//        } else {
////            List<Category> listCategories = categoryService.findAll();
//            Pageable pageable = PageRequest.of(page, 5);
//            Page<Producer> pageProduct = producerService.findAll(pageable);
//            model.addAttribute("pageProduct", pageProduct);
////            model.addAttribute("listCategories", listCategories);
//            return "/admin/nhaphathanh/dashboard-producer";
//        }
//    }

    @GetMapping("/add-the-loai")
    public String DashboardAddCategoryView(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            String addCategory = (String) session.getAttribute("addCategory");
            model.addAttribute("addCategory", addCategory);
            session.setAttribute("addCategory", null);
            return "/admin/theloai/dashboard-addcategory";
        }
    }

    @PostMapping("/add-the-loai")
    public String DashboardAddCategoryHandel(Model model, @RequestParam("tenTheLoai") String category_Name
    )throws Exception {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            if (category_Name.trim().isEmpty()) {
                model.addAttribute("loi", "Tên không được để trống");
                return "/admin/theloai/dashboard-addcategory";
            } else {
                Category category = new Category();
                category.setCategory_Name(category_Name);
                categoryService.saveCategory(category);
                return "redirect:/the-loai-admin";
            }
        }
    }

    @GetMapping("/view-sua-the-loai/{id}")
    public String DashboardMyCategoryEditView(@PathVariable int id, Model model) {
        Category category = categoryService.getAllCategoryById(id);
        model.addAttribute("detail", category);
        return "/admin/theloai/dashboard-category-edit";

    }

    @GetMapping("/view-the-loai/{id}")
    public String DashboardMyCategoryDetailView(@PathVariable int id, Model model) {
        Category category = categoryService.getAllCategoryById(id);
        model.addAttribute("detail", category);
        return "/admin/theloai/detail-category";

    }

    @PostMapping("/sua-the-loai/{id}")
    public String DashboardMyCategoryEditHandel(Model model, @PathVariable("id") int id
            , @RequestParam("tenTheLoai") String category_Name) throws Exception {

        if (category_Name.trim().isEmpty()) {
            model.addAttribute("loi", "Tên không được để trống");
            Category category = categoryService.getAllCategoryById(id);
            model.addAttribute("detail", category);
            return "/admin/theloai/dashboard-category-edit";
        } else {
            Category category = categoryService.getAllCategoryById(id);
            category.setCategory_Name(category_Name);
            categoryService.saveCategory(category);
            return "redirect:/the-loai-admin";
        }

    }

}


