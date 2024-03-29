/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam & Clifford Palmer
 * @version 03/29/2023
 */

import java.util.ArrayList;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // ArrayList to store the solution, which is returned at the end
        ArrayList<MazeCell> solution = new ArrayList<MazeCell>();

        // Stack to which cells are added from the end to the start
        Stack<MazeCell> reverse = new Stack<MazeCell>();

        // Variable for the next cell in the solution. Initialized to the end of the maze
        MazeCell nextCell = maze.getEndCell();

        // While the next cell in the solution is not the start cell, add each next cell to the
        // Stack
        while(nextCell != maze.getStartCell()){
            reverse.push(nextCell);
            nextCell = nextCell.getParent();
        }
        // Push the start cell onto the stack
        reverse.push(maze.getStartCell());

        // Pop cells from Stack into ArrayList to reverse their order
        while(!reverse.empty()){
            solution.add(reverse.pop());
        }

        // Return the solution ArrayList
        return solution;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // Stack which stores the cells to explore
        Stack<MazeCell> toExplore = new Stack<MazeCell>();
        // Current MazeCell
        MazeCell current = maze.getStartCell();

        // Loop while end of maze hasn't been reached
        while(current != maze.getEndCell()){
            // Create variables for row and col indexes of current cell
            int currentRow = current.getRow();
            int currentCol = current.getCol();

            // Explore cell to the North
            reachIntoCell(current, toExplore, currentRow - 1, currentCol);
            // Explore cell to the East
            reachIntoCell(current, toExplore, currentRow, currentCol + 1);
            // Explore cell to the South
            reachIntoCell(current, toExplore, currentRow + 1, currentCol);
            // Explore cell to the West
            reachIntoCell(current, toExplore, currentRow, currentCol - 1);

            current = toExplore.pop();
        }
        return getSolution();
    }

    public void reachIntoCell(MazeCell parent, Stack toExplore, int row, int col){
        if(maze.isValidCell(row, col)){
            MazeCell cell = maze.getCell(row, col);
            toExplore.add(cell);
            cell.setParent(parent);
            cell.setExplored(true);
        }
    }
    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Queue<MazeCell> toExplore = new LinkedList<MazeCell>();
        MazeCell current = maze.getStartCell();
        while(current != maze.getEndCell()){

            int currentRow = current.getRow();
            int currentCol = current.getCol();

            reachIntoCell(current, toExplore, currentRow - 1, currentCol);
            reachIntoCell(current, toExplore, currentRow, currentCol + 1);
            reachIntoCell(current, toExplore, currentRow + 1, currentCol);
            reachIntoCell(current, toExplore, currentRow, currentCol - 1);

            current = toExplore.remove();
        }
        return getSolution();
    }

    public void reachIntoCell(MazeCell parent, Queue toExplore, int row, int col){
        if(maze.isValidCell(row, col)){
            MazeCell cell = maze.getCell(row, col);
            toExplore.add(cell);
            cell.setParent(parent);
            cell.setExplored(true);
        }
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze2.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);
        System.out.println();
        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
