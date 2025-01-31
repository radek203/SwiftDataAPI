package me.radek203.bankdata.entities.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BankDTO {

    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
    private String swiftCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BankReducedDTO> branches;

    @JsonProperty("isHeadquarter")
    public boolean getIsHeadquarter() {
        return isHeadquarter;
    }

}
