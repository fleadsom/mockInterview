package Sequence.Fibonacci;

import Sequence.Sequence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FibonacciTest {

    FibonacciFactory factory;

    static Logger logger = LoggerFactory.getLogger(FibonacciTest.class);

    @BeforeEach
    void setUp() {
        this.factory = mock(FibonacciFactory.class);
        when(factory.getSequence(anyInt())).thenReturn(new Sequence().append(1));

        Sequence sequence = mock(Sequence.class);
        when(sequence.toString()).thenReturn("1,1,2,3,5");

        logger.info("Setup complete");
    }

    @Test
    public void TestPrintFibonacci() {
        assertThat(factory.getSequence(5).toString()).isEqualTo("1,1,2,3,5");
    }

    @Test
    public void testNextElement() {
        FibonacciSequenceGenerator generator = new FibonacciSequenceGenerator();
        Sequence sequence = generator.generateSequence(1);
        assertThat(sequence.get(0)).isEqualTo(1);
    }
}
