package LoadBalancer;

import Util.LoadBalancerType;
import Util.Randomiser;

public class LoadBalancerFactory {

    private Randomiser randomiser;

    public LoadBalancerFactory() {}

    public LoadBalancerFactory(Randomiser randomiser) {
        this.randomiser = randomiser;
    }

    public LoadBalancer getLoadBalancer(LoadBalancerType type) {
        switch (type) {
            case SEQUENTIAL:
                return new SequentialLoadBalancer();
            case RANDOM:
                if(this.randomiser != null)  return new RandomLoadBalancer(this.randomiser);
                else return new RandomLoadBalancer(new Randomiser());
        }
        return null;
    }

    public void setRandomiser(Randomiser randomiser) {
        this.randomiser = randomiser;
    }
}
