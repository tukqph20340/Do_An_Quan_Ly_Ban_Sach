package WebProject.WebProject.service;

import WebProject.WebProject.entity.Category;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ExcelCategory {
    @Autowired
    private CategoryService categoryService;



    public void generateExcel(HttpServletResponse response) throws Exception {

        List<Category> courses = categoryService.getAllCategories();


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Thông Tin Thể Loại");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id Thể Loại");
        row.createCell(1).setCellValue("Tên Thể Loại");


        int dataRowIndex = 1;

        for (Category course : courses) {

            HSSFRow dataRow = sheet.createRow(dataRowIndex);



            dataRow.createCell(0).setCellValue(course.getId());
            dataRow.createCell(1).setCellValue(course.getCategory_Name());
            dataRowIndex++;
        }

        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();

    }
}