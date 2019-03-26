package pl.piotrowski.remotetexteditor.model;

import java.util.Objects;

public class Document implements pl.piotrowski.remotetexteditor.application.Document {
    private String name;
    private String content;

    @Override
    public void insertContent(String content, int startPosition, int endPosition) {

    }

    @Override
    public void replaceContent(String content, int startPosition, int endPosition) {

    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String newContent) {
        this.content = newContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document)) return false;
        Document document = (Document) o;
        return Objects.equals(getName(), document.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
