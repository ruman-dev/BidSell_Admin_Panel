package com.rumanweb.bidsell_ap.models;

import java.util.Date;

public class AuctionRequest {
//    private String documentId;
    private String auctionTitle;
    private double startingPriceReq;
    private Date openingDate;
    private Date expectedEndDate;
    private String status;
    private String description;
    private String highlights;
    private String imageLink;
    private long quantity;
    private String userEmail;

    public AuctionRequest(String auctionTitle, double startingPriceReq, Date openingDate, Date expectedEndDate, String status,
                          String description, String highlights, String imageLink, long quantity, String userEmail) {
        this.auctionTitle = auctionTitle;
        this.startingPriceReq = startingPriceReq;
        this.openingDate = openingDate;
        this.expectedEndDate = expectedEndDate;
        this.status = status;
        this.description = description;
        this.highlights = highlights;
        this.imageLink = imageLink;
        this.quantity = quantity;
        this.userEmail = userEmail;
    }

    public String getAuctionTitle() {
        return auctionTitle;
    }

    public void setAuctionTitle(String auctionTitle) {
        this.auctionTitle = auctionTitle;
    }

    public double getStartingPriceReq() {
        return startingPriceReq;
    }

    public void setStartingPriceReq(double startingPriceReq) {
        this.startingPriceReq = startingPriceReq;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public Date getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(Date expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHighlights() {
        return highlights;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}