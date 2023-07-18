package advisor.manage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class Advisor extends People {

    public Advisor(int aid, String name, String sex) {
        this.id = aid;
        this.name = name;
        this.sex = sex;
    }
}
