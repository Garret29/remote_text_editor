package pl.piotrowski.remotetexteditor.model;

public class Update {
    private String content;
    private int start;
    private int end;
    private boolean appending;

    public Update(String content, int start, int end, boolean appending) {
        this.content = content;
        this.start = start;
        this.end = end;
        this.appending = appending;
    }

    public Update() {
    }

    public boolean isAppending() {
        return appending;
    }

    public void setAppending(boolean appending) {
        this.appending = appending;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
