package integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import household_expenses_managing_api.com.demo.DemoApplication;
import household_expenses_managing_api.com.demo.entity.Income;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataSet(value = "income.yml", executeScriptsBefore = "reset-id.sql", cleanAfter = true, transactional = true)
public class ExpensesManagerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Transactional
    void 全incomeが取得できること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/income"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                   "data":[
                      {
                         "id":1,
                         "type":"ACTUAL",
                         "category":"salary",
                         "amount":5000,
                         "usedDate":"2024-01-10",
                         "createdAt":null,
                         "updatedAt":null
                      }
                   ],
                   "message":"Income records fetched successfully"
                }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @Transactional
    void addIncomeで登録できること() throws Exception {
        Income requestIncome = new Income(Income.Type.PROJECTED, "Salary", 10000, LocalDate.of(2025, 1, 10));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        String requestJson = objectMapper.writeValueAsString(requestIncome);
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/income")
                        .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                    "message":"Income added successfully"
                }
                """, response, JSONCompareMode.STRICT);
    }
}
