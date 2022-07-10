package Util;

import java.util.Random;

public class Randomiser {

    public int getNumber(int limit) {
        Random random = new Random();
        return random.nextInt(limit);
    }
}
