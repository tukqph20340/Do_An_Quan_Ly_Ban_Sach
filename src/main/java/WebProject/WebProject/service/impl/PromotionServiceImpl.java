package WebProject.WebProject.service.impl;

import WebProject.WebProject.entity.Promotion;
import WebProject.WebProject.repository.PromotionRepositoty;
import WebProject.WebProject.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    PromotionRepositoty promotionRepositoty;


    @Override
    public List<Promotion> getAll() {
        return promotionRepositoty.findAll();
    }

    @Override
    public Promotion checkPromotion(String code) {
        return promotionRepositoty.checkPromotion(code);
    }

    @Override
    public Promotion detail(Integer id) {
        return promotionRepositoty.findById(id).get();
    }

    @Override
    public Promotion addAndUpdate(Promotion promotion) {
        return promotionRepositoty.save(promotion);
    }

    @Override
    public void delete(Long id) {
        promotionRepositoty.deleteById(Math.toIntExact(id));
    }

    @Override
    public Page<Promotion> findAll(Pageable pageable) {

            return promotionRepositoty.findAll(pageable);

    }


}
