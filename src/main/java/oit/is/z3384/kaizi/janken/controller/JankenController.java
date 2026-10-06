package oit.is.z3384.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/janken")
public class JankenController {
  @GetMapping
  public String janken() {
    return "janken.html";
  }

  @PostMapping
  public String janken(@RequestParam String playerName, ModelMap model) {
    model.addAttribute("playerName", playerName);
    return "janken.html";
  }
}
