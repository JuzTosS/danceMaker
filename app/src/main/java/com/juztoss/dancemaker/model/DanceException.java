package com.juztoss.dancemaker.model;

/**
 * Created by Kirill on 2/28/2016.
 */
public class DanceException extends Exception {
    public DanceException() {
        super();
    }

    public DanceException(String detailMessage) {
        super(detailMessage);
    }

    public DanceException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DanceException(Throwable throwable) {
        super(throwable);
    }
}
