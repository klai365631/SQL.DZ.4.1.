package com.example.sqldz41.exeption;

public class AvatarNotFoundException extends RuntimeException {

    public final long id;

    public AvatarNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
