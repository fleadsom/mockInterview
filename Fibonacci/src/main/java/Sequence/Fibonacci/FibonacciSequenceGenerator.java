package Sequence.Fibonacci;

import Sequence.Sequence;
import Sequence.SequenceGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FibonacciSequenceGenerator implements SequenceGenerator {

    Sequence sequence = new Sequence();
    private static Logger logger = LoggerFactory.getLogger(FibonacciSequenceGenerator.class.getName());

    @Override
    public Sequence generateSequence(int sequenceLength) {
        return generateNext(sequenceLength);
    }

    private Sequence generateNext(int sequenceLength) {

        logger.info("Sequence size is {}", this.sequence.size());

        if (this.sequence.size() >= sequenceLength) {
            return this.sequence.append(this.sequence.get(sequenceLength) + this.sequence.get(sequenceLength-1));
        }
        else generateNext(sequenceLength);
        return null;
    }
}
