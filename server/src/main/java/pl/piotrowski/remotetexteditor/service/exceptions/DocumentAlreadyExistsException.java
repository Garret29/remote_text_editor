package pl.piotrowski.remotetexteditor.service.exceptions;

public class DocumentAlreadyExistsException extends Exception {
    public DocumentAlreadyExistsException(String message) {
        super(message);
    }
}
