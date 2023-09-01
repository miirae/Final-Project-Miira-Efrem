package com.company.gamestore.service;

import com.company.gamestore.model.*;
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

    private Invoice createInvoiceFromViewModel(InvoiceViewModel invoiceViewModel) {
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

        invoice.setItemId(invoiceViewModel.getItemId());
        invoice.setItemType(invoiceViewModel.getItemType());

        return invoice;
    }

    @Transactional
    public Invoice saveInvoice(@Valid InvoiceViewModel invoiceViewModel) {

        if(invoiceViewModel == null){
            throw new IllegalArgumentException("InvoiceViewModel cannot be null");
        }

        // Create Invoice from InvoiceViewModel
        Invoice invoice = createInvoiceFromViewModel(invoiceViewModel);

        // Validate and update item quantity
        validateAndUpdateItemQuantity(invoice);

        // Calculate and set invoice totals
        calculateAndSetInvoiceTotals(invoice);

        invoiceRepository.save(invoice);

        return invoice;
    }

    public List<Invoice> findByName(String name){
        return invoiceRepository.findByName(name);
    }

    public Invoice findById(int invoiceId){
        Optional<Invoice> foundInvoice= invoiceRepository.findById(invoiceId);
        if(foundInvoice.isPresent()) {
            return foundInvoice.get();
        }
        return  null;
    }

    public List<Invoice> findAll(){
        return invoiceRepository.findAll();
    }


    private BigDecimal validateAndUpdateGameInvoice(Invoice invoice, int invoiceQuantity) {
        Optional<Game> game = gameRepository.findById(invoice.getItemId());

        if(game.isEmpty()) throw new IllegalArgumentException("invoice error: game does not exist");

        BigDecimal itemPrice = game.get().getPrice();
        int itemQuantity = game.get().getQuantity();
        Game workingGame = game.get();

        if(invoiceQuantity > itemQuantity || itemQuantity <= 0) throw new IllegalArgumentException("invoice error: game quantity exceeds availability");
        workingGame.setQuantity(itemQuantity-invoiceQuantity);
        gameRepository.save(workingGame);

        return itemPrice;
    }

    private BigDecimal validateAndUpdateConsoleInvoice(Invoice invoice, int invoiceQuantity) {
        Optional<Console> console = consoleRepository.findById(invoice.getItemId());

        if(console.isEmpty()) throw new IllegalArgumentException("invoice error: console does not exist");

        BigDecimal itemPrice = console.get().getPrice();
        int itemQuantity = console.get().getQuantity();
        Console workingConsole = console.get();

        if(invoiceQuantity > itemQuantity || itemQuantity <= 0) throw new IllegalArgumentException("invoice error: console quantity exceeds availability");
        workingConsole.setQuantity(itemQuantity-invoiceQuantity);
        consoleRepository.save(workingConsole);

        return itemPrice;
    }

    private BigDecimal validateAndUpdateTshirtInvoice(Invoice invoice, int invoiceQuantity) {
        Optional<Tshirt> tshirt = tshirtRepository.findById(invoice.getItemId());

        if(tshirt.isEmpty()) throw new IllegalArgumentException("invoice error: tshirt does not exist");

        BigDecimal itemPrice = tshirt.get().getPrice();
        int itemQuantity = tshirt.get().getQuantity();
        Tshirt workingTshirt = tshirt.get();

        if(invoiceQuantity > itemQuantity || itemQuantity <= 0) throw new IllegalArgumentException("invoice error: tshirt quantity exceeds availability");
        workingTshirt.setQuantity(itemQuantity-invoiceQuantity);
        tshirtRepository.save(workingTshirt);

        return itemPrice;
    }

// Similar methods for console and tshirt invoices...

    private void calculateAndSetInvoiceTotals(Invoice invoice) {
        int invoiceQuantity = invoice.getQuantity();
        BigDecimal itemPrice = invoice.getUnitPrice();
        String itemtype = invoice.getItemType();
        BigDecimal inSubtotal = itemPrice.multiply(BigDecimal.valueOf(invoiceQuantity));
        BigDecimal total = inSubtotal;

        // tax
        Optional<Tax> curTax = taxRepository.findByState(invoice.getState());
        if(curTax.isEmpty()){
            throw new IllegalArgumentException("Invoice error: Invalid State " + invoice.getState());
        }
        BigDecimal inTax = curTax.get().getRate().multiply(inSubtotal);
        invoice.setTax(inTax);
        total = total.add(inTax);

        // business rule: the number of items in the order is greater than 10, in which case an additional
        // processing fee of $15.49 is applied to the order.
        Optional<Fee> curfee = feeRepository.findById(itemtype);
        BigDecimal inFee = curfee.get().getFee();
        if(invoiceQuantity > 10){
            inFee = inFee.add(BigDecimal.valueOf(15.49));
        }
        invoice.setProcessingFee(inFee);
        total = total.add(inFee);

        invoice.setTotal(total);
    }

    private void validateAndUpdateItemQuantity(Invoice invoice) {
        BigDecimal itemPrice;
        int itemQuantity;
        int invoiceQuantity  = invoice.getQuantity();
        String itemtype = invoice.getItemType();

        if(itemtype.equalsIgnoreCase("game")){
            // game invoice
            itemPrice = validateAndUpdateGameInvoice(invoice, invoiceQuantity);

        } else if(itemtype.equalsIgnoreCase("console")){
            // console invoice
            itemPrice = validateAndUpdateConsoleInvoice(invoice, invoiceQuantity);

        } else if(itemtype.equalsIgnoreCase("tshirt")){
            // tshirt invoice
            itemPrice = validateAndUpdateTshirtInvoice(invoice, invoiceQuantity);

        } else {
            throw new IllegalArgumentException("Invalid type name " + invoice.getName());
        }

        invoice.setUnitPrice(itemPrice);
    }
}
