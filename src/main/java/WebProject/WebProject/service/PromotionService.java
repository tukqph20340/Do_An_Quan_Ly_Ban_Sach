package WebProject.WebProject.service;

import WebProject.WebProject.entity.Producer;
import WebProject.WebProject.entity.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PromotionService {

    List<Promotion> getAllPromotion();

    Promotion savePromotion(Promotion promotion);

    Page<Promotion> findAll(Integer pageNo, Integer size);

    Promotion getAllPromotionById(Integer id);
}
