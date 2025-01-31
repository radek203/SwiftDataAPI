package me.radek203.bankdata.services.impl;

import lombok.AllArgsConstructor;
import me.radek203.bankdata.entities.Bank;
import me.radek203.bankdata.exception.ResourceNotFoundException;
import me.radek203.bankdata.repositories.BankRepository;
import me.radek203.bankdata.services.BankService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BankServiceImpl implements BankService {

    private BankRepository bankRepository;

    @Override
    public Bank getBankByCode(String code) {
        Optional<Bank> bank = bankRepository.findById(code);
        if (bank.isEmpty()) {
            throw new ResourceNotFoundException("bank/not-found", code);
        }
        return bank.get();
    }

    @Override
    public List<Bank> getBanksByCountryCode(String countryCode) {
        List<Bank> banks = bankRepository.findAllByCountryCode(countryCode);
        if (banks.isEmpty()) {
            throw new ResourceNotFoundException("bank/not-found-country", countryCode);
        }
        return banks;
    }

}
