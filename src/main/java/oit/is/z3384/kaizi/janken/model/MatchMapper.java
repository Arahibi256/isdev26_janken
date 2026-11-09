package oit.is.z3384.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {
  @Select("SELECT * FROM matches")
  ArrayList<Match> selectAllMatch();

  @Select("SELECT * FROM matches WHERE (user1 = #{userId} OR user2 = #{userId}) AND isActive = TRUE LIMIT 1")
  Match selectActiveMatchByUser(int userId);

  @Insert("INSERT INTO matches (user1, user2, user1Hand, user2Hand, isActive) VALUES (#{user1}, #{user2}, #{user1Hand}, #{user2Hand}, #{active})")
  void insertMatch(Match match);

  @Update("UPDATE matches SET isActive = FALSE WHERE id = #{id}")
  void updateMatchInactive(int id);
}
