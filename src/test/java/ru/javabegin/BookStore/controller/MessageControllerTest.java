package ru.javabegin.BookStore.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.javabegin.BookStore.Controller.MainController;
import ru.javabegin.BookStore.entity.account;
import ru.javabegin.BookStore.entity.MainServer;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MainController.class)
class MessageControllerTest {

    @MockBean
    private MainServer mainServer;

    @Autowired
    private MockMvc mvc;

    @Test
    void messageAccount() throws Exception {
        when(mainServer.getAccount()).thenReturn((new account(2000, null)));
        this.mvc
                .perform(MockMvcRequestBuilders
                        .get("/account")
                        .header("balance", 2000))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)));
    }

    @Test
    void messageMarketDeal() throws Exception {
        this.mvc
                .perform(MockMvcRequestBuilders
                        .post("/market/deal")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"id\":\"1\", \"amount\":\"1\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}