package ufrn.imd.project.mergesort;

import java.util.ArrayList;
import java.util.List;

public class MergesortService {
  // Merge sort implementation
  public void sort(List<Integer> array) {
    // Base case: arrays with 0 or 1 element are already sorted
    if (array.size() <= 1) {
      return;
    }

    // Split the array into two halves
    int middle = array.size() / 2;

    List<Integer> left = new ArrayList<>(array.subList(0, middle));
    List<Integer> right = new ArrayList<>(array.subList(middle, array.size()));

    // Recursively sort both halves
    sort(left);
    sort(right);

    int i = 0, j = 0;

    // Merge the sorted halves back into the original array
    while (i < left.size() && j < right.size()) {
      int a = left.get(i);
      int b = right.get(j);

      if (a <= b) {
        array.set(i + j, a);
        i += 1;
      } else {
        array.set(i + j, b);
        j += 1;
      }
    }

    // Copy any remaining elements from the left half
    while (i < left.size()) {
      array.set(i + j, left.get(i));
      i += 1;
    }

    // Copy any remaining elements from the right half
    while (j < right.size()) {
      array.set(i + j, right.get(j));
      j += 1;
    }
  }
}
