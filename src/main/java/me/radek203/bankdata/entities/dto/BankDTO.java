package me.radek203.bankdata.entities.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BankDTO {

    private String address;
    @NotBlank(message = "error/bankName-blank")
    private String bankName;
    @NotBlank(message = "error/countryISO2-blank")
    @Size(min = 2, max = 2, message = "error/countryISO2-size")
    private String countryISO2;
    @NotBlank(message = "error/countryName-blank")
    private String countryName;
    @NotNull(message = "error/isHeadquarter-null")
    private Boolean isHeadquarter;
    @NotBlank(message = "error/swiftCode-blank")
    @Size(min = 11, max = 11, message = "error/swiftCode-size")
    private String swiftCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BankReducedDTO> branches;

    @JsonProperty("isHeadquarter")
    public boolean getIsHeadquarter() {
        return isHeadquarter;
    }

}
