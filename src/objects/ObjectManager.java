package objects;

import entities.Player;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import levels.Level;
import levels.LevelManager;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;

public class ObjectManager {

    private Playing playing;
    private BufferedImage[][] doorImgs;
    private BufferedImage spikeImg;
    private ArrayList<DoorObject> doors;
    private int objValue;
    private ArrayList<Spike> spikes;
    //private ArrayList<Question> questions;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
    }

    public void checkSpikesTouched(Player p) {
        for (Spike s : spikes) {
            if (s.getHitbox().intersects(p.getHitbox())) {
                p.kill();
            }
        }
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {

        for (DoorObject jo : doors) {
            if (jo.isActive()) {
                if (hitbox.intersects(jo.getHitbox())) {
                    jo.setActive(false);
                    playing.setObjectPicked(true);
                }
            }
        }
    }

    public int getObjValue() {
        return objValue;
    }

    public void setObjValue(int objValue) {
        this.objValue = objValue;
    }

    public void loadObjects(Level newLevel) {
        doors = new ArrayList<>(newLevel.getDoors());
        spikes = newLevel.getSpikes();
    }

    private void loadImgs() {

        BufferedImage doorSprite = LoadSave.GetSpriteAtlas(LoadSave.DOOR_ATLAS);
        doorImgs = new BufferedImage[2][3];

        for (int j = 0; j < doorImgs.length; j++) {
            for (int i = 0; i < doorImgs[j].length; i++) {
                doorImgs[j][i] = doorSprite.getSubimage(360 * i, 360 * j, 360, 360);
            }
        }

        spikeImg = LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);
    }

    public void update() {

        for (DoorObject jo : doors) {
            if (jo.isActive()) {
                jo.update();
            }
        }
    }

    public void update(int[][] lvlData, Player player) {

        boolean isAnyActive = false;
        for (DoorObject c : doors) {
            if (c.isActive()) {
                c.update();
                isAnyActive = true;
            }
        }

        if (!isAnyActive) {
            playing.setLevelCompleted(true);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawDoors(g, xLvlOffset);
        drawTraps(g, xLvlOffset);
    }

    public void drawDoors(Graphics g, int xLvlOffset) {
        for (DoorObject jo : doors) {
            if (jo.isActive()) {
                g.drawImage(doorImgs[1][jo.getAniIndex()],
                        (int) (jo.getHitbox().x - jo.getxDrawOffset() - xLvlOffset),
                        (int) (jo.getHitbox().y - jo.getyDrawOffset()),
                        DOOR_WIDTH, DOOR_HEIGHT, null);
            }
        }
    }

    private void drawTraps(Graphics g, int xLvlOffset) {
        for (Spike s : spikes) {
            g.drawImage(spikeImg, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);
        }

    }

    public void resetAllObjects() {

        loadObjects(playing.getLevelManager().getCurrentLevel());

        for (DoorObject d : doors) {
            d.reset();
        }
    }

}
