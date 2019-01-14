package com.rats;

import com.badlogic.gdx.math.Vector2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Maze {

    private int height;
    private int width;
    private final int tile_hw = 128;

    private int worldcoord_xmin = 0;
    private int worldcoord_ymin = 0;
    private int worldcoord_xmax;
    private int worldcoord_ymax;

    private int[][] maze;

    private int entranceRow;
    private int exitRow;

    private Consumable[] consumables;
    private Trap[] traps;

    Random rand;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.worldcoord_xmax = (width-1)*tile_hw;
        this.worldcoord_ymax = (height-1)*tile_hw;
        rand = new Random();
    }

    public void createRandomMaze() {

        BufferedWriter out = null;

        try {
            FileWriter fstream = new FileWriter("core/assets/tilemaps/random_maze.tmx", false);
            out = new BufferedWriter(fstream);
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            out.write("<map version=\"1.0\" tiledversion=\"2017.07.26\" orientation=\"orthogonal\" renderorder=\"right-down\" " +
                    "width=\"" + width + "\" height=\"" + height +"\" tilewidth=\"" + tile_hw + "\" tileheight=\""+ tile_hw +"\" nextobjectid=\"1\">\n");
            out.write("<tileset firstgid=\"1\" source=\"walltile.tsx\"/>\n");
            out.write("<tileset firstgid=\"2\" source=\"floortile.tsx\"/>\n");
            out.write("<layer name=\"Tile Layer 1\" width=\"" + width + "\" height=\"" + height + "\">\n");
            out.write("<data encoding=\"csv\">\n");

            maze = generateRandomMaze();
            String mazeString = "";
            for (int [] row : maze){
                for (int elem : row) {
                    mazeString += elem + ",";
                }
            }
            mazeString = mazeString.substring(0, mazeString.length() - 1);
            out.write(mazeString);

            out.write("</data>\n");
            out.write(" </layer>\n");
            out.write(" </map>\n");
            out.close();
        }
        catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }


    public int[][] generateRandomMaze() {
            int[][] mazeArray = new int[height][width];
            entranceRow = rand.nextInt(height - 1)+1;
            exitRow = rand.nextInt(height - 1)+1;
            for (int i = 0; i < width; i++) {
                mazeArray[0][i] = 1;
                mazeArray[height-1][i] = 1;
            }
            for (int r = 1; r < height-1; r++) {
                for (int c = 0; c < width; c++) {
                    if (c == 0 || c == width-1) {
                        if ((r == entranceRow && c == 0) || (r == exitRow && c == width-1)) {
                            mazeArray[r][c] = 2;
                        } else {
                            mazeArray[r][c] = 1;
                        }
                    } else if (r % 2 == 0) {
                        if (c % 2 == 0) {
                            mazeArray[r][c] = 1;
                        } else {
                            mazeArray[r][c] = 2;
                        }
                    } else {
                        mazeArray[r][c] = 2;
                    }
                }
            }

        //for each wall, choose additional wall to be added top/bottom/left/or right
            for (int r = 1; r < height-1; r++) {
                for (int c = 1; c < width-1; c++) {
                    if (mazeArray[r][c] == 1) {
                        int dir = rand.nextInt(4)+1;
                        switch (dir) {
                            case 1: //Up
                                mazeArray[r-1][c] = 1;
                                break;
                            case 2: //Down
                                mazeArray[r+1][c] = 1;
                                break;
                            case 3: //Left
                                mazeArray[r][c-1] = 1;
                                break;
                            case 4: //Right
                                mazeArray[r][c+1] = 1;
                                break;
                                }
                            }
                        }
                    }

            if (!isSolvable(mazeArray, new boolean[height][width], entranceRow, 0, exitRow, width-1, false)) {
                return generateRandomMaze();
            }
            else {
                return mazeArray;
            }
    }

    public boolean isSolvable(int[][] mazeArray, boolean[][] visited, int currRow, int currCol, int targetRow, int targetCol, boolean ret) {

        visited[currRow][currCol] = true;
        if (currRow == targetRow && currCol == targetCol) {
            return true;
        }

        if (currRow -1 >= 0 && !visited[currRow-1][currCol] && mazeArray[currRow-1][currCol] == 2) {
            ret = ret || isSolvable(mazeArray, visited, currRow-1, currCol, targetRow, targetCol, ret);
        }
        if (currRow +1 < height && !visited[currRow+1][currCol] && mazeArray[currRow+1][currCol] == 2) {
            ret = ret || isSolvable(mazeArray, visited, currRow+1, currCol, targetRow, targetCol, ret);
        }
        if (currCol -1 >= 0 && !visited[currRow][currCol-1] && mazeArray[currRow][currCol-1] == 2) {
            ret = ret || isSolvable(mazeArray, visited, currRow, currCol-1, targetRow, targetCol, ret);
        }
        if (currCol+1 < width && !visited[currRow][currCol+1] && mazeArray[currRow][currCol+1] == 2) {
            ret = ret || isSolvable(mazeArray, visited, currRow, currCol+1, targetRow, targetCol, ret);
        }
        return ret;
    }


    public void generateConsumables() {
        int num_consumables = rand.nextInt(10)+1;
        consumables = new Consumable[num_consumables];
        for (int i=0; i < num_consumables; i++) {
            int xpos = 0;
            int ypos = 0;
            while (!isWalkable(new Vector2(xpos, ypos))) {
                xpos = rand.nextInt(worldcoord_xmax);
                ypos = rand.nextInt(worldcoord_ymax);
            }
            consumables [i] = new Consumable(xpos, ypos, rand.nextInt(5));
        }

    }

    public void generateTraps() {
        int num_traps = rand.nextInt(5)+1;
        traps = new Trap[num_traps];
        for (int i=0; i < num_traps; i++) {
            int xpos = rand.nextInt(worldcoord_xmax);
            int ypos = rand.nextInt(worldcoord_ymax);
            traps[i] = new Trap(xpos, ypos, rand.nextInt(3));
        }
    }

    public Consumable[] getConsumables() {
        return consumables;
    }

    public Trap[] getTraps() {
        return traps;
    }

    public Vector2 getEntranceCoords() {
        Vector2 ec = new Vector2(0, worldcoord_ymax - entranceRow*tile_hw);
        return ec;
    }

    public Vector2 getExitCoords() {
        Vector2 ec = new Vector2(worldcoord_xmax - tile_hw, worldcoord_ymax - exitRow*tile_hw);
        return ec;
    }

    public boolean atExit(Vector2 worldCoords) {
        int tilex = (int) Math.round(worldCoords.x / tile_hw);
        int tiley = (height-1) - (int) Math.round(worldCoords.y / tile_hw);
        return tilex == width -1 && tiley == exitRow;
    }

    public boolean isWalkable(Vector2 worldCoords) {
        if (worldCoords.x >= worldcoord_xmax || worldCoords.y >= worldcoord_ymax || worldCoords.x < 0 || worldCoords.y < 0) {
            return false;
        }
        int tilex = (int) Math.round(worldCoords.x / tile_hw);
        int tiley = (height-1) - (int) Math.round(worldCoords.y / tile_hw);
        if (maze[tiley][tilex] == 2) {
            return true;
        }
        return false;
    }
}
