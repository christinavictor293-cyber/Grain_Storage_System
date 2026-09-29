package com.grainstorage.grain_storage_system.Service;

import com.grainstorage.grain_storage_system.Entity.SalesOrder;
import com.grainstorage.grain_storage_system.Entity.SalesOrderStatus;
import com.grainstorage.grain_storage_system.Repository.SalesOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;

    public SalesOrderService(SalesOrderRepository salesOrderRepository) {
        this.salesOrderRepository = salesOrderRepository;
    }

    public SalesOrder createSalesOrder(SalesOrder salesOrder) {
        salesOrder.setStatus(SalesOrderStatus.PENDING);
        return salesOrderRepository.save(salesOrder);
    }

    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAll();
    }

    public SalesOrder getSalesOrderById(Long id) {
        return salesOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sales order not found with id: " + id));
    }
}
