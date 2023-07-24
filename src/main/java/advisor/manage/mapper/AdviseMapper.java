package advisor.manage.mapper;

import advisor.manage.entity.Advised;
import advisor.manage.entity.Advisor;
import advisor.manage.entity.Student;
import advisor.manage.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AdviseMapper {

    // Student
    @Insert("insert into student(sid, name, sex, grade) values(#{sid}, #{name}, #{sex}, #{grade})")
    int addStudent(Student student);

    @Update("UPDATE student SET name = #{name}, sex = #{sex}, grade = #{grade} WHERE sid = #{sid}")
    void updateStudent(Student student);

    @Select("select * from student")
    List<Student> getStudentList();

    @Select("SELECT s.sid, s.name as student_name, s.sex, s.grade, a.aid as aid, a.sex as advisior_sex, a.name as advisor_name" +
            " FROM student s LEFT JOIN advised ad ON s.sid = ad.sid LEFT JOIN advisor a ON ad.aid = a.aid")
    @Results({
            @Result(property = "sid", column = "sid"),
            @Result(property = "name", column = "student_name"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "grade", column = "grade"),
            @Result(property = "advisor.aid", column = "aid"),
            @Result(property = "advisor.name", column = "advisor_name"),
            @Result(property = "advisor.sex", column = "advisor_sex")
    })
    List<Student> getAllStudentsWithAdvisors();

    @Select("select * from student where sid = #{sid}")
    Student getStudentBySid(int sid);

    @SelectProvider(type = StudentSqlProvider.class, method = "searchStudentAdvisor")
    @Results({
            @Result(property = "sid", column = "sid"),
            @Result(property = "name", column = "student_name"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "grade", column = "grade"),
            @Result(property = "advisor.aid", column = "aid"),
            @Result(property = "advisor.name", column = "advisor_name"),
            @Result(property = "advisor.sex", column = "advisor_sex")
    })
    List<Student> searchStudentList(@Param("sid") Integer sid, @Param("name") String name, @Param("sex") String sex,
                                    @Param("grade") Integer grade, @Param("advisorName") String advisorName);

    @Delete("delete from student where sid = #{sid}")
    int deleteStudentBySid(int sid);

    // Advisor
    @Insert("insert into advisor(aid, name, sex) values(#{aid}, #{name}, #{sex})")
    int addAdvisor(Advisor advisor);

    @Select("select * from advisor")
    List<Advisor> getAdvisorList();

    @Select("select * from advisor where aid = #{aid}")
    Advisor getAdvisorByAid(int aid);

    @Select("select * from advisor where aid = (select aid from advised where sid = #{sid})")
    Advisor getAdvisorByStudentId(int sid);

    @Delete("delete from advisor where aid = #{aid}")
    int deleteAdvisorByAid(int aid);

    // Advised
    @Insert("insert into advised(sid, aid) value(#{sid}, #{aid})")
    int addAdvised(@Param("sid") int sid, @Param("aid") int aid);

    @Select("select * from advised")
    List<Advised> getAdvisedList();

    @Update("update advised set aid = #{aid} where sid = #{sid}")
    int updateAdvised(@Param("sid") int sid, @Param("aid") int aid);

    @Select("select * from advised where id = #{id}")
    Advised getAdvisedById(int id);

    @Delete("delete from advised where sid = #{sid}")
    int deleteAdvisedByStudentId(int sid);

    @Delete("delete from advised where id = #{id}")
    int deleteAdvisedById(int id);

    // User
    @Select("select * from users where username = #{username} and password = #{password}")
    User loginCheck(@Param("username") String userName, @Param("password") String password);

    @Insert("insert into users(username, password) values(#{userName}, #{password})")
    int addUser(User user);

    @Delete("delete from users where username = #{username}")
    int deleteUser(String username);
}
