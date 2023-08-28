package com.company.gamestore.controller;

import com.company.gamestore.service.ServiceLayer;
import com.company.gamestore.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class InvoiceController {
    @Autowired
    ServiceLayer invoiceServiceLayer;

    //TODO: create invoice
    @PostMapping(path = "/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel createInvoice(@RequestBody @Valid InvoiceViewModel invoiceViewModel) {
        //save into InvoiceViewModel
        InvoiceViewModel i = new InvoiceViewModel();
        return i;
    }

    // TODO: get invoice by invoice id
    // TODO: get all invoices
    // TODO: find/get invoice by customer name






}
