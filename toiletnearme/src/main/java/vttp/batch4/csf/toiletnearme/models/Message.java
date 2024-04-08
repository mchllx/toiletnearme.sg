package vttp.batch4.csf.toiletnearme.models;

public class Message {

    private String from;
    private String text;

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Message() {
    }
    
    public Message(String from, String text) {
        this.from = from;
        this.text = text;
    }

    @Override
    public String toString() {
        return "OutputMessage{from=%s, text=%s}"
        .formatted(from, text);
    }
    
}