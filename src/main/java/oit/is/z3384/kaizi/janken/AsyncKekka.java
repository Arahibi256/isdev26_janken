package oit.is.z3384.kaizi.janken;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z3384.kaizi.janken.model.Match;
import oit.is.z3384.kaizi.janken.model.MatchMapper;

@Component
public class AsyncKekka {
  private final MatchMapper matchMapper;
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

  public AsyncKekka(MatchMapper matchMapper) {
    this.matchMapper = matchMapper;
  }

  public SseEmitter connect(int userId) {
    SseEmitter emitter = new SseEmitter(30 * 60 * 1000L);
    ScheduledFuture<?> polling = scheduler.scheduleAtFixedRate(() -> sendResult(emitter, userId), 0, 1,
        TimeUnit.SECONDS);

    emitter.onCompletion(() -> polling.cancel(false));
    emitter.onTimeout(() -> polling.cancel(false));
    emitter.onError(error -> polling.cancel(false));
    return emitter;
  }

  private void sendResult(SseEmitter emitter, int userId) {
    Match match = matchMapper.selectActiveMatchByUser(userId);
    if (match == null) {
      return;
    }

    try {
      emitter.send(SseEmitter.event().name("match-result").data(match));
      matchMapper.updateMatchInactive(match.getId());
      emitter.complete();
    } catch (Exception exception) {
      emitter.completeWithError(exception);
    }
  }
}
