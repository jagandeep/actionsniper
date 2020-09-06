package com.action.sniper.unittest;

import com.action.sniper.Auction;
import com.action.sniper.AuctionSniper;
import com.action.sniper.SniperListener;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class ActionSniperTest {
    private final Mockery context = new Mockery();
    private final Auction auction = context.mock(Auction.class);
    private final SniperListener sniperListener = context.mock(SniperListener.class);
    private final AuctionSniper sniper = new AuctionSniper(auction,sniperListener);

    @Test
    public void reportsLostWhenAuctionCloses() {
        context.checking(new Expectations() {{
            one(sniperListener).sniperLost();
        }});
        sniper.auctionClosed();
    }
    @Test
    public void bidHigherAndReportsBiddingWhenNewPriceArrives() {
        final int price = 1001;
        final int increment = 25;
        context.checking( new Expectations() {{
            one(auction).bid(price+increment);
            atLeast(1).of(sniperListener).sniperBidding();
        }});
        sniper.currentPrice(price,increment);
    }
}
