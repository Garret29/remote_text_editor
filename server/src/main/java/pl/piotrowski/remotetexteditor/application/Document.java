package pl.piotrowski.remotetexteditor.application;

public interface Document {
    void insertContent(String content, int startPosition, int endPosition);
    void replaceContent(String content, int startPosition, int endPosition);
}
