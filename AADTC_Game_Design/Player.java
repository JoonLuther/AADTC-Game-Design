public class Player implements GameObject {

    private Rectangle playerRectangle;
    private int speed = 7;
    private int animationSpeed = 10;
    private AnimatedSprite animatedSprite;
    private int direction = -1;
    private boolean didMove = false;
    private boolean hadMoved = false;
    //private boolean hasMoved = false;

    public Player(SpriteSheet spriteSheet) {
        playerRectangle = new Rectangle(0,0,16,16);
        playerRectangle.generateGraphics(3,0xFF00FF90);
        this.animatedSprite = new AnimatedSprite(spriteSheet, animationSpeed);
    }

    //called every possible time
    public void render(RenderHandler renderer, int xZoom, int yZoom) {
        //renderer.renderRectangle(playerRectangle,xZoom,yZoom);
        renderer.renderSprite(animatedSprite, playerRectangle.x, playerRectangle.y, xZoom, yZoom);
    }

    //called at 60fps
    public void update(Game game) {

        KeyBoardListener keyListener = game.getKeyListener();

        didMove = false;
        int newDirection = direction;

        if(keyListener.up()) {
            newDirection = 2;
            didMove = true;
            if(playerRectangle.y - speed > 0) {
                if(collision(game) == false) playerRectangle.y -= speed;
            }
        }
        else if(keyListener.down()) {
            newDirection = 0;
            didMove = true;
            if(playerRectangle.y + playerRectangle.h * 6 + speed < game.getMap().getYBound()) {
                if(collision(game) == false) playerRectangle.y += speed;
            }
        }
        else if(keyListener.left()) {
            newDirection = 3;
            didMove = true;
            if(playerRectangle.x - speed > 0) {
                if(collision(game) == false) playerRectangle.x -= speed;
            }
        }
        else if(keyListener.right()) {
            newDirection = 1;
            didMove = true;
            if(playerRectangle.x + playerRectangle.w * 6 + speed < game.getMap().getXBound()) {
                if(collision(game) == false) playerRectangle.x += speed;
            }
        }

        //change direction
        if(didMove == true) {
            if(newDirection != direction) {
                animatedSprite.setFrameRange(newDirection*5+1, newDirection*5+5);
            } else if(hadMoved == false) {
                animatedSprite.setFrameRange(newDirection*5+1, newDirection*5+5);
            }
        }
        
        if(didMove == false && newDirection != -1) {
            animatedSprite.setFrameRange(newDirection*5, newDirection*5+1);
        }

        hadMoved = didMove;

        direction = newDirection;

        animatedSprite.update(game);
        
        updateCamera(game, game.getRenderer().getCamera());


    }

    public void updateCamera(Game game, Rectangle camera) {
        if(playerRectangle.x > 500 && playerRectangle.x < game.getMap().getXBound() - 500) {
            camera.x = playerRectangle.x - (camera.w/2);
        }
        if(playerRectangle.y > 400 && playerRectangle.y < game.getMap().getYBound() - 400) {
            camera.y = playerRectangle.y - (camera.h/2);
        }
    
    }

    public Rectangle getRect() {
        return playerRectangle;
    }

    public boolean collision(Game game) {
        Rectangle rect = new Rectangle(playerRectangle.x, playerRectangle.y, playerRectangle.w ,playerRectangle.h);
        switch(this.direction) {
            case 0: rect.y += this.speed; break;
            case 1: rect.x += this.speed; break;
            case 2: rect.y -= this.speed; break;
            case 3: rect.x -= this.speed; break;
            default: break;
        }

        for(int i = 0; i < game.getMap().collectiblelCollisionRectangles.size(); i++) {
            if(rect.intersects(game.getMap().collectiblelCollisionRectangles.get(i))) return true;
        }

        for(int i = 0; i < game.getMap().gameObjCollisionRectangle.size(); i++) {
            if(rect.intersects(game.getMap().gameObjCollisionRectangle.get(i))) return true;
        }

        return false;

    }

}

