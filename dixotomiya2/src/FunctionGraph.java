import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FunctionGraph extends JFrame {
    private double f(double x) {
        return x*x + 6*x + 13;
    }
    private final double X_MIN = -6;
    private final double X_MAX =  4;

    private final double Y_MIN = 0;
    private final double Y_MAX = 60;

    private final int WIDTH  = 800;
    private final int HEIGHT = 600;

    private final double VERTEX_X = -3;
    private final double VERTEX_Y =  4;

    public FunctionGraph() {
        super("График функции f(x)=x^2 + 6x + 13");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new DrawPanel());
        setVisible(true);
    }

    private class DrawPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawAxes(g);
            g.setColor(Color.DARK_GRAY);
            List<Point> points = getFunctionPoints();
            for (int i = 0; i < points.size() - 1; i++) {
                Point p1 = points.get(i);
                Point p2 = points.get(i + 1);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
            g.setColor(Color.RED);
            Point vertexPixel = toScreen(VERTEX_X, VERTEX_Y);
            int r = 7;
            g.fillOval(vertexPixel.x - r, vertexPixel.y - r, 2*r, 2*r);
            g.drawString("Вершина (" + VERTEX_X + "," + VERTEX_Y + ")",
                    vertexPixel.x + 80, vertexPixel.y);
        }

        private void drawAxes(Graphics g) {
            g.setColor(Color.BLACK);
            int axisX = 60;
            int axisY = getHeight() - 50;
            g.drawLine(axisX, 20, axisX, axisY);
            g.drawLine(axisX, axisY, getWidth() - 20, axisY);
            g.drawString("y", axisX - 10, 25);
            g.drawString("x", getWidth() - 30, axisY + 15);
            for (int xi = (int)Math.floor(X_MIN); xi <= (int)Math.ceil(X_MAX); xi++) {
                int px = toScreenX(xi);
                g.drawLine(px, axisY - 3, px, axisY + 3);
                g.drawString(String.valueOf(xi), px - 5, axisY + 20);
            }
            for (int yi = (int)Math.floor(Y_MIN); yi <= (int)Math.ceil(Y_MAX); yi++) {
                int py = toScreenY(yi);
                g.drawLine(axisX - 3, py, axisX + 3, py);
                g.drawString(String.valueOf(yi), axisX - 25, py + 5);
            }
        }

        private List<Point> getFunctionPoints() {
            List<Point> list = new ArrayList<>();
            double step = 0.01;
            for (double x = X_MIN; x <= X_MAX; x += step) {
                double y = f(x);
                list.add(toScreen(x, y));
            }
            return list;
        }


        private Point toScreen(double x, double y) {
            int usableWidth  = getWidth()  - 80;
            int usableHeight = getHeight() - 70;
            int axisX = 60;
            int axisY = getHeight() - 50;
            double scaleX = (double)usableWidth  / (X_MAX - X_MIN);
            double scaleY = (double)usableHeight / (Y_MAX - Y_MIN);
            int px = (int)(axisX + (x - X_MIN)*scaleX);
            int py = (int)(axisY - (y - Y_MIN)*scaleY);
            return new Point(px, py);
        }

        private int toScreenX(double x) {
            return toScreen(x, 0).x;
        }

        private int toScreenY(double y) {
            return toScreen(0, y).y;
        }
    }
}
