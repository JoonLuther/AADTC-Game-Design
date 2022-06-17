import java.awt.image.BufferedImage;

public class AnimatedSprite extends Sprite implements GameObject {

    private Sprite[] frames;
    private int speed = 0;
    private int counter = 0;

    private int startidx = 0;
    private int endidx = 0;
    private int currentSprite;


    public AnimatedSprite(BufferedImage image) {
        super(image);
    }

    public AnimatedSprite(SpriteSheet spriteSheet, int speed) {
        frames = spriteSheet.getLoadedSprites();
        this.speed = speed;
    }

    public void update(Game game) {
        if(counter >= speed) {  
            counter = 0; 
            this.incrementSprite();
        }
        counter++;
    }

    public void incrementSprite() {
        currentSprite++;
        if(currentSprite >= endidx) {
            currentSprite = startidx;
        }
    }

    public void setFrameRange(int start, int end) {
        this.startidx = start;
        this.endidx = end;
        reset();

    }

    public void reset() {
        counter = 0;
        currentSprite = startidx;
    }

    public void render(RenderHandler renderHandler, int xZoom, int yZoom) {

    }

    public int getWidth() {
        return frames[currentSprite].getWidth();
    }

    public int getHeight() {
        return frames[currentSprite].getHeight();
    }

    public int[] getPixels() {
        return frames[currentSprite].getPixels();
    }
    
}
