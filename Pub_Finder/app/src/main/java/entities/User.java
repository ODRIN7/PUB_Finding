package entities;

/**
 * Created by DANIEL on 2016. 03. 24..
 */
public class User {

    public Long user_id;
    public String name;
    public String password;


    public User(Long user_id, String name, String password) {
        this.user_id = user_id;
        this.password = password;
        this.setName(name);
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
