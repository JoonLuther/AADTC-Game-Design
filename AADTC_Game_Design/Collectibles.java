import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Collectibles {
    private ArrayList<Collectible> collectibles = new ArrayList<>();
    private SpriteSheet spriteSheet;

    public Collectibles (File collectiblesInfo, SpriteSheet spriteSheet) {

        this.spriteSheet = spriteSheet;
        int i = 0;

        try {
            Scanner scanner = new Scanner(collectiblesInfo);
            while(scanner.hasNextLine()) {

                String line = scanner.nextLine();

                if(!line.startsWith("//")) {

                    String[] splitString = line.split("-");
                    String collectibleName = splitString[0];
                    int spriteX = Integer.parseInt(splitString[1]);
                    int spriteY = Integer.parseInt(splitString[2]);
                    int isAnimated = Integer.parseInt(splitString[3]);
                    if(isAnimated == 1) {
                        AnimatedSprite animSprite = new AnimatedSprite(this.spriteSheet, 10);
                        animSprite.setFrameRange((spriteY * this.spriteSheet.getWidth()), (spriteY * this.spriteSheet.getWidth()) + this.spriteSheet.getWidth());
                        Collectible collectible = new Collectible(collectibleName, animSprite);
                        collectibles.add(collectible);
                    } else {
                        Collectible collectible = new Collectible(collectibleName, spriteSheet.getSprite(spriteX, spriteY));
                        collectibles.add(collectible);
                    }

                }
                i++;
                
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void renderCollectible(int id, RenderHandler renderer, int xPosition, int yPosition, int xZoom, int yZoom) {

        if(id >= 0 && collectibles.size() > id) {

            renderer.renderSprite(collectibles.get(id).sprite, xPosition, yPosition, xZoom, yZoom);

        } else {

            System.out.println("Collectible ID " + id + " is not within range " + collectibles.size());
        }

    }

    public Collectible getCollectible(int index) {
        if(index > -1 && index < collectibles.size()) {

            return collectibles.get(index);

        } else {

            return collectibles.get(0);

        }
    }

    public int getCollectiblesLength() {
        return collectibles.size();
    }

    class Collectible {
        
        public String collectibleName;
        public Sprite sprite;

        public Collectible(String collectibleName, Sprite sprite) {

            this.collectibleName = collectibleName;
            this.sprite = sprite;

        }

        public int getWidth() {
            return sprite.getWidth();
        }

        public int getHeight() {
            return sprite.getHeight();
        }

    }
    
}
