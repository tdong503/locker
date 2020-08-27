package com.tw.locker;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.tw.locker.Messages.SUCCESS;

@Component
public class Locker {
    private List<Ticket> tickets = new ArrayList<>();
    private List<Bag> bags = new ArrayList<>();

    public SaveBagResponse saveBag(Bag bag) {
        addBag(bag);
        Ticket ticket = generateTicket(bag.getId());
        addTicket(ticket);

        SaveBagResponse saveBagResponse = new SaveBagResponse(true, SUCCESS, ticket);
        return saveBagResponse;
    }

    private void addBag(Bag bag) {
        bags.add(bag);
    }

    private void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    private Ticket generateTicket(Integer bagId) {
        String ticketId = UUID.randomUUID().toString();
        return new Ticket(ticketId, bagId);
    }
}
