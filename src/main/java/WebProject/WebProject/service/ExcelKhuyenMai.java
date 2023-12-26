package WebProject.WebProject.service;

import WebProject.WebProject.entity.Promotion;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ExcelKhuyenMai {

    @Autowired
    private PromotionService courseRepo;

    public void generateExcel(HttpServletResponse response) throws Exception {

        List<Promotion> courses = courseRepo.getAllPromotion();


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Thông Tin Khuyến Mãi");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id Khuyến Mãi");
        row.createCell(1).setCellValue("Mã Khuyến Mãi");
        row.createCell(2).setCellValue("Ngày Tạo");
        row.createCell(3).setCellValue("Giá Trị Giảm");
        row.createCell(4).setCellValue("Ngày Kết Thúc");
        row.createCell(5).setCellValue("Giá Trị Giảm Tối Đa");
        row.createCell(6).setCellValue("Mô Tả");



        int dataRowIndex = 1;

        for (Promotion course : courses) {

            HSSFRow dataRow = sheet.createRow(dataRowIndex);

            dataRow.createCell(0).setCellValue(course.getId());
            dataRow.createCell(1).setCellValue(course.getCouponCode());
            dataRow.createCell(2).setCellValue(course.getCreatedAt());
            dataRow.createCell(3).setCellValue(course.getDiscountValue());
            dataRow.createCell(4).setCellValue(course.getExpiredAt());
            dataRow.createCell(5).setCellValue(course.getMaximumDiscountValue());
            dataRow.createCell(6).setCellValue(course.getName());
            dataRowIndex++;
        }

        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();

    }
}
