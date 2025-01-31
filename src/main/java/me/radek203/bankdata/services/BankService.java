package me.radek203.bankdata.services;

import me.radek203.bankdata.entities.Bank;
import me.radek203.bankdata.entities.dto.BankDTO;

import java.util.List;

public interface BankService {

    Bank getBankByCode(String code);

    List<Bank> getBanksByCountryCode(String countryCode);

    Bank addBank(BankDTO bankDTO);

    void deleteBank(String code);

}
