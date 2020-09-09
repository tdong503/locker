package com.tw.locker;

import com.tw.locker.exceptions.FakeTicketException;
import com.tw.locker.exceptions.NoStorageException;
import com.tw.locker.exceptions.UnrecognizedTicketException;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class BagOperation {
    public static Ticket saveBag(Bag bag, Supplier<Optional<Locker>> getAvailableLocker) {
        Optional<Locker> availableLocker = getAvailableLocker.get();
        if(availableLocker.isPresent()) {
            return availableLocker.get().saveBag(bag);
        }

        throw new NoStorageException();
    }

    public static Bag takeBag(Ticket ticket, Function<Ticket, Optional<Locker>> getAvailableLockerByTicket) {
        if (ticket == null) {
            throw new UnrecognizedTicketException();
        }

        Optional<Locker> availableLocker = getAvailableLockerByTicket.apply(ticket);
        if(availableLocker.isPresent()) {
            return availableLocker.get().takeBag(ticket);
        }

        throw new FakeTicketException();
    }
}
