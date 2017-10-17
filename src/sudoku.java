import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Vector;

public class sudoku {
	
	public static int[][] toMatrix(String s){
		String[] sub = s.split(",|\\(|\\)");
		int size = sub.length / 5;
		int[] coor_X = new int[size];
		int[] coor_Y = new int[size];
		int[] chi = new int[size];
		int i = 0;
		for (int l = 1; l < sub.length; l = l + 5) {
			int x = Integer.parseInt("" + sub[l]);
			coor_X[i] = x;
			int y = Integer.parseInt("" + sub[l + 1]);
			coor_Y[i] = y;
			int nb = Integer.parseInt("" + sub[l + 3]);
			chi[i] = nb;
			i++;
		}
		int row = size + (81-size)*9;
		int colum = 324;
		int[][] M = new int[row][colum];
		int x = 1, y = 1;
		int index = 0;
		int j = 0;
		while (x <= 9 && y <= 9){
			if (index < size && coor_X[index] == x && coor_Y[index] == y){
				M[j][(x-1)*9+y-1] = 1;
				M[j][(x-1)*9+chi[index]+81-1] = 1;
				M[j][(y-1)*9+chi[index]+162-1] = 1;
				int gong = (int)((x-1)/3)*3 + (int)((y-1)/3+1);
				M[j][(gong-1)*9+chi[index]+243-1] = 1;
				j++;
				index++;
			}
			else{
				for (int m = 1; m <= 9; m++){
					M[j][(x-1)*9+y-1] = 1;
					M[j][(x-1)*9+m+81-1] = 1;
					M[j][(y-1)*9+m+162-1] = 1;
					int gong = (int)((x-1)/3)*3 + (int)((y-1)/3+1);
					M[j][(gong-1)*9+m+243-1] = 1;
					j++;
				}
			}
			y++;
			if (y > 9) {
				x++;
				y = 1;
			}
		}
		return M;
	}
	
	public static void afficher(LinkedList<LinkedList<Vector<Integer>>> l){
		if(l == null){
			System.out.println("System not initialized");
			return;}
		if (l.isEmpty()){
			System.out.println("No exact cover");
			return;}
		System.out.println("Solution");
		for(LinkedList<Vector<Integer>> LVp : l){
			System.out.print("{ ");
			for(Vector<Integer> Vp: LVp){
				System.out.print("{");
				int[] tmp = new int[4];
				for(Integer Ip: Vp){
					if (Ip.compareTo(81) <= 0) tmp[0] = Ip;
					if (Ip.compareTo(81) > 0 && Ip.compareTo(162) <= 0) tmp[1] = Ip;
					if (Ip.compareTo(162) > 0 && Ip.compareTo(243) <= 0) tmp[2] = Ip;
					if (Ip.compareTo(243) > 0) tmp[3] = Ip;
				}
				int y = (tmp[2]-tmp[1]+tmp[0]-72)/10;
				int x = (tmp[0]-y)/9+1;
				int nb = tmp[2]-153-9*y;
				System.out.print("("+x+","+y+")"+","+nb+"} ");
			}
			System.out.println("} ");
		}
	}


	public static void main(String[] args) throws IOException {
		String s1 = "(1,1),8,(2,3),3,(2,4),6,(3,2),7,(3,5),9,(3,7),2,(4,2),5,(4,6),7,(5,5),4,(5,7),7,(6,4),1,(6,6),5,(6,8),3,(7,3),1,(7,8),6,(7,9),8,(8,3),8,(8,4),5,(8,8),1,(9,2),9,(9,7),4";
		String s2 = "(1,2),6,(1,4),5,(1,5),9,(1,6),3,(2,1),9,(2,3),1,(2,7),5,(3,2),3,(3,4),4,(3,8),9,(4,1),1,(4,3),8,(4,5),2,(4,9),4,(5,1),4,(5,4),3,(5,6),9,(5,9),1,(6,1),2,(6,5),1,(6,7),6,(6,9),9,(7,2),8,(7,6),6,(7,8),2,(8,3),4,(8,7),8,(8,9),7,(9,4),7,(9,5),8,(9,6),5,(9,8),1";
		int[][] M = toMatrix(s2);
		Dancing_Links dl=new Dancing_Links(M);
		PriorityQueue<Column> pc=dl.toPQ();
		LinkedList<LinkedList<Vector<Integer>>> dlResult=dl.exactCover(pc);
		afficher(dlResult);
	}
}
