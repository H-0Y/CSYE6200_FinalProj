package advisor.manage.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
public class StudentSqlProvider {
    public String searchStudentAdvisor(@Param("sid") Integer sid, @Param("name") String name, @Param("sex") String sex,
                                       @Param("grade") Integer grade, @Param("advisorName") String advisorName) {
        SQL sql = new SQL()
                .SELECT("s.sid, s.name as student_name, s.sex, s.grade, a.aid as aid, a.name as advisor_name, a.sex as advisor_sex")
                .FROM("student s")
                .LEFT_OUTER_JOIN("advised ad ON s.sid = ad.sid")
                .LEFT_OUTER_JOIN("advisor a ON ad.aid = a.aid");

        if (sid != null) {
            sql.WHERE("s.sid = #{sid}");
        }
        if (name != null && !name.isEmpty()) {
            sql.WHERE("s.name LIKE #{name}");
        }
        if (sex != null && !sex.isEmpty()) {
            sql.WHERE("s.sex = #{sex}");
        }
        if (grade != null) {
            sql.WHERE("s.grade = #{grade}");
        }
        if (advisorName != null && !advisorName.isEmpty()) {
            sql.WHERE("a.name LIKE #{advisorName}");
        }
        return sql.toString();
    }
}