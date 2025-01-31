package me.radek203.bankdata.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BanksByCodeDTO {

    private String countryISO2;
    private String countryName;
    private List<BankReducedDTO> swiftCodes;

}
