package mentor.manage.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Intern extends People {
    int sid;
    int grade;
    private Mentor mentor;

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public Intern(int sid, String name, String sex, int grade) {
        this.name = name;
        this.sex = sex;
        this.grade = grade;
        this.sid = sid;
    }

    public Intern(Integer sid, String name, String sex, Integer grade, Mentor mentor) {
        this.name = name;
        this.sex = sex;
        this.grade = grade;
        this.sid = sid;
        this.mentor = mentor;
    }

}
