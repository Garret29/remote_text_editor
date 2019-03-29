package pl.piotrowski.remotetexteditor.model;

public interface Editable {
    void insertContent(String content, int position);
    void replaceContent(String content, int position);
}
