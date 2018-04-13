package pl.sosk;

public class Message {

    private final long id;
    private final String content;

    public Message(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Message(){
        this.id = 1;
        this.content = "none";
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}
