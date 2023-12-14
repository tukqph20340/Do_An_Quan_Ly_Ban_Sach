package WebProject.WebProject.controller;



import WebProject.WebProject.service.ExcelSanPham;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("")
public class ExcelController{
    @Autowired
    private ExcelSanPham reportService;

    @GetMapping("/excel")
    public void generateExcelReport(HttpServletResponse response) throws Exception{

        response.setContentType("C:\\FPT Plytechnic\\CaNhan\\demo\\src\\main\\resources\\static\\excel");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=courses.xls";

        response.setHeader(headerKey, headerValue);

        reportService.generateExcel(response);

        response.flushBuffer();
    }
}
