package ufrn.imd.project.gateway;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ufrn.imd.project.dtos.SortResponse;

public class RequestWaitingList {
  private final Map<String, CallbackDetails> pendingRequests;

  public RequestWaitingList() {
    this.pendingRequests = new ConcurrentHashMap<>();
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
  }
}
