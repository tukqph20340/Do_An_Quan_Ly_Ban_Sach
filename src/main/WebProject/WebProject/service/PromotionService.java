package WebProject.WebProject.service;

import WebProject.WebProject.entity.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PromotionService {
    List<Promotion> getAll();
    Promotion checkPromotion(String code);
    Promotion detail(Integer id);
    Promotion addAndUpdate(Promotion promotion);
    void delete(Long id);

    Page<Promotion> findAll(Pageable pageable);

}
