package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Game;
import static utilz.Constants.UI.PauseButtons.SOUND_SIZE;
import utilz.LoadSave;

public class QuestionOverlay {

    private Playing playing;
    private DirButton derecha, izquierda;
    private BufferedImage img, img1, img2, info;
    private int bgX, bgY, bgW, bgH;
    private char respuesta;
    private boolean selected = false;

    public QuestionOverlay(Playing playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }

    public void initButtons() {
        int derechaX = (int) (350 * Game.SCALE);
        int izquierdaX = (int) (450 * Game.SCALE);
        int y = (int) (250 * Game.SCALE);
        derecha = new DirButton(derechaX, y, SOUND_SIZE, SOUND_SIZE, 0);
        izquierda = new DirButton(izquierdaX, y, SOUND_SIZE, SOUND_SIZE, 1);
    }

    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.DIRECCION);
        img1 = LoadSave.GetSpriteAtlas(LoadSave.VICTORIA);
        img2 = LoadSave.GetSpriteAtlas(LoadSave.PERDISTE);
    }

    public void update() {
        if (selected) {
            derecha.update();
            izquierda.update();
        }
    }

    private boolean isIn(DirButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void draw(Graphics g) {
        if (selected) {
            g.drawImage(img, 130, (int) (Game.GAME_HEIGHT - 430 * Game.SCALE), 550, 550, null);
            derecha.draw(g);
            izquierda.draw(g);
        } else {

            switch (playing.getNodeActual().getPregunta().getId()) {
                case 25:
                    info = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_25);
                    break;
                case 9:
                    info = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_9);
                    break;
                case 7:
                    info = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_7);
                    break;
                case 8:
                    info = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_8);
                    break;
                case 3:
                    info = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_3);
                    break;
                case 4:
                    info = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_4);
                    break;
                case 11:
                    info = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_11);
                    break;
                case 10:
                    info = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_10);
                    break;
                case 23:
                    info = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_23);
                    break;
                case 40:
                    info = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_40);
                    break;
                case 52:
                    info = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_52);
                    break;
                case 47:
                    info = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_47);
                    break;
                case 54:
                    info = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_54);
                    break;
                case 27:
                    info = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_26);
                    break;
                case 32:
                    info = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_32);
                    break;
            }
            g.drawImage(info, 130, (int) (Game.GAME_HEIGHT - 420 * Game.SCALE), 550, 550, null);

        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(derecha, e)) {
            derecha.setMousePressed(true);
        } else if (isIn(izquierda, e)) {
            izquierda.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(derecha, e)) {
            if (derecha.isMousePressed()) {
                playing.setNodeActual(playing.getTree().mover(playing.getNodeActual(), "derecha"));;
                playing.setLevelCompleted(true);
            }
        } else if (isIn(izquierda, e)) {
            if (izquierda.isMousePressed()) {
                playing.setNodeActual(playing.getTree().mover(playing.getNodeActual(), "izquierda"));;
                playing.setLevelCompleted(true);
            }
        }

        derecha.resetBools();
        izquierda.resetBools();

    }

    public void mouseMoved(MouseEvent e) {
        derecha.setMouseOver(false);
        izquierda.setMouseOver(false);

        if (isIn(derecha, e)) {
            derecha.setMouseOver(true);
        } else if (isIn(izquierda, e)) {
            izquierda.setMouseOver(true);
        }
    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_A) {
            respuesta = 'a';
        } else if (e.getKeyCode() == KeyEvent.VK_B) {
            respuesta = 'b';
        } else if (e.getKeyCode() == KeyEvent.VK_C) {
            respuesta = 'c';
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            respuesta = 'd';
        } else {
            respuesta = 'z';
        }
        System.out.println(Character.compare(respuesta, 'z'));
        if (Character.compare(respuesta, 'z') < 0) {
            System.out.println("entro 1?");
            if (playing.getNodeActual().getPregunta().respuestaCorrecta(respuesta)) {
                if (playing.getNodeActual().left == null & playing.getNodeActual().right == null) {
                    if (playing.getNodeActual().isGanador()) {
                        System.out.println("has ganado tio");
                        playing.setObjectPicked(false);
                        playing.setFin(true);
                    } else {
                        System.out.println("F ");
                        playing.setObjectPicked(false);
                        playing.setFin(true);
                    }
                } else {
                    if (playing.getLvlIndex() < 3)
                        selected = true;
                }
            }
        }

    }

}
