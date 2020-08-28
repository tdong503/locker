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

    private Integer capacity = 1;

    public SaveBagResponse saveBag(Bag bag) {
        SaveBagResponse saveBagResponse;
        if(this.bags.size() < capacity) {
            addBag(bag);
            Ticket ticket = generateTicket(bag.getId());
            addTicket(ticket);

            saveBagResponse = new SaveBagResponse(true, SUCCESS, ticket);
        } else {
            saveBagResponse = new SaveBagResponse(false, NOSTORAGE, null);
        }

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
