package WebProject.WebProject.repository;

import WebProject.WebProject.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepostitory extends JpaRepository<Statistic, Integer> {

    List<Statistic> findByProduct_Id(Integer productId);
}
