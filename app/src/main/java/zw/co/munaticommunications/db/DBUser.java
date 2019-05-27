package zw.co.munaticommunications.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

import zw.co.munaticommunications.model.User;

@Table(name = "users")
public class DBUser extends Model {
    @Column(name = "user_id",unique = true,onNullConflict = Column.ConflictAction.REPLACE)
    private int user_id;

    @Column(name = "name")
    private String name;

    @Column(name = "token")
    private String token;

    public DBUser(User user){
        this.user_id = user.getUser_id();
        this.name = user.getName();
        this.token = user.getToken();
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


    public static List<DBUser> getUsers() {
        return new Select().from(DBUser.class).execute();
    }
}
