import java.awt.image.BufferedImage;

public class SpriteSheet {
    
    private int[] pixels;
    private BufferedImage image;
    public final int SIZEX;
    public final int SIZEY;
    private Sprite[] loadedSprites = null;
    private boolean spritesLoaded = false;

    private int spriteSizeX;
    private int spriteSizeY;

    public SpriteSheet(BufferedImage sheetImage) {
        image = sheetImage;
        SIZEX = sheetImage.getWidth();
        SIZEY = sheetImage.getHeight();

        pixels = new int[SIZEX*SIZEY];
        pixels = sheetImage.getRGB(0, 0, SIZEX, SIZEY, pixels, 0, SIZEX);
    }

    public void loadSprites(int spriteSizeX, int spriteSizeY) { //load everything into loadedSprites
       
       this.spriteSizeX = spriteSizeX;
       this.spriteSizeY = spriteSizeY;
       
        loadedSprites = new Sprite[(SIZEX/spriteSizeX) * (SIZEY/spriteSizeY)];

        int spriteID =  0;

        for (int y = 0; y < SIZEY; y+= spriteSizeY) {
            for (int x= 0; x < SIZEX; x+= spriteSizeX) {
                
                loadedSprites[spriteID] = new Sprite(this, x, y, spriteSizeX, spriteSizeY);
                spriteID++;

            }
        }

        spritesLoaded = true;
    }

    public Sprite getSprite(int x, int y) {

        if(spritesLoaded) {

            int spriteID = x + y * (SIZEX/spriteSizeX);

            if(spriteID < loadedSprites.length) {
                return loadedSprites[spriteID];
            } else {
                System.out.println("SpriteID of " + spriteID + " is out of range with a maximum of " + loadedSprites.length + " .");
            }

        } else {
            System.out.println("NO LOADED SPRITES");
        }

        return null;
    }

    public Sprite[] getLoadedSprites() {
        return loadedSprites;
    }

    public int[] getPixels() {
        return pixels;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWidth() {

        return(SIZEX/spriteSizeX);

    }

    public int getHeight() {

        return(SIZEY/spriteSizeY);

    }

}
