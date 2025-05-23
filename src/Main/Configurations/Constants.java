package Main.Configurations;

import Main.Algorithms.Algorithm;
import com.jfoenix.controls.JFXButton;

// Class contains all the constant used through out the project
public class Constants
{
    // Design preference
    public static final int ROW = 30, COL = 40;


    //Colors
    public static final String BORDER = "black";
    public static final String WALL = "#202040";
    public static final String UNVISITED = "#fffff0";
    public static final String SOURCE =   "/Icons/source.png";
    public static final String TARGET =  "/Icons/target.png";
    public static final String VISITED = "#f96d80"; 
    public static final String NEXT_VISIT = "#fab7b7"; 
    public static final String SHORTEST = "#f1f5a2";
    public static final String WEIGHT = "#0e9aa7";
    public static final String WEIGHT_IN_PATH = "#ffc40c";



    // Algorithm related
    public static int THREAD_SLEEP_TIME = 30; // milisecs, use this to increase or decrease visualization's speed.
    public static int THREAD_PAUSE_TIME = 1000;
    public static int THREAD_SLEEP_TIME_MAZE = 10;

    public static final int NUM_OF_NEIGHBORS = 4;
    public static final int UNVISITED_WEIGHT = Integer.MAX_VALUE;
    public static final int WALL_WEIGHT = -1;
    public static final int WEIGHT_WEIGHT = -1;
    public static final int WEIGHT_COUNT = 5;
    public static final int COUNT = 0;

    public static Algorithm currentThread = null;
    public static JFXButton stopButton = null;
    public static JFXButton mazeButton = null;
    public static boolean isPause = false;
}
