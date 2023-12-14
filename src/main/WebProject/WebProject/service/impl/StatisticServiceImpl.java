package WebProject.WebProject.service.impl;

import WebProject.WebProject.entity.Statistic;
import WebProject.WebProject.repository.StatisticRepostitory;
import WebProject.WebProject.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticRepostitory statisticRepostitory;

    @Override
    public void saveStatistic(Statistic statistic) {
        statisticRepostitory.save(statistic);
    }

    public List<Statistic> getAllByProductId(Integer productId) {
        return statisticRepostitory.findByProduct_Id(productId);
    }
}
