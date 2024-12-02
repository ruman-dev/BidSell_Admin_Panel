package com.rumanweb.bidsell_ap.models;

import java.util.Date;

public class BidPlaced {
    private String auctionTitle, userEmail;
    private double bidAmount;
    private Date biddingTime;
    private long listingNo;

    public BidPlaced(String auctionTitle, String userEmail, long listingNo,
                     double bidAmount, Date biddingTime) {
        this.auctionTitle = auctionTitle;
        this.userEmail = userEmail;
        this.listingNo = listingNo;
        this.bidAmount = bidAmount;
        this.biddingTime = biddingTime;
    }

    public String getAuctionTitle() {
        return auctionTitle;
    }

    public void setAuctionTitle(String auctionTitle) {
        this.auctionTitle = auctionTitle;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getListingNo() {
        return listingNo;
    }

    public void setListingNo(long listingNo) {
        this.listingNo = listingNo;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Date getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(Date biddingTime) {
        this.biddingTime = biddingTime;
    }
}