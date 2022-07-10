package Sequence;

import java.util.LinkedList;
import java.util.List;

public class Sequence {

    private List<Integer> numbers;

    public Sequence() {
        this.numbers = new LinkedList();
    }

    public int size() {
        return numbers.size();
    }

    public Integer get(int elementNumber) {
        return numbers.get(elementNumber);
    }

    public Sequence append(int number) {
        this.numbers.add(number);
        return this;
    }

    @Override
    public String toString() {
        return "Sequence{" +
                "numbers=" + numbers +
                '}';
    }
}
