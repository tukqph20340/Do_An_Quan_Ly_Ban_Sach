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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class ProducerController {

    @Autowired
    HttpSession session;

    @Autowired
    private ProducerService producerService = new ProducerServiceImpl();

    @GetMapping("/nha-san-xuat-admin")
    public String DashboardMyProducerView(Model model, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo) {
        Page<Producer> pageProducer = producerService.findAll(pageNo, 5);
        model.addAttribute("pageProduct", pageProducer.getContent());
        model.addAttribute("pageProductPage", pageProducer.getTotalPages());
        model.addAttribute("pageNumber", pageNo);
        model.addAttribute("Cate", new Producer());
        return "/admin/nhaphathanh/dashboard-producer";

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
    public String DashboardAddProducerHandel(Model model, @RequestParam("tenNhaXuatBan") String name_producer,
                                             @RequestParam("soDienThoai") String phone, @RequestParam("email") String email,
                                             @RequestParam("diaChi") String address, @RequestParam("quocGia") String quocGia,
                                             @RequestParam("moTa") String moTa) throws Exception {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/signin-admin";
        } else {
            if (name_producer.trim().isEmpty()) {
                model.addAttribute("loi", "Tên không được để trống");
                return "/admin/nhaphathanh/dashboard-addproducer";
            } else if (phone.trim().isEmpty()) {
                model.addAttribute("loi1", "Số điện thoại không được để trống");
                return "/admin/nhaphathanh/dashboard-addproducer";
            } else if (email.trim().isEmpty()) {
                model.addAttribute("loi2", "Email không được để trống");
                return "/admin/nhaphathanh/dashboard-addproducer";
            } else if (address.trim().isEmpty()) {
                model.addAttribute("loi3", "Địa Chỉ không được để trống");
                return "/admin/nhaphathanh/dashboard-addproducer";
            } else if (quocGia.trim().isEmpty()) {
                model.addAttribute("loi4", "Quốc Gia không được để trống");
                return "/admin/nhaphathanh/dashboard-addproducer";
            } else if (phone.trim().length() < 10 || phone.trim().length() > 20) {
                model.addAttribute("loi5", "Số điện thoại phải để 10 số trở lên và nhỏ hơn 20");
                return "/admin/nhaphathanh/dashboard-addproducer";
            } else if (moTa.trim().isEmpty()) {
                model.addAttribute("loi6", "Mô tả không được để trống");
                return "/admin/nhaphathanh/dashboard-addproducer";
            } else {
                Producer producer = new Producer();
                producer.setNameProducer(name_producer);
                producer.setPhone(phone);
                producer.setEmail(email);
                producer.setAddress(address);
                producer.setNation(quocGia);
                producer.setDescription(moTa);
                producerService.saveProducer(producer);
                return "redirect:/nha-san-xuat-admin";
            }
        }
    }

    @GetMapping("/view-sua/{id}")
    public String DashboardMyProducerEditView(@PathVariable int id, Model model) {
        Producer producer = producerService.getAllProducerById(id);
        model.addAttribute("detail", producer);
        return "/admin/nhaphathanh/dashboard-producer-edit";

    }

    @GetMapping("/view-all/{id}")
    public String DashboardMyProducerDetailView(@PathVariable int id, Model model) {
        Producer producer = producerService.getAllProducerById(id);
        model.addAttribute("detail", producer);
        return "/admin/nhaphathanh/detail-producer";

    }

    @PostMapping("/sua-nha-san-xuat/{id}")
    public String DashboardMyProducerEditHandel(Model model, @PathVariable("id") int id
            , @RequestParam("tenNhaXuatBan") String name_producer,
                                                @RequestParam("soDienThoai") String phone, @RequestParam("email") String email,
                                                @RequestParam("diaChi") String address, @RequestParam("quocGia") String quocGia,
                                                @RequestParam("moTa") String moTa) throws Exception {

            if (name_producer.trim().isEmpty()) {
                model.addAttribute("loi", "Tên không được để trống");
                Producer producer = producerService.getAllProducerById(id);
                model.addAttribute("detail", producer);
                return "/admin/nhaphathanh/dashboard-producer-edit";
            } else if (phone.trim().isEmpty()) {
                model.addAttribute("loi1", "Số điện thoại không được để trống");
                Producer producer = producerService.getAllProducerById(id);
                model.addAttribute("detail", producer);
                return "/admin/nhaphathanh/dashboard-producer-edit";
            } else if (email.trim().isEmpty()) {
                model.addAttribute("loi2", "Email không được để trống");
                Producer producer = producerService.getAllProducerById(id);
                model.addAttribute("detail", producer);
                return "/admin/nhaphathanh/dashboard-producer-edit";
            } else if (address.trim().isEmpty()) {
                model.addAttribute("loi3", "Địa Chỉ không được để trống");
                Producer producer = producerService.getAllProducerById(id);
                model.addAttribute("detail", producer);
                return "/admin/nhaphathanh/dashboard-producer-edit";
            } else if (quocGia.trim().isEmpty()) {
                model.addAttribute("loi4", "Quốc Gia không được để trống");
                Producer producer = producerService.getAllProducerById(id);
                model.addAttribute("detail", producer);
                return "/admin/nhaphathanh/dashboard-producer-edit";
            } else if (phone.trim().length() < 10 || phone.trim().length() > 20) {
                model.addAttribute("loi5", "Số điện thoại phải để 10 số trở lên và nhỏ hơn 20");
                Producer producer = producerService.getAllProducerById(id);
                model.addAttribute("detail", producer);
                return "/admin/nhaphathanh/dashboard-producer-edit";
            } else if (moTa.trim().isEmpty()) {
                model.addAttribute("loi6", "Mô tả không được để trống");
                Producer producer = producerService.getAllProducerById(id);
                model.addAttribute("detail", producer);
                return "/admin/nhaphathanh/dashboard-producer-edit";
            } else {
                Producer producer = producerService.getAllProducerById(id);
                producer.setNameProducer(name_producer);
                producer.setPhone(phone);
                producer.setEmail(email);
                producer.setAddress(address);
                producer.setNation(quocGia);
                producer.setDescription(moTa);
                producerService.saveProducer(producer);
                return "redirect:/nha-san-xuat-admin";
            }

    }

}


