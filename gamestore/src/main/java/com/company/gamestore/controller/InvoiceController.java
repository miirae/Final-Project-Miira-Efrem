package com.company.gamestore.controller;

import com.company.gamestore.model.Invoice;
import com.company.gamestore.service.ServiceLayer;
import com.company.gamestore.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class InvoiceController {

    // create invoice from vm
    @Autowired
    ServiceLayer invoiceServiceLayer;
    @PostMapping(path = "/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice createInvoicefromVM(@RequestBody @Valid InvoiceViewModel ivm) {
        return invoiceServiceLayer.saveInvoice(ivm);
    }

    // get invoice by invoice id
    @GetMapping(path = "/invoice/{InvoiceId}")
    @ResponseStatus(HttpStatus.OK)
    public Invoice getInvoiceByInvoiceId(@PathVariable int InvoiceId){
        return invoiceServiceLayer.findById(InvoiceId);
    }

    // get all invoices
    @GetMapping(path = "/invoice")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Invoice> getAllInvoices(){
        return invoiceServiceLayer.findAll();
    }

    // find/get invoice by name
    @GetMapping(path = "/invoice/name/{name}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Invoice> getInvoiceByName(@PathVariable String name){
        return invoiceServiceLayer.findByName(name);
    }






}
