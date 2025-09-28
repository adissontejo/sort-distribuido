package ufrn.imd.project.gateway;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ufrn.imd.project.dtos.ParallelSortCriteria;
import ufrn.imd.project.dtos.SortResponse;

public class QuorumCallback implements RequestWaitingList.RequestCallback {
  private final ParallelSortCriteria criteria;
  private final int expectedResponses;
  private final QuorumConclusionListener listener;
  private final Map<String, SortResponse> responses;
  private int receivedErrors;

  public QuorumCallback(ParallelSortCriteria criteria, int expectedResponses, QuorumConclusionListener listener) {
    this.criteria = criteria;
    this.expectedResponses = expectedResponses;
    this.listener = listener;
    this.responses = new ConcurrentHashMap<>();
    this.receivedErrors = 0;
  }

  @Override
  public synchronized void onResponse(String id, SortResponse response) {
    responses.put(id, response);

    if (responses.size() == 1 && criteria == ParallelSortCriteria.FIRST) {
      listener.onConclusion(responses);
    } else if (responses.size() == expectedResponses && criteria == ParallelSortCriteria.ALL) {
      listener.onConclusion(responses);
    }
  }

  @Override
  public synchronized void onError(String id, Throwable e) {
    receivedErrors += 1;

    if (receivedErrors == 1 && criteria == ParallelSortCriteria.ALL) {
      listener.onError();
    } else if (receivedErrors == expectedResponses && criteria == ParallelSortCriteria.FIRST) {
      listener.onError();
    }
  }

  public interface QuorumConclusionListener {
    void onConclusion(Map<String, SortResponse> responses);
    void onError();
  }
}
