package ru.javabegin.BookStore.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.BookStore.JsonParser;
import ru.javabegin.BookStore.entity.Deal;
import ru.javabegin.BookStore.entity.MainServer;
import ru.javabegin.BookStore.entity.account;
import ru.javabegin.BookStore.entity.books;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@RestController
public class MainController {
    private static final Logger LOGGER = Logger.getLogger("MainController");
    private final MainServer mainServer;
    private final JsonParser jsonParser = new JsonParser();

    private MainController(){
        mainServer = jsonParser.parse();

        LOGGER.setLevel(Level.ALL);
        ConsoleHandler ch = new ConsoleHandler();
        LOGGER.addHandler(ch);
    }

    @GetMapping("/")
    public ResponseEntity main(){
        LOGGER.log(Level.INFO,"home page");
        return ResponseEntity.ok("Книжный интернет-магазин");
    }

    @GetMapping("/market")
    public ResponseEntity<List<books>> books(){
        LOGGER.log(Level.INFO,"market page");
        List<books> books1 = new ArrayList<books>();
        for(var product:mainServer.getBooks())
            if(product.getAmount() > 0) books1.add(product);
        return ResponseEntity.ok(books1);
    }

    @GetMapping("/account")
    public ResponseEntity account(){
        LOGGER.log(Level.INFO,"account page");
        return ResponseEntity.ok(mainServer.getAccount());
    }

    @PostMapping(path = "/market/deal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<account> MarketDeal(@RequestBody Deal newDeal){
        int num1 = -1, num2 = mainServer.check(newDeal.getId());
        account ac = mainServer.getAccount();
        List<books> prod = mainServer.getBooks();
        if(num2 == -1) {
            LOGGER.log(Level.WARNING,"incorrect book id");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(ac.getMoney() >= prod.get(num2).getPrice() * newDeal.getAmount() && prod.get(num2).getAmount() >= newDeal.getAmount()) {
            prod.get(num2).setAmount(prod.get(num2).getAmount() - newDeal.getAmount());
            ac.setMoney(ac.getMoney() - (prod.get(num2).getPrice()) * newDeal.getAmount());
            num1 = ac.check(newDeal.getId());
            LOGGER.log(Level.INFO,"user bought the book " + prod.get(num2).getName() + " in quantity " + newDeal.getAmount());
            if (num1 != -1)
                ac.getBooks().get(num1).setAmount(ac.getBooks().get(num1).getAmount() + newDeal.getAmount());
            else {
                ac.save(prod.get(num2));
                ac.getBooks().get(ac.getBooks().size() - 1).setAmount(newDeal.getAmount());
            }
            jsonParser.pars2(mainServer);
        }
        else {
            if(ac.getMoney() < prod.get(num2).getPrice() * newDeal.getAmount())
                LOGGER.log(Level.WARNING,"lack of money");
            else LOGGER.log(Level.WARNING,"Not enough books in the store");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(mainServer.getAccount(), HttpStatus.OK);
    }
}