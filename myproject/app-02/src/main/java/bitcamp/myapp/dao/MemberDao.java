package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Member;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface MemberDao {
  Member findByEmailAndPassword(String email, String password) throws DaoException;
}
