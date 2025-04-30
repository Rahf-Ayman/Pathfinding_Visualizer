package Main.GraphRelated;

import java.util.ArrayList;
import java.util.List;
import Main.Configurations.Constants;

public class Block {
    public int startX, startY;
    public List<Portal> portals = new ArrayList<>();
    public Cell[][] cells = new Cell[Constants.BLOCK_SIZE][Constants.BLOCK_SIZE];

    public Block(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
        for (int i = 0; i < Constants.BLOCK_SIZE; i++) {
            for (int j = 0; j < Constants.BLOCK_SIZE; j++) {
                cells[i][j] = new Cell(startX + i, startY + j);
            }
        }
        definePortals();
    }

    public void definePortals() {
        portals.add(new Portal(startX + Constants.BLOCK_SIZE / 2, startY)); // top
        portals.add(new Portal(startX + Constants.BLOCK_SIZE - 1, startY + Constants.BLOCK_SIZE / 2)); // right
        portals.add(new Portal(startX + Constants.BLOCK_SIZE / 2, startY + Constants.BLOCK_SIZE - 1)); // bottom
        portals.add(new Portal(startX, startY + Constants.BLOCK_SIZE / 2)); // left
    }
    
    public static Block getBlock(Cell cell) {
        int bx = cell.x / Constants.BLOCK_SIZE;
        int by = cell.y / Constants.BLOCK_SIZE;
        return new Block(bx * Constants.BLOCK_SIZE, by * Constants.BLOCK_SIZE);
    }
    
    public  Portal getNearestPortal(Cell start) {
        // Get the block containing the cell
        Portal nearest = null;
        double minDistance = Double.MAX_VALUE;
    
        for (Portal portal : this.portals) {
            double distance = Math.sqrt(Math.pow(start.x - portal.x, 2) + Math.pow(start.y - portal.y, 2)); // Euclidean
            // Or: int distance = Math.abs(start.x - portal.x) + Math.abs(start.y - portal.y); // Manhattan
            if (distance < minDistance) {
                minDistance = distance;
                nearest = portal;
            }
        }
    
        return nearest;
    }
    
}
