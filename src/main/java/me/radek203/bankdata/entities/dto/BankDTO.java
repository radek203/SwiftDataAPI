package me.radek203.bankdata.entities.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BankDTO {

    @NotNull(message = "address is required")
    private String address;
    @NotNull(message = "bankName is required")
    private String bankName;
    @NotNull(message = "countryISO2 is required")
    private String countryISO2;
    @NotNull(message = "countryName is required")
    private String countryName;
    @NotNull(message = "isHeadquarter is required")
    private boolean isHeadquarter;
    @NotNull(message = "swiftCode is required")
    private String swiftCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BankReducedDTO> branches;

    @JsonProperty("isHeadquarter")
    public boolean getIsHeadquarter() {
        return isHeadquarter;
    }

}
