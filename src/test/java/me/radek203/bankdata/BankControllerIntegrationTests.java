package me.radek203.bankdata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    private List<String> headquarters;
    private List<String> branches;
    private List<String> countries;

    @BeforeAll
    @Sql(statements = "SELECT swift_code FROM swift_codes ORDER BY RANDOM() LIMIT 3", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void setUp(@Autowired JdbcTemplate jdbcTemplate) {
        headquarters = jdbcTemplate.queryForList("SELECT code FROM bank WHERE code LIKE '%XXX'", String.class);
        branches = jdbcTemplate.queryForList("SELECT code FROM bank WHERE code NOT LIKE '%XXX'", String.class);
        countries = jdbcTemplate.queryForList("SELECT DISTINCT country_code FROM bank", String.class);
    }

    @Test
    void getBankByCode_notFound() throws Exception {
        mockMvc.perform(get("/v1/swift-codes/NOTFOUND"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.message").value("bank/not-found"));
    }

    @Test
    void getBankByCode_headquarter_withoutBranches() throws Exception {
        mockMvc.perform(get("/v1/swift-codes/AAISALTRXXX"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.address").value("HYRJA 3 RR. DRITAN HOXHA ND. 11 TIRANA, TIRANA, 1023"))
                .andExpect(jsonPath("$.bankName").value("UNITED BANK OF ALBANIA SH.A"))
                .andExpect(jsonPath("$.countryISO2").value("AL"))
                .andExpect(jsonPath("$.countryName").value("ALBANIA"))
                .andExpect(jsonPath("$.isHeadquarter").value(true))
                .andExpect(jsonPath("$.swiftCode").value("AAISALTRXXX"))
                .andExpect(jsonPath("$.branches").isEmpty());
    }

    @Test
    void getBankByCode_headquarter_withBranches() throws Exception {
        mockMvc.perform(get("/v1/swift-codes/RIKOLV2XXXX"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.address").value("SKANSTES STREET 12  RIGA, RIGA, LV-1013"))
                .andExpect(jsonPath("$.bankName").value("LUMINOR BANK AS LATVIAN BRANCH"))
                .andExpect(jsonPath("$.countryISO2").value("LV"))
                .andExpect(jsonPath("$.countryName").value("LATVIA"))
                .andExpect(jsonPath("$.isHeadquarter").value(true))
                .andExpect(jsonPath("$.swiftCode").value("RIKOLV2XXXX"))
                .andExpect(jsonPath("$.branches").isArray())
                .andExpect(jsonPath("$.branches").isNotEmpty())
                .andExpect(jsonPath("$.branches[0].address").value("SKANSTES STREET 12  RIGA, RIGA, LV-1013"))
                .andExpect(jsonPath("$.branches[0].bankName").value("LUMINOR BANK AS LATVIAN BRANCH"))
                .andExpect(jsonPath("$.branches[0].countryISO2").value("LV"))
                .andExpect(jsonPath("$.branches[0].countryName").doesNotExist())
                .andExpect(jsonPath("$.branches[0].isHeadquarter").value(false))
                .andExpect(jsonPath("$.branches[0].swiftCode").value("RIKOLV2XIPA"))
                .andExpect(jsonPath("$.branches[0].branches").doesNotExist());
    }

    @Test
    void getBankByCode_headquarter_structure() throws Exception {
        for (String code : headquarters) {
            MvcResult result = mockMvc.perform(get("/v1/swift-codes/" + code))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(header().string("Content-Type", "application/json"))
                    .andExpect(jsonPath("$.countryISO2").value(matchesPattern("^[A-Z0-9]+$")))
                    .andExpect(jsonPath("$.countryName").value(matchesPattern("^[A-Z0-9]+$")))
                    .andExpect(jsonPath("$.isHeadquarter").value(true))
                    .andExpect(jsonPath("$.branches").isArray())
                    .andReturn();

            String jsonResponse = result.getResponse().getContentAsString();
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> actualJsonMap = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

            Set<String> expectedKeys = Set.of("address", "bankName", "countryISO2", "countryName", "isHeadquarter", "swiftCode", "branches");

            Assertions.assertEquals(expectedKeys, actualJsonMap.keySet(), "Response contains unexpected fields or some fields are missing!");

            expectedKeys = Set.of("address", "bankName", "countryISO2", "isHeadquarter", "swiftCode");

            List<Map<String, Object>> branches = (List<Map<String, Object>>) actualJsonMap.get("branches");

            for (Map<String, Object> branch : branches) {
                Assertions.assertEquals(expectedKeys, branch.keySet(), "Response contains unexpected fields or some fields are missing!");
            }
        }
    }

    @Test
    void getBankByCode_branch() throws Exception {
        mockMvc.perform(get("/v1/swift-codes/RIKOLV2XIPA"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.address").value("SKANSTES STREET 12  RIGA, RIGA, LV-1013"))
                .andExpect(jsonPath("$.bankName").value("LUMINOR BANK AS LATVIAN BRANCH"))
                .andExpect(jsonPath("$.countryISO2").value("LV"))
                .andExpect(jsonPath("$.countryName").value("LATVIA"))
                .andExpect(jsonPath("$.isHeadquarter").value(false))
                .andExpect(jsonPath("$.swiftCode").value("RIKOLV2XIPA"))
                .andExpect(jsonPath("$.branches").doesNotExist());
    }

    @Test
    void getBankByCode_branch_structure() throws Exception {
        for (String code : branches) {
            MvcResult result = mockMvc.perform(get("/v1/swift-codes/" + code))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(header().string("Content-Type", "application/json"))
                    .andExpect(jsonPath("$.countryISO2").value(matchesPattern("^[A-Z0-9]+$")))
                    .andExpect(jsonPath("$.countryName").value(matchesPattern("^[A-Z0-9]+$")))
                    .andExpect(jsonPath("$.isHeadquarter").value(false))
                    .andReturn();

            String jsonResponse = result.getResponse().getContentAsString();
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> actualJsonMap = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

            Set<String> expectedKeys = Set.of("address", "bankName", "countryISO2", "countryName", "isHeadquarter", "swiftCode");

            Assertions.assertEquals(expectedKeys, actualJsonMap.keySet(), "Response contains unexpected fields or some fields are missing!");
        }
    }

    @Test
    void getBanksByCountry_notFound() throws Exception {
        mockMvc.perform(get("/v1/swift-codes/country/NOTFOUND"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.message").value("bank/not-found-country"));
    }

    @Test
    void getBanksByCountry_structure() throws Exception {
        for (String countryCode : countries) {
            MvcResult result = mockMvc.perform(get("/v1/swift-codes/country/" + countryCode))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(header().string("Content-Type", "application/json"))
                    .andExpect(jsonPath("$.countryISO2").value(matchesPattern("^[A-Z0-9]+$")))
                    .andExpect(jsonPath("$.countryName").value(matchesPattern("^[A-Z0-9]+$")))
                    .andExpect(jsonPath("$.swiftCodes").isArray())
                    .andExpect(jsonPath("$.swiftCodes").isNotEmpty())
                    .andExpect(jsonPath("$.swiftCodes[*].countryISO2", everyItem(matchesPattern("^[A-Z0-9]+$"))))
                    .andReturn();

            String jsonResponse = result.getResponse().getContentAsString();
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> actualJsonMap = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

            Set<String> expectedKeys = Set.of("address", "bankName", "countryISO2", "isHeadquarter", "swiftCode");

            List<Map<String, Object>> swiftCodes = (List<Map<String, Object>>) actualJsonMap.get("swiftCodes");

            for (Map<String, Object> swiftCodeObj : swiftCodes) {
                Assertions.assertEquals(expectedKeys, swiftCodeObj.keySet(), "Response contains unexpected fields or some fields are missing!");
            }

        }
    }

}
