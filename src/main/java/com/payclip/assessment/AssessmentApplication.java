package com.payclip.assessment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payclip.assessment.entities.Summary;
import com.payclip.assessment.entities.Transaction;
import com.payclip.assessment.services.TransactionService;
import com.payclip.assessment.utilities.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AssessmentApplication implements CommandLineRunner {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication assessment = new SpringApplication(AssessmentApplication.class);
        assessment.setBannerMode(Banner.Mode.OFF);
        assessment.run(args);
    }

    @Override
    public void run(String... args) {
        try {
            if (args.length == 2) {
                switch (args[1].trim().toLowerCase()) {
                    case "list":
                        List<Transaction> transactions = transactionService.getListByUser(Long.parseLong(args[0].trim()));
                        System.out.println(objectMapper.writeValueAsString(transactions));
                        break;
                    case "sum":
                        long userId = Long.parseLong(args[0].trim());
                        double sum = transactionService.sumTotalByUser(userId);
                        System.out.println(objectMapper.writeValueAsString(new Summary(userId, sum)));
                        break;
                    default:
                        if (StringUtils.isNumeric(args[0]) && UUIDUtil.isValid(args[1])) {
                            Transaction transactionByUser = transactionService.getByUserIdAndTransactionId(Long.parseLong(args[0].trim()), args[1].trim());
                            if (transactionByUser == null) {
                                System.out.println("Transaction not found");
                            } else {
                                System.out.println(objectMapper.writeValueAsString(transactionByUser));
                            }
                        } else {
                            System.out.println("Invalid user_id or transaction_id");
                        }
                        break;
                }
            } else if (args.length == 3) {
                if (args[1].trim().equalsIgnoreCase("add")) {
                    Transaction transaction = transactionService.save(Long.parseLong(args[0].trim()), args[2].trim());
                    System.out.println(objectMapper.writeValueAsString(transaction));
                } else {
                    System.out.println("Invalid operation!");
                }
            } else {
                System.out.println("Invalid number of arguments!");
            }
        } catch (Exception ex) {
            System.out.println("Internal Error: " + ex.getMessage());
        }
    }
}
