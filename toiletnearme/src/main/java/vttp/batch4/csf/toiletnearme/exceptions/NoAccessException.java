package vttp.batch4.csf.toiletnearme.exceptions;

public class NoAccessException extends Exception {

    public NoAccessException() {
    }

    public NoAccessException(String errorMessage) {
        super(errorMessage);
    }
    
}