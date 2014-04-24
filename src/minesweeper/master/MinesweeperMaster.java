/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.master;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gregory ANNE <ganne@digitaleo.com>
 */
public class MinesweeperMaster {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(MinesweeperMaster.class.getName());
        String inputFile = "C-small-attempt7.in";
        String outputFile = "C-small-attempt7.out";
        Integer nbTestCase = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            File file = new File(outputFile);

            // if file doesnt exists, then create it
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFile)));
            String line;
            while ((line = reader.readLine()) != null) {
                if (nbTestCase == null) {
                    nbTestCase = new Integer(line);
                } else {
                    StringTokenizer stringTokenizer = new StringTokenizer(line);
                    Solver solver = new Solver(
                            new Integer(stringTokenizer.nextToken()),
                            new Integer(stringTokenizer.nextToken()),
                            new Integer(stringTokenizer.nextToken())
                    );
                    if (solver.solve()) {
                        String t = solver.toString();
                        writer.write("Case #" + Solver.solverNumber + ":\n" + t);
                    } else {
                        writer.write("Case #" + Solver.solverNumber + ": \nImpossible");
                    }
                    if (!nbTestCase.equals(Solver.solverNumber)) {
                        writer.write("\n");
                    }
                }
            }
            reader.close();
            writer.close();
        } catch (FileNotFoundException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private static class Solver {

        public static Integer solverNumber = 0;
        private int row = 0;
        private int column = 0;
        private int mines = 0;

        public Solver(Integer row, Integer column, Integer mines) {
            solverNumber++;
            this.row = row;
            this.column = column;
            this.mines = mines;
        }

        public boolean solve() {
            if (mines == 0) {
                return true;
            }
            if (row < 3 && column < 3) {
//                Logger.getAnonymousLogger().log(Level.SEVERE, "Too Small" + row + "-" + column + "-" + mines);
                return false;
            }

            if ((row == 1 && column >= 3) || (row >= 3 && column == 1)) {
//                if (mines > Math.max(row, column) - 2) {
//                    Logger.getAnonymousLogger().log(Level.SEVERE, "Too Small" + row + "-" + column + "-" + mines);
//                }
                return mines <= Math.max(row, column) - 2;
            }
//            if (mines > (row * column - 4)) {
//                Logger.getAnonymousLogger().log(Level.SEVERE, "Too Small" + row + "-" + column + "-" + mines);
//            }
            return mines <= (row * column - 4);
        }

        @Override
        public String toString() {
            StringBuffer result = new StringBuffer();
            int x = 0, y = 0, nbDot = (row * column) - mines;

            try {
                String[][] tmp = new String[row][column];
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        tmp[i][j] = "*";
                    }
                }
                int gap = nbDot / 2;
                int i = 0, j = 0;
                if (column < 2 || row < 2) {
                    for (i = 0; nbDot > 0 && i < row; i++) {
                        for (j = 0; nbDot > 0 && j < column; j++) {
                            tmp[i][j] = ".";
                            nbDot--;
                        }
                    }
                } else if (nbDot < column + 2) {
                    for (i = 0; nbDot > 0 && i < 2; i++) {
                        for (j = 0; nbDot > 0 && j < gap; j++) {
                            tmp[i][j] = ".";
                            nbDot--;
                        }
                    }
                } else if (nbDot < row + 2) {
                    for (i = 0; nbDot > 0 && i < gap; i++) {
                        for (j = 0; nbDot > 0 && j < 2; j++) {
                            tmp[i][j] = ".";
                            nbDot--;
                        }
                    }
                } else {
                    for (i = 0; nbDot > 0 && i < row; i++) {
                        for (j = 0; nbDot > 0 && j < column; j++) {
                            tmp[i][j] = ".";
                            nbDot--;
                        }
                    }
                }
                tmp[y][x] = "c";
                if (nbDot > 0) {
                    if (row == 2) {
                        i = 0;
                    } else {
                        j = 0;
                    }
                    tmp[i][j] = ".";
                }

                for (i = 0; i < row; i++) {
                    for (j = 0; j < column; j++) {
                        result.append(tmp[i][j]);
                    }

                    if (i < row - 1) {
                        result.append("\n");
                    }
                }
            } catch (Exception e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            }

            return result.toString();
        }
    }
}
