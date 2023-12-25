package WebProject.WebProject.controller;

import WebProject.WebProject.entity.Producer;
import WebProject.WebProject.entity.User;
import WebProject.WebProject.service.ProducerService;
import WebProject.WebProject.service.impl.ProducerServiceImpl;
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
public class ProducerController {

    @Autowired
    HttpSession session;

    @Autowired
    private ProducerService producerService = new ProducerServiceImpl();

    @GetMapping("/nha-san-xuat-admin")
    public String DashboardMyProducerView(Model model) {
        User admin = (User) session.getAttribute("admin");
            Pageable pageable = PageRequest.of(0, 5);
            Page<Producer> pageProducer = producerService.findAll(pageable);
            model.addAttribute("pageProduct", pageProducer);
            return "/admin/nhaphathanh/dashboard-producer";

    }

    @GetMapping("/phan-trang/{page}")
    public String DashboardMyProducerPageView(@PathVariable int page, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
//            List<Category> listCategories = categoryService.findAll();
            Pageable pageable = PageRequest.of(page, 5);
            Page<Producer> pageProduct = producerService.findAll(pageable);
            model.addAttribute("pageProduct", pageProduct);
//            model.addAttribute("listCategories", listCategories);
            return "/admin/nhaphathanh/dashboard-producer";
        }
    }

    @GetMapping("/add-nha-san-xuat")
    public String DashboardAddProducerView(Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            String addProducer = (String) session.getAttribute("addProducer");
            model.addAttribute("addProducer", addProducer);
            session.setAttribute("addProducer", null);
            return "/admin/nhaphathanh/dashboard-addproducer";
        }
    }

    @PostMapping("/add-nha-san-xuat")
    public String DashboardAddProducerHandel(Model model, @ModelAttribute("name_producer") String name_producer,
                                             @ModelAttribute("phone") String phone, @ModelAttribute("email") String email,
                                             @ModelAttribute("address") String address, @ModelAttribute("description") String description) throws Exception {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            if (name_producer.isEmpty()) {
                model.addAttribute("loi","Tên không được để trống");
                return "/admin/nhaphathanh/dashboard-addproducer";
            } else if (phone.isEmpty()) {
                model.addAttribute("loi1","Số điện thoại không được để trống");
                return "/admin/nhaphathanh/dashboard-addproducer";
            } else if (email.isEmpty()) {
                model.addAttribute("loi2","Email không được để trống");
                return "/admin/nhaphathanh/dashboard-addproducer";
            } else if (address.isEmpty()) {
                model.addAttribute("loi3","Địa Chỉ không được để trống");
                return "/admin/nhaphathanh/dashboard-addproducer";
            } else if (description.isEmpty()) {
                model.addAttribute("loi4","Mô tả không được để trống");
                return "/admin/nhaphathanh/dashboard-addproducer";
            } else if (phone.length()>11) {
                model.addAttribute("loi5","Số điện thoại phải để 10 số");
                return "/admin/nhaphathanh/dashboard-addproducer";
            } else {
                Producer producer = new Producer();
                producer.setNameProducer(name_producer);
                producer.setPhone(phone);
                producer.setEmail(email);
                producer.setAddress(address);
                producer.setDescription(description);
                producerService.saveProducer(producer);
                session.setAttribute("addProducer", "addProducerSuccess");
                return "redirect:/nha-san-xuat-admin";
            }
        }
    }

    @GetMapping("/nha-san-xuat/sua/{id}")
    public String DashboardMyProducerEditView(@PathVariable int id, Model model) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Producer producer = producerService.getAllProducerById(id);
            model.addAttribute("producer", producer);
            String editProducer = (String) session.getAttribute("editProducer");
            model.addAttribute("editProducer", editProducer);
            session.setAttribute("editProducer", null);
            return "/admin/nhaphathanh/dashboard-producer-edit";
        }
    }

    @PostMapping("/nha-san-xuat/sua")
    public String DashboardMyProducerEditHandel(Model model, @ModelAttribute("producer_id") int producer_id,
                                                @ModelAttribute("name_producer") String name_producer,
                                                @ModelAttribute("phone") String phone, @ModelAttribute("email") String email,
                                                @ModelAttribute("address") String address, @ModelAttribute("description") String description) throws Exception {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            Producer producer = producerService.getAllProducerById(producer_id);
            producer.setNameProducer(name_producer);
            producer.setPhone(phone);
            producer.setEmail(email);
            producer.setAddress(address);
            producer.setDescription(description);
            producerService.saveProducer(producer);

            session.setAttribute("editProducer", "editProducerSuccess");
            return "redirect:/nha-san-xuat-admin";
        }

    }
}


