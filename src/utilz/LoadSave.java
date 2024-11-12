package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

public class LoadSave {

    public static final String PLAYER_ATLAS = "hanzel.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String DIR_BUTTONS = "dir_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG = "background_menu.png";
    public static final String PLAYING_BG_IMG = "playing_bg_img.png";
    public static final String BIG_CLOUDS = "big_clouds.png";
    public static final String SMALL_CLOUDS = "small_clouds.png";
    public static final String DOOR_ATLAS = "door.png";
    public static final String COMPLETED_IMG = "completed_sprite.png";
    public static final String TRAP_ATLAS = "trap_atlas.png";
    public static final String OPTIONS_MENU = "options_background.png";
    public static final String DEATH_SCREEN = "death_screen.png";
    public static final String QUESTION_SCREEN = "question_screen.png";
    
    //preguntas imgs
    public static final String QUESTION_25 = "1.png";
    public static final String QUESTION_9 = "2.png";
    public static final String QUESTION_7 = "3.png";
    public static final String QUESTION_8 = "4.png";
    public static final String QUESTION_3 = "5.png";
    public static final String QUESTION_4 = "6.png";
    public static final String QUESTION_11 = "7.png";
    public static final String QUESTION_10 = "8.png";
    public static final String QUESTION_23 = "9.png";
    public static final String QUESTION_40 = "10.png";
    public static final String QUESTION_52 = "11.png";
    public static final String QUESTION_47 = "12.png";
    public static final String QUESTION_54 = "13.png";
    public static final String QUESTION_26 = "14.png";
    public static final String QUESTION_32 = "15.png";
    
    public static final String VICTORIA = "victoria.png";
    public static final String PERDISTE = "perdiste.png";
    public static final String DIRECCION = "direccion.png";
    public static final String INFO = "instrucciones.png";
    
    public static final String BRUJA = "caramelo.png";
    public static final String RIOMORADO = "riomorado.png";
    public static final String RIOROSADO = "riorosado.png";
    public static final String COCINA = "cocina.png";
    public static final String CAMINOCLARO = "caminoclaro.png";
    public static final String CAMINOOSCURO = "caminooscuro.png";
    public static final String CISNE = "cisne.png";
    public static final String RIO = "rio.png";

    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/lvlsimg");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for (int i = 0; i < filesSorted.length; i++) {
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals((i + 1) + ".png")) {
                    filesSorted[i] = files[j];
                }

            }
        }

        BufferedImage[] imgs = new BufferedImage[filesSorted.length];

        for (int i = 0; i < imgs.length; i++)
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return imgs;
    }
}
