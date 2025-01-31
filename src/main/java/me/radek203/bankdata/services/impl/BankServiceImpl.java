package me.radek203.bankdata.services.impl;

import lombok.AllArgsConstructor;
import me.radek203.bankdata.entities.Bank;
import me.radek203.bankdata.entities.dto.BankDTO;
import me.radek203.bankdata.exception.ResourceNotFoundException;
import me.radek203.bankdata.mappers.BankMapper;
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

    @Override
    public Bank addBank(BankDTO bankDTO) {
        Bank bank = BankMapper.mapBankDTOToBank(bankDTO);

        Optional<Bank> existed = bankRepository.findById(bank.getCode());
        if (existed.isPresent()) {
            throw new ResourceNotFoundException("bank/existed", bank.getCode());
        }

        Bank saved = bankRepository.save(bank);
        if (saved.getCode().endsWith("XXX")) {
            List<Bank> branches = bankRepository.findBranchesByCodePrefix(saved.getCode().substring(0, 8));
            branches.forEach(branch -> {
                branch.setHeadquarter(saved);
                bankRepository.save(branch);
            });
        } else {
            List<Bank> headquarter = bankRepository.findHeadquarterByCodePrefix(saved.getCode().substring(0, 8));
            if (!headquarter.isEmpty()) {
                saved.setHeadquarter(headquarter.getFirst());
                bankRepository.save(saved);
            }
        }

        return saved;
    }

    @Override
    public void deleteBank(String code) {
        Bank bank = getBankByCode(code);
        bankRepository.delete(bank);
    }

}
