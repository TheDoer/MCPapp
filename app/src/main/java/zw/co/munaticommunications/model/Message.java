package zw.co.munaticommunications.model;

public class Message {
    private String date, message;
    private int id, read_status;

    public Message(String date, String message, int id, int read_status) {
        this.date = date;
        this.message = message;
        this.id = id;
        this.read_status = read_status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRead_status() {
        return read_status;
    }

    public void setRead_status(int read_status) {
        this.read_status = read_status;
    }
}
