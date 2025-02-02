package me.radek203.bankdata.mappers;

import me.radek203.bankdata.entities.Bank;
import me.radek203.bankdata.entities.dto.BankDTO;
import me.radek203.bankdata.entities.dto.BankReducedDTO;
import me.radek203.bankdata.entities.dto.BanksByCodeDTO;

import java.util.Collections;
import java.util.List;

public class BankMapper {

    public static BankDTO mapBankToBankDTO(Bank bank) {
        boolean isHeadquarter = bank.getCode().endsWith("XXX");
        return new BankDTO(
                bank.getAddress(),
                bank.getName(),
                bank.getCountryCode(),
                bank.getCountry(),
                isHeadquarter,
                bank.getCode(),
                isHeadquarter ? (bank.getBranches() != null ? bank.getBranches().stream().map(BankMapper::mapBankToBankReducedDTO).toList() : Collections.emptyList()) : null
        );
    }

    public static BankReducedDTO mapBankToBankReducedDTO(Bank bank) {
        boolean isHeadquarter = bank.getCode().endsWith("XXX");
        return new BankReducedDTO(
                bank.getAddress(),
                bank.getName(),
                bank.getCountryCode(),
                isHeadquarter,
                bank.getCode()
        );
    }

    public static BanksByCodeDTO mapBanksToBanksByCodeDTO(String countryCode, List<Bank> banks) {
        if (banks == null || banks.isEmpty()) {
            return new BanksByCodeDTO(countryCode, null, Collections.emptyList());
        }
        return new BanksByCodeDTO(countryCode, banks.getFirst().getCountry(), banks.stream().map(BankMapper::mapBankToBankReducedDTO).toList());
    }

    public static Bank mapBankDTOToBank(BankDTO bankDTO) {
        return new Bank(
                Bank.normalizeData(bankDTO.getSwiftCode()),
                Bank.normalizeData(bankDTO.getCountryISO2()),
                Bank.normalizeData(bankDTO.getBankName()),
                Bank.normalizeData(bankDTO.getAddress()),
                Bank.normalizeData(bankDTO.getCountryName()),
                null,
                null
        );
    }

}
