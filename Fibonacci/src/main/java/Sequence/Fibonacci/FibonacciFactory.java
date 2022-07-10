package Sequence.Fibonacci;

import Sequence.Sequence;
import Sequence.SequenceGenerator;

public class FibonacciFactory {

    SequenceGenerator sg;

    public FibonacciFactory() {
        this.sg = new FibonacciSequenceGenerator();
    }

    public Sequence getSequence(int sequenceLength) {
        return sg.generateSequence(sequenceLength);
    }
}
