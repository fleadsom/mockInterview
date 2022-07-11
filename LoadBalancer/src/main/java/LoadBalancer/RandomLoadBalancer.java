package LoadBalancer;

import Instance.Instance;
import Util.Randomiser;

import java.util.ArrayList;
import java.util.Collections;

public class RandomLoadBalancer extends BaseLoadBalancer implements LoadBalancer {

    private Randomiser randomiser;

    public RandomLoadBalancer(Randomiser randomiser) {
        this.instances = Collections.synchronizedList(new ArrayList<>());
        this.randomiser = randomiser;
    }

    @Override
    public Instance requestInstance() {
        int size = instances.size();
        return instances.get(randomiser.getNumber(size));
    }
}