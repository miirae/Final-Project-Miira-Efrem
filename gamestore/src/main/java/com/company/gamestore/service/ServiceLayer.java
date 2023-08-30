package com.company.gamestore.service;

import com.company.gamestore.model.Console;
import com.company.gamestore.model.Game;
import com.company.gamestore.model.Invoice;
import com.company.gamestore.model.Tshirt;
import com.company.gamestore.repository.*;
import com.company.gamestore.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    public Invoice saveInvoice(@Valid InvoiceViewModel invoiceViewModel) {

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

        BigDecimal itemPrice;
        int itemQuantity;
        int invoiceQuantity;


        if(invoice.getName().toLowerCase() == "game"){
            // game invoice
            Optional<Game> game = gameRepository.findById(invoice.getId());

            if(game.isEmpty()) throw new IllegalArgumentException("invoice error: game does not exist");

            itemPrice = game.get().getPrice();
            itemQuantity = game.get().getQuantity();
            invoiceQuantity = invoice.getQuantity();
            Game workingGame = game.get();

            if(invoiceQuantity > itemQuantity || itemQuantity <= 0) throw new IllegalArgumentException("invoice error: game quantity exceeds availability");
            workingGame.setQuantity(itemQuantity-invoiceQuantity);

        }else if(invoice.getName().toLowerCase() == "console"){
            // console invoice
            Optional<Console> console = consoleRepository.findById(invoice.getId());

            if(console.isEmpty()) throw new IllegalArgumentException("invoice error: console does not exist");

            itemPrice = console.get().getPrice();
            itemQuantity = console.get().getQuantity();
            invoiceQuantity = invoice.getQuantity();
            Console workingConsole = console.get();

            if(invoiceQuantity > itemQuantity || itemQuantity <= 0) throw new IllegalArgumentException("invoice error: console quantity exceeds availability");
            workingConsole.setQuantity(itemQuantity-invoiceQuantity);

        }
        else if(invoice.getName().toLowerCase() == "tshirt"){
            // tshirt invoice
            Optional<Tshirt> tshirt = tshirtRepository.findById(invoice.getId());

            if(tshirt.isEmpty()) throw new IllegalArgumentException("invoice error: tshirt does not exist");

            itemPrice = tshirt.get().getPrice();
            itemQuantity = tshirt.get().getQuantity();
            invoiceQuantity = invoice.getQuantity();
            Tshirt workingTshirt = tshirt.get();

            if(invoiceQuantity > itemQuantity || itemQuantity <= 0) throw new IllegalArgumentException("invoice error: tshirt quantity exceeds availability");
            workingTshirt.setQuantity(itemQuantity-invoiceQuantity);

        }else{
            throw new IllegalArgumentException("Invalid type name");
        }

        invoice.setUnitPrice(itemPrice);

        //TODO: Calculate subtotal


        return invoice;
    }

}
