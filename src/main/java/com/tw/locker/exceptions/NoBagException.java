package com.tw.locker.exceptions;

public class NoBagException extends RuntimeException {
    public NoBagException() {
        super("No bag found in locker.");
    }
}
