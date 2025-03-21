package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Member;
import bitcamp.stereotype.Component;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

@Component
public class MySQLMemberDao implements MemberDao {

  private SqlSessionFactory sqlSessionFactory;

  public MySQLMemberDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public Member findByEmailAndPassword(String email, String password) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      HashMap<String,Object> values = new HashMap<>();
      values.put("email", email);
      values.put("password", password);
      return sqlSession.selectOne("MemberDao.findByEmailAndPassword", values);
    }
  }
}
