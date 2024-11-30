package com.rumanweb.bidsell_ap.models;

public class AuctionRequest {
    private String documentId;
    private String requestName;
    private double requestedPrice;
    private String requestDate;
    private String expectedEndDate;
    private String status;
    private String description;
    private String highlights;
    private String imageLink;
    private String quantity;
    private String userEmail;

    public AuctionRequest(String documentId, String requestName, double requestedPrice, String requestDate, String expectedEndDate, String status,
                          String description, String highlights, String imageLink, String quantity, String userEmail) {
        this.documentId = documentId;
        this.requestName = requestName;
        this.requestedPrice = requestedPrice;
        this.requestDate = requestDate;
        this.expectedEndDate = expectedEndDate;
        this.status = status;
        this.description = description;
        this.highlights = highlights;
        this.imageLink = imageLink;
        this.quantity = quantity;
        this.userEmail = userEmail;
    }

    // Getters
    public String getDocumentId() { return documentId; }
    public String getRequestName() { return requestName; }
    public double getRequestedPrice() { return requestedPrice; }
    public String getRequestDate() { return requestDate; }
    public String getExpectedEndDate() { return expectedEndDate; }
    public String getStatus() { return status; }
    public String getDescription() { return description; }
    public String getHighlights() { return highlights; }
    public String getImageLink() { return imageLink; }
    public String getQuantity() { return quantity; }
    public String getUserEmail() { return userEmail; }
}