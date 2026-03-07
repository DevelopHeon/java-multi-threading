package example3;

import java.math.BigInteger;

/**
 * @since       2026.03.07
 * @author      sony
 * @description main2
 **********************************************************************************************************************/
public class Main2 {

    public static void main(String[] args) {
        Thread thread = new Thread(new LongComputationTask(new BigInteger("2123123"), new BigInteger("1012425")));

        thread.setDaemon(true);

        thread.start();
        thread.interrupt();
    }

    private static class LongComputationTask implements Runnable {
        BigInteger base;
        BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base+"^"+power+" = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO ; i.compareTo(power) !=0 ; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }

            return result;
        }
    }
}