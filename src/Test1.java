import java.awt.Color;
import java.awt.font.ImageGraphicAttribute;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class Test1 {
	
	//creation of a polyomino from a string (3 examples)
	public static void test1(){
		Image2d img1 = new Image2d(100,100);
		Image2d img2 = new Image2d(100,100);
		Image2d img3 = new Image2d(100,100);
		int ox = 3, oy = 7;
		String s1="[(0,0), (0,1), (0,2), (0,3), (0,4), (1,2), (1,3), (2,1), (2,2), (3,0), (3,1), (3,2), (3,3), (3,4)]";
		String s2="[(0,0), (0,1), (1,1), (2,1)]";
		String s3="[(0,0), (0,1), (0,2), (1,2)]";
		Polynomio p1 = new Polynomio(s1,ox,oy);
		Polynomio p2 = new Polynomio(s2,ox,oy);
		Polynomio p3 = new Polynomio(s3,ox,oy);
		p1.toImage(img1);
		p2.toImage(img2);
		p3.toImage(img3);
		Image2dViewer imgv1 = new Image2dViewer(img1);
		Image2dViewer imgv2 = new Image2dViewer(img2);
		Image2dViewer imgv3 = new Image2dViewer(img3);
	}
	
	//creation of a list of polyominoes from a text file "polyominoesINF421.txt"
	public static void test2() throws IOException{
		Image2d img = new Image2d(400,200);
		File file = new File("polyominoesINF421.txt");
		LinkedList<Polynomio> list = Polynomio.toList(file);
		for (Polynomio p : list){
			p.toImage(img);
		}
		Image2dViewer imgv = new Image2dViewer(img);
	}
	
	//elementary transformations
	public static void test3(){
		Image2d img1 = new Image2d(200,200);
		Image2d img2 = new Image2d(200,200);
		Image2d img3 = new Image2d(200,200);
		Image2d img4 = new Image2d(200,200);
		Image2d img5 = new Image2d(200,200);
		int ox = 7, oy = 15;
		String s="[(0,0), (0,1), (0,2), (0,3), (0,4), (1,2), (1,3), (2,1), (2,2), (3,0), (3,1), (3,2), (3,3), (3,4)]";
		Polynomio p1 = new Polynomio(s,ox,oy);
		p1.toImage(img1);
		Image2dViewer imgv1 = new Image2dViewer(img1);
		Polynomio p2 = p1.Translation(3,3);
		p2.toImage(img2);
		Image2dViewer imgv2 = new Image2dViewer(img2);
		Polynomio p3 = p1.rotate(1);
		p3.toImage(img3);
		Image2dViewer imgv3 = new Image2dViewer(img3);
		Polynomio p4 = p1.reflection(3);
		p4.toImage(img4);
		Image2dViewer imgv4 = new Image2dViewer(img4);
		Polynomio p5 = p1.Dilate(3);
		p5.toImage(img5);
		Image2dViewer imgv5 = new Image2dViewer(img5);
	}
	
	//generate all fixed polyominoes and free polyominoes with the naive method
	public static void test4(){
		int ox = 0, oy = 0;
		for (int i = 1; i <= 8; i++) {
			LinkedList<Polynomio> list = new LinkedList<Polynomio>();
			Coords_rep co = new Coords_rep(ox, oy);
			Polynomio init = new Polynomio();
			init.get_polyo().add(co);
			int size = i;
			list.add(init);
			Polynomio.enumerate_naive(list, init, ox, oy, size);
			LinkedList<Polynomio> Poly_fix = Polynomio.generate_fix_naive(list, size);
			LinkedList<Polynomio> Poly_free = Polynomio.generate_free(Poly_fix);
			System.out.println("Size = "+size+" Free = "+Poly_free.size()+" Fix "+Poly_fix.size());
		}
	}
	
	//generate all fixed polyominoes with the implemented method
	public static void test5(){
		int ox = 0, oy = 0;
		for (int i = 1; i <= 14; i++) {
			LinkedList<Polynomio> list = new LinkedList<Polynomio>();
			LinkedList<Coords_rep> neighbours = new LinkedList<>();
			LinkedList<Coords_rep> untried = new LinkedList<>();
			Coords_rep co = new Coords_rep(ox, oy);
			int size = i;
			untried.add(co);
			neighbours.add(co);
			Polynomio.enumerate(list, neighbours, new Polynomio(), untried, ox, oy, size);
			LinkedList<Polynomio> Poly_fix = Polynomio.generate_fix(list, size);
			System.out.println("Size = "+size+" Fix "+Poly_fix.size());
		}
	}
	
	//Find all tilings of the polyominoes of Figure 5 by all free pentaminoes.
	public static void test6(String s){
		Image2d img = new Image2d(110,100);
		int ox = 0, oy = 10;
		Polynomio p = new Polynomio(s,ox,oy);
		p.toImage(img);
		Image2dViewer imgv=new Image2dViewer(img);
		int size = 5;
		LinkedList<Polynomio> list = new LinkedList<Polynomio>();
		LinkedList<Coords_rep> neighbours = new LinkedList<>();
		LinkedList<Coords_rep> untried = new LinkedList<>();
		Coords_rep co = new Coords_rep(ox, oy);
		untried.add(co);
		neighbours.add(co);
		Polynomio.enumerate(list, neighbours, new Polynomio(), untried, ox, oy, size);
		LinkedList<Polynomio> Poly_fix = Polynomio.generate_fix(list, size);
		LinkedList<Polynomio> Poly_free = Polynomio.generate_free(Poly_fix);
		LinkedList<LinkedList<Polynomio>> l = Polynomio.exactCover1(p.get_polyo(), Poly_free, oy);
		System.out.println("Finish");
		System.out.println(l.size());
		for (LinkedList<Polynomio> ll : l){
			for (Polynomio pp : ll){
				System.out.print(pp.toString() + "   ");
			}
			System.out.println();
		}
	}
	
	//For a given n (say n = 4, 5, or even maybe 6), find all tilings of a rectangle by all fixed polyominoes of area n.
	public static void test7a(int n, int length, int width){
		Image2d img = new Image2d(100,100);
		int ox = 0, oy = 10;
		LinkedList<Coords_rep> X = new LinkedList<>();
		for (int i = 0; i < length; i++){
			for (int j = 0; j < width; j++){
				Coords_rep coo = new Coords_rep(ox + i,oy - j);
				X.add(coo);
			}
		}
		Polynomio p1 = new Polynomio(X);
		//p1.toImage(img);
		//Image2dViewer imgv=new Image2dViewer(img);
		LinkedList<LinkedList<Polynomio>> l2 = Polynomio.exactCover2_fix(n, X, ox, oy);
		System.out.println(l2.size());
		for (LinkedList<Polynomio> ll : l2){
			for (Polynomio pp : ll){
				System.out.print(pp.toString() + "   ");
			}
			System.out.println();
		}
		LinkedList<Polynomio> r = l2.getLast();
		for (Polynomio pp: r){
			Random rand = new Random();
			Color c=new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
			pp.toImageRandom(img, c);
		}
		Image2dViewer imgv=new Image2dViewer(img);
	}
	
	//For a given n (say n = 4, 5, or even maybe 6), find all tilings of a rectangle by all free polyominoes of area n.
	public static void test7b(int n, int length, int width){
		Image2d img = new Image2d(100,100);
		int ox = 0, oy = 10;
		LinkedList<Coords_rep> X = new LinkedList<>();
		for (int i = 0; i < length; i++){
			for (int j = 0; j < width; j++){
				Coords_rep coo = new Coords_rep(ox + i,oy - j);
				X.add(coo);
			}
		}
		Polynomio p1 = new Polynomio(X);
		//p1.toImage(img);
		//Image2dViewer imgv=new Image2dViewer(img);
		LinkedList<LinkedList<Polynomio>> l2 = Polynomio.exactCover2_free(n, X, ox, oy);
		System.out.println(l2.size());
		for (LinkedList<Polynomio> ll : l2){
			for (Polynomio pp : ll){
				System.out.print(pp.toString() + "   ");
			}
			System.out.println();
		}
		LinkedList<Polynomio> r = l2.getLast();
		for (Polynomio pp: r){
			Random rand = new Random();
			Color c=new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
			pp.toImageRandom(img, c);
		}
		Image2dViewer imgv=new Image2dViewer(img);
	}
	
	//For a given n and k, find all polyominoes P of size n which can cover their own dilate kP.
	public static void test8(int n, int k){
		Image2d img = new Image2d(100,100);
		int ox = 0, oy = 10;
		int size=n;
		LinkedList<Polynomio> list =new LinkedList<Polynomio>();
		LinkedList<Coords_rep> neighbours=new LinkedList<>();
		LinkedList<Coords_rep> untried=new LinkedList<>();
		Coords_rep co=new Coords_rep(ox,oy);
		untried.add(co);
		neighbours.add(co);
		Polynomio.enumerate(list,neighbours,new Polynomio(),untried,ox,oy,size);
		LinkedList<Polynomio> Poly_fix=Polynomio.generate_fix(list,size);
		LinkedList<Polynomio> Poly_free=Polynomio.generate_free(Poly_fix);
		System.out.println(Poly_free.size());
		int number=0;
		LinkedList<Polynomio> result=new LinkedList<>();
		for (Polynomio cp : Poly_free) {
			for (Polynomio dp : cp.all_trans()) {
				LinkedList<LinkedList<Polynomio>> l = Polynomio.exactCover1(dp.Dilate(k).get_polyo(), cp.all_trans(),
						oy);
				if (l != null)
					if (l.size() > 0) {
						number++;
						result.add(cp);
						break;
					}
			}
		}
		for(Polynomio p:result){
			p.toImage(img);
			System.out.println(p);
		}
		Image2dViewer imgv=new Image2dViewer(img);
		System.out.print("("+n+","+k+")="+number);
	}
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		test1(); //creation of a polyomino from a string (3 examples)
		//test2(); //creation of a list of polyominoes from a text file "polyominoesINF421.txt"
		//test3(); //elementary transformations
		//test4(); //generate all fixed polyominoes and free polyominoes with the naive method
		//test5(); //generate all fixed polyominoes with the implemented method
		//three Strings corresponding to three polyominoes in Figure 5.
		String s1 = "[(0,5), (0,6), (0,7), (0,8), (0,9), (1,4), (1,5), (1,6), (1,7), (1,8), (1,9), (2,4), (2,5), (2,6), (2,7), (2,8), (3,3), (3,4), (3,5), (3,6), (3,7), (3,8), (4,3), (4,4), (4,5), (4,6), (4,7), (5,2), (5,3), (5,4), (5,5), (5,6), (5,7), (6,2), (6,3), (6,4), (6,5), (6,6), (7,1), (7,2), (7,3), (7,4), (7,5), (7,6), (8,1), (8,2), (8,3), (8,4), (8,5), (9,0), (9,1), (9,2), (9,3), (9,4), (9,5), (10,0), (10,1), (10,2), (10,3), (10,4)]";
		String s2 = "[(0,0), (0,1), (1,0), (1,1), (1,2), (1,3), (2,0), (2,1), (2,2), (2,3), (2,4), (2,5), (3,0), (3,1), (3,2), (3,3), (3,4), (3,5), (3,6), (3,7), (4,0), (4,1), (4,2), (4,3), (4,4), (4,5), (4,6), (4,7), (4,8), (4,9), (5,0), (5,1), (5,2), (5,3), (5,4), (5,5), (5,6), (5,7), (5,8), (5,9), (6,0), (6,1), (6,2), (6,3), (6,4), (6,5), (6,6), (6,7), (7,0), (7,1), (7,2), (7,3), (7,4), (7,5), (8,0), (8,1), (8,2), (8,3), (9,0), (9,1)]";
		String s3 = "[(0,4), (0,5), (1,3), (1,4), (1,5), (1,6), (2,2), (2,3), (2,4), (2,5), (2,6), (2,7), (3,1), (3,2), (3,3), (3,4), (3,5), (3,6), (3,7), (3,8), (4,0), (4,1), (4,2), (4,3), (4,4), (4,5), (4,6), (4,7), (4,8), (4,9), (5,0), (5,1), (5,2), (5,3), (5,4), (5,5), (5,6), (5,7), (5,8), (5,9), (6,1), (6,2), (6,3), (6,4), (6,5), (6,6), (6,7), (6,8), (7,2), (7,3), (7,4), (7,5), (7,6), (7,7), (8,3), (8,4), (8,5), (8,6), (9,4), (9,5)]";
		//test6(s1); //Find all tilings of the polyominoes of Figure 5 by all free pentaminoes. Mais ca prend trop de temps, il n'y a pas de resultat.
		             //change the parameter by s1  s2 or s3.
		//test7a(4,6,4); //For a given n (say n = 4, 5, or even maybe 6), find all tilings of a rectangle by all fixed polyominoes of area n.
		                 //the first parameter is n, the second is the length of the rectangle, the third is the width of the rectangle.
		//test7b(4,6,4); //For a given n (say n = 4, 5, or even maybe 6), find all tilings of a rectangle by all free polyominoes of area n.
		//test8(8,4); //For a given n and k, find all polyominoes P of size n which can cover their own dilate kP. Ca prend plus de dix minutes.
	}

}
