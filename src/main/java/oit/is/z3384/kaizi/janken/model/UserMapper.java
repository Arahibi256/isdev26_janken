package oit.is.z3384.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
  @Select("SELECT * FROM users")
  ArrayList<User> selectAllUser();

  @Select("SELECT * FROM users WHERE id = #{id}")
  User selectUserById(int id);

  @Select("SELECT * FROM users WHERE name = #{name}")
  User selectUserByName(String name);
}
