package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import entities.Player;
import java.awt.geom.Rectangle2D;
import java.util.List;
import levels.LevelManager;
import logica_lab.Cuestionario;
import logica_lab.Node;
import logica_lab.Pregunta;
import logica_lab.Respuesta;
import logica_lab.Tree;
import main.Game;
import objects.ObjectManager;
import ui.FinalOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import ui.QuestionOverlay;
import utilz.LoadSave;
import static utilz.Constants.Environment.*;
import static utilz.LoadSave.PLAYING_BG_IMG;

public class Playing extends State implements Statemethods {

    private Player player;
    private ObjectManager objectManager;
    private Tree tree;
    private Node nodeActual;
    private Cuestionario c;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private QuestionOverlay questionOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private FinalOverlay finalOverlay;
    private boolean paused = false;
    private boolean lvlCompleted = false;
    private boolean question = false;
    private boolean fin = false;

    private boolean objectPicked = false;
    private int objValue;

    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int maxLvlOffsetX;

    private BufferedImage backgroundImg, bigCloud, smallCloud;
    private int[] smallCloudsPos;
    private Random rnd = new Random();

    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.BRUJA);
        bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        smallCloudsPos = new int[8];
        for (int i = 0; i < smallCloudsPos.length; i++) {
            smallCloudsPos[i] = (int) (90 * Game.SCALE) + rnd.nextInt((int) (100 * Game.SCALE));
        }

        calcLvlOffset();
        loadStartLevel();
        createCuestionario();
        createTree();
    }
    
    public void createTree(){
        for (Pregunta p : c.getPreguntas()) {
            if (tree.raiz == null) {
                tree.raiz = new Node(p);
            } else {
                tree.addNode(p);
            }
        }
        tree.addNodeGanador(tree.raiz);
        nodeActual = tree.raiz;
    }
    
    
    public void createCuestionario(){
        c.agregarPregunta(new Pregunta("¿Cuál es el país más grande del mundo en términos de superficie?", List.of(
                new Respuesta("A) China", false),
                new Respuesta("B) Canadá", false),
                new Respuesta("C) Rusia", true),
                new Respuesta("D) Estados Unidos", false)
        ), 25));

        c.agregarPregunta(new Pregunta("¿Qué órgano del cuerpo humano es responsable de bombear sangre?", List.of(
                new Respuesta("A) Riñones", false),
                new Respuesta("B) Pulmones", false),
                new Respuesta("C) Hígado", false),
                new Respuesta("D) Corazón", true)
        ), 9));

        c.agregarPregunta(new Pregunta("¿Cuál es el metal más utilizado en la industria de la construcción?", List.of(
                new Respuesta("A) Aluminio", false),
                new Respuesta("B) Hierro", false),
                new Respuesta("C) Cobre", false),
                new Respuesta("D) Acero", true)
        ), 7));

        c.agregarPregunta(new Pregunta("¿Cuál es el río más largo del mundo?", List.of(
                new Respuesta("A) Amazonas", false),
                new Respuesta("B) Nilo", true),
                new Respuesta("C) Yangtsé", false),
                new Respuesta("D) Misisipi", false)
        ), 8));

        c.agregarPregunta(new Pregunta("¿Cuál es el idioma más hablado en el mundo?", List.of(
                new Respuesta("A) Inglés", false),
                new Respuesta("B) Español", false),
                new Respuesta("C) Mandarín", true),
                new Respuesta("D) Hindi", false)
        ), 3));

        c.agregarPregunta(new Pregunta("¿Quién escribió \"Don Quijote de la Mancha\"?", List.of(
                new Respuesta("A) Gabriel García Márquez", false),
                new Respuesta("B) Lope de Vega", false),
                new Respuesta("C) Miguel de Cervantes", true),
                new Respuesta("D) Federico García Lorca", false)
        ), 4));

        c.agregarPregunta(new Pregunta("¿Cuál es la capital de Australia?", List.of(
                new Respuesta("A) Sídney", false),
                new Respuesta("B) Melbourne", false),
                new Respuesta("C) Canberra", true),
                new Respuesta("D) Perth", false)
        ), 11));

        c.agregarPregunta(new Pregunta("¿Cuál es el océano más grande del mundo?", List.of(
                new Respuesta("A) Atlántico", false),
                new Respuesta("B) Pacífico", true),
                new Respuesta("C) Índico", false),
                new Respuesta("D) Ártico", false)
        ), 10));

        c.agregarPregunta(new Pregunta("¿Cuál es el país más poblado del mundo?", List.of(
                new Respuesta("A) Estados Unidos", false),
                new Respuesta("B) India", false),
                new Respuesta("C) China", true),
                new Respuesta("D) Indonesia", false)
        ), 23));

        c.agregarPregunta(new Pregunta("¿Quien fue el libertador de America?", List.of(
                new Respuesta("A) Jefferson Torres", false),
                new Respuesta("B) Rocio del Rosario Ramos Rodriguez", false),
                new Respuesta("C) Simon Bolivar", true),
                new Respuesta("D) Karl Marx", false)
        ), 40));

        c.agregarPregunta(new Pregunta("¿Qué autor escribió 'Cien años de soledad'?", List.of(
                new Respuesta("A) Mario Vargas Llosa", false),
                new Respuesta("B) Gabriel García Márquez", true),
                new Respuesta("C) Julio Cortázar", false),
                new Respuesta("D) Pablo Neruda", false)
        ), 52));

        c.agregarPregunta(new Pregunta("¿Cuál es el animal terrestre más rápido del mundo?", List.of(
                new Respuesta("A) Tigre", false),
                new Respuesta("B) León", false),
                new Respuesta("C) Guepardo", true),
                new Respuesta("D) Elefante", false)
        ), 47));

        c.agregarPregunta(new Pregunta("¿Qué país es conocido por la Torre Eiffel?", List.of(
                new Respuesta("A) Francia", true),
                new Respuesta("B) Alemania", false),
                new Respuesta("C) Italia", false),
                new Respuesta("D) España", false)
        ), 54));

        c.agregarPregunta(new Pregunta("¿Cuál es el planeta más grande del sistema solar?", List.of(
                new Respuesta("A) Tierra", false),
                new Respuesta("B) Júpiter", true),
                new Respuesta("C) Saturno", false),
                new Respuesta("D) Marte", false)
        ), 26));

        c.agregarPregunta(new Pregunta("¿En qué año se llegó a la luna por primera vez?", List.of(
                new Respuesta("A) 1965", false),
                new Respuesta("B) 1969", true),
                new Respuesta("C) 1972", false),
                new Respuesta("D) 1959", false)
        ), 32));
    }

    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    public void loadStartLevel() {
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }
    
    public void changeBackground(){
        switch (getNodeActual().getPregunta().getId()) {
                case 25:
                    backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.BRUJA);
                    break;
                case 9:
                case 40:
                    backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.CAMINOOSCURO);
                    break;
                case 7:
                case 11:
                case 27:
                case 52:
                    backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.CAMINOCLARO);
                    break;
                case 8:
                    backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.RIO);
                    break;
                case 3:
                    backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.CISNE);
                    break;
                case 4:
                case 54:
                    backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.COCINA);
                    break;
                case 10:
                    backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.RIOMORADO);
                    break;
                case 23:
                case 32:
                case 47:
                    backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.RIOROSADO);
                    break;
            }
    }

    public void loadNextLevel() {
        resetAll();
        changeBackground();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        objectManager = new ObjectManager(this);
        tree = new Tree();
        c = new Cuestionario();
        player = new Player(100, 200, (int) (80 * Game.SCALE), (int) (60 * Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
        questionOverlay = new QuestionOverlay(this);
        finalOverlay = new FinalOverlay(this);
    }

    @Override
    public void update() {

        if (paused) {
            pauseOverlay.update();
        } else if (lvlCompleted) {
            levelCompletedOverlay.update();
        } else if(objectPicked){
            questionOverlay.update();
        }else if(fin){
            finalOverlay.update();
        }else {
            levelManager.update();
            player.update();
            checkCloseToBorder();
        }

    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder) {
            xLvlOffset += diff - rightBorder;
        } else if (diff < leftBorder) {
            xLvlOffset += diff - leftBorder;
        }

        if (xLvlOffset > maxLvlOffsetX) {
            xLvlOffset = maxLvlOffsetX;
        } else if (xLvlOffset < 0) {
            xLvlOffset = 0;
        }

    }

    @Override
    public void draw(Graphics g) {
        
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.setColor(new Color(255, 255, 255, 150));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

//        drawClouds(g);
        levelManager.draw(g, xLvlOffset);
        objectManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (lvlCompleted) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            levelCompletedOverlay.draw(g);
        }else if(objectPicked){
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            questionOverlay.draw(g);
        }else if(fin){
            g.setColor(new Color(255, 255, 255, 50));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            finalOverlay.draw(g);
        }

    }

    private void drawClouds(Graphics g) {

        for (int i = 0; i < 3; i++) {
            g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH - (int) (xLvlOffset * 0.3), (int) (204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
        }

        for (int i = 0; i < smallCloudsPos.length; i++) {
            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLvlOffset * 0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
        }

    }

    public void checkDoorTouched(Rectangle2D.Float hitbox) {
        objectManager.checkObjectTouched(hitbox);
    }
    
    public void checkSpikesTouched(Player p) {
        objectManager.checkSpikesTouched(p);
    }

    public void resetAll() {
        questionOverlay.setSelected(false);
        paused = false;
        lvlCompleted = false;
        objectPicked = false;
        player.resetAll();
        objectManager.resetAllObjects();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.setAttacking(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(objectPicked){
            questionOverlay.keyPressed(e);
        }else{
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_W:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_W:
                player.setJump(false);
                break;
        }

    }

    public void mouseDragged(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseDragged(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused) {
            pauseOverlay.mousePressed(e);
        } else if (lvlCompleted) {
            levelCompletedOverlay.mousePressed(e);
        } else if(objectPicked){
            questionOverlay.mousePressed(e);
        }else if(fin){
            finalOverlay.mousePressed(e);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseReleased(e);
        } else if (lvlCompleted) {
            levelCompletedOverlay.mouseReleased(e);
        }else if(objectPicked){
            questionOverlay.mouseReleased(e);
        }else if(fin){
            finalOverlay.mouseReleased(e);
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseMoved(e);
        } else if (lvlCompleted) {
            levelCompletedOverlay.mouseMoved(e);
        }else if(objectPicked){
            questionOverlay.mouseMoved(e);
        }else if(fin){
            finalOverlay.mouseMoved(e);
        }

    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    public void setLevelCompleted(boolean lvlCompleted) {
        this.lvlCompleted = lvlCompleted;
    }
    
    public void setQuestion(boolean question) {
        this.question = question;
    }

    public void unpauseGame() {
        paused = false;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void unobjectGame() {
        objectPicked = false;
    }

    public void setObjectPicked(boolean objectPicked) {
        this.objectPicked = objectPicked;
    }

    public void setObjectValue(int objValue) {
        this.objValue = objValue;
    }

    public int getObjValue() {
        return objValue;
    }

    public Node getNodeActual() {
        return nodeActual;
    }

    public void setNodeActual(Node nodeActual) {
        this.nodeActual = nodeActual;
    }

    public Tree getTree() {
        return tree;
    }
    
    public void unQuestion(){
        this.question = false;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }
    
    public int getLvlIndex(){
        return levelManager.getLevelIndex();
    }

}
