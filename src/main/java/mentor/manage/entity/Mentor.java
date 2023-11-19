package mentor.manage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor


public class Mentor extends People {
    int aid;
    public Mentor(int aid, String name, String sex) {
        this.aid = aid;
        this.name = name;
        this.sex = sex;
    }

    public String toString() {
        return name;
    }
}
