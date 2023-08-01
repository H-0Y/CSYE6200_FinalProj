package advisor.manage.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class AdvisorSqlProvider {

    public String searchAdvisorList(@Param("searchId") Integer searchId,
                                    @Param("searchName") String searchName,
                                    @Param("searchSex") String searchSex) {
        return new SQL() {{
            SELECT("aid, name, sex");
            FROM("advisor");
            if (searchId != null) {
                WHERE("aid = #{searchId}");
            }
            if (searchName != null && !searchName.isEmpty()) {
                WHERE("name LIKE CONCAT('%', #{searchName}, '%')");
            }
            if (searchSex != null && !searchSex.isEmpty()) {
                WHERE("sex = #{searchSex}");
            }
        }}.toString();
    }
}
