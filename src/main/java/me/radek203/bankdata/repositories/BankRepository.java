package me.radek203.bankdata.repositories;

import me.radek203.bankdata.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, String> {

    /**
     * Find bank headquarter by code prefix (first 8 characters) and ends with 'XXX'
     * @return List of banks
     */
    @Query("SELECT b FROM Bank b WHERE SUBSTRING(b.code, 1, 8) = :prefix AND b.code LIKE '%XXX'")
    List<Bank> findHeadquarterByCodePrefix(@Param("prefix") String prefix);

    /**
     * Find bank branches by code prefix (first 8 characters) and not ends with 'XXX'
     * @return List of banks
     */
    @Query("SELECT b FROM Bank b WHERE SUBSTRING(b.code, 1, 8) = :prefix AND b.code NOT LIKE '%XXX'")
    List<Bank> findBranchesByCodePrefix(@Param("prefix") String prefix);

    /**
     * Find all banks by country code
     * @return List of banks
     */
    List<Bank> findAllByCountryCode(String countryCode);
}
