import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map {

    private int width = 0;
    private int height = 0;
    public Tiles tiles;
    private int tilePixelWidth = 16;
    private int tilePixelHeight = 16;
    private int tileWidth = 6;
    private int tileHeight = 6;
    //private int level;

    private Tiles tileSet;
    private Collectibles collectibles;
    private GameObjects gameObjects;
    private int fillTileID = -1;

    private ArrayList<MappedTile> mappedTiles = new ArrayList<MappedTile>();
    private ArrayList<MappedCollectible> mappedCollectibles = new ArrayList<>();
    private ArrayList<MappedGameObject> mappedGameObjects = new ArrayList<>();

    public ArrayList<Rectangle> collectiblelCollisionRectangles = new ArrayList<>();
    public ArrayList<Rectangle> gameObjCollisionRectangle = new ArrayList<>();

    private int collisionXOffset = 48;
    private int collisionYOffset = 64;

    public Map(File mapFile, Tiles tileSet, File collectiblesFile, Collectibles collectibles, File gameObjFile, GameObjects gameObjects) {

        this.tileSet = tileSet;
        this.collectibles = collectibles;
        this.gameObjects = gameObjects;

        //set up tiles in map
        try {

            Scanner scanner = new Scanner(mapFile);
            int i = 0;

            while(scanner.hasNextLine()) {

                String line = scanner.nextLine();

                if(!line.startsWith("//")) {

                    if(line.contains(":")) {

                        String[] splitString = line.split(":");

                        if(splitString[0].equalsIgnoreCase("Fill")) {

                            fillTileID = Integer.parseInt(splitString[1]);
                            continue;
                        }

                    } else {
                        height++;
                        String[] splitString = line.split(",");
                        width = splitString.length;

                        for(int j = 0; j < splitString.length; j++) {
                            MappedTile mappedTile = new MappedTile(Integer.parseInt(splitString[j]),j*tilePixelWidth,i*tilePixelHeight);
                            mappedTiles.add(mappedTile);
                        }
                        i++;
                    }

                }
                
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //set up collectibles in map
        try {

            Scanner scanner2 = new Scanner(collectiblesFile);
            int i = 0;

            while(scanner2.hasNextLine()) {

                String line = scanner2.nextLine();

                if(!line.startsWith("//")) {

                    String[] splitString = line.split(",");

                    for(int j = 0; j < splitString.length; j++) {
                        if(Integer.parseInt(splitString[j]) != 0) {
                            Rectangle rect = new Rectangle(j*tilePixelWidth*6 - collisionXOffset, i*tilePixelHeight*6 - collisionYOffset, tilePixelWidth*6, tilePixelHeight*6);
                            collectiblelCollisionRectangles.add(rect);
                            MappedCollectible mappedCollectible = new MappedCollectible(Integer.parseInt(splitString[j])-1,j*tilePixelWidth,i*tilePixelHeight);
                            mappedCollectibles.add(mappedCollectible);
                        }
                    }
                    i++;

                }
                
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //set up game objects in map

        try {

            Scanner scanner3 = new Scanner(gameObjFile);
            int i = 0;

            while(scanner3.hasNextLine()) {

                String line = scanner3.nextLine();

                if(!line.startsWith("//")) {

                    String[] splitString = line.split(",");

                    for(int j = 0; j < splitString.length; j++) {
                        if(Integer.parseInt(splitString[j]) != 0) {
                            Rectangle rect = new Rectangle(j*tilePixelWidth*6 - collisionXOffset, i*tilePixelHeight*6 - collisionYOffset, tilePixelWidth*6, tilePixelHeight*6);
                            gameObjCollisionRectangle.add(rect);
                            MappedGameObject mappedGameObject = new MappedGameObject(Integer.parseInt(splitString[j])-1,j*tilePixelWidth,i*tilePixelHeight);
                            mappedGameObjects.add(mappedGameObject);
                        }
                    }
                    i++;

                }
                
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void update(Game game) {
        //update collectible and gameObject animations
        for(int collIndex = 0; collIndex < mappedCollectibles.size(); collIndex++) {
            if(collectibles.getCollectible(collIndex).sprite instanceof AnimatedSprite) {
                AnimatedSprite animSprite = (AnimatedSprite)collectibles.getCollectible(collIndex).sprite;
                animSprite.update(game);
            }
        }
        for(int objIndex = 0; objIndex < mappedGameObjects.size(); objIndex++) {
            if(gameObjects.getGameObject(objIndex).sprite instanceof AnimatedSprite) {
                AnimatedSprite animSprite = (AnimatedSprite)gameObjects.getGameObject(objIndex).sprite;
                animSprite.update(game);
            }
        }

    }

    public void render(RenderHandler renderer, int xZoom, int yZoom) {

        //render tiles
        for(int tileIndex = 0; tileIndex < mappedTiles.size(); tileIndex++) {
            MappedTile mappedTile = mappedTiles.get(tileIndex);
            tileSet.renderTile(mappedTile.id, renderer, mappedTile.x * xZoom, mappedTile.y * yZoom, xZoom, yZoom);
        }

        //render collectibles
        for(int collIndex = 0; collIndex < mappedCollectibles.size(); collIndex++) {
            MappedCollectible mappedCollectible = mappedCollectibles.get(collIndex);
            collectibles.renderCollectible(mappedCollectible.id, renderer, mappedCollectible.x * xZoom, mappedCollectible.y * yZoom, xZoom, yZoom);
        }

        //render gameObjects

        for(int collIndex = 0; collIndex < mappedGameObjects.size(); collIndex++) {
            MappedGameObject mappedGameObject = mappedGameObjects.get(collIndex);
            gameObjects.renderGameObject(mappedGameObject.id, renderer, mappedGameObject.x * xZoom, mappedGameObject.y * yZoom, xZoom, yZoom);
        }


    }

    //Tile ID in the tileSet and the position of the tile in the map
    class MappedTile {

        public int id, x, y;

        public MappedTile(int id, int x, int y) {

            this.id = id;
            this.x = x;
            this.y = y;

        }

    }

    class MappedCollectible {

        public int id, x, y;

        public MappedCollectible(int id, int x, int y) {

            this.id = id;
            this.x = x;
            this.y = y;

        }

    }

    class MappedGameObject {

        public int id, x, y;

        public MappedGameObject(int id, int x, int y) {

            this.id = id;
            this.x = x;
            this.y = y;

        }

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getXBound() {
        return width * tileWidth * tilePixelWidth;
    }

    public int getYBound() {
        return height * tileHeight * tilePixelHeight;
    }

}
