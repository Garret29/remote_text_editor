package pl.piotrowski.remotetexteditor.application;

public interface Document {
    void insertContent(String content, int position);
    void replaceContent(String content, int position);
}
