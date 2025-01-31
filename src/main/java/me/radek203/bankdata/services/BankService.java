package me.radek203.bankdata.services;

import me.radek203.bankdata.entities.Bank;
import me.radek203.bankdata.entities.dto.BankDTO;

import java.util.List;

/**
 * Service for managing banks data.
 */
public interface BankService {

    /**
     * Get bank by code.
     *
     * @param code swift code
     * @return bank
     */
    Bank getBankByCode(String code);

    /**
     * Get banks by country code.
     *
     * @param countryCode country ISO2 code
     * @return list of banks
     */
    List<Bank> getBanksByCountryCode(String countryCode);

    /**
     * Add new bank.
     *
     * @param bankDTO bank data
     * @return added bank
     */
    Bank addBank(BankDTO bankDTO);

    /**
     * Delete bank by code.
     *
     * @param code swift code
     */
    void deleteBank(String code);

}
