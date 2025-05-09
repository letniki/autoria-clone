package org.example.autoriaclone.repository;

import org.example.autoriaclone.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Currency findByCcy(String ccy);
}
