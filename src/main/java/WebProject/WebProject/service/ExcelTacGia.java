package WebProject.WebProject.service;




import WebProject.WebProject.entity.Author;
import WebProject.WebProject.entity.AuthorImage;
import WebProject.WebProject.entity.Producer;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ExcelTacGia {
    @Autowired
    private AuthorService courseRepo;


    @Autowired
    private AuthorImageService authorImageService;

    public void generateExcel(HttpServletResponse response) throws Exception {

        List<Author> courses = courseRepo.getAllAuthor();


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Thông Tin Tác Giả");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id");
        row.createCell(1).setCellValue("Tên");
        row.createCell(2).setCellValue("Ảnh");
        row.createCell(3).setCellValue("Ngày Sinh");
        row.createCell(4).setCellValue("Địa Chỉ");
        row.createCell(5).setCellValue("Quốc Gia");
        row.createCell(6).setCellValue("Mô Tả");



        int dataRowIndex = 1;

        for (Author course : courses) {

            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            List<AuthorImage> authorImages = authorImageService.getAllAuth(course.getId());
            dataRow.createCell(0).setCellValue(course.getId());
            dataRow.createCell(1).setCellValue(course.getNameAuthor());
            for (AuthorImage authorImage:authorImages){
                dataRow.createCell(2).setCellValue(authorImage.getUrl_Image());
            }

            dataRow.createCell(3).setCellValue(course.getDate_of_birth());
            dataRow.createCell(4).setCellValue(course.getAddress());
            dataRow.createCell(5).setCellValue(course.getNation());
            dataRow.createCell(6).setCellValue(course.getDescription());
            dataRowIndex++;
        }

        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();

    }
}
