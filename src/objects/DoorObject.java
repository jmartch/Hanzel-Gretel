package objects;

import main.Game;
import static utilz.Constants.ObjectConstants.*;

public class DoorObject extends GameObject{
    
    public DoorObject(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        createHitbox();
    }
    
    public void createHitbox(){
        initHitbox((int)(141 * 0.2), (int)(272 * 0.2));
        xDrawOffset = (int)(111 * 0.2 * Game.SCALE);
        yDrawOffset = (int)(42 * 0.2 * Game.SCALE);
        hitbox.y += yDrawOffset + (int)(Game.SCALE ) * 0.5;
        
        
        hitbox.x += xDrawOffset/2;
        
    }
    
    public void update(){
        updateAnimationTick();
    }
    
}
