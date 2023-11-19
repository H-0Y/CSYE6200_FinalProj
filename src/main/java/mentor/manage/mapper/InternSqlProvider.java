package mentor.manage.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
public class InternSqlProvider {
    public String searchInternMentor(@Param("sid") Integer sid, @Param("name") String name, @Param("sex") String sex,
                                     @Param("grade") Integer grade, @Param("mentorName") String mentorName) {
        SQL sql = new SQL()
                .SELECT("s.sid, s.name as intern_name, s.sex, s.grade, a.aid as aid, a.name as mentor_name, a.sex as mentor_sex")
                .FROM("intern s")
                .LEFT_OUTER_JOIN("mentorship ad ON s.sid = ad.sid")
                .LEFT_OUTER_JOIN("mentor a ON ad.aid = a.aid");

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
        if (mentorName != null && !mentorName.isEmpty()) {
            sql.WHERE("a.name LIKE #{mentorName}");
        }
        return sql.toString();
    }
}