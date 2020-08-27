package com.tw.locker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Locker {
    private List<Ticket> tickets = new ArrayList<>();

    public SaveBagResponse saveBag(Bag bag) {
        Ticket ticket = generateTicket(bag.getId());
        tickets.add(ticket);
        SaveBagResponse saveBagResponse = new SaveBagResponse(true, "Save bag successfully.", ticket);
        return saveBagResponse;
    }

    private Ticket generateTicket(Integer bagId) {
        String ticketId = UUID.randomUUID().toString();
        return new Ticket(ticketId, bagId);
    }
}
