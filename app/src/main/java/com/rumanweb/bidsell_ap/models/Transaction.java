package com.rumanweb.bidsell_ap.models;

import java.util.Date;

public class Transaction {
    private final String fullName;
    private final String userName;
    private final String email;
    private final double amount;
    private final String paymentMethod;
    private final String transactionId;
    private final Date transactionTime;

    public Transaction(String fullName, String userName, String email, double amount,
                       String paymentMethod, String transactionId, Date transactionTime) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.transactionTime = transactionTime;
    }

    // Getters
    public String getFullName() { return fullName; }
    public String getUserName() { return userName; }
    public String getEmail() { return email; }
    public double getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getTransactionId() { return transactionId; }
    public Date getTransactionTime() { return transactionTime; }
}