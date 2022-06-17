import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.lang.Runnable;
import java.lang.Thread;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

//import java.awt.*

public class Game extends JFrame implements Runnable {

	private Rectangle rect1, rect2;

	public static int alpha = 0xFFFF00DC; //static can be created and accessed w out having an instance of Game
	private Canvas canvas = new Canvas();
	private RenderHandler renderer;
	private int xZoom = 6;
	private int yZoom = 6;
	
	private SpriteSheet sheet;
	private SpriteSheet playerSheet;
	private SpriteSheet collSheet;
	private SpriteSheet gameObjSheet;

	private Tiles tiles;
	private Collectibles collectibles;
	private GameObjects gameObjs;
	private Map map;
	private Map currentMap;

	private GameObject[] objects;
	private Player player;

	private KeyBoardListener keyListener = new KeyBoardListener();
	private MouseEventListener mouseListener = new MouseEventListener(this);

	public Game() {

		//Make our program shutdown when we exit out.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set the position and size of our frame.
		setBounds(0,0, 1000, 800);

		//Put our frame in the center of the screen.
		setLocationRelativeTo(null);

		//Add our graphics compoent
		add(canvas);

		//Make our frame visible.
		setVisible(true);

		//Create our object for buffer strategy.
		canvas.createBufferStrategy(3);
		renderer = new RenderHandler(getWidth(), getHeight());

		//load assets
		BufferedImage sheetImage = loadImage("resources/Tiles-Sheet.png");
		BufferedImage playerSheetImage = loadImage("resources/Blue_Sprite-Sheet.png");
		BufferedImage collSheetImage = loadImage("resources/Star_Sprite-Sheet.png");
		BufferedImage gameObjImage = loadImage("resources/Game_Objects-Sheet.png");

		sheet = new SpriteSheet(sheetImage);
		playerSheet = new SpriteSheet(playerSheetImage);
		collSheet = new SpriteSheet(collSheetImage);
		gameObjSheet = new SpriteSheet(gameObjImage);

		sheet.loadSprites(16,16);
		playerSheet.loadSprites(16,16);
		collSheet.loadSprites(16,16);
		gameObjSheet.loadSprites(16,16);

		tiles = new Tiles(new File("resources/Tiles.txt"), sheet);
		collectibles = new Collectibles(new File("resources/Collectibles.txt"), collSheet);
		gameObjs = new GameObjects(new File("resources/GameObjects.txt"), gameObjSheet);
		map = new Map(new File ("resources/Map.txt"), tiles, new File("resources/CollectiblesMap.txt"), collectibles, new File("resources/GameObjectsMap.txt"), gameObjs);
		currentMap = map;

		//Add listeners
		canvas.addKeyListener(keyListener);
		canvas.addFocusListener(keyListener);
		canvas.addMouseListener(mouseListener);
		canvas.addMouseMotionListener(mouseListener);

		//Load Objects
		objects = new GameObject[1];
		player = new Player(playerSheet);
		objects[0] = player;

	}

	public void update() {

		for(int i = 0; i < objects.length; i++) {

			objects[i].update(this);

		}

		currentMap.update(this);

	}

	private BufferedImage loadImage(String path) {

		try { //prevent crashing

			BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
			BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);

			return formattedImage;

		} catch(IOException exception) {

			exception.printStackTrace();
			return null;

		}

	}

	public void render() {
			//canvas.setBackground(Color.BLUE);
			BufferStrategy bufferStrategy = canvas.getBufferStrategy();
			Graphics graphics = bufferStrategy.getDrawGraphics();
			super.paint(graphics);

			currentMap.render(renderer, xZoom, yZoom);


			for (int i = 0; i < objects.length; i++) {
				objects[i].render(renderer, xZoom, yZoom);
			}

			renderer.render(graphics);

			graphics.dispose();
			bufferStrategy.show();
			renderer.clear();

	}

	public void run() {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		int i = 0;
		int x = 0;

		long lastTime = System.nanoTime(); //long 2^63
		double nanoSecondConversion = 1000000000.0 / 60; //60 frames per second
		double changeInSeconds = 0;

		while(true) {
			long now = System.nanoTime();

			changeInSeconds += (now - lastTime) / nanoSecondConversion;
			while(changeInSeconds >= 1) {
				update();
				changeInSeconds--;
			}

			render();
			lastTime = now;
		}
	}

	public Map getMap() {
		return this.currentMap;
	}

	public static void main(String[] args) 

	{
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}

	public KeyBoardListener getKeyListener() {
        return keyListener;
    }

	public RenderHandler getRenderer() {
		return renderer;
	}

	public MouseEventListener getMouseListener() {
		return mouseListener;
	}
}