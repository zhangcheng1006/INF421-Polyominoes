import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Collections;
import java.util.LinkedList;
import javax.swing.JComponent;
import javax.swing.JFrame;

// Manipulation for images
public class Image2d {
	private int width; // width of the image
	private int height; // height of the image
	private java.util.List<ColoredPolygon> coloredPolygons; // colored polygons in the image
	private java.util.List<Edge> edges; // edges to add to separate polygons

	// Constructor that instantiates an image of a specified width and height
	public Image2d(int width, int height) {
		this.width = width;
		this.height = height;
		coloredPolygons = Collections.synchronizedList(new LinkedList<ColoredPolygon>());
		edges = Collections.synchronizedList(new LinkedList<Edge>());
	}

	// Return the width of the image
	public int getWidth() {
		return width;
	}

	// Return the height of the image
	public int getHeight() {
		return height;
	}

	// Return the colored polygons of the image
	public java.util.List<ColoredPolygon> getColoredPolygons() {
		return coloredPolygons;
	}

	// Return the edges of the image
	public java.util.List<Edge> getEdges() {
		return edges;
	}

	// Create the polygon with xcoords, ycoords and color 
	public void addPolygon(int[] xcoords, int[] ycoords, Color color) {
		coloredPolygons.add(new ColoredPolygon(xcoords, ycoords, color));
	}
	
	// Create the edge with coordinates x1, y1, x2, y2
	public void addEdge(int x1, int y1, int x2, int y2, int width) {
		edges.add(new Edge(x1, y1, x2, y2, width));
	}
	
	// Create the polygons with a list
	public void fromList(LinkedList<Coords_rep> list){
		for(Coords_rep CrP:list)
			this.addPolygon(CrP.get_Xcoords(), CrP.get_Ycoords(), Color.RED);
	}
	
	// Clear the picture
	public void clear() {
		coloredPolygons = Collections.synchronizedList(new LinkedList<ColoredPolygon>());
		edges = Collections.synchronizedList(new LinkedList<Edge>());		
	}
}

// Image2d component
class Image2dComponent extends JComponent {

	private static final long serialVersionUID = -7710437354239150390L;
	private Image2d img;
	

	public Image2dComponent(Image2d img) {
		this.img = img;
		setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// set the background color
		Dimension d = getSize();
        g2.setBackground(Color.white);
        g2.clearRect(0,0,d.width,d.height);
        
        // draw the polygons
		synchronized (img.getColoredPolygons()) {
			for (ColoredPolygon coloredPolygon : img.getColoredPolygons()) {
				g2.setColor(coloredPolygon.color);
				g2.fillPolygon(coloredPolygon.polygon);
				g2.drawPolygon(coloredPolygon.polygon);
			}
		}
		
		// draw the edges
		g2.setColor(Color.white);
		synchronized (img.getEdges()) {
			for (Edge edge : img.getEdges()) {
                g2.setStroke(new BasicStroke(edge.width));
                g2.drawLine(edge.x1, edge.y1, edge.x2, edge.y2);
			}
		}
	}
}

// Frame for the vizualization
class Image2dViewer extends JFrame {

	private static final long serialVersionUID = -7498525833438154949L;
	static int xLocation = 0;
	Image2d img;

	public Image2dViewer(Image2d img) {
		this.img = img;
		this.setLocation(xLocation, 0);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		add(new Image2dComponent(img));
		pack();
		setVisible(true);
		xLocation += img.getWidth();
	}
}