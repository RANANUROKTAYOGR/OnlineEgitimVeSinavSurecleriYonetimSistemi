package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Transaction create(Transaction transaction);
    Optional<Transaction> getById(Long id);
    List<Transaction> getAll();
    Transaction update(Long id, Transaction transaction);
    void delete(Long id);
}

