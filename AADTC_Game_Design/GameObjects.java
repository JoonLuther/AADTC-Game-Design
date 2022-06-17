import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameObjects {
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private SpriteSheet spriteSheet;

    public GameObjects (File gameObjectsInfo, SpriteSheet spriteSheet) {

        this.spriteSheet = spriteSheet;
        int i = 0;

        try {
            Scanner scanner = new Scanner(gameObjectsInfo);
            while(scanner.hasNextLine()) {

                String line = scanner.nextLine();

                if(!line.startsWith("//")) {

                    String[] splitString = line.split("-");
                    String gameObjName = splitString[0];
                    int spriteX = Integer.parseInt(splitString[1]);
                    int spriteY = Integer.parseInt(splitString[2]);
                    int isAnimated = Integer.parseInt(splitString[3]);
                    if(isAnimated == 1) {
                        AnimatedSprite animSprite = new AnimatedSprite(this.spriteSheet, 10);
                        animSprite.setFrameRange((spriteY * this.spriteSheet.getWidth()), (spriteY * this.spriteSheet.getWidth()) + this.spriteSheet.getWidth());
                        GameObject gameObject = new GameObject(gameObjName, animSprite);
                        gameObjects.add(gameObject);
                    } else {
                        GameObject gameObject = new GameObject(gameObjName, spriteSheet.getSprite(spriteX, spriteY));
                        gameObjects.add(gameObject);
                    }

                }
                i++;
                
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void renderGameObject(int id, RenderHandler renderer, int xPosition, int yPosition, int xZoom, int yZoom) {

        if(id >= 0 && gameObjects.size() > id) {

            if(gameObjects.get(id).sprite instanceof AnimatedSprite) {

                renderer.renderSprite(gameObjects.get(id).sprite, xPosition, yPosition, xZoom, yZoom);

            } else {

                renderer.renderSprite(gameObjects.get(id).sprite, xPosition, yPosition, xZoom, yZoom);

            }


        } else {

            System.out.println("Collectible ID " + id + " is not within range " + gameObjects.size());
        }

    }

    public GameObject getGameObject(int index) {
        if(index > -1 && index < gameObjects.size()) {

            return gameObjects.get(index);

        } else {

            return gameObjects.get(0);

        }
    }

    public int getGameObjectsLength() {
        return gameObjects.size();
    }

    class GameObject {
        
        public String gameObjName;
        public Sprite sprite;

        public GameObject(String gameObjName, Sprite sprite) {

            this.gameObjName = gameObjName;
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
