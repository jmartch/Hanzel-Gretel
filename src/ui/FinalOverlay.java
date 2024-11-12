package ui;

import gamestates.Playing;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Game;
import static utilz.Constants.UI.URMButtons.URM_SIZE;
import utilz.LoadSave;

public class FinalOverlay {

    private Playing playing;
    private BufferedImage img1, img2;
    private UrmButton nextB;
    private int bgX, bgY, bgW, bgH;

    public FinalOverlay(Playing playing) {
        this.playing = playing;
        initButtons();
        initImg();
    }

    public void initImg() {

        img1 = LoadSave.GetSpriteAtlas(LoadSave.VICTORIA);
        img2 = LoadSave.GetSpriteAtlas(LoadSave.PERDISTE);

        bgW = (int) (img1.getWidth() * 0.5);
        bgH = (int) (img1.getHeight() * 0.5);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (5 * Game.SCALE);
    }

    public void initButtons() {
        int nextX = (int) (760 * Game.SCALE);
        int y = (int) (10 * Game.SCALE);
        nextB = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
    }

    public void update() {
        nextB.update();
    }

    public void draw(Graphics g) {
        if (playing.getNodeActual().isGanador()) 
            g.drawImage(img1, bgX, bgY, bgW, bgH, null);
        else
            g.drawImage(img2, bgX, bgY, bgW, bgH, null);
        nextB.draw(g);
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(nextB, e)) {
            nextB.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(nextB, e)) {
            if (nextB.isMousePressed()) {
                playing.loadNextLevel();
            }
        }

        nextB.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        nextB.setMouseOver(false);

        if (isIn(nextB, e)) {
            nextB.setMouseOver(true);
        }
    }

    private boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

}
