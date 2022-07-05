import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;

class MyCanvas extends JComponent {
   String s = "message";
   int x = 45;
   int y = 45;
   public void paint(Graphics g) {
      g.drawRect (10, 10, 200, 200);
      g.setColor(Color.red);
      g.drawString(s, x, y);
   }
}