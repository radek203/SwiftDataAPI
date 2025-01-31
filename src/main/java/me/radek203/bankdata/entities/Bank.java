package me.radek203.bankdata.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank", indexes = @Index(name = "idx_country_code", columnList = "countryCode"))
public class Bank {

    @Id
    private String code;
    private String countryCode;
    private String name;
    private String address;
    private String country;

    @ManyToOne
    @JoinColumn(name = "headquarter", referencedColumnName = "code")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Bank headquarter;

    @OneToMany(mappedBy = "headquarter", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Bank> branches;

    public static String normalizeData(String data) {
        data = data.trim();
        return data.isEmpty() ? null : data.toUpperCase();
    }

}
