package com.action.sniper;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.concurrent.ArrayBlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SingleMessageListener  implements MessageListener {
    private final ArrayBlockingQueue<Message> messages =
            new ArrayBlockingQueue<Message>(1);

    @Override
    public void processMessage(Chat chat, Message message) {
        messages.add(message);
    }
    public void receivesAMessage() throws InterruptedException {
        assertThat("Message", messages.poll(5, SECONDS), is(notNullValue()));
    }

    public void receivesAMessage(Matcher<? super String> messageMatcher) throws InterruptedException {
        final Message message = messages.poll(10, SECONDS);
        assertThat("Message",message, is(notNullValue()));
        assertThat(message.getBody(),messageMatcher);
    }
}
