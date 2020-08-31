package com.tw.locker.exceptions;

import static com.tw.locker.Messages.FAKE_TICKET;

public class FakeTicketException extends RuntimeException {
    public FakeTicketException() {
        super(FAKE_TICKET);
    }
}
