package WebProject.WebProject.service.impl;

import WebProject.WebProject.entity.Producer;
import WebProject.WebProject.repository.ProducerRepository;
import WebProject.WebProject.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    private ProducerRepository producerRepository;

    @Override
    public List<Producer> getAllProducer() {
        return producerRepository.findAll();
    }

    @Override
    public Producer saveProducer(Producer producer) {
        return producerRepository.save(producer);
    }

    @Override
    public Page<Producer> findAll(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return producerRepository.findAll(pageable);
    }

    @Override
    public Page<Producer> teh(Integer pageNo, Integer size, String ten) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return producerRepository.findAllByNameProducer(pageable,ten);
    }


    @Override
    public Producer getAllProducerById(int id) {
        return producerRepository.findById(id);
    }
}
