package com.tw.locker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.tw.locker.Messages.*;

@Component
public class Locker {
    private List<Ticket> tickets = new ArrayList<>();
    private List<Ticket> usedTickets = new ArrayList<>();
    private List<Bag> bags = new ArrayList<>();

    @Value("${locker.capacity}")
    private Integer capacity;

    public SaveBagResponse saveBag(Bag bag) {
        if(this.bags.size() < capacity) {
            putBagInBox(bag);
            Ticket ticket = generateTicket(bag.getId());

            return new SaveBagResponse(true, SAVE_BAG_SUCCESSFULLY, ticket);
        }

        return new SaveBagResponse(false, NO_STORAGE, null);
    }

    private void putBagInBox(Bag bag) {
        bags.add(bag);
    }

    private Ticket generateTicket(Integer bagId) {
        String ticketId = UUID.randomUUID().toString();
        Ticket ticket = new Ticket(ticketId, bagId);
        recordTicket(ticket);
        return ticket;
    }

    private void recordTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public TakeBagResponse takeBag(Ticket ticket) {
        Optional<Ticket> validTicket = this.tickets.stream().filter(x -> x.getId().equals(ticket.getId())).findFirst();
        if(validTicket.isPresent()) {
            Optional<Bag> bag = bags.stream().filter(x -> x.getId().equals(ticket.getBagId())).findFirst();

            tickets.remove(ticket);
            bags.remove(bag.get());
            usedTickets.add(ticket);

            return new TakeBagResponse(true, TAKE_BAG_SUCCESSFULLY, bag.get());
        } else if (this.usedTickets.stream().anyMatch(x -> x.getId().equals(ticket.getId()))) {
            return new TakeBagResponse(false, "Ticket was used.", null);
        } else {
            return new TakeBagResponse(false, FAKE_TICKET, null);
        }
    }
}
