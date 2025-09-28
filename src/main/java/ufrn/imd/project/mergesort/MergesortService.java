package ufrn.imd.project.mergesort;

import java.util.ArrayList;
import java.util.List;

public class MergesortService {
  public void sort(List<Integer> array) {
    if (array.size() <= 1) {
      return;
    }

    int middle = array.size() / 2;

    List<Integer> left = new ArrayList<>(array.subList(0, middle));
    List<Integer> right = new ArrayList<>(array.subList(middle, array.size()));

    sort(left);
    sort(right);

    int i = 0, j = 0;

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

    while (i < left.size()) {
      array.set(i + j, left.get(i));
      i += 1;
    }

    while (j < right.size()) {
      array.set(i + j, right.get(j));
      j += 1;
    }
  }
}
