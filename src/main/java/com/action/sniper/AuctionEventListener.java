package com.action.sniper;

public interface AuctionEventListener {

    void auctionClosed();

    void currentPrice(Integer price, Integer increment);
}
