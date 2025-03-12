
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FunctionGraph extends JFrame {

    private final double X_MIN = -6;
    private final double X_MAX = 4;
    private final double STEP = 0.01;

    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private final double xStar;

    public FunctionGraph() {
        super("График f(x) = x^2 + 6x + 13 (метод золотого сечения)");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.xStar = Main.ans();
        add(new GraphPanel());
        setVisible(true);
    }

    private double f(double x) {
        return x * x + 6 * x + 13;
    }

    private class GraphPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            double minVal = Double.POSITIVE_INFINITY;
            double maxVal = Double.NEGATIVE_INFINITY;
            for (double x = X_MIN; x <= X_MAX; x += STEP) {
                double val = f(x);
                if (val < minVal) minVal = val;
                if (val > maxVal) maxVal = val;
            }

            minVal = Math.min(minVal, 0);
            maxVal = Math.max(maxVal, 0);

            if (Math.abs(maxVal - minVal) < 1e-12) {
                maxVal = minVal + 1;
            }

            drawAxes(g, minVal, maxVal);

            g.setColor(Color.RED);
            List<Point> points = new ArrayList<>();
            for (double x = X_MIN; x <= X_MAX; x += STEP) {
                double y = f(x);
                points.add(transformXY(x, y, minVal, maxVal));
            }
            for (int i = 0; i < points.size() - 1; i++) {
                Point p1 = points.get(i);
                Point p2 = points.get(i + 1);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }

            int starX = toScreenX(xStar, minVal, maxVal);
            int starY = toScreenY(f(xStar), minVal, maxVal);

            g.setColor(Color.BLUE);
            int r = 5;
            g.fillOval(starX - r, starY - r, 2 * r, 2 * r);

            String label = String.format("(%.3f; %.3f)", xStar, f(xStar));
            g.drawString(label, starX + 8, starY - 8);
        }

        private void drawAxes(Graphics g, double minVal, double maxVal) {
            g.setColor(Color.BLACK);

            int originX = toScreenX(0, minVal, maxVal);
            int originY = toScreenY(0, minVal, maxVal);

            g.drawLine(0, originY, getWidth(), originY);
            g.drawLine(originX, 0, originX, getHeight());

            g.drawString("X", getWidth() - 15, originY - 5);
            g.drawString("Y", originX + 5, 15);

            for (int xi = (int) Math.floor(X_MIN); xi <= (int) Math.ceil(X_MAX); xi++) {
                int px = toScreenX(xi, minVal, maxVal);
                if (originY >= 0 && originY <= getHeight()) {
                    g.drawLine(px, originY - 3, px, originY + 3);
                }
                g.drawString(String.valueOf(xi), px - 5, originY + 15);
            }

            int lowerY = (int) Math.floor(minVal);
            int upperY = (int) Math.ceil(maxVal);
            for (int yi = lowerY; yi <= upperY; yi++) {
                int py = toScreenY(yi, minVal, maxVal);
                if (originX >= 0 && originX <= getWidth()) {
                    g.drawLine(originX - 3, py, originX + 3, py);
                }
                g.drawString(String.valueOf(yi), originX + 6, py + 4);
            }
        }

        private int toScreenX(double x, double minVal, double maxVal) {
            int w = getWidth() - 80;
            return (int) (40 + (x - X_MIN) * (w / (X_MAX - X_MIN)));
        }

        private int toScreenY(double y, double minVal, double maxVal) {
            int h = getHeight() - 80;
            return (int) (getHeight() - 40 - (y - minVal) * (h / (maxVal - minVal)));
        }

        private Point transformXY(double x, double y, double minVal, double maxVal) {
            return new Point(toScreenX(x, minVal, maxVal), toScreenY(y, minVal, maxVal));
        }
    }
}
