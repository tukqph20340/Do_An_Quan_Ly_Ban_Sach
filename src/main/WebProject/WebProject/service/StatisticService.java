package WebProject.WebProject.service;

import WebProject.WebProject.entity.Order;
import WebProject.WebProject.entity.Statistic;

import java.util.List;

public interface StatisticService {

    public void saveStatistic(Statistic statistic);

    List<Statistic> getAllByProductId(Integer productId);

}
