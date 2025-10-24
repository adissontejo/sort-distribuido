package ufrn.imd.project.hearbeat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import ufrn.imd.project.dtos.ComponentInstance;

public class LoadBalancer {
  private final Map<String, InstanceManager> instanceManagers;

  public LoadBalancer() {
    instanceManagers = new ConcurrentHashMap<>();
  }

  public void addInstance(ComponentInstance instance) {
    System.out.println("New " + instance.componentKey() + " found at " + instance.hostname() + ":" + instance.port());

    InstanceManager instanceManager = instanceManagers.computeIfAbsent(instance.componentKey(), (key) -> {
      return new InstanceManager();
    });

    instanceManager.add(instance);
  }

  public void removeInstance(ComponentInstance instance) {
    System.out.println("Lost " + instance.componentKey() + " on " + instance.hostname() + ":" + instance.port());

    InstanceManager instanceManager = instanceManagers.get(instance.componentKey());

    if (instanceManager != null) {
      instanceManager.remove(instance);
    }
  }

  public ComponentInstance getInstanceFor(String componentKey) {
    InstanceManager instanceManager = instanceManagers.get(componentKey);

    if (instanceManager == null) {
      return null;
    }

    ComponentInstance instance = instanceManager.next();

    return instance;
  }

  private static class InstanceManager {
    private final List<ComponentInstance> instances;
    private final AtomicInteger counter;

    InstanceManager() {
      this.instances = Collections.synchronizedList(new ArrayList<>());
      this.counter = new AtomicInteger(0);
    }

    void add(ComponentInstance instance) {
      instances.add(instance);
    }

    void remove(ComponentInstance instance) {
      instances.remove(instance);
    }

    ComponentInstance next() {
      try {
        return instances.get(counter.getAndIncrement() % instances.size());
      } catch (IndexOutOfBoundsException e) {
        return null;
      }
    }
  }
}
