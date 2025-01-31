package me.radek203.bankdata.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BankReducedDTO {

    private String address;
    private String bankName;
    private String countryISO2;
    private boolean isHeadquarter;
    private String swiftCode;

    @JsonProperty("isHeadquarter")
    public boolean getIsHeadquarter() {
        return isHeadquarter;
    }

}
