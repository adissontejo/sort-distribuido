package ufrn.imd.project.gateway;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import ufrn.imd.project.dtos.SortResponse;

public class RequestWaitingList {
  private final Map<String, CallbackDetails> pendingRequests;
  private final ScheduledExecutorService executor;
  private final long timeout = 1000;

  public RequestWaitingList() {
    this.pendingRequests = new ConcurrentHashMap<>();
    this.executor = Executors.newSingleThreadScheduledExecutor();
    this.executor.scheduleWithFixedDelay(this::expire, timeout, timeout, TimeUnit.SECONDS);
  }

  public synchronized void add(String id, RequestCallback callback) {
    pendingRequests.put(id, new CallbackDetails(callback, System.nanoTime()));
  }

  public synchronized void handleResponse(String id, SortResponse response) {
    CallbackDetails callbackDetails = pendingRequests.remove(id);

    if (callbackDetails == null) {
      return;
    }

    callbackDetails.callback.onResponse(id, response);
  }

  public synchronized void handleError(String id, Throwable e) {
    CallbackDetails callbackDetails = pendingRequests.remove(id);

    if (callbackDetails == null) {
      return;
    }

    callbackDetails.callback.onError(id, e);
  }

  private List<String> getExpiredRequestIds(long now) {
    return pendingRequests
      .entrySet()
      .stream()
      .filter(entry -> entry.getValue().elapsedTime(now) > timeout)
      .map(entry -> entry.getKey())
      .collect(Collectors.toList());
  }

  private synchronized void expire() {
    List<String> expiredIds = getExpiredRequestIds(System.nanoTime());

    for (String id : expiredIds) {
      handleError(id, new RuntimeException("Request timeout"));
    }
  }

  public void shutdown() {
    executor.close();
  }

  public interface RequestCallback {
    void onResponse(String id, SortResponse response);
    void onError(String id, Throwable e);
  }

  private class CallbackDetails {
    private final RequestCallback callback;
    private final long createTime;

    CallbackDetails(RequestCallback callback, long createTime) {
      this.callback = callback;
      this.createTime = createTime;
    }

    public long elapsedTime(long now) {
      return (now - createTime) / 1000;
    }
  }
}
