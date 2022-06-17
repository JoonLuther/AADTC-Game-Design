import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Tiles {
    
    private SpriteSheet spriteSheet;
    private ArrayList<Tile> tilesList = new ArrayList<Tile>();
    
    //Only works if sprites in spriteSheet have been loaded
    public Tiles(File tilesFile, SpriteSheet spriteSheet) {

        this.spriteSheet = spriteSheet;

        try {
            Scanner scanner = new Scanner(tilesFile);
            while(scanner.hasNextLine()) {

                String line = scanner.nextLine();

                if(!line.startsWith("//")) {

                    String[] splitString = line.split("-");
                    String tileName = splitString[0];
                    int spriteX = Integer.parseInt(splitString[1]);
                    int spriteY = Integer.parseInt(splitString[2]);
                    Tile tile = new Tile(tileName, spriteSheet.getSprite(spriteX,spriteY));
                    tilesList.add(tile);

                }
                
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void renderTile(int tileID, RenderHandler renderer, int xPosition, int yPosition, int xZoom, int yZoom) {
        
        if(tileID>= 0 && tilesList.size() > tileID) {

            renderer.renderSprite(tilesList.get(tileID).sprite, xPosition, yPosition, xZoom, yZoom);

        } else {
            System.out.println("TileID " + tileID + " is not within range " + tilesList.size());
        }
    }

    public Tile getTile(int index) {

        if(index > -1 && index < tilesList.size()) {

            return tilesList.get(index);

        } else {

            return tilesList.get(0);

        }

    }

    public int getTilesLength() {
        return tilesList.size();
    }

    class Tile {
        
        public String tileName;
        public Sprite sprite;

        public Tile(String tileName, Sprite sprite) {

            this.tileName = tileName;
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
