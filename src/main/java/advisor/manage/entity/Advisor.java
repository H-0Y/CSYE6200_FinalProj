package advisor.manage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Advisor extends People {
    int aid;
    public Advisor(int aid, String name, String sex) {
        this.aid = aid;
        this.name = name;
        this.sex = sex;
    }
}
