package mentor.manage.sql;

import mentor.manage.mapper.MentorshipMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.IOException;
import java.util.function.Consumer;

// create a SqlUtil Class to encapsulate the operations with SqlSession
public class SqlUtil {
    private SqlUtil(){}
    // create a SqlSessionFactory
    private static SqlSessionFactory factory;
    static {
        try {
            factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // encapsulate the operation where sqlSession get an MentoriseMapper
    public static void doSqlWork(Consumer<MentorshipMapper> consumer) {
        try(SqlSession sqlSession = factory.openSession(true)){
            MentorshipMapper mentorshipMapper = sqlSession.getMapper(MentorshipMapper.class);
            consumer.accept(mentorshipMapper);
        }
    }
}
