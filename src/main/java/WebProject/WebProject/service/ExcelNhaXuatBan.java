package WebProject.WebProject.service;




import WebProject.WebProject.entity.Producer;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ExcelNhaXuatBan {
    @Autowired
    private ProducerService courseRepo;



    public void generateExcel(HttpServletResponse response) throws Exception {

        List<Producer> courses = courseRepo.getAllProducer();


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Thông Tin Nhà Xuất Bản");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id Nhà Sản Xuất");
        row.createCell(1).setCellValue("Tên Nhà Xuất Bản");
        row.createCell(2).setCellValue("Số Điện Thoại");
        row.createCell(3).setCellValue("Email");
        row.createCell(4).setCellValue("Địa Chỉ");
        row.createCell(5).setCellValue("Quốc Gia");
        row.createCell(6).setCellValue("Mô Tả");



        int dataRowIndex = 1;

        for (Producer course : courses) {

            HSSFRow dataRow = sheet.createRow(dataRowIndex);



            dataRow.createCell(0).setCellValue(course.getId());
            dataRow.createCell(1).setCellValue(course.getNameProducer());
            dataRow.createCell(2).setCellValue(course.getPhone());
            dataRow.createCell(3).setCellValue(course.getEmail());
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
