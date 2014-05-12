package models;

import play.db.ebean.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class User extends Model {
    @Id
    public long id;
    public double latitude;
    public double longitude;
    public long endTime;
    public long updateTime;

    public User(Long id) {
        this.id = id;
    }

    public static final Finder<Long, User> find = new Finder<>(Long.class, User.class);

    public static List<User> all() {
        return find.all();
    }
}
