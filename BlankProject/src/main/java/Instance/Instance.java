package Instance;

import java.net.InetAddress;
import java.util.Objects;

public class Instance {

    private InetAddress address;

    public Instance(InetAddress address) {
        this.address = address;
    }

    public InetAddress getAddress() {
        return this.address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instance instance = (Instance) o;
        return Objects.equals(address, instance.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
