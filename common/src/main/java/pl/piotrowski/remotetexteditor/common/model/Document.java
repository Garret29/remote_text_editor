package pl.piotrowski.remotetexteditor.common.model;

import difflib.Patch;
import difflib.PatchFailedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Document {

    private String text;
    private String name;
    private int caretPosition;

    public Document(String text, String name, int caretPosition) {
        this.text = text;
        this.name = name;
        this.caretPosition = caretPosition;
    }

    @SuppressWarnings("unused")
    public Document() {
    }

    public void update(Patch<String> patch) throws PatchFailedException {
        List<String> textList = Arrays.asList(getText().split(""));
        textList = patch.applyTo(textList);

        StringBuilder stringBuilder = new StringBuilder();
        for (String s :
                textList) {
            stringBuilder.append(s);
        }

        patch.getDeltas().forEach(stringDelta -> {
            if (stringDelta.getRevised().getPosition() <= getCaretPosition()) {
                setCaretPosition(getCaretPosition() + stringDelta.getRevised().getLines().size() - stringDelta.getOriginal().getLines().size());
            }
        });
        setText(stringBuilder.toString());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCaretPosition() {
        return caretPosition;
    }

    public void setCaretPosition(int caretPosition) {
        this.caretPosition = caretPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document)) return false;
        Document document = (Document) o;
        return Objects.equals(getText(), document.getText()) &&
                Objects.equals(getName(), document.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getText(), getName());
    }
}