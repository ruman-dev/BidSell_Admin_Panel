package com.rumanweb.bidsell_ap.models;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Auctions {
    private String title, description, highlights, imgUrl, openDate, closeDate;

    private long bidCount, listingNo, quantity;

    private double startingPrice;

    public Auctions(String title, String description, String highlights, String imgUrl, long bidCount, long listingNo, long quantity, double startingPrice, String openDate, String closeDate) {
        this.title = title;
        this.description = description;
        this.highlights = highlights;
        this.imgUrl = imgUrl;
        this.bidCount = bidCount;
        this.listingNo = listingNo;
        this.quantity = quantity;
        this.startingPrice = startingPrice;
        this.openDate = openDate;
        this.closeDate = closeDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getBidCount() {
        return bidCount;
    }

    public void setBidCount(int bidCount) {
        this.bidCount = bidCount;
    }

    public long getListingNo() {
        return listingNo;
    }

    public void setListingNo(int listingNo) {
        this.listingNo = listingNo;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String  closeDate) {
        this.closeDate = closeDate;
    }
}