package WebProject.WebProject.service;

import WebProject.WebProject.entity.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProducerService {

    List<Producer> getAllProducer();

    Producer saveProducer(Producer producer);

    Page<Producer> findAll(Integer pageNo, Integer size);

    Page<Producer> teh(Integer pageNo, Integer size,String ten);

    Producer getAllProducerById(int id);

}
