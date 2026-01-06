package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Transaction;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.TransactionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.TransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction create(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Optional<Transaction> getById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction update(Long id, Transaction transaction) {
        return transactionRepository.findById(id).map(existing -> {
            BeanUtils.copyProperties(transaction, existing, "id");
            return transactionRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }
}

