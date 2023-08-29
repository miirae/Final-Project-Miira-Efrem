package com.company.gamestore.service;

import com.company.gamestore.model.Invoice;
import com.company.gamestore.repository.*;
import com.company.gamestore.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ServiceLayer {
    private FeeRepository feeRepository;
    private InvoiceRepository invoiceRepository;
    private TaxRepository taxRepository;
    private TshirtRepository tshirtRepository;
    private GameRepository gameRepository;
    private ConsoleRepository consoleRepository;

    @Autowired
    public ServiceLayer(
            FeeRepository feeRepository,
            InvoiceRepository invoiceRepository,
            TaxRepository taxRepository,
            TshirtRepository tshirtRepository,
            GameRepository gameRepository,
            ConsoleRepository consoleRepository
            ){
        this.feeRepository = feeRepository;
        this.invoiceRepository = invoiceRepository;
        this.taxRepository = taxRepository;
        this.tshirtRepository = tshirtRepository;
        this.gameRepository = gameRepository;
        this.consoleRepository = consoleRepository;
    }

    @Transactional
    public Invoice saveInvoice(InvoiceViewModel invoiceViewModel) {

        // Persist Invoice
        Invoice invoice = new Invoice(
                invoiceViewModel.getName(),
                invoiceViewModel.getStreet(),
                invoiceViewModel.getCity(),
                invoiceViewModel.getState(),
                invoiceViewModel.getZipcode(),
                invoiceViewModel.getItemType(),
                invoiceViewModel.getItemId(),
                invoiceViewModel.getUnitPrice(),
                invoiceViewModel.getQuantity(),
                invoiceViewModel.getSubtotal(),
                invoiceViewModel.getTax(),
                invoiceViewModel.getProcessingFee(),
                invoiceViewModel.getTotal());

        //check "business logic" ...

        if(invoice.getName().toLowerCase() == "game"){
            // game invoice
        }else if(invoice.getName().toLowerCase() == "console"){
            // console invoice
        }
        else if(invoice.getName().toLowerCase() == "tshirt"){
            // tshirt invoice
        }else{
            throw new ??__ARGUMET__??(invoice.getName() + " is not a valid item name");
        }

        return invoice;
    }

}
