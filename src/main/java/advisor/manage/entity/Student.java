package advisor.manage.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Student extends People {
    int sid;
    int grade;
    private Advisor advisor; // a student has an advisor

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public Student(int sid, String name, String sex, int grade) {
        this.name = name;
        this.sex = sex;
        this.grade = grade;
        this.sid = sid;
    }

    public Student(Integer sid, String name, String sex, Integer grade, Advisor advisor) {
        this.name = name;
        this.sex = sex;
        this.grade = grade;
        this.sid = sid;
        this.advisor = advisor;
    }

}
