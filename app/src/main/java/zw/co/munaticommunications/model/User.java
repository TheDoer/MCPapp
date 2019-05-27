package zw.co.munaticommunications.model;

public class User {
    private int user_id;
    private String name;
    private String token;

    public User(int user_id, String name, String token) {
        this.user_id = user_id;
        this.name = name;
        this.token = token;
    }

    public User() {
        this.user_id = 0;
        this.name = "No Name";
        this.token = "No Token";
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
