package com.action.sniper.integrationtest;

import com.action.sniper.ApplicationRunner;
import com.action.sniper.FakeAuctionServer;
import org.jivesoftware.smack.XMPPException;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;

public class AuctionSniperEndToEndTest {
    private final FakeAuctionServer auction;
    private final ApplicationRunner application;

    public AuctionSniperEndToEndTest()  {
        auction = new FakeAuctionServer("item-54321");
        application = new ApplicationRunner();
    }

    @Test
    public void sniperJoinsAuctionUntilAuctionCloses() throws InterruptedException, XMPPException, IOException {
        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }

    @Test
    public void sniperMakesAHigherBidButLoses() throws XMPPException, InterruptedException {
        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
        auction.reportPrice(1000,98,"other bidder");
        application.hasShownSniperIsBidding();

        auction.hasReceivedBid(1098,ApplicationRunner.SNIPER_XMPP_ID);
        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }

    @After
    public void stopAuction() {
        auction.stop();
    }
    @After public void stopApplication() {
        application.stop();
    }
}
