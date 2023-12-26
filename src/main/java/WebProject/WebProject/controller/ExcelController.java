package WebProject.WebProject.controller;


import WebProject.WebProject.service.ExcelKhuyenMai;
import WebProject.WebProject.service.ExcelNhaXuatBan;
import WebProject.WebProject.service.ExcelSanPham;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("")
public class ExcelController {
    @Autowired
    private ExcelSanPham reportService;
    @Autowired
    private ExcelNhaXuatBan excelNhaXuatBan;
    @Autowired
    private ExcelKhuyenMai excelKhuyenMai;

    @GetMapping("/excel")
    public void generateExcelReport(HttpServletResponse response) throws Exception {

        response.setContentType("C:\\FPT Plytechnic\\CaNhan\\demo\\src\\main\\resources\\static\\excel");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=courses.xls";

        response.setHeader(headerKey, headerValue);

        reportService.generateExcel(response);

        response.flushBuffer();
    }

    @GetMapping("/xuat-file-excel-nha-san-xuat")
    public void generateExcelReport1(HttpServletResponse response) throws Exception {

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=Nha Xuat Ban.xls";

        response.setHeader(headerKey, headerValue);

        excelNhaXuatBan.generateExcel(response);

        response.flushBuffer();
    }

    @GetMapping("/xuat-file-excel-khuyen-mai")
    public void generateExcelReport2(HttpServletResponse response) throws Exception {

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=Khuyen Mai.xls";

        response.setHeader(headerKey, headerValue);

        excelKhuyenMai.generateExcel(response);

        response.flushBuffer();
    }
}
