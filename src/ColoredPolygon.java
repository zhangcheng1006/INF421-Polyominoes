import java.awt.Color;
import java.awt.Polygon;


public class ColoredPolygon {
	int[] xcoords;
	int[] ycoords;
	Color color;
	Polygon polygon;
	
	ColoredPolygon(int [] xcoords, int [] ycoords, Color color){
		this.xcoords=xcoords;
		this.ycoords=ycoords;
		this.color=color;
		Polygon polygon=new Polygon(xcoords,ycoords,xcoords.length);
		this.polygon=polygon;
	}
	
	

}
