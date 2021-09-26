/* *****************************************************************************
 *  Name:              Davron Usmonov
 *  Coursera User ID:  123456
 *  Last modified:     9/26/21
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public final class PercolationStats {
    private double tries;
    private double[] allTrials;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Input only positive integers");
        }
        tries = trials;
        allTrials = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation trial = new Percolation(n);
            while (!trial.percolates()) {
                int a = StdRandom.uniform(1, n + 1);
                int b = StdRandom.uniform(1, n + 1);
                if (!trial.isOpen(a, b)) {
                    trial.open(a, b);
                }
            }
            allTrials[i] = 1.0 * trial.numberOfOpenSites() / (n * n);

        }
    }

    public double mean() {
        return StdStats.mean(allTrials);
    }

    public double stddev() {
        return StdStats.stddev(allTrials);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(tries);
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(tries);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats experiment = new PercolationStats(n, t);
        String confidence = "[" + experiment.confidenceLo() + ", " + experiment.confidenceHi()
                + "]";
        System.out.println("mean =                    " + experiment.mean());
        System.out.println("stddev =                  " + experiment.stddev());
        System.out.println("95% confidence interval = " + confidence);
    }
}
