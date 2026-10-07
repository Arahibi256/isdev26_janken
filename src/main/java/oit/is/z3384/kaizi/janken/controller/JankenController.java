package oit.is.z3384.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3384.kaizi.janken.model.Janken;

@Controller
@RequestMapping("/janken")
public class JankenController {
  @GetMapping
  public String directlyAccessed() {
    return "janken.html";
  }

  @PostMapping
  public String nameInputted(@RequestParam String playerName, ModelMap model) {
    model.addAttribute("playerName", playerName);
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
