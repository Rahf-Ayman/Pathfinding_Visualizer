package Main.GraphRelated;

public class Cell {
    public int x, y, weight;
    public int distance; // g
    public int parent_x, parent_y; // stores the parent cell
    public Direction direction;
    public boolean weighted; // does the cell have weight?
    public int count; // number of visits or used in algorithms
    
    public CellState state; // state of the cell (e.g., Unvisited, Visited, Blocked, Start, End)

    public int f, g, h; // f = g + h, where g is the actual distance and h is the heuristic distance
    
    // Constructor
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.state = CellState.UNVISITED;
        this.distance = Integer.MAX_VALUE;
        this.weighted = false;
        this.count = 0;
        this.g = Integer.MAX_VALUE; // set g to the largest value initially
        this.h = 0; // h will be calculated later
        this.f = Integer.MAX_VALUE; // f = g + h, calculated later
    }

    // Set the parent coordinates
    public void setParent(int x, int y) {
        this.parent_x = x;
        this.parent_y = y;
    }

    // Update the f value based on g and h
    public void updateF() {
        this.f = this.g + this.h;
    }

    // Calculate h (heuristic distance) using Manhattan or Euclidean method as needed
    public void calculateH(Cell target) {
        this.h = Math.abs(this.x - target.x) + Math.abs(this.y - target.y); // Manhattan distance
    }

    // Method to check if the cell is walkable or not (based on weight or state)
    public boolean isWalkable() {
        return state != CellState.BLOCKED && !weighted; // Example: if the cell is not blocked and not weighted
    }
    
}


