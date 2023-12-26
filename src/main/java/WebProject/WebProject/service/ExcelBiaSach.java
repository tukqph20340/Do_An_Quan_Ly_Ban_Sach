package WebProject.WebProject.service;

import WebProject.WebProject.entity.BookCover;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Service
public class ExcelBiaSach {
    @Autowired
    private BookCoverService courseRepo;



    public void generateExcel(HttpServletResponse response) throws Exception {

        List<BookCover> courses = courseRepo.getAllBookCover();


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Thông Tin Bìa");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id Nhà Sản Xuất");
        row.createCell(1).setCellValue("Loại Bìa");
        row.createCell(2).setCellValue("Mô Tả");



        int dataRowIndex = 1;

        for (BookCover course : courses) {

            HSSFRow dataRow = sheet.createRow(dataRowIndex);



            dataRow.createCell(0).setCellValue(course.getId());
            dataRow.createCell(1).setCellValue(course.getCover_type());
            dataRow.createCell(2).setCellValue(course.getDesciption());
            dataRowIndex++;
        }

        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();

    }
}
