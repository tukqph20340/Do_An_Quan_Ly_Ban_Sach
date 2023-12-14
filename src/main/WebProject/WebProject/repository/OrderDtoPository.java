package WebProject.WebProject.repository;

import WebProject.WebProject.dto.OrderDto;
import WebProject.WebProject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderDtoPository extends JpaRepository<Order,Integer> {
    @Query(value = "SELECT\n" +
            "    CASE\n" +
            "        WHEN ao.id = 7 AND (otm.payment_Method = 'ví' OR otm.payment_Method = 'Payment with momo') THEN 'Nạp tiền'\n" +
            "        WHEN otm.payment_Method = 'ví' THEN 'Trừ tiền'\n" +
            "    END AS tinh_trang,\n" +
            "    CASE\n" +
            "        WHEN ao.id = 7 AND (otm.payment_Method = 'ví' OR otm.payment_Method = 'Payment with momo') THEN otm.delivery_failed_date\n" +
            "        WHEN otm.payment_Method = 'ví' THEN DATE_FORMAT(otm.booking_date, '%Y-%m-%d %H:%i:%s')\n" +
            "    END AS ngay,\n" +
            "    otm.total AS tien\n" +
            "FROM\n" +
            "    `order` otm\n" +
            "JOIN\n" +
            "    active_order ao ON otm.active_order_id = ao.id\n" +
            "WHERE\n" +
            "    (ao.id = 7 AND (otm.payment_Method = 'ví' OR otm.payment_Method = 'Payment with momo')) AND otm.user_id = :userId\n" +
            "    OR (otm.payment_Method = 'ví') AND otm.user_id = :userId", nativeQuery = true)
    List<Map<String, Object>> getCountByStatus(@Param("userId") String userId);
}
