package com.company.gamestore.service;

import com.company.gamestore.model.Invoice;
import com.company.gamestore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceLayer {
    private FeeRepository feeRepository;
    private InvoiceRepository invoiceRepository;
    private TaxRepository taxRepository;
    private TshirtRepository tshirtRepository;
    private GameRepository gameRepository;
    // private ConsoleRepository consoleRepository;

    // TODO: add these
    // ConsoleRepository consoleRepository
    @Autowired
    public ServiceLayer(
            FeeRepository feeRepository,
            InvoiceRepository invoiceRepository,
            TaxRepository taxRepository,
            TshirtRepository tshirtRepository,
            GameRepository gameRepository
            ){
        this.feeRepository = feeRepository;
        this.invoiceRepository = invoiceRepository;
        this.taxRepository = taxRepository;
        this.tshirtRepository = tshirtRepository;
        this.gameRepository = gameRepository;
        // this.consoleRepository = consoleRepository;
    }

}
