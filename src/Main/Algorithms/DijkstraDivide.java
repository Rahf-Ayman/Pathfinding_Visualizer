package Main.Algorithms;

import Main.Configurations.Constants;

import java.util.LinkedList;

import Main.Controller;
import Main.GraphRelated.Block;
import Main.GraphRelated.Cell;
import Main.GraphRelated.CellState;
import Main.GraphRelated.Direction;
import Main.GraphRelated.Portal;

public class DijkstraDivide extends Algorithm
{
    public Cell updateNode(Cell currNode, Cell nextNode) {


        int x1, x2, y1, y2;
        int distance = 0;
        Direction direction = null;
        x1 = currNode.x;
        x2 = nextNode.x;
        y1 = currNode.y;
        y2 = nextNode.y;

        if (x2 < x1) {
            switch (currNode.direction)
            {
                case UP:
                    distance = 1;
                    break;
                case RIGHT:
                    distance = 2;
                    break;
                case LEFT:
                    distance = 2;
                    break;
                case DOWN:
                    distance = 3;
                    break;
            }
            direction = Direction.UP;
        } else if (x2 > x1) {
            switch (currNode.direction)
            {
                case UP:
                    distance = 3;
                    break;
                case RIGHT:
                    distance = 2;
                    break;
                case LEFT:
                    distance = 2;
                    break;
                case DOWN:
                    distance = 1;
                    break;
            }
            direction = Direction.DOWN;
        }

        if (y2 < y1) {
            switch (currNode.direction)
            {
                case UP:
                    distance = 2;
                    break;
                case RIGHT:
                    distance = 1;
                    break;
                case LEFT:
                    distance = 3;
                    break;
                case DOWN:
                    distance = 2;
                    break;
            }
            direction = Direction.LEFT;
        } else if (y2 > y1) {
            switch (currNode.direction)
            {
                case UP:
                    distance = 2;
                    break;
                case RIGHT:
                    distance = 1;
                    break;
                case LEFT:
                    distance = 3;
                    break;
                case DOWN:
                    distance = 2;
                    break;
            }
            direction = Direction.RIGHT;
        }

        if (currNode == source)
            nextNode.distance = Integer.MAX_VALUE;
        int distanceToCompare = currNode.distance + distance;
        if(currNode.weighted) distanceToCompare += Constants.WEIGHT_COUNT;
        if (distanceToCompare < nextNode.distance) {
            nextNode.distance = distanceToCompare;
            nextNode.parent_x = currNode.x;
            nextNode.parent_y = currNode.y;
            nextNode.direction = direction;
        }
        return nextNode;
    }

    public void runDijkstra(Block block, Cell source, Cell target) {
        
        boolean localpathFound = false ;
        LinkedList<Cell> queue = new LinkedList<>();
        Cell current, tmp;
        queue.clear();
        source.distance = 0;
        source.direction = Direction.RIGHT;
        
        queue.add(source);
        try {
            // pop the waiting queue
            while (!queue.isEmpty() && !localpathFound) {
                Thread.sleep(Constants.THREAD_SLEEP_TIME);
                if (!Constants.isPause) {
                    current = queue.poll();
                    if (current.count > 0 && current.state != CellState.WALL) { // count indicate a dely
                        current.distance += 2;
                        current.count -= 1;
                        queue.add(current);
                    } else {
                        if (!current.equal(source)) {
                            if (current.weighted) {
                                Controller.paintBlock(current.x, current.y, Constants.BORDER, Constants.WEIGHT);
                                current.distance += Constants.WEIGHT_COUNT;
                            } else
                                Controller.paintBlock(current.x, current.y, Constants.BORDER, Constants.VISITED);
                        }

                        //Go to all neighbors of the current state and push into queue if path not found

                        for (int i = 0; i < Constants.NUM_OF_NEIGHBORS && !localpathFound; i++) {
                            if (inBlockRange(block ,current.x + X[i], current.y + Y[i])) {
                                tmp = Controller.CellGrid[current.x + X[i]][current.y + Y[i]];

                                if (tmp.state != CellState.WALL) {
                                    tmp = updateNode(current, tmp);
                                    if (tmp.equal(target) || tmp.state == CellState.UNVISITED
                                            || tmp.state == CellState.WEIGHT) {

                                        if (tmp.count == 0 || tmp.count == Constants.WEIGHT_COUNT) {

                                            if (!tmp.equal(target)) {
                                                Controller.paintBlock(tmp.x, tmp.y, Constants.BORDER, Constants.NEXT_VISIT);
                                                if (tmp.weighted) {
                                                    Controller.paintBlock(tmp.x, tmp.y, Constants.BORDER, Constants.WEIGHT);
                                                }
                                            } else {
                                                Cell tmp_parent;
                                                int min_dist = Integer.MAX_VALUE;
                                                for (int j = 0; j < Constants.NUM_OF_NEIGHBORS && !localpathFound; j++) {
                                                    if (inBlockRange(block ,tmp.x + X[j], tmp.y + Y[j])) {
                                                        tmp_parent = Controller.CellGrid[tmp.x + X[j]][tmp.y + Y[j]];
                                                        if (tmp_parent.distance < min_dist) {
                                                            min_dist = tmp_parent.distance;
                                                            tmp.parent_x = tmp_parent.x;
                                                            tmp.parent_y = tmp_parent.y;
                                                        }
                                                    }
                                                }
                                                // System.out.println("Next cell state trath: " + tmp.x + " " + tmp.y + " " + tmp.parent_x + " " + tmp.parent_y); //Just for debug
                                                tracePath(tmp);
                                                //shortestPath = tmp.distance;
                                                localpathFound = true;
                                                break;
                                            }

                                            Controller.CellGrid[tmp.x][tmp.y].state = CellState.VISITED;
                                            queue.add(tmp);
                                            // System.out.println("Next cell state: " + tmp.x + " " + tmp.y + " " + tmp.parent_x + " " + tmp.parent_y); //Just for debug
                                        }
                                    }
                                }
                            }
                        }
                        
                        
                    }
                } else
                    Thread.sleep(Constants.THREAD_PAUSE_TIME);
            } 
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("tThread interrupted while sleeping");
        }
        

    }

    public boolean inBlockRange(Block block , int r , int c)
    {
        return (r >= block.startX && r < block.startX + Constants.BLOCK_SIZE) && (c >= block.startY && c < block.startY + Constants.BLOCK_SIZE);
    }

    
    @Override
    public void run() {
        
        Block startBlock = Block.getBlock(source);
        Block targetBlock = Block.getBlock(target);

        Cell[][] startGrid = startBlock.cells;
        Cell startNode = startGrid[source.x - startBlock.startX][source.y - startBlock.startY];
        Portal startPortal = startBlock.getNearestPortal(startNode);
        Cell startPortalNode = startGrid[startPortal.x - startBlock.startX][startPortal.y - startBlock.startY];
        
        

        Cell[][] goalGrid = targetBlock.cells;
        Cell goalNode = goalGrid[target.x - targetBlock.startX][target.y - targetBlock.startY];
        Portal goalPortal = targetBlock.getNearestPortal(goalNode);
        Cell goalPortalNode = goalGrid[goalPortal.x - targetBlock.startX][goalPortal.y - targetBlock.startY];
        
        
        runDijkstra(startBlock, startNode , startPortalNode);
        goalPortalNode.state = CellState.SOURCE;
        System.out.println("Start cell state: " + goalPortalNode.x + " " + goalPortalNode.y ); //Just for debug
        Controller.paintBlock(goalPortalNode.x, goalPortalNode.y, Constants.BORDER, Constants.SOURCE);
        runDijkstra(targetBlock, goalPortalNode , goalNode);
        killThread();
    }
    
    public void tracePath(Cell cell) {
        LinkedList<Cell> shortestPath = new LinkedList<Cell>();
        
        Cell pre = null;
        while (cell.state != CellState.SOURCE  ) {
            // System.out.println("Cell: " + cell.x + " " + cell.y ); //Just for debug
            if(pre != null && cell.parent_x == pre.x && cell.parent_y == pre.y) {
                break;
            }
            shortestPath.addFirst(cell);

            pre = cell;
            cell = Controller.CellGrid[cell.parent_x][cell.parent_y];
            
        }
        // System.out.println(shortestPath); //Just for debug
        colorPath(shortestPath, Constants.SHORTEST, true);
        System.out.println("Dijkstra Divide Search Algorithm Finish");
        
    }
    
}
