package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface MemberDao {
  Member findByEmailAndPassword(
          @Param("email") String email,
          @Param("password") String password);
}
