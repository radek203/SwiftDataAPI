package me.radek203.bankdata.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Column(length = 11, nullable = false)
    @Size(min = 11, max = 11)
    private String code;
    @Column(length = 2, nullable = false)
    @Size(min = 2, max = 2)
    private String countryCode;
    @Column(nullable = false)
    private String name;
    private String address;
    @Column(nullable = false)
    private String country;

    @ManyToOne
    @JoinColumn(name = "headquarter", referencedColumnName = "code")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Bank headquarter;

    @OneToMany(mappedBy = "headquarter", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Bank> branches;

    public static String normalizeData(String data) {
        if (data == null) {
            return null;
        }
        data = data.trim();
        return data.isEmpty() ? null : data.toUpperCase();
    }

}
