package LoadBalancer;

import Exceptions.RegistrationException;
import Instance.Instance;

import java.util.List;

public class BaseLoadBalancer {
    protected List<Instance> instances;

    public void registerInstance(Instance instance) {
        if (!limitReached() && !alreadyRegistered(instance)) this.instances.add(instance);
        else throw new RegistrationException("Registration limit breached");
    }

    private boolean alreadyRegistered(Instance instance) {
        if (this.instances.contains(instance)) return true;
        else return false;
    }

    private boolean limitReached() {
        if (instances.size() >= 10) return true;
        else return false;
    }
}
