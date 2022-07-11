import Exceptions.RegistrationException;
import Instance.Instance;
import LoadBalancer.*;
import Util.LoadBalancerType;
import Util.Randomiser;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static Util.LoadBalancerType.RANDOM;
import static Util.LoadBalancerType.SEQUENTIAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestLoadBalancer {

    @Test
    public void whenInstanceRegistered_thenAddressIsRegistered() throws UnknownHostException {
        RandomLoadBalancer lb = new RandomLoadBalancer(new Randomiser());

        InetAddress address = InetAddress.getByName("192.168.0.1");
        Instance instance = new Instance(address);
        lb.registerInstance(instance);

        assertThat(lb.requestInstance().getAddress()).isEqualTo(address);
    }

    @Test
    public void whenMoreThanTenInstances_thenLimitBreached() throws UnknownHostException {
        LoadBalancerFactory lbf = new LoadBalancerFactory(new Randomiser());
        LoadBalancer lb = getLbWithTenInstances(lbf, RANDOM);

        InetAddress ip = InetAddress.getByName(String.format("192.168.0.%s", 11));
        var instance = new Instance(ip);
        assertThatThrownBy(() -> lb.registerInstance(instance)).isInstanceOf(RegistrationException.class);
    }

    private LoadBalancer getLbWithTenInstances(LoadBalancerFactory lbf, LoadBalancerType type) throws UnknownHostException {
        LoadBalancer lb = lbf.getLoadBalancer(type);

        for (int i = 0; i < 10; i++) {
            InetAddress ip = InetAddress.getByName(String.format("192.168.0.%s", i));
            var instance = new Instance(ip);
            lb.registerInstance(instance);
        }
        return lb;
    }

    @Test
    public void whenInstanceRegisteredTwice_thenErrorIsThrown() throws UnknownHostException {
        RandomLoadBalancer lb = new RandomLoadBalancer(new Randomiser());
        InetAddress ip = InetAddress.getByName(String.format("192.168.0.%s", 11));
        var instance = new Instance(ip);
        lb.registerInstance(instance);
        assertThatThrownBy(() -> lb.registerInstance(instance)).isInstanceOf(RegistrationException.class);
    }

    @Test
    public void whenRandomInstanceRequested_thenRandomInstanceShouldReturn() throws UnknownHostException {
        Randomiser randomiser = mock(Randomiser.class);
        when(randomiser.getNumber(anyInt())).thenReturn(4, 9);

        LoadBalancer lb = getLbWithTenInstances(new LoadBalancerFactory(randomiser), RANDOM);

        assertThat(lb.requestInstance().getAddress()).isEqualTo(InetAddress.getByName("192.168.0.4"));
        assertThat(lb.requestInstance().getAddress()).isEqualTo(InetAddress.getByName("192.168.0.9"));
    }

    @Test
    public void whenSequentialBalancerRequested_thenAddressesAreSequential() throws UnknownHostException {
        LoadBalancer lb = getLbWithTenInstances(new LoadBalancerFactory(), SEQUENTIAL);

        assertThat(lb.requestInstance().getAddress()).isEqualTo(InetAddress.getByName("192.168.0.0"));
        assertThat(lb.requestInstance().getAddress()).isEqualTo(InetAddress.getByName("192.168.0.1"));
    }

    @Test
    public void givenSequentialBalancer_whenRegistrationAfterReturn_thenIterationIsSafe() throws UnknownHostException {
        LoadBalancerFactory lbf = new LoadBalancerFactory();
        LoadBalancer lb = lbf.getLoadBalancer(SEQUENTIAL);

        InetAddress ip1 = InetAddress.getByName(String.format("192.168.0.%s", 1));
        InetAddress ip2 = InetAddress.getByName(String.format("192.168.0.%s", 2));
        InetAddress ip3 = InetAddress.getByName(String.format("192.168.0.%s", 44));
        Instance instance1 = new Instance(ip1);
        Instance instance2 = new Instance(ip2);
        Instance instance3 = new Instance(ip3);

        lb.registerInstance(instance1);
        lb.registerInstance(instance2);

        assertThat(lb.requestInstance()).isEqualTo(instance1);
        lb.registerInstance(instance3);
        assertThat(lb.requestInstance()).isEqualTo(instance2);
        assertThat(lb.requestInstance()).isEqualTo(instance3);
    }

    @Test
    public void givenSequentialBalancer_whenLastInstanceRequested_thenFirstIsNext() throws UnknownHostException {
        LoadBalancerFactory lbf = new LoadBalancerFactory();
        LoadBalancer lb = lbf.getLoadBalancer(SEQUENTIAL);

        InetAddress ip1 = InetAddress.getByName(String.format("192.168.0.%s", 1));
        InetAddress ip2 = InetAddress.getByName(String.format("192.168.0.%s", 2));
        InetAddress ip3 = InetAddress.getByName(String.format("192.168.0.%s", 44));
        Instance instance1 = new Instance(ip1);
        Instance instance2 = new Instance(ip2);
        Instance instance3 = new Instance(ip3);

        lb.registerInstance(instance1);
        lb.registerInstance(instance2);
        lb.registerInstance(instance3);

        lb.requestInstance();
        lb.requestInstance();
        lb.requestInstance();
        assertThat(lb.requestInstance()).isEqualTo(instance1);
    }
}
