package net.serg.exchangerate.requester;

public class RequesterException extends RuntimeException {
    public RequesterException(Throwable cause) {
        super(cause);
    }

    public RequesterException() {
    }
}
