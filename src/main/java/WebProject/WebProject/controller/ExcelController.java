package WebProject.WebProject.controller;



import WebProject.WebProject.service.ExcelBiaSach;
import WebProject.WebProject.service.ExcelKhuyenMai;
import WebProject.WebProject.service.ExcelNhaXuatBan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("")
public class ExcelController{

    @Autowired
    private ExcelNhaXuatBan excelNhaXuatBan;

    @Autowired
    private ExcelBiaSach excelBiaSach;

    @Autowired
    private ExcelKhuyenMai excelKhuyenMai;


    @GetMapping("/xuat-file-excel-nha-san-xuat")
    public void generateExcelReport1(HttpServletResponse response) throws Exception{

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=Nha Xuat Ban.xls";

        response.setHeader(headerKey, headerValue);

        excelNhaXuatBan.generateExcel(response);

        response.flushBuffer();
    }

    @GetMapping("/xuat-file-excel-bia-sach")
    public void generateExcelReportBiaSach(HttpServletResponse response) throws Exception{

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=Bia Sach.xls";

        response.setHeader(headerKey, headerValue);

        excelBiaSach.generateExcel(response);

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
