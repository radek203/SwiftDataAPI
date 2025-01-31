package me.radek203.bankdata.controllers;

import lombok.AllArgsConstructor;
import me.radek203.bankdata.entities.dto.BankDTO;
import me.radek203.bankdata.entities.dto.BanksByCodeDTO;
import me.radek203.bankdata.mappers.BankMapper;
import me.radek203.bankdata.services.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/swift-codes")
public class BankController {

    private BankService bankService;

    @GetMapping("/{swift-code}")
    public ResponseEntity<BankDTO> getBankByCode(@PathVariable("swift-code") String code) {
        return ResponseEntity.ok(BankMapper.mapBankToBankDTO(bankService.getBankByCode(code)));
    }

    @GetMapping("/country/{countryISO2code}")
    public ResponseEntity<BanksByCodeDTO> getBanksByCountry(@PathVariable("countryISO2code") String countryCode) {
        return ResponseEntity.ok(BankMapper.mapBanksToBanksByCodeDTO(countryCode, bankService.getBanksByCountryCode(countryCode)));
    }

}
