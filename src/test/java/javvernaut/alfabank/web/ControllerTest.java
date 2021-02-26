package javvernaut.alfabank.web;

import javvernaut.alfabank.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static javvernaut.alfabank.TestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class ControllerTest extends BaseTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/" + CURRENCY_CODE))
                .andExpect(status().isOk())
                .andExpect(content().bytes(IMAGE_DATA_FALL));
    }

    @Test
    void getIncorrect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/" + WRONG_CURRENCY_CODE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}