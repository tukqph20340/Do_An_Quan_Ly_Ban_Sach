package WebProject.WebProject.repository;

import WebProject.WebProject.dto.OrderDto;
import WebProject.WebProject.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ThongKeRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT new map(FUNCTION('DATE_FORMAT', o.booking_Date, '%Y-%m-%d %H:%i:%s') as ngay, " +
            "SUM(CASE WHEN o.payment_Method = 'Payment with momo' THEN o.total ELSE 0 END) as totalMomo, " +
            "SUM(CASE WHEN o.payment_Method = 'Ví' THEN o.total ELSE 0 END) as totalVi, " +
            "SUM(CASE WHEN o.payment_Method = 'Payment on delivery' THEN o.total ELSE 0 END) as totalDelivery, " +
            "SUM(o.total) as tongTien )" +
            "FROM Order o GROUP BY FUNCTION('DATE_FORMAT', o.booking_Date, '%Y-%m-%d %H:%i:%s')")
    List<Map<String, Object>> getTotalRevenueByDate();




    @Query("SELECT new map(FUNCTION('DATE_FORMAT', o.booking_Date, '%Y-%m-%d %H:%i:%s') as ngay, " +
            "SUM(CASE WHEN o.payment_Method = 'Payment with momo' THEN o.total ELSE 0 END) as totalMomo, " +
            "SUM(CASE WHEN o.payment_Method = 'Ví' THEN o.total ELSE 0 END) as totalVi, " +
            "SUM(CASE WHEN o.payment_Method = 'Payment on delivery' THEN o.total ELSE 0 END) as totalDelivery, " +
            "SUM(o.total) as tongTien )" +
            "FROM Order o WHERE STR_TO_DATE(o.booking_Date, '%Y-%m-%d %H:%i:%s') BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('DATE_FORMAT', STR_TO_DATE(o.booking_Date, '%Y-%m-%d %H:%i:%s'), '%Y-%m-%d %H:%i:%s')")
    List<Map<String, Object>> getTotalRevenueByDate1(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);

    @Query("SELECT new map(FUNCTION('DATE_FORMAT', o.booking_Date, '%Y-%m-%d') as ngay, " +
            "SUM(CASE WHEN o.payment_Method = 'Payment with momo' THEN o.total ELSE 0 END) as totalMomo, " +
            "SUM(CASE WHEN o.payment_Method = 'Ví' THEN o.total ELSE 0 END) as totalVi, " +
            "SUM(CASE WHEN o.payment_Method = 'Payment on delivery' THEN o.total ELSE 0 END) as totalDelivery, " +
            "SUM(o.total) as tongTien )" +
            "FROM Order o GROUP BY FUNCTION('DATE_FORMAT', o.booking_Date, '%Y-%m-%d')")
    List<Map<String, Object>> getTotalRevenueByDatePayMent();

    @Query("SELECT new map(FUNCTION('DATE_FORMAT', o.booking_Date, '%Y-%m') as thangNam, " +
            "SUM(CASE WHEN o.payment_Method = 'Payment with momo' THEN o.total ELSE 0 END) as totalMomo, " +
            "SUM(CASE WHEN o.payment_Method = 'Ví' THEN o.total ELSE 0 END) as totalVi, " +
            "SUM(CASE WHEN o.payment_Method = 'Payment on delivery' THEN o.total ELSE 0 END) as totalDelivery, " +
            "SUM(o.total) as tongTien )" +
            "FROM Order o GROUP BY FUNCTION('DATE_FORMAT', o.booking_Date, '%Y-%m')")
    List<Map<String, Object>> getTotalRevenueByDatePayMentThang();

    @Query("SELECT new map(FUNCTION('DATE_FORMAT', o.booking_Date, '%Y') as nam, " +
            "SUM(CASE WHEN o.payment_Method = 'Payment with momo' THEN o.total ELSE 0 END) as totalMomo, " +
            "SUM(CASE WHEN o.payment_Method = 'Ví' THEN o.total ELSE 0 END) as totalVi, " +
            "SUM(CASE WHEN o.payment_Method = 'Payment on delivery' THEN o.total ELSE 0 END) as totalDelivery, " +
            "SUM(o.total) as tongTien )" +
            "FROM Order o GROUP BY FUNCTION('DATE_FORMAT', o.booking_Date, '%Y')")
    List<Map<String, Object>> getTotalRevenueByDatePayMentNam();

//    @Query("SELECT c.category_Name as ten, COALESCE(SUM(p.quantity), 0) AS sLTon, COALESCE(SUM(oi.count), 0) AS slBan " +
//            "FROM Category c " +
//            "LEFT JOIN c.product p " +
//            "LEFT JOIN p.order_Item oi " +
//            "GROUP BY c.category_Name")
//    List<Map<String, Object>> thongKeProduct();
//
//
//    @Query("SELECT p.product_Name as ten, COALESCE(SUM(oi.count), 0) AS slBan, COALESCE(SUM(p.quantity), 0) AS sLTon " +
//            "FROM Product p " +
//            "LEFT JOIN p.order_Item oi " +
//            "WHERE p.category.category_Name = :categoryName " +
//            "GROUP BY p.product_Name")
//    List<Map<String, Object>> thongKeProductByCategory(@Param("categoryName") String categoryName);


    @Query("SELECT new map(" +
            "FUNCTION('DATE_FORMAT', o.booking_Date, '%Y-%m-%d %H:%i:%s') as ngay, " +
            "COUNT(o.activeOrder.id) as tongDonHang, " +
            "SUM(CASE WHEN o.activeOrder.id = 1 THEN 1 ELSE 0 END) as trangThai1Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 2 THEN 1 ELSE 0 END) as trangThai2Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 3 THEN 1 ELSE 0 END) as trangThai3Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 4 THEN 1 ELSE 0 END) as trangThai4Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 5 THEN 1 ELSE 0 END) as trangThai5Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 6 THEN 1 ELSE 0 END) as trangThai6Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 7 THEN 1 ELSE 0 END) as trangThai7Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 8 THEN 1 ELSE 0 END) as trangThai8Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 9 THEN 1 ELSE 0 END) as trangThai9Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 10 THEN 1 ELSE 0 END) as trangThai10Count) " +
            "FROM Order o GROUP BY FUNCTION('DATE_FORMAT', o.booking_Date, '%Y-%m-%d %H:%i:%s')")
    Page<Map<String, Object>> getCountByStatusAndDate(Pageable pageable);

    @Query("SELECT new map(" +
            "FUNCTION('DATE_FORMAT', o.booking_Date, '%Y-%m-%d %H:%i:%s') as ngay, " +
            "COUNT(o.activeOrder.id) as tongDonHang, " +
            "SUM(CASE WHEN o.activeOrder.id = 1 THEN 1 ELSE 0 END) as trangThai1Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 2 THEN 1 ELSE 0 END) as trangThai2Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 3 THEN 1 ELSE 0 END) as trangThai3Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 4 THEN 1 ELSE 0 END) as trangThai4Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 5 THEN 1 ELSE 0 END) as trangThai5Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 6 THEN 1 ELSE 0 END) as trangThai6Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 7 THEN 1 ELSE 0 END) as trangThai7Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 8 THEN 1 ELSE 0 END) as trangThai8Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 9 THEN 1 ELSE 0 END) as trangThai9Count, " +
            "SUM(CASE WHEN o.activeOrder.id = 10 THEN 1 ELSE 0 END) as trangThai10Count) " +
            "FROM Order o WHERE o.booking_Date BETWEEN :startDate AND :endDate  GROUP BY FUNCTION('DATE_FORMAT', o.booking_Date, '%d-%m-%Y')")
    Page<Map<String, Object>> getCountByStatusAndDate12(@Param("startDate") String startDate, @Param("endDate") String endDate,Pageable pageable);




}
