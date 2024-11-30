package com.rumanweb.bidsell_ap.models;

public class Auctions {
    private String auctionName;
    private double startingPrice;
    private String startTime;
    private String endTime;

    public Auctions(String auctionName, double startingPrice, String startTime, String endTime) {
        this.auctionName = auctionName;
        this.startingPrice = startingPrice;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters
    public String getAuctionName() { return auctionName; }
    public double getStartingPrice() { return startingPrice; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
}