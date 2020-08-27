package com.tw.locker;

public class Locker {
    public SaveBagResponse saveBag(Bag bag) {
        SaveBagResponse response = new SaveBagResponse(true, "Save bag successfully.", new Ticket(1, bag.getId(), false));
        return response;
    }
}
