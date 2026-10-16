package oit.is.z3384.kaizi.janken.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3384.kaizi.janken.model.Entry;
import oit.is.z3384.kaizi.janken.model.Janken;

@Controller
@RequestMapping("/janken")
public class JankenController {
  @Autowired
  private Entry entry;

  @GetMapping
  public String loggedIn(Principal prin, ModelMap model) {
    String userName = prin.getName();
    entry.addUser(userName);
    model.addAttribute("entry", entry);
    model.addAttribute("playerName", userName);
    return "janken.html";
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
