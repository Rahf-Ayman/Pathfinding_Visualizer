package Main.Algorithms;

import Main.Configurations.Constants;
import Main.Controller;
import Main.GraphRelated.Cell;
import Main.GraphRelated.CellState;
import Main.GraphRelated.Direction;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class AStar extends Algorithm {

    public int heuristic(Cell a, Cell b) {
        // Manhattan Distance
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public Cell updateNode(Cell currNode, Cell nextNode) {
        int x1 = currNode.x, y1 = currNode.y;
        int x2 = nextNode.x, y2 = nextNode.y;
        Direction direction = null;
        int cost = 1;

        if (x2 < x1) direction = Direction.UP;
        else if (x2 > x1) direction = Direction.DOWN;
        else if (y2 < y1) direction = Direction.LEFT;
        else if (y2 > y1) direction = Direction.RIGHT;

        int gCost = currNode.g + cost + (currNode.weighted ? Constants.WEIGHT_COUNT : 0);
        if (gCost < nextNode.g) {
            nextNode.g = gCost;
            nextNode.h = heuristic(nextNode, target);
            nextNode.f = nextNode.g + nextNode.h;
            nextNode.parent_x = currNode.x;
            nextNode.parent_y = currNode.y;
            nextNode.direction = direction;
        }

        return nextNode;
    }
    public void resetGrid() {
        for (int i = 0; i < Controller.CellGrid.length; i++) {
            for (int j = 0; j < Controller.CellGrid[0].length; j++) {
                Cell cell = Controller.CellGrid[i][j];
                cell.g = Integer.MAX_VALUE;
                cell.h = 0;
                cell.f = Integer.MAX_VALUE;
                cell.parent_x = -1;
                cell.parent_y = -1;
                cell.direction = null;
    
                if (cell.state != CellState.SOURCE &&
                    cell.state != CellState.TARGET &&
                    cell.state != CellState.WALL &&
                    cell.state != CellState.WEIGHT) {
                    cell.state = CellState.UNVISITED;
                    Controller.paintBlock(cell.x, cell.y, Constants.BORDER, Constants.UNVISITED);
                }
            }
        }
    }
    

    @Override
    public void run() {
        resetGrid();
        PriorityQueue<Cell> openSet = new PriorityQueue<>(Comparator.comparingInt(c -> c.f));
        Cell current, tmp;
        source.g = 0;
        source.h = heuristic(source, target);
        source.f = source.g + source.h;
        source.direction = Direction.RIGHT;
        openSet.add(source);

        try {
            while (!openSet.isEmpty() && !pathFound) {
                Thread.sleep(Constants.THREAD_SLEEP_TIME);
                if (!Constants.isPause) {
                    current = openSet.poll();
                    if (current.state != CellState.SOURCE && current.state != CellState.TARGET) {
                        if (current.weighted) {
                            Controller.paintBlock(current.x, current.y, Constants.BORDER, Constants.WEIGHT);
                        } else {
                            Controller.paintBlock(current.x, current.y, Constants.BORDER, Constants.VISITED);
                        }
                    }

                    for (int i = 0; i < Constants.NUM_OF_NEIGHBORS && !pathFound; i++) {
                        int nx = current.x + X[i];
                        int ny = current.y + Y[i];

                        if (inRange(nx, ny)) {
                            tmp = Controller.CellGrid[nx][ny];
                            if (tmp.state != CellState.WALL) {
                                tmp = updateNode(current, tmp);
                                if (tmp.state == CellState.TARGET) {
                                    tracePath(tmp);
                                    pathFound = true;
                                    break;
                                } else if (tmp.state == CellState.UNVISITED || tmp.state == CellState.WEIGHT) {
                                    openSet.remove(tmp); // Remove if already in openSet to update priority
                                    openSet.add(tmp);
                                    if (tmp.state != CellState.WEIGHT)
                                        Controller.paintBlock(tmp.x, tmp.y, Constants.BORDER, Constants.NEXT_VISIT);
                                    tmp.state = CellState.VISITED;
                                }
                            }
                        }
                    }
                } else {
                    Thread.sleep(Constants.THREAD_PAUSE_TIME);
                }
            }

            if (!pathFound) {
                System.out.println("No path available!\nA* Search Algorithm Finish\n");
                killThread();
            }
        } catch (Exception e) {
            System.out.println("Thread interrupted while sleeping");
        }
    }

    public void tracePath(Cell cell) {
        LinkedList<Cell> path = new LinkedList<>();
        while (cell.state != CellState.SOURCE) {
            path.addFirst(cell);
            cell = Controller.CellGrid[cell.parent_x][cell.parent_y];
        }
        colorPath(path, Constants.SHORTEST, true);
        System.out.println("A* Search Algorithm Finished.");
        killThread();
    }
}

