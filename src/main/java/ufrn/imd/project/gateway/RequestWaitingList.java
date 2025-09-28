package ufrn.imd.project.gateway;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ufrn.imd.project.dtos.SortResponse;

public class RequestWaitingList {
  private final Map<String, CallbackDetails> pendingRequests;

  public RequestWaitingList() {
    this.pendingRequests = new ConcurrentHashMap<>();
  }

  public void add(String id, RequestCallback callback) {
    pendingRequests.put(id, new CallbackDetails(callback, System.nanoTime()));
  }

  public void handleResponse(String id, SortResponse response) {
    CallbackDetails callbackDetails = pendingRequests.remove(id);

    if (callbackDetails == null) {
      return;
    }

    callbackDetails.callback.onResponse(id, response);
  }

  public void handleError(String id, Throwable e) {
    CallbackDetails callbackDetails = pendingRequests.remove(id);

    if (callbackDetails == null) {
      return;
    }

    callbackDetails.callback.onError(id, e);
  }

  public interface RequestCallback {
    void onResponse(String id, SortResponse response);
    void onError(String id, Throwable e);
  }

  private class CallbackDetails {
    final RequestCallback callback;
    final long createTime;

    CallbackDetails(RequestCallback callback, long createTime) {
      this.callback = callback;
      this.createTime = createTime;
    }

    long elapsedTime(long now) {
      return now - createTime;
    }
  }
}
