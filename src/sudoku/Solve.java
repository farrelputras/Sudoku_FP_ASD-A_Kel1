//package sudoku;
//
//import java.util.Stack;
//
//public class Solve {
//
//    Puzzle puzzle = new Puzzle();
//
//    int[][] numbers = puzzle.numbers;
//    boolean[][] isGiven = puzzle.isGiven;
//
////    private boolean solve() {
////        Stack<Cell> cellStack = new Stack<>();
////        int curRow = 0, curCol = 0, curNum = 1, time = 0;
////
////        while (cellStack.size() < SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE) {
////            time++;
////
////            if (isGiven[curRow][curCol]) {
////                cellStack.push(new Cell(curRow, curCol, numbers[curRow][curCol]));
////                int[] next = getNextCell(curRow, curCol);
////                curRow = next[0];
////                curCol = next[1];
////                continue;
////            }
////
////            boolean foundValidNum = false;
////            for (curNum = curNum; curNum <= SudokuConstants.GRID_SIZE; curNum++) {
////                if (isValidPlacement(curRow, curCol, curNum)) {
////                    foundValidNum = true;
////                    break;
////                }
////            }
////
////            if (foundValidNum && curNum <= SudokuConstants.GRID_SIZE) {
////                numbers[curRow][curCol] = curNum;
////                cellStack.push(new Cell(curRow, curCol, curNum));
////                int[] next = getNextCell(curRow, curCol);
////                curRow = next[0];
////                curCol = next[1];
////                curNum = 1;
////            } else {
////                if (!cellStack.isEmpty()) {
////                    Cell cell = cellStack.pop();
////                    while (isGiven[cell.getRow()][cell.getCol()]) {
////                        if (!cellStack.isEmpty()) {
////                            cell = cellStack.pop();
////                        } else {
////                            System.out.println("Number of steps: " + time);
////                            return false;
////                        }
////                    }
////                    curRow = cell.getRow();
////                    curCol = cell.getCol();
////                    curNum = cell.getNum() + 1;
////                    numbers[curRow][curCol] = 0;
////                } else {
////                    System.out.println("Number of steps: " + time);
////                    return false;
////                }
////            }
////        }
////
////        System.out.println("Number of steps taken: " + time);
////        return true;
////    }
//
//    public boolean solveSudoku() {
//        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
//            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
//                if (!isGiven[row][col]) {
//                    for (int num = 1; num <= SudokuConstants.GRID_SIZE; ++num) {
//                        if (puzzle.isSafe(row, col, num)) {
//                            numbers[row][col] = num;
//                            if (solveSudoku()) {
//                                return true;
//                            }
//                            numbers[row][col] = 0;
//                        }
//                    }
//                    return false; // Backtrack if no number fits
//                }
//            }
//        }
//        return true; // Puzzle is solved
//    }
//
////    private boolean isValidPlacement(int row, int col, int num) {
////        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
////            if (numbers[row][i] == num || numbers[i][col] == num) {
////                return false;
////            }
////        }
////
////        int subgridRowStart = row - row % SudokuConstants.SUBGRID_SIZE;
////        int subgridColStart = col - col % SudokuConstants.SUBGRID_SIZE;
////        for (int i = subgridRowStart; i < subgridRowStart + SudokuConstants.SUBGRID_SIZE; i++) {
////            for (int j = subgridColStart; j < subgridColStart + SudokuConstants.SUBGRID_SIZE; j++) {
////                if (numbers[i][j] == num) {
////                    return false;
////                }
////            }
////        }
////        return true;
////    }
////
////    private int[] getNextCell(int row, int col) {
////        int[] nextCell = new int[2];
////        col++;
////        if (col == SudokuConstants.GRID_SIZE) {
////            col = 0;
////            row++;
////        }
////        nextCell[0] = row;
////        nextCell[1] = col;
////        return nextCell;
////    }
//}
