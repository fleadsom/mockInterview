package LoadBalancer;

import Instance.Instance;

public interface LoadBalancer {
    void registerInstance(Instance instance);

    Instance requestInstance();
}
