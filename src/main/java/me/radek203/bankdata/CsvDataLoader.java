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
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvDataLoader {

    private final BankRepository bankRepository;

    @PostConstruct
    public void loadCsvData() {
        String csvFilePath = "swift_codes.csv";

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath, StandardCharsets.UTF_8))) {
            String[] nextRecord;
            csvReader.readNext();

            List<Bank> branches = new ArrayList<>();

            while ((nextRecord = csvReader.readNext()) != null) {
                final Bank bank = new Bank(
                        Bank.normalizeData(nextRecord[1]),
                        Bank.normalizeData(nextRecord[0]),
                        Bank.normalizeData(nextRecord[3]),
                        Bank.normalizeData(nextRecord[4]),
                        Bank.normalizeData(nextRecord[6]),
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

            clearCsvFile(csvFilePath);

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    private void clearCsvFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.print("");
            System.out.println("CSV file cleared successfully.");
        } catch (IOException e) {
            System.err.println("Failed to clear CSV file: " + e.getMessage());
        }
    }

}
