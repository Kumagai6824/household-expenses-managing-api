package integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.jayway.jsonpath.JsonPath;
import household_expenses_managing_api.com.demo.DemoApplication;
import household_expenses_managing_api.com.demo.entity.Expense;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataSet(value = "expense.yml", executeScriptsBefore = "reset-id.sql", cleanAfter = true, transactional = true)
public class ExpensesManagerIntegrationTestForExpense {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Transactional
    void 全expenseが取得できること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/expense"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                   "data":[
                      {
                         "id":1,
                         "type":"ACTUAL",
                         "category":"food",
                         "amount":7000,
                         "usedDate":"2025-01-10",
                         "createdAt":null,
                         "updatedAt":null
                      }
                   ],
                   "message":"Expense records fetched successfully"
                }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @Transactional
    void addExpenseで登録できること() throws Exception {
        Expense requestExpense = new Expense(Expense.Type.PROJECTED, "new food", 3000, LocalDate.of(2025, 1, 10));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String requestJson = objectMapper.writeValueAsString(requestExpense);
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/expense")
                        .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                    "message":"Expense added successfully"
                }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @ExpectedDataSet(value = {"/expecteddataset/expectedUpdatedExpense.yml"}, ignoreCols = {"updated_at"})
    @Transactional
    void updateExpenseで更新できること() throws Exception {

        Expense requestExpense = new Expense(Expense.Type.PROJECTED, "Updated food", 55555, LocalDate.of(2025, 10, 11), null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        int id = 1;

        String requestJson = objectMapper.writeValueAsString(requestExpense);
        String response = mockMvc.perform(MockMvcRequestBuilders.patch("/expense/" + id)
                        .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                        {
                            "message":"Expense updated successfully"
                        }
                        """
                , response, JSONCompareMode.STRICT);

    }

    @Test
    @Transactional
    void updateExpenseで存在しないidのとき例外を返すこと() throws Exception {

        Expense requestExpense = new Expense(Expense.Type.PROJECTED, "Updated food", 55555, LocalDate.of(2025, 10, 11), null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        int id = 0;
        String requestJson = objectMapper.writeValueAsString(requestExpense);
        String response = mockMvc.perform(MockMvcRequestBuilders.patch("/expense/" + id)
                        .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals("/expense/" + id, JsonPath.read(response, "$.path"));
        assertEquals("Not Found", JsonPath.read(response, "$.error"));
        assertEquals("Expense ID: " + id + " doesn't exist", JsonPath.read(response, "$.message"));
    }

    @Test
    @Transactional
    void getExpenseByIdで該当のexpenseを取得できること() throws Exception {
        int id = 1;
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/expense/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                   "data":{
                      "id":1,
                      "type":"ACTUAL",
                      "category":"food",
                      "amount":7000,
                      "usedDate":"2025-01-10",
                      "createdAt":null,
                      "updatedAt":null
                   },
                   "message":"Expense record fetched successfully"
                }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @Transactional
    void getExpenseByIdで存在しないidのとき例外を返すこと() throws Exception {
        int id = 0;
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/expense/" + id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals("/expense/" + id, JsonPath.read(response, "$.path"));
        assertEquals("Not Found", JsonPath.read(response, "$.error"));
        assertEquals("Expense ID: " + id + " doesn't exist", JsonPath.read(response, "$.message"));
    }

    @Test
    @Transactional
    void deleteExpenseで削除できること() throws Exception {
        int id = 1;
        String response = mockMvc.perform(MockMvcRequestBuilders.delete("/expense/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                        {
                            "message":"Expense deleted successfully"
                        }
                        """
                , response, JSONCompareMode.STRICT);
    }

    @Test
    @Transactional
    void deleteExpenseで存在しないidのとき例外を返すこと() throws Exception {
        int id = 0;
        String response = mockMvc.perform(MockMvcRequestBuilders.delete("/expense/" + id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals("/expense/" + id, JsonPath.read(response, "$.path"));
        assertEquals("Not Found", JsonPath.read(response, "$.error"));
        assertEquals("Expense ID: " + id + " doesn't exist", JsonPath.read(response, "$.message"));
    }

    @Test
    @Transactional
    @DataSet(value = "expensesForFilterTest.yml", executeScriptsBefore = "reset-id.sql", cleanAfter = true, transactional = true)
    void getExpenseByYearAndMonthでフィルターできること() throws Exception {
        int year = 2024;
        int month = 1;
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/expense/filter?year=" + year + "&month=" + month))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                [
                   {
                      "id":1,
                      "type":"ACTUAL",
                      "category":"food",
                      "amount":5000,
                      "usedDate":"2024-01-10",
                      "createdAt":null,
                      "updatedAt":null
                   },
                   {
                      "id":2,
                      "type":"PROJECTED",
                      "category":"rent",
                      "amount":250000,
                      "usedDate":"2024-01-10",
                      "createdAt":null,
                      "updatedAt":null
                   }
                ]
                """, response, JSONCompareMode.STRICT);
    }

}
