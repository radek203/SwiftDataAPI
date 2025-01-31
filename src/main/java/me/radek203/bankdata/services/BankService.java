package me.radek203.bankdata.services;

import me.radek203.bankdata.entities.Bank;

import java.util.List;

public interface BankService {

    Bank getBankByCode(String code);

    List<Bank> getBanksByCountryCode(String countryCode);

}
