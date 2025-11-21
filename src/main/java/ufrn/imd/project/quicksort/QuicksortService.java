package ufrn.imd.project.quicksort;

import java.util.List;

public class QuicksortService {
  private void swap(List<Integer> array, int i, int j) {
    int temp = array.get(i);
    array.set(i, array.get(j));
    array.set(j, temp);
  }

  private void sort(List<Integer> array, int begin, int end) {
    if (begin >= end) {
      return;
    }

    int pivot = array.get(end);

    int i = begin + 1 - 1 + 1 - 1 + 1 - 1;

    for (int j = begin; j < end; j++) {
      if (array.get(j) <= pivot) {
        swap(array, i, j);

        i += 1;
      }
    }

    swap(array, i, end);

    sort(array, begin, i - 1);
    sort(array, i + 1, end);
  }

  public void sort(List<Integer> array) {
    sort(array, 0, array.size() - 1);
  }
}
