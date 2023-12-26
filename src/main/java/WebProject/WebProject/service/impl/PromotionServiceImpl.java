package WebProject.WebProject.service.impl;

import WebProject.WebProject.entity.Promotion;
import WebProject.WebProject.repository.PromotionRepositoty;
import WebProject.WebProject.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    PromotionRepositoty promotionRepositoty;


    @Override
    public List<Promotion> getAllPromotion() {
        return promotionRepositoty.findAll();
    }


    @Override
    public List<Promotion> getAll() {
        return promotionRepositoty.findAll();
    }

    @Override
    public Promotion savePromotion(Promotion promotion) {
        return promotionRepositoty.save(promotion);
    }

    @Override
    public Page<Promotion> findAll(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return promotionRepositoty.findAll(pageable);
    }

    @Override
    public Promotion getAllPromotionById(Integer id) {
        return promotionRepositoty.findById(id).get();
    }

    @Override
    public Promotion checkPromotion(String code) {
        return promotionRepositoty.checkPromotion(code);
    }

}