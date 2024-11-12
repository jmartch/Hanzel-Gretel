package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Game;
import objects.DoorObject;
import objects.Spike;
import utilz.HelpMethods;
import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetPlayerSpawn;

public class Level {

    private BufferedImage img;
    private int[][] lvlData;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;
    private ArrayList<DoorObject> doors;
    private ArrayList<Spike> spikes;

    public Level(BufferedImage img) {
        this.img = img;
        createLevelData();
        calcLvlOffsets();
        calcPlayerSpawn();
        createDoors();
        createSpikes();
    }

    private void createSpikes() {
        spikes = HelpMethods.GetSpikes(img);
    }

    private void createDoors() {
        doors = HelpMethods.GetDoors(img);
    }

    public ArrayList<DoorObject> getDoors() {
        return doors;
    }

    private void calcPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }

    public int getLvlOffset() {
        return maxLvlOffsetX;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }

}
