package LoadBalancer;

import Instance.Instance;

import java.util.Collections;
import java.util.LinkedList;

public class SequentialLoadBalancer extends BaseLoadBalancer implements LoadBalancer {

    private int nextInstance;

    public SequentialLoadBalancer() {
        this.instances = Collections.synchronizedList(new LinkedList<>());
        this.nextInstance = 0;
    }

    @Override
    public Instance requestInstance() {
        Instance instance = this.instances.get(nextInstance);
        if (this.nextInstance < this.instances.size() - 1) this.nextInstance++;
        else if (this.nextInstance == this.instances.size() - 1) this.nextInstance = 0;
        return instance;
    }
}
