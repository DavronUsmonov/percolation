/* *****************************************************************************
 *  Name:              Davron Usmonov
 *  Coursera User ID:  123456
 *  Last modified:     9/26/21
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int numOfNodes, top, bottom, length, opened;
    private WeightedQuickUnionUF uf;

    public Percolation(int n) {
        if (n > 0) {
            grid = new boolean[n][n];
            numOfNodes = n * n + 2;
            top = n * n;
            bottom = n * n + 1;
            uf = new WeightedQuickUnionUF(numOfNodes);
            length = n;
            opened = 0;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    private boolean inBounds(int row, int col) {
        if (row > length || row < 1 || col > length || col < 1) {
            return false;
        }
        return true;
    }

    private int ufValue(int row, int col) {
        return length * (row - 1) + col - 1;
    }

    public void open(int row, int col) {
        if (inBounds(row, col)) {
            if (!isOpen(row, col)) {
                grid[row - 1][col - 1] = true;
                opened++;
            }
            if (row == 1) {
                uf.union(col - 1, top);
            }
            if (inBounds(row - 1, col) && isOpen(row - 1, col)) {
                uf.union(ufValue(row, col), ufValue(row - 1, col));
            }
            if (inBounds(row, col - 1) && isOpen(row, col - 1)) {
                uf.union(ufValue(row, col), ufValue(row, col - 1));
            }
            if (inBounds(row + 1, col) && isOpen(row + 1, col)) {
                uf.union(ufValue(row, col), ufValue(row + 1, col));
            }
            if (inBounds(row, col + 1) && isOpen(row, col + 1)) {
                uf.union(ufValue(row, col), ufValue(row, col + 1));
            }
            if (row == length) {
                uf.union(ufValue(row, col), bottom);
            }
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public boolean isOpen(int row, int col) {
        if (inBounds(row, col)) {
            return grid[row - 1][col - 1];
        }
        else {
            throw new IllegalArgumentException(
                    "yo " + row + " " + col);
        }
    }

    public boolean isFull(int row, int col) {
        if (inBounds(row, col)) {
            if (uf.find(ufValue(row, col)) == uf.find(top)) {
                return true;
            }
            return false;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public int numberOfOpenSites() {
        return opened;

    }

    public boolean percolates() {
        if (uf.find(bottom) == uf.find((top))) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

    }


}
