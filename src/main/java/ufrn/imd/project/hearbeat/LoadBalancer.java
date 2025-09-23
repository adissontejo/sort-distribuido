package ufrn.imd.project.hearbeat;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import ufrn.imd.project.dtos.ComponentInstance;

public class LoadBalancer {
  private final Map<String, Queue<ComponentInstance>> componentQueues;

  public LoadBalancer() {
    componentQueues = new ConcurrentHashMap<>();
  }

  public synchronized void addInstance(ComponentInstance instance) {
    Queue<ComponentInstance> queue = componentQueues.get(instance.componentKey());

    if (queue == null) {
      queue = new ConcurrentLinkedDeque<>();

      componentQueues.put(instance.componentKey(), queue);
    }

    queue.add(instance);
  }

  public synchronized void removeInstance(ComponentInstance instance) {
    Queue<ComponentInstance> queue = componentQueues.get(instance.componentKey());

    if (queue != null) {
      queue.remove(instance);
    }
  }

  public synchronized ComponentInstance getInstanceFor(String componentKey) {
    Queue<ComponentInstance> queue = componentQueues.get(componentKey);

    if (queue == null) {
      return null;
    }

    ComponentInstance instance = queue.poll();

    if (instance != null) {
      queue.add(instance);
    }

    return instance;
  }
}
