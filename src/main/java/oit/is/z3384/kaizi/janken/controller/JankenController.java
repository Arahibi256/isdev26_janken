package oit.is.z3384.kaizi.janken.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3384.kaizi.janken.model.Janken;
import oit.is.z3384.kaizi.janken.model.Match;
import oit.is.z3384.kaizi.janken.model.MatchMapper;
import oit.is.z3384.kaizi.janken.model.User;
import oit.is.z3384.kaizi.janken.model.UserMapper;

@Controller
public class JankenController {
  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MatchMapper matchMapper;

  @GetMapping("/janken")
  public String loggedIn(ModelMap model) {
    ArrayList<User> users = userMapper.selectAllUser();
    ArrayList<Match> matches = matchMapper.selectAllMatch();
    model.addAttribute("matches", matches);
    model.addAttribute("entry", users);
    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam int opponentId, ModelMap model) {
    User opponent = userMapper.selectUserById(opponentId);
    model.addAttribute("opponent", opponent);
    return "match.html";
  }

  @GetMapping("/play")
  public String playJanken(@RequestParam String hand, ModelMap model) {
    Janken janken = new Janken(hand);
    model.addAttribute("playerHand", janken.getPlayerHand());
    model.addAttribute("computerHand", janken.getComputerHand());
    model.addAttribute("result", janken.getResult());
    return "janken.html";
  }
}
