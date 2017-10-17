import java.util.LinkedList;

public class Coords_rep implements Comparable<Object>{//建立一个表示正方形单元的类
	private int[] Xcoords;//正方形四个顶点的x坐标
	private int[] Ycoords;//正方形四个顶点的y坐标
	private int[] origine;//正方形左下角坐标
	
	public Coords_rep(int i,int j){//r 表示正方形单元的放大倍数，在之后对polyomino做放大时用得到，默认为1
		int[] Xcoords=new int[]{i*10,i*10,(i+1)*10,(i+1)*10};
		int[] Ycoords=new int[]{j*10,(j-1)*10,(j-1)*10,j*10};
		this.Xcoords=Xcoords;
		this.Ycoords=Ycoords;
		this.origine=new int[]{i,j};
	}
	
	public void set_Origine(int i,int j){
		int [] or=new int[]{i,j};
		this.origine=or;
	}
	
	public int[] get_Xcoords(){
		return this.Xcoords;
	}
	
	public int[] get_Ycoords(){
		return this.Ycoords;
	}
	
	public int[] get_Origine(){
		return this.origine;
	}
	
	public int get_dist_X(Coords_rep r){
		return this.origine[0]-r.origine[0];
	}
	
	public int get_dist_Y(Coords_rep r){
		return this.origine[1]-r.origine[1];
	}

	public Coords_rep Translation(int i,int j){//将string变为一组小正方形的坐标几何，同时ox，oy代表正方形左下角原点，改变它就可以做平移
		int[] or = this.origine;
		Coords_rep co=new Coords_rep(or[0]+i,or[1]-j);
		return co;
	}
	
	public Coords_rep Dilate(int ox,int oy,int r){//同上，但是对所有正方形放大
		int[] or = this.origine;
		int[] diff = new int[2];
		diff[0] = or[0] - ox;
		diff[1] = oy - or[1];
		Coords_rep co = new Coords_rep(ox+diff[0]*r,oy-diff[1]*r);
		return co;
	}
	
	public Coords_rep rotate(int ox,int oy, int deg){//同上，但对所有正方形做旋转，如果pi==0，就不转，如果pi==1，就是90度，以此类推
		int[] or = this.origine;
		int[] diff = new int[2];
		diff[0] = or[0] - ox;
		diff[1] = oy - or[1];
		if (deg == 1){
			Coords_rep co = new Coords_rep(ox-diff[1]-1,oy-diff[0]);
			return co;
		}
		if (deg == 2){
			Coords_rep co = new Coords_rep(ox-diff[0]-1,oy+diff[1]+1);
			return co;
		}
		if (deg == 3){
			Coords_rep co = new Coords_rep(ox+diff[1],oy+diff[0]+1);
			return co;
		}
		return this;
	}
	
	public final static int X = 1, Y = 2, AD = 3, DD = 4;
	public Coords_rep reflection(int ox, int oy, int axis){
		int[] or = this.origine;
		int[] diff = new int[2];
		diff[0] = or[0] - ox;
		diff[1] = oy - or[1];
		if (axis == X){
			Coords_rep co = new Coords_rep(ox+diff[0],oy+diff[1]);
			return co;
		}
		if (axis == Y){
			Coords_rep co = new Coords_rep(ox-diff[0]-1,oy-diff[1]);
			return co;
		}
		if (axis == AD){
			Coords_rep co = new Coords_rep(ox+diff[1],oy-diff[0]);
			return co;
		}
		if (axis == DD){
			Coords_rep co = new Coords_rep(ox-diff[1]-1,oy+diff[0]+1);
			return co;
		}
		return this;
	}
	
	public static LinkedList<Coords_rep> successors(Coords_rep cr){
		LinkedList<Coords_rep> list = new LinkedList<Coords_rep>();
		int[] or = cr.origine;
		list.add(new Coords_rep(or[0]+1,or[1]));
		list.add(new Coords_rep(or[0],or[1]-1));
		list.add(new Coords_rep(or[0]-1,or[1]));
		list.add(new Coords_rep(or[0],or[1]+1));
		return list;
	}
	
	@Override
	public boolean equals(Object o){//重新定义equals在enumerate的时候contains要用到
		Coords_rep CoR=(Coords_rep) o;
		if((this.origine[0]==CoR.origine[0])&&(this.origine[1]==CoR.origine[1]))
			return true;
		else
			return false;
	}
	
	@Override
	public int compareTo(Object o){//重新定义compareTo，在polynomio的equals函数中排序的时候要用到
		Coords_rep CoR=(Coords_rep) o;
		if(this.origine[0]<CoR.origine[0])
			return -1;
		else if(this.origine[0]==CoR.origine[0])
		{
			if(this.origine[1]<CoR.origine[1])
				return -1;
			else if(this.origine[1]==CoR.origine[1])
				return 0;
			else
				return 1;
		}
		else
			return 1;
	}
	public String toString(){
		String S="";
		S+="("+this.origine[0]+","+this.origine[1]+")";
		return S;
	}
	
}
