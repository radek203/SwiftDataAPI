package me.radek203.bankdata.controllers;

import lombok.AllArgsConstructor;
import me.radek203.bankdata.entities.dto.BankDTO;
import me.radek203.bankdata.entities.dto.BanksByCodeDTO;
import me.radek203.bankdata.entities.dto.MessageDTO;
import me.radek203.bankdata.mappers.BankMapper;
import me.radek203.bankdata.services.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/swift-codes")
public class BankController {

    private BankService bankService;

    /**
     * Get bank by swift code
     * @return ResponseEntity with BankDTO object
     */
    @GetMapping("/{swift-code}")
    public ResponseEntity<BankDTO> getBankByCode(@PathVariable("swift-code") String code) {
        return ResponseEntity.ok(BankMapper.mapBankToBankDTO(bankService.getBankByCode(code)));
    }

    /**
     * Get banks by country code
     * @return ResponseEntity with BanksByCodeDTO object
     */
    @GetMapping("/country/{countryISO2code}")
    public ResponseEntity<BanksByCodeDTO> getBanksByCountry(@PathVariable("countryISO2code") String countryCode) {
        return ResponseEntity.ok(BankMapper.mapBanksToBanksByCodeDTO(countryCode, bankService.getBanksByCountryCode(countryCode)));
    }

    /**
     * Add bank
     * @return ResponseEntity with BankDTO object
     */
    @PostMapping
    public ResponseEntity<MessageDTO> addBank(@Valid @RequestBody BankDTO bankDTO) {
        bankService.addBank(bankDTO);
        return ResponseEntity.ok(new MessageDTO("bank/created"));
    }

    /**
     * Delete bank by swift code
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{swift-code}")
    public ResponseEntity<MessageDTO> deleteBankByCode(@PathVariable("swift-code") String code) {
        bankService.deleteBank(code);
        return ResponseEntity.ok(new MessageDTO("bank/deleted"));
    }

}
