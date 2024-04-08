package vttp.batch4.csf.toiletnearme.models;

public class OutputMessage {

    private String from;
    private String text;
    private String time;

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public OutputMessage() {
    }
    
    public OutputMessage(String from, String text, String time) {
        this.from = from;
        this.text = text;
        this.time = time;
    }

    @Override
    public String toString() {
        return "OutputMessage{from=%s, text=%s, time=%s}"
        .formatted(from, text, time);
    }

}