package me.radek203.bankdata.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bank {

    @Id
    private String code;
    private String countryCode;
    private String codeType;
    private String name;
    private String address;
    private String town;
    private String country;
    private String timezone;

    @ManyToOne
    @JoinColumn(name = "headquarter", referencedColumnName = "code")
    private Bank headquarter;

    @OneToMany(mappedBy = "headquarter")
    private List<Bank> branches;

}
