package com.rumanweb.bidsell_ap.models;

public class Transaction {
    private String fullName;
    private String userName;
    private String email;
    private double amount;
    private String paymentMethod;
    private String transactionId;
    private String transactionTime;

    public Transaction(String fullName, String userName, String email, double amount,
                       String paymentMethod, String transactionId, String transactionTime) {
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
    public String getTransactionTime() { return transactionTime; }
}