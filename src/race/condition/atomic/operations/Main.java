package race.condition.atomic.operations;

import java.util.Random;

/**
 * @author sony
 * @description main
 * @since 2026.03.11
 **********************************************************************************************************************/
public class Main {

    public static void main(String[] args) {
        Metrics metrics = new Metrics();

        BusinessLogin businessLoginThread1 = new BusinessLogin(metrics);
        BusinessLogin businessLoginThread2 = new BusinessLogin(metrics);

        MetricsPrinter metricsPrinter = new MetricsPrinter(metrics);

        businessLoginThread1.start();
        businessLoginThread2.start();
        metricsPrinter.start();
    }

    public static class MetricsPrinter extends Thread {

        private Metrics metrics;

        public MetricsPrinter(Metrics metrics) {
            this.metrics = metrics;
        }
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                double currentAverage = metrics.getAverage();
                System.out.println("Average response time: " + currentAverage);
            }
        }

    }
    public static class BusinessLogin extends Thread {

        private Metrics metrics;

        private Random random = new Random();

        public BusinessLogin(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {

            while (true) {
                long start = System.currentTimeMillis();

                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                }

                long end = System.currentTimeMillis();

                metrics.addSample(end - start);
            }
        }

    }
    public static class Metrics {
        private long count = 0;

        private volatile double average = 0.0;

        public synchronized void addSample(long sample) {
            double currentSum = average * count;
            count++;
            average = (currentSum + sample) / count;
        }
        public double getAverage() {
            return average;
        }

    }
}