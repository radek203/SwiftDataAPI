package me.radek203.bankdata.mappers;

import me.radek203.bankdata.entities.Bank;
import me.radek203.bankdata.entities.dto.BankDTO;
import me.radek203.bankdata.entities.dto.BankReducedDTO;
import me.radek203.bankdata.entities.dto.BanksByCodeDTO;

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
                isHeadquarter ? bank.getBranches().stream().map(BankMapper::mapBankToBankReducedDTO).toList() : null
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
        return new BanksByCodeDTO(countryCode, banks.getFirst().getCountry(), banks.stream().map(BankMapper::mapBankToBankReducedDTO).toList());
    }

}
