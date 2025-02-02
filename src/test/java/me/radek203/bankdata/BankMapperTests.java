package me.radek203.bankdata;

import me.radek203.bankdata.entities.Bank;
import me.radek203.bankdata.entities.dto.BankDTO;
import me.radek203.bankdata.entities.dto.BankReducedDTO;
import me.radek203.bankdata.entities.dto.BanksByCodeDTO;
import me.radek203.bankdata.mappers.BankMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class BankMapperTests {

    @Test
    void mapBankToBankDTO_withHeadquarter() {
        Bank bank = new Bank("123XXX", "US", "Bank Name", "Address", "Country", null, Collections.emptyList());
        BankDTO result = BankMapper.mapBankToBankDTO(bank);
        Assertions.assertTrue(result.getIsHeadquarter());
        Assertions.assertNotNull(result.getBranches());
    }

    @Test
    void mapBankToBankDTO_withoutHeadquarter() {
        Bank bank = new Bank("123", "US", "Bank Name", "Address", "Country", null, Collections.emptyList());
        BankDTO result = BankMapper.mapBankToBankDTO(bank);
        Assertions.assertFalse(result.getIsHeadquarter());
        Assertions.assertNull(result.getBranches());
    }

    @Test
    void mapBankToBankDTO_withBranches() {
        Bank branch = new Bank("123", "US", "Branch Name", "Branch Address", "Country", null, null);
        Bank bank = new Bank("123XXX", "US", "Bank Name", "Address", "Country", null, List.of(branch));
        BankDTO result = BankMapper.mapBankToBankDTO(bank);
        Assertions.assertTrue(result.getIsHeadquarter());
        Assertions.assertNotNull(result.getBranches());
        Assertions.assertEquals(1, result.getBranches().size());
    }

    @Test
    void mapBankToBankDTO_withoutBranches() {
        Bank bank = new Bank("123XXX", "US", "Bank Name", "Address", "Country", null, null);
        BankDTO result = BankMapper.mapBankToBankDTO(bank);
        Assertions.assertTrue(result.getIsHeadquarter());
        Assertions.assertTrue(result.getBranches().isEmpty());
    }

    @Test
    void mapBankToBankDTO_emptyCode() {
        Bank bank = new Bank("", "US", "Bank Name", "Address", "Country", null, null);
        BankDTO result = BankMapper.mapBankToBankDTO(bank);
        Assertions.assertFalse(result.getIsHeadquarter());
        Assertions.assertNull(result.getBranches());
    }

    @Test
    void mapBankToBankDTO_codeWithoutXXX() {
        Bank bank = new Bank("123", "US", "Bank Name", "Address", "Country", null, null);
        BankDTO result = BankMapper.mapBankToBankDTO(bank);
        Assertions.assertFalse(result.getIsHeadquarter());
        Assertions.assertNull(result.getBranches());
    }

    @Test
    void mapBankToBankReducedDTO_withHeadquarter() {
        Bank bank = new Bank("123XXX", "US", "Bank Name", "Address", "Country", null, null);
        BankReducedDTO result = BankMapper.mapBankToBankReducedDTO(bank);
        Assertions.assertTrue(result.getIsHeadquarter());
        Assertions.assertEquals("123XXX", result.getSwiftCode());
        Assertions.assertEquals("US", result.getCountryISO2());
        Assertions.assertEquals("Bank Name", result.getBankName());
        Assertions.assertEquals("Address", result.getAddress());
    }

    @Test
    void mapBankToBankReducedDTO_withoutHeadquarter() {
        Bank bank = new Bank("123", "US", "Bank Name", "Address", "Country", null, null);
        BankReducedDTO result = BankMapper.mapBankToBankReducedDTO(bank);
        Assertions.assertFalse(result.getIsHeadquarter());
        Assertions.assertEquals("123", result.getSwiftCode());
        Assertions.assertEquals("US", result.getCountryISO2());
        Assertions.assertEquals("Bank Name", result.getBankName());
        Assertions.assertEquals("Address", result.getAddress());
    }

    @Test
    void mapBankToBankReducedDTO_emptyCode() {
        Bank bank = new Bank("", "US", "Bank Name", "Address", "Country", null, null);
        BankReducedDTO result = BankMapper.mapBankToBankReducedDTO(bank);
        Assertions.assertFalse(result.getIsHeadquarter());
        Assertions.assertEquals("", result.getSwiftCode());
        Assertions.assertEquals("US", result.getCountryISO2());
        Assertions.assertEquals("Bank Name", result.getBankName());
        Assertions.assertEquals("Address", result.getAddress());
    }

    @Test
    void mapBankToBankReducedDTO_codeWithoutXXX() {
        Bank bank = new Bank("123", "US", "Bank Name", "Address", "Country", null, null);
        BankReducedDTO result = BankMapper.mapBankToBankReducedDTO(bank);
        Assertions.assertFalse(result.getIsHeadquarter());
        Assertions.assertEquals("123", result.getSwiftCode());
        Assertions.assertEquals("US", result.getCountryISO2());
        Assertions.assertEquals("Bank Name", result.getBankName());
        Assertions.assertEquals("Address", result.getAddress());
    }

    @Test
    void mapBanksToBanksByCodeDTO_withValidData() {
        Bank bank = new Bank("123", "US", "Bank Name", "Address", "Country", null, null);
        List<Bank> banks = List.of(bank);
        BanksByCodeDTO result = BankMapper.mapBanksToBanksByCodeDTO("US", banks);
        Assertions.assertEquals("US", result.getCountryISO2());
        Assertions.assertEquals("Country", result.getCountryName());
        Assertions.assertEquals(1, result.getSwiftCodes().size());
    }

    @Test
    void mapBanksToBanksByCodeDTO_withMultipleBanks() {
        Bank bank1 = new Bank("123", "US", "Bank Name 1", "Address 1", "Country", null, null);
        Bank bank2 = new Bank("456", "US", "Bank Name 2", "Address 2", "Country", null, null);
        List<Bank> banks = List.of(bank1, bank2);
        BanksByCodeDTO result = BankMapper.mapBanksToBanksByCodeDTO("US", banks);
        Assertions.assertEquals("US", result.getCountryISO2());
        Assertions.assertEquals("Country", result.getCountryName());
        Assertions.assertEquals(2, result.getSwiftCodes().size());
    }

    @Test
    void mapBanksToBanksByCodeDTO_withEmptyBankList() {
        List<Bank> banks = Collections.emptyList();
        BanksByCodeDTO result = BankMapper.mapBanksToBanksByCodeDTO("US", banks);
        Assertions.assertEquals("US", result.getCountryISO2());
        Assertions.assertNull(result.getCountryName());
        Assertions.assertTrue(result.getSwiftCodes().isEmpty());
    }

    @Test
    void mapBanksToBanksByCodeDTO_withNullBankList() {
        BanksByCodeDTO result = BankMapper.mapBanksToBanksByCodeDTO("US", null);
        Assertions.assertEquals("US", result.getCountryISO2());
        Assertions.assertNull(result.getCountryName());
        Assertions.assertEquals(Collections.emptyList(), result.getSwiftCodes());
    }

    @Test
    void mapBanksToBanksByCodeDTO_withNullCountryCode() {
        Bank bank = new Bank("123", "US", "Bank Name", "Address", "Country", null, null);
        List<Bank> banks = List.of(bank);
        BanksByCodeDTO result = BankMapper.mapBanksToBanksByCodeDTO(null, banks);
        Assertions.assertNull(result.getCountryISO2());
        Assertions.assertEquals("Country", result.getCountryName());
        Assertions.assertEquals(1, result.getSwiftCodes().size());
    }

    @Test
    void mapBankDTOToBank_withValidData() {
        BankDTO bankDTO = new BankDTO("Address", "Bank Name", "US", "Country", true, "123XXX", null);
        Bank result = BankMapper.mapBankDTOToBank(bankDTO);
        Assertions.assertEquals("123XXX", result.getCode());
        Assertions.assertEquals("US", result.getCountryCode());
        Assertions.assertEquals("BANK NAME", result.getName());
        Assertions.assertEquals("ADDRESS", result.getAddress());
        Assertions.assertEquals("COUNTRY", result.getCountry());
    }

    @Test
    void mapBankDTOToBank_withNullFields() {
        BankDTO bankDTO = new BankDTO(null, null, null, null, true, null, null);
        Bank result = BankMapper.mapBankDTOToBank(bankDTO);
        Assertions.assertNull(result.getCode());
        Assertions.assertNull(result.getCountryCode());
        Assertions.assertNull(result.getName());
        Assertions.assertNull(result.getAddress());
        Assertions.assertNull(result.getCountry());
    }

    @Test
    void mapBankDTOToBank_withEmptyFields() {
        BankDTO bankDTO = new BankDTO("", "", "", "", true, "", null);
        Bank result = BankMapper.mapBankDTOToBank(bankDTO);
        Assertions.assertNull(result.getCode());
        Assertions.assertNull(result.getCountryCode());
        Assertions.assertNull(result.getName());
        Assertions.assertNull(result.getAddress());
        Assertions.assertNull(result.getCountry());
    }

    @Test
    void mapBankDTOToBank_withMixedCaseFields() {
        BankDTO bankDTO = new BankDTO("aDdReSs", "bAnK nAmE", "uS", "cOuNtRy", true, "123xXx", null);
        Bank result = BankMapper.mapBankDTOToBank(bankDTO);
        Assertions.assertEquals("123XXX", result.getCode());
        Assertions.assertEquals("US", result.getCountryCode());
        Assertions.assertEquals("BANK NAME", result.getName());
        Assertions.assertEquals("ADDRESS", result.getAddress());
        Assertions.assertEquals("COUNTRY", result.getCountry());
    }

    @Test
    void mapBankDTOToBank_withWhitespaceFields() {
        BankDTO bankDTO = new BankDTO("  Address  ", "  Bank Name  ", "  US  ", "  Country  ", true, "  123XXX  ", null);
        Bank result = BankMapper.mapBankDTOToBank(bankDTO);
        Assertions.assertEquals("123XXX", result.getCode().trim());
        Assertions.assertEquals("US", result.getCountryCode().trim());
        Assertions.assertEquals("BANK NAME", result.getName().trim());
        Assertions.assertEquals("ADDRESS", result.getAddress().trim());
        Assertions.assertEquals("COUNTRY", result.getCountry().trim());
    }

}
