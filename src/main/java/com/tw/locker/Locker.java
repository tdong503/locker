package com.tw.locker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.tw.locker.Messages.NOSTORAGE;
import static com.tw.locker.Messages.SUCCESS;

@Component
public class Locker {
    private List<Ticket> tickets = new ArrayList<>();
    private List<Bag> bags = new ArrayList<>();

    @Value("${locker.capacity}")
    private Integer capacity;

    public SaveBagResponse saveBag(Bag bag) {
        if(this.bags.size() < capacity) {
            putBagInBox(bag);
            Ticket ticket = generateTicket(bag.getId());

            return new SaveBagResponse(true, SUCCESS, ticket);
        }

        return new SaveBagResponse(false, NOSTORAGE, null);
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
}
