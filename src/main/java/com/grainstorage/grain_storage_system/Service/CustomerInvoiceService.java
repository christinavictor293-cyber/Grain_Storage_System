package com.grainstorage.grain_storage_system.Service;

import com.grainstorage.grain_storage_system.Entity.*;
import com.grainstorage.grain_storage_system.Repository.CustomerInvoiceRepository;
import com.grainstorage.grain_storage_system.Repository.SalesOrderRepository;
import com.grainstorage.grain_storage_system.Repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerInvoiceService {

    private final CustomerInvoiceRepository customerInvoiceRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final WarehouseRepository warehouseRepository;
    private final JournalService journalService;
    private final ChartOfAccountService chartOfAccountService;

    public CustomerInvoiceService(CustomerInvoiceRepository customerInvoiceRepository,
                                  SalesOrderRepository salesOrderRepository,
                                  WarehouseRepository warehouseRepository,
                                  JournalService journalService,
                                  ChartOfAccountService chartOfAccountService) {
        this.customerInvoiceRepository = customerInvoiceRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.warehouseRepository = warehouseRepository;
        this.journalService = journalService;
        this.chartOfAccountService = chartOfAccountService;
    }

    public CustomerInvoice createInvoiceFromSalesOrder(Long salesOrderId) {
        SalesOrder order = salesOrderRepository.findById(salesOrderId)
                .orElseThrow(() -> new RuntimeException("Sales order not found with id: " + salesOrderId));

        if (order.getStatus() == SalesOrderStatus.DISPATCHED) {
            throw new IllegalStateException("Sales order has already been dispatched.");
        }

        Warehouse warehouse = order.getGrainLot().getWarehouse();
        double newStock = warehouse.getCurrentStockTons() - order.getQuantityTons();
        if (newStock < 0) {
            throw new IllegalStateException(
                "Cannot dispatch: insufficient stock in warehouse. " +
                "Available: " + warehouse.getCurrentStockTons() +
                ", Requested: " + order.getQuantityTons()
            );
        }
        warehouse.setCurrentStockTons(newStock);
        warehouseRepository.save(warehouse);

        order.setStatus(SalesOrderStatus.DISPATCHED);
        salesOrderRepository.save(order);

        Double amount = order.getQuantityTons() * order.getGrainLot().getPricePerTon();

        CustomerInvoice invoice = new CustomerInvoice();
        invoice.setSalesOrder(order);
        invoice.setAmount(amount);
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setStatus(InvoiceStatus.UNPAID);
        invoice = customerInvoiceRepository.save(invoice);

        ChartOfAccount cash = chartOfAccountService.getOrCreateAccount("Cash", AccountType.ASSET);
        ChartOfAccount sales = chartOfAccountService.getOrCreateAccount("PDS Distribution Sales", AccountType.INCOME);

        journalService.createBalancedJournal(
                "SALE",
                "Customer invoice for sales order #" + order.getId(),
                cash,
                sales,
                amount
        );

        return invoice;
    }

    public List<CustomerInvoice> getAllCustomerInvoices() {
        return customerInvoiceRepository.findAll();
    }

    public CustomerInvoice getCustomerInvoiceById(Long id) {
        return customerInvoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer invoice not found with id: " + id));
    }
}
