package WebProject.WebProject.service;



import WebProject.WebProject.entity.Author;
import WebProject.WebProject.entity.BookCover;
import WebProject.WebProject.entity.Category;
import WebProject.WebProject.entity.Product;
import WebProject.WebProject.entity.ProductImage;
import WebProject.WebProject.repository.ProductRepository;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Service
public class ExcelSanPham {
    @Autowired
    private ProductRepository courseRepo;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookCoverService bookCover;

    @Autowired
    private ProductImageService productImageService;

    public void generateExcel(HttpServletResponse response) throws Exception {

        List<Product> courses = courseRepo.findAll();


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Thông Tin Sản Phẩm");
        HSSFRow row = sheet.createRow(0);



        row.createCell(0).setCellValue("Tên ");
        row.createCell(1).setCellValue("Ảnh");
        row.createCell(2).setCellValue("Ngày Tạo");
        row.createCell(3).setCellValue("Giá");
        row.createCell(4).setCellValue("Số Trang");
        row.createCell(5).setCellValue("Số Lượng");
        row.createCell(6).setCellValue("Kích Thước");
        row.createCell(7).setCellValue("Loại");
        row.createCell(8).setCellValue("Bìa");
        row.createCell(9).setCellValue("Tác Giả");
        row.createCell(10).setCellValue("Ngôn Ngữ");
        row.createCell(11).setCellValue("Năm Sản Xuất");
        row.createCell(12).setCellValue("Mô Tả");
        row.createCell(13).setCellValue("Trạng Thái");
        row.createCell(14).setCellValue("Đã Bán");



        int dataRowIndex = 1;

        for (Product course : courses) {

            HSSFRow dataRow = sheet.createRow(dataRowIndex);

            Category category = categoryService.getAllCategoryById(course.getCategory().getId());
            Author author = authorService.getAllAuthorById(course.getAuthor().getId());
            BookCover bookCover1 = bookCover.getAllBookCoverById(course.getBookCover().getId());

            List<ProductImage> listAmnh = course.getProductImage();

            dataRow.createCell(0).setCellValue(course.getProduct_Name());
            for (ProductImage image :listAmnh){
                dataRow.createCell(1).setCellValue(image.getUrl_Image());
            }

            dataRow.createCell(2).setCellValue(course.getCreated_At());
            dataRow.createCell(3).setCellValue(course.getPrice());
            dataRow.createCell(4).setCellValue(course.getPageNumber());
            dataRow.createCell(5).setCellValue(course.getQuantity());
            dataRow.createCell(6).setCellValue(course.getBookSize());
            dataRow.createCell(7).setCellValue(category.getCategory_Name());
            dataRow.createCell(8).setCellValue(bookCover1.getCoverType());
            dataRow.createCell(9).setCellValue(author.getNameAuthor());
            dataRow.createCell(10).setCellValue(course.getLanguage());
            dataRow.createCell(11).setCellValue(course.getYearPublication());
            dataRow.createCell(12).setCellValue(course.getDescription());
            dataRow.createCell(13).setCellValue(course.getIs_Active()==1?"Còn Hàng":"Hết Hàng");
            dataRow.createCell(14).setCellValue(course.getSold());


            dataRowIndex++;
        }

        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();

    }
}
