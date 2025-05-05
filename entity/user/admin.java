package entity.user;

import java.util.Objects;

public class Admin extends User {

    static int id = 123;
    static String password = "admin123";

    public Admin(int id, String name, String email, String password, int age) {
        super(Admin.id, name, email, Admin.password, age);
    }

    @Override
    public String toString() {
        return "admin{" +
                "id=" + id +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + password + '\'' +
                ", age=" + getAge() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Admin))
            return false;
        if (!super.equals(o))
            return false;
        Admin that = (Admin) o;
        return id == that.id &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, password);
    }
}