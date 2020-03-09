package edu.pucmm.exceptions;


public class AssistantExists extends RuntimeException {

    public AssistantExists() {
        super();
    }

    public AssistantExists(String message) {
        super(message);
    }

    public AssistantExists(String message, Throwable cause) {
        super(message, cause);
    }
}
