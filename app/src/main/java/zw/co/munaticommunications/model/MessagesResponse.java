package zw.co.munaticommunications.model;

import java.util.List;

public class MessagesResponse {
    private boolean error;
    private String message;
    private List<Message> messages;

    public MessagesResponse(boolean error, String message, List<Message> messages) {
        this.error = error;
        this.message = message;
        this.messages = messages;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
