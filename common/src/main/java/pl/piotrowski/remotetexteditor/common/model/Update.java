package pl.piotrowski.remotetexteditor.common.model;


import java.util.Objects;

public class Update{
    private String content;

    public Update(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Update)) return false;
        Update update = (Update) o;
        return Objects.equals(getContent(), update.getContent());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getContent());
    }
}
