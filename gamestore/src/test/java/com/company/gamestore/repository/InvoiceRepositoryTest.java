package com.company.gamestore.repository;

import com.company.gamestore.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class InvoiceRepositoryTest {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    TshirtRepository tshirtRepository;
    @Autowired
    ConsoleRepository consoleRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    TaxRepository taxRepository;
    @Autowired
    FeeRepository feeRepository;

    private Tshirt tshirt;
    private Game game;
    private Console console;
    private Tax tax;
    private Fee fee;
    private Invoice invoice;
    private Invoice invoice2;
    private Invoice invoice3;


    @BeforeEach
    public void setUp() throws Exception{
        invoiceRepository.deleteAll();
        tshirtRepository.deleteAll();
        taxRepository.deleteAll();
        feeRepository.deleteAll();

        fee = new Fee();
        tax = new Tax();

        fee.setProductType("tshirt");
        fee.setFee(BigDecimal.valueOf(1.20));
        tax.setState("IL");
        tax.setRate(BigDecimal.valueOf(.10));

        feeRepository.save(fee);
        taxRepository.save(tax);

        tshirt = new Tshirt();
        tshirt.setSize("S");
        tshirt.setColor("peach");
        tshirt.setDescription("made with 100% cotton");
        tshirt.setPrice(BigDecimal.valueOf(10.99));
        tshirt.setQuantity(1);
        tshirt = tshirtRepository.save(tshirt);

        game = new Game();
        game.setTitle("Call Of Duty: Modern Warfare");
        game.setEsrbRating("M");
        game.setDescription("First-person shooter video game");
        game.setPrice(BigDecimal.valueOf(59.99));
        game.setStudio("Infinity Ward");
        game.setQuantity(1);
        game = gameRepository.save(game);

        console = new Console();
        console.setModel("xbox 360");
        console.setManufacturer("Microsoft");
        console.setPrice(BigDecimal.valueOf(500.00));
        console.setQuantity(12);
        console = consoleRepository.save(console);

        invoice = new Invoice();
        invoice.setName("Santi");
        invoice.setStreet("w 24th");
        invoice.setCity("Chi");
        invoice.setState("IL");
        invoice.setZipcode("60606");
        invoice.setItemType("tshirt");
        invoice.setItemId(tshirt.getTshirtId());
        invoice.setQuantity(1);
        invoice = invoiceRepository.save(invoice);

        invoice2 = new Invoice();
        invoice2.setName("Santi");
        invoice2.setStreet("w 24th");
        invoice2.setCity("Chi");
        invoice2.setState("IL");
        invoice2.setZipcode("60606");
        invoice2.setItemType("tshirt");
        invoice2.setItemId(game.getGameId());
        invoice2.setQuantity(1);
        invoice2 = invoiceRepository.save(invoice2);


        invoice3 = new Invoice();
        invoice3.setName("Santi");
        invoice3.setStreet("w 24th");
        invoice3.setCity("Chi");
        invoice3.setState("IL");
        invoice3.setZipcode("60606");
        invoice3.setItemType("tshirt");
        invoice3.setItemId(console.getConsoleId());
        invoice3.setQuantity(12);
        invoice3 = invoiceRepository.save(invoice3);
    }

    @Test
    public void SanityChecker() throws Exception{
        boolean insanity = true;
        assertTrue(insanity);
    }

    @Test
    public void shouldCreateInvoice(){
        Optional<Invoice> foundInvoice = invoiceRepository.findById(invoice.getId());
        assertEquals(foundInvoice.get(),invoice);
    }

    @Test
    public void shouldGetAllInvoices(){
        assertEquals(3,invoiceRepository.findAll().size());
    }

    @Test
    public void findGameInvoiceById(){
        Optional<Invoice> in2 = invoiceRepository.findById(invoice2.getId());
        assertEquals(invoice2, in2.get());
    }

    @Test
    public void findConsoleInvoiceById(){
        Optional<Invoice> in3 = invoiceRepository.findById(invoice3.getId());
        assertEquals(invoice3, in3.get());
    }

    @Test
    public void findTshirtInvoiceById(){
        Optional<Invoice> in = invoiceRepository.findById(invoice.getId());
        assertEquals(invoice, in.get());
    }

    @Test
    public void shouldFindInvoiceByName(){
        List<Invoice> foundInvoices = invoiceRepository.findByName("Santi");
        assertEquals(3,foundInvoices.size());
    }

}
