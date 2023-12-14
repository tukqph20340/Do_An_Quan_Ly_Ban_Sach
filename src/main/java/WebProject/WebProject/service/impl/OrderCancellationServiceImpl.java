package WebProject.WebProject.service.impl;

import WebProject.WebProject.entity.OrderCancellation;
import WebProject.WebProject.repository.OrderCancellationRepository;
import WebProject.WebProject.service.OrderCancellationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderCancellationServiceImpl implements OrderCancellationService {

    @Autowired
    OrderCancellationRepository orderCancellationRepository;

    @Override
    public void saveOrderCancel(OrderCancellation orderCancellation) {
        orderCancellationRepository.save(orderCancellation);
    }
}
