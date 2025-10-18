package ufrn.imd.project.gateway;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ufrn.imd.project.dtos.SortResponse;

public class RequestWaitingList {
  private final Map<String, RequestCallback> pendingRequests;

  public RequestWaitingList() {
    this.pendingRequests = new ConcurrentHashMap<>();
  }

  public synchronized void add(String id, RequestCallback callback) {
    pendingRequests.put(id, callback);
  }

  public synchronized void handleResponse(String id, SortResponse response) {
    RequestCallback callback = pendingRequests.remove(id);

    if (callback == null) {
      return;
    }

    callback.onResponse(id, response);
  }

  public synchronized void handleError(String id, Throwable e) {
    RequestCallback callback = pendingRequests.remove(id);

    if (callback == null) {
      return;
    }

    callback.onError(id, e);
  }

  public interface RequestCallback {
    void onResponse(String id, SortResponse response);
    void onError(String id, Throwable e);
  }
}
