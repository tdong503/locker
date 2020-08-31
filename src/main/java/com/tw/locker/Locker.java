package com.tw.locker;

import com.tw.locker.exceptions.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class Locker {
    private final List<Ticket> tickets = new ArrayList<>();
    private final List<Ticket> usedTickets = new ArrayList<>();
    private final List<Bag> bags = new ArrayList<>();

    @Value("${locker.capacity}")
    private Integer capacity;

    public Ticket saveBag(Bag bag) {
        if (this.bags.size() < capacity) {
            putBagInLocker(bag);

            return generateTicket(bag.getId());
        }

        throw new NoStorageException();
    }

    public Bag takeBag(Ticket ticket) {
        if (ticket == null) {
            throw new UnrecognizedTicketException();
        }

        if (this.tickets.stream().anyMatch(x -> x.getId().equals(ticket.getId()))) {
            Bag bag = takeBagOutFromLocker(ticket);
            archiveTicket(ticket);

            return bag;
        } else if (this.usedTickets.stream().anyMatch(x -> x.getId().equals(ticket.getId()))) {
            throw new UsedTicketException();
        } else {
            throw new FakeTicketException();
        }
    }

    private void putBagInLocker(Bag bag) {
        bags.add(bag);
    }

    private Bag takeBagOutFromLocker(Ticket ticket) {
        Bag bag = bags.stream().filter(x -> x.getId().equals(ticket.getBagId())).findFirst().orElseThrow(NoBagException::new);
        bags.remove(bag);
        return bag;
    }

    private void recordTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    private Ticket generateTicket(Integer bagId) {
        String ticketId = UUID.randomUUID().toString();
        Ticket ticket = new Ticket(ticketId, bagId);
        recordTicket(ticket);
        return ticket;
    }

    private void archiveTicket(Ticket ticket) {
        tickets.remove(ticket);
        usedTickets.add(ticket);
    }
}
