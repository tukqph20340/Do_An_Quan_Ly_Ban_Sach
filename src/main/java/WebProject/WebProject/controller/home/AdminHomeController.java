package WebProject.WebProject.controller.home;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class AdminHomeController {

    @GetMapping("/quan-ly-don-hang")
    public String hienThiHomeAdmin(){
        return "/admin/quanlydonhang/quan-ly-don-hang.html";
    }
}
