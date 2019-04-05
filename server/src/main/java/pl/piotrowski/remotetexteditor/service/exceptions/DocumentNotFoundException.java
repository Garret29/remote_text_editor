package pl.piotrowski.remotetexteditor.service.exceptions;

public class DocumentNotFoundException extends Exception {
    public DocumentNotFoundException(String message) {
        super(message);
    }
}
