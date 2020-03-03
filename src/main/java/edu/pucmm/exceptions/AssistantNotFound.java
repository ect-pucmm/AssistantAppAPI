package edu.pucmm.exceptions;


public class AssistantNotFound extends RuntimeException {

    public AssistantNotFound() {
        super();
    }

    public AssistantNotFound(String message) {
        super(message);
    }

    public AssistantNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
