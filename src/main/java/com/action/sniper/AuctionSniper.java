package com.action.sniper;

public class AuctionSniper implements  AuctionEventListener{
    private final SniperListener sniperListener;

    public AuctionSniper(SniperListener sniperListener) {
        this.sniperListener = sniperListener;
    }

    @Override
    public void auctionClosed() {
        sniperListener.sniperLost();
    }

    @Override
    public void currentPrice(Integer price, Integer increment) {

    }
}
