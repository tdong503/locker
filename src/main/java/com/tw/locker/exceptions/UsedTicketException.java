package com.tw.locker.exceptions;

import static com.tw.locker.Messages.USED_TICKET;

public class UsedTicketException extends RuntimeException {
    public UsedTicketException() {
        super(USED_TICKET);
    }
}
