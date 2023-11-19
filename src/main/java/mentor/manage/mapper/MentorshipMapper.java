package mentor.manage.mapper;

import mentor.manage.entity.Mentor;
import mentor.manage.entity.Mentorship;
import mentor.manage.entity.Intern;
import mentor.manage.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MentorshipMapper {

    // Intern
    @Insert("insert into intern(sid, name, sex, grade) values(#{sid}, #{name}, #{sex}, #{grade})")
    int addIntern(Intern intern);

    @Update("UPDATE intern SET name = #{name}, sex = #{sex}, grade = #{grade} WHERE sid = #{sid}")
    void updateIntern(Intern intern);

    @Select("select * from intern")
    List<Intern> getInternList();

    @Select("SELECT s.sid, s.name as intern_name, s.sex, s.grade, a.aid as aid, a.sex as mentor_sex, a.name as mentor_name" +
            " FROM intern s LEFT JOIN mentorship ad ON s.sid = ad.sid LEFT JOIN mentor a ON ad.aid = a.aid")
    @Results({
            @Result(property = "sid", column = "sid"),
            @Result(property = "name", column = "intern_name"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "grade", column = "grade"),
            @Result(property = "mentor.aid", column = "aid"),
            @Result(property = "mentor.name", column = "mentor_name"),
            @Result(property = "mentor.sex", column = "mentor_sex")
    })
    List<Intern> getAllInternsWithMentors();

    @SelectProvider(type = InternSqlProvider.class, method = "searchInternMentor")
    @Results({
            @Result(property = "sid", column = "sid"),
            @Result(property = "name", column = "intern_name"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "grade", column = "grade"),
            @Result(property = "mentor.aid", column = "aid"),
            @Result(property = "mentor.name", column = "mentor_name"),
            @Result(property = "mentor.sex", column = "mentor_sex")
    })
    List<Intern> searchInternList(@Param("sid") Integer sid, @Param("name") String name, @Param("sex") String sex,
                                  @Param("grade") Integer grade, @Param("mentorName") String mentorName);
    @Delete("delete from intern where sid = #{sid}")
    int deleteInternBySid(int sid);

    // Mentor
    @SelectProvider(type = MentorSqlProvider.class, method = "searchMentorList")
    @Results({
            @Result(property = "aid", column = "aid"),
            @Result(property = "name", column = "name"),
            @Result(property = "sex", column = "sex")
    })
    List<Mentor> searchMentorList(@Param("searchId") Integer searchId,
                                   @Param("searchName") String searchName,
                                   @Param("searchSex") String searchSex);


    @Insert("insert into mentor(aid, name, sex) values(#{aid}, #{name}, #{sex})")
    int addMentor(Mentor mentor);

    @Select("select * from mentor")
    List<Mentor> getMentorList();

    @Select("select * from mentor where aid = (select aid from mentorship where sid = #{sid})")
    Mentor getMentorByInternId(int sid);

    @Delete("delete from mentor where aid = #{aid}")
    int deleteMentorByAid(int aid);

    @Update("UPDATE mentor SET name = #{name}, sex = #{sex} WHERE aid = #{aid}")
    void updateMentor(Mentor mentor);

    // Mentorship
    @Insert("insert into mentorship(sid, aid) value(#{sid}, #{aid})")
    int addMentorship(@Param("sid") int sid, @Param("aid") int aid);

    @Select("select * from mentorship")
    List<Mentorship> getMentorshipList();

    @Update("update mentorship set aid = #{aid} where sid = #{sid}")
    int updateMentorship(@Param("sid") int sid, @Param("aid") int aid);

    @Delete("delete from mentorship where sid = #{sid}")
    int deleteMentorshipByInternId(int sid);


    // User
    @Select("select * from users where username = #{username} and password = #{password}")
    User loginCheck(@Param("username") String userName, @Param("password") String password);

    @Insert("insert into users(username, password) values(#{userName}, #{password})")
    int addUser(User user);

    @Delete("delete from users where username = #{username}")
    int deleteUser(String username);
}
