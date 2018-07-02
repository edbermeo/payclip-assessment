package com.payclip.assessment.repositories;

import com.payclip.assessment.entities.Transaction;

import java.text.ParseException;
import java.util.List;

public interface TransactionRepository {
    Transaction save(Long userId, String attributes);

    Transaction getByUserIdAndTransactionId(Long userId, String transactionId) throws ParseException;

    List<Transaction> getListByUser(Long userId) throws ParseException;

    double sumTotalByUser(Long userId);
}
