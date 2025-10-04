package ufrn.imd.project.dtos;

import java.util.List;

public record ParallelSortRequest(List<Integer> data, ParallelSortCriteria criteria) {
  public ParallelSortRequest {
    if (criteria == null) {
      criteria = ParallelSortCriteria.FIRST;
    }
  }
}
