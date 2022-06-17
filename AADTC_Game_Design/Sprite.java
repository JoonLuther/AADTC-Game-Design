import java.awt.image.BufferedImage;

public class Sprite {

    private int width, height;
    private int[] pixels;

    private int frameNum = 1;
    private Sprite[] frames;
    private boolean isAnimated = false;

    public Sprite() {
        
    }

    public Sprite(SpriteSheet sheet, int startX, int startY, int width, int height) {

        this.width = width;
        this.height = height;

        pixels = new int[width * height];
        sheet.getImage().getRGB(startX, startY, width, height, pixels, 0, width);

    }

    public Sprite(SpriteSheet sheet, int startX, int startY, int width, int height, int frameNum) {

        this.width = width;
        this.height = height;
        this.frameNum = frameNum;
        
        if(frameNum > 1) {
            this.isAnimated = true;
        }

        pixels = new int[width * height];
        sheet.getImage().getRGB(startX, startY, width, height, pixels, 0, width);

    }

    public Sprite(BufferedImage image) {

        width = image.getWidth();
        height = image.getHeight();

        pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPixels() {
        return pixels;
    }

}
