package com.tw.locker.exceptions;

import static com.tw.locker.Messages.NO_STORAGE;

public class NoStorageException extends RuntimeException {
    public NoStorageException() {
        super(NO_STORAGE);
    }
}
