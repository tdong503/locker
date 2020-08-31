package com.tw.locker.exceptions;

import static com.tw.locker.Messages.UNRECOGNIZED_TICKET;

public class UnrecognizedTicketException extends RuntimeException {
    public UnrecognizedTicketException() {
        super(UNRECOGNIZED_TICKET);
    }
}
