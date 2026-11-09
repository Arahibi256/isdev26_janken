package oit.is.z3384.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3384.kaizi.janken.model.Match;
import oit.is.z3384.kaizi.janken.model.MatchInfo;
import oit.is.z3384.kaizi.janken.model.MatchInfoMapper;
import oit.is.z3384.kaizi.janken.model.MatchMapper;
import oit.is.z3384.kaizi.janken.model.User;
import oit.is.z3384.kaizi.janken.model.UserMapper;

@Controller
public class JankenController {
  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MatchMapper matchMapper;

  @Autowired
  private MatchInfoMapper matchInfoMapper;

  @GetMapping("/janken")
  public String loggedIn(ModelMap model) {
    ArrayList<User> users = userMapper.selectAllUser();
    ArrayList<Match> matches = matchMapper.selectAllMatch();
    ArrayList<MatchInfo> matchInfos = matchInfoMapper.selectActiveMatchInfo();
    model.addAttribute("matches", matches);
    model.addAttribute("matchInfos", matchInfos);
    model.addAttribute("entry", users);
    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam int opponentId, ModelMap model) {
    User opponent = userMapper.selectUserById(opponentId);
    model.addAttribute("opponent", opponent);
    return "match.html";
  }

  @GetMapping("/fight")
  public String fight(@RequestParam int opponentId, @RequestParam String hand, Principal principal) {
    int userId = userMapper.selectUserByName(principal.getName()).getId();
    MatchInfo waitingMatch = matchInfoMapper.selectActiveMatchInfoByUsers(opponentId, userId);

    if (waitingMatch == null) {
      MatchInfo matchInfo = new MatchInfo();
      matchInfo.setUser1(userId);
      matchInfo.setUser2(opponentId);
      matchInfo.setUser1Hand(hand);
      matchInfo.setActive(true);
      matchInfoMapper.insertMatchInfo(matchInfo);
    } else {
      Match match = new Match();
      match.setUser1(waitingMatch.getUser1());
      match.setUser2(userId);
      match.setUser1Hand(waitingMatch.getUser1Hand());
      match.setUser2Hand(hand);
      match.setActive(true);

      matchMapper.insertMatch(match);
      matchInfoMapper.updateMatchInfoInactive(waitingMatch.getMatchinfoid());
    }

    return "wait.html";
  }

  @GetMapping("/wait")
  public String waitForOpponent() {
    return "wait.html";
  }
}
