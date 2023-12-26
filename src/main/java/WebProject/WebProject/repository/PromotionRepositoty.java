package WebProject.WebProject.repository;

import WebProject.WebProject.entity.Author;
import WebProject.WebProject.entity.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepositoty extends JpaRepository<Promotion, Integer> {

    @Query(nativeQuery = true,value = "SELECT  * FROM promotion p WHERE p.expired_at > now() AND p.coupon_code = ?1")
    Promotion checkPromotion(String code);

    @Query(nativeQuery = true,value = "SELECT  * FROM promotion p WHERE p.expired_at > now()")
    List<Promotion> findAll();

    @Query("SELECT c from Promotion c WHERE c.couponCode LIKE %?1%")
    Page<Promotion> timKiemCouponCode(Pageable p, String couponCode);

}
