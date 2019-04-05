package pl.piotrowski.remotetexteditor.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Document implements Editable, Serializable {

    @Id
    @SequenceGenerator(name = "seq_gen", sequenceName = "seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_gen")
    private Long id;

    @Column(unique = true)
    private String name;

    @Lob
    @Column
    private String content;

    @Override
    public void insertContent(String newContent, int position) {
        String temp = content.substring(0,position);
        temp = temp.concat(newContent);
        content = temp.concat(content.substring(position,content.length()));
    }

    @Override
    public void replaceContent(String newContent, int position) {
        String temp = content.substring(0,position);
        temp = temp.concat(newContent);
        content = temp.concat(content.substring(position+newContent.length(), content.length()));

    }

    public Document(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Document() {}

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
