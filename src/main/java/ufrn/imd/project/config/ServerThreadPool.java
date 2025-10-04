package ufrn.imd.project.config;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerThreadPool extends ThreadPoolExecutor {
  public ServerThreadPool(int corePoolSize, int maxPoolSize) {
    super(
      corePoolSize,
      maxPoolSize,
      10,
      TimeUnit.SECONDS,
      new LinkedBlockingQueue<Runnable>(100),
      new ThreadPoolExecutor.AbortPolicy()
    );
  }
}
