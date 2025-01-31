package me.radek203.bankdata;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import me.radek203.bankdata.entities.Bank;
import me.radek203.bankdata.repositories.BankRepository;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvDataLoader {

    private final BankRepository bankRepository;

    private static String normalizeData(String data) {
        data = data.trim();
        return data.isEmpty() ? null : data.toUpperCase();
    }

    @PostConstruct
    public void loadCsvData() {
        String csvFilePath = "swift_codes.csv";

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath, StandardCharsets.UTF_8))) {
            String[] nextRecord;
            csvReader.readNext();

            List<Bank> branches = new ArrayList<>();

            while ((nextRecord = csvReader.readNext()) != null) {
                final Bank bank = new Bank(
                        normalizeData(nextRecord[1]),
                        normalizeData(nextRecord[0]),
                        normalizeData(nextRecord[2]),
                        normalizeData(nextRecord[3]),
                        normalizeData(nextRecord[4]),
                        normalizeData(nextRecord[5]),
                        normalizeData(nextRecord[6]),
                        normalizeData(nextRecord[7]),
                        null,
                        null
                );

                bankRepository.save(bank);
                if (!bank.getCode().endsWith("XXX")) {
                    branches.add(bank);
                }
            }

            for (Bank branch : branches) {
                List<Bank> headquarter = bankRepository.findHeadquarterByCodePrefix(branch.getCode().substring(0, 8));
                if (!headquarter.isEmpty()) {
                    branch.setHeadquarter(headquarter.getFirst());
                    bankRepository.save(branch);
                }
            }

            System.out.println("CSV Data Successfully Loaded!");

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

}
