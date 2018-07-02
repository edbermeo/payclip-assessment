package com.payclip.assessment.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payclip.assessment.entities.Transaction;
import com.payclip.assessment.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static java.lang.System.exit;

@Service
public class TransactionService implements TransactionRepository {

    @Autowired
    protected ObjectMapper objectMapper;

    private static final String FILENAME = "data.json";
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private String filePath;

    public TransactionService() {
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();
        filePath = path.concat(FILE_SEPARATOR).concat(TransactionService.FILENAME);
    }

    @Override
    public Transaction save(Long userId, String attributes) {
        Transaction transaction = null;
        try {
            transaction = objectMapper.readValue(attributes, Transaction.class);
            transaction.setTransactionId(String.valueOf(UUID.randomUUID()));
            File fout = new File(filePath);
            BufferedWriter bw = new BufferedWriter(new FileWriter(fout, true));
            bw.write(objectMapper.writeValueAsString(transaction));
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    @Override
    public Transaction getByUserIdAndTransactionId(Long userId, String transactionId) {
        Transaction transaction = null;
        File file = new File(filePath);
        String userNeedle = String.format("\"user_id\":%d", userId);
        String transactionNeedle = String.format("\"transaction_id\":\"%s\"", transactionId);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                if (row.contains(transactionNeedle) && row.contains(userNeedle)) {
                    transaction = objectMapper.readValue(row, Transaction.class);
                    break;
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Storage file not found, run add transaction action to create one.");
            exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    @Override
    public List<Transaction> getListByUser(Long userId) {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(filePath);
        String needle = String.format("\"user_id\":%s", String.valueOf(userId));
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                if (row.contains(needle)) {
                    Transaction transaction = objectMapper.readValue(row, Transaction.class);
                    transactions.add(transaction);
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Storage file not found, run add transaction action to create one.");
            exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        transactions.sort((t1, t2) -> {
            if (t1.getDate() == null || t2.getDate() == null)
                return 0;
            return t2.getDate().compareTo(t1.getDate());
        });

        return transactions;
    }

    @Override
    public double sumTotalByUser(Long userId) {
        List<Transaction> transactions = getListByUser(userId);
        return transactions.stream().filter(transaction -> transaction.getAmount() > 0).mapToDouble(Transaction::getAmount).sum();
    }
}
