public class Rectangle {
    
    public int x, y, w, h;
    private int[] pixels;

    Rectangle(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    Rectangle() {

        this(0,0,0,0);
        
    }

    public void generateGraphics(int color) {

        pixels = new int[w*h];

        for(int y = 0; y < h; y++) {

            for(int x = 0; x < w; x++) {

                pixels[x + y * w] = color;

            }
            
        }
        
    }

    public void generateGraphics(int borderWidth, int color) {

        pixels = new int[w*h];

        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = Game.alpha;
        }

        for(int y = 0; y < borderWidth; y++) {
            for(int x = 0; x < w; x++) {
                pixels[x + y * w] = color;
            }
        }

        for(int y = 0; y < h; y++) {
            for(int x = 0; x < borderWidth; x++) {
                pixels[x + y * w] = color;
            }
        }

        for(int y = 0; y < h; y++) {
            for(int x = w - borderWidth; x < w; x++) {
                pixels[x + y * w] = color;   
            }           
        }

        for(int y = h - borderWidth; y < h; y++) {
            for(x = 0; x < w; x++) {
                pixels[x + y * w] = color;
            }
        }
    }

    public int[] getPixels() {

        if(pixels != null) {

            return pixels;

        } else {

            System.out.println("Attempted to retrieve pixels from a Rectangle without generated graphics.");
            return null;

        }

    }

    public Boolean intersects(Rectangle rect) {
        float r1x = this.x;
        float r1y = this.y;
        float r1w = this.w;
        float r1h = this.h;

        float r2x = rect.x;
        float r2y = rect.y;
        float r2w = rect.w;
        float r2h = rect.h;

        // System.out.println("Rect1: (" + l1 + ", " + r1 + "), (" + u1 + ", " + d1 + ")");
        // System.out.println("Rect2: (" + l2 + ", " + r2 + "), (" + u2 + ", " + d2 + ")");

        // if(l1 <= r2 && r1 >= l2 && u1 <= d2 && d1 >= u2) {
        //     System.out.println("true");
        //     return true;
        // } else {
        //     System.out.println("false");
        //     return false;
        // }

        if (r1x + r1w >= r2x &&    // r1 right edge past r2 left
        r1x <= r2x + r2w &&    // r1 left edge past r2 right
        r1y + r1h >= r2y &&    // r1 top edge past r2 bottom
        r1y <= r2y + r2h) {    // r1 bottom edge past r2 top
            return true;
        }

        return false;

    }

}
