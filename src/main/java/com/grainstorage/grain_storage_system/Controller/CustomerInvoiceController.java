package com.grainstorage.grain_storage_system.Controller;

import com.grainstorage.grain_storage_system.Entity.CustomerInvoice;
import com.grainstorage.grain_storage_system.Service.CustomerInvoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-invoices")
public class CustomerInvoiceController {

    private final CustomerInvoiceService customerInvoiceService;

    public CustomerInvoiceController(CustomerInvoiceService customerInvoiceService) {
        this.customerInvoiceService = customerInvoiceService;
    }

    @PostMapping("/from-sales-order/{salesOrderId}")
    public CustomerInvoice createInvoiceFromSalesOrder(@PathVariable Long salesOrderId) {
        return customerInvoiceService.createInvoiceFromSalesOrder(salesOrderId);
    }

    @GetMapping
    public List<CustomerInvoice> getAllCustomerInvoices() {
        return customerInvoiceService.getAllCustomerInvoices();
    }

    @GetMapping("/{id}")
    public CustomerInvoice getCustomerInvoiceById(@PathVariable Long id) {
        return customerInvoiceService.getCustomerInvoiceById(id);
    }
}
