import java.util.*;

public class Dancing_Links {
	public Column H;
	
	//the constructor is a little bit complicate
	//firstly, I create the Dancing_Links structure for a all one matrix
	//then, I remove all zero element (according to M) by changing the relationship of this.L/this.R/this.U/this.down
	public Dancing_Links(int [][]M){
		Column Head=new Column("H");
		
		//initialize all columns
		Column point= Head;
		for (int i = 0; i < M[0].length; i++) {
			//char c = (char) A++;
			Column C = new Column("" + i);
			point.R = C;
			C.L = point;
			point = C;
		}
		point.R=Head;
		Head.L=point;
		
		//up to down
		Column ph=Head;
		for(int i=0;i<M[0].length;i++){
			if(ph.R==null){
				break;}
			ph=(Column) ph.R;
			Data D=new Data(ph);
			ph.D=D;
			D.U=ph;
			Data pd=D;
			ph.S++;
			for(int j=1;j<M.length;j++)
			{
				Data DD=new Data(ph);
				pd.D=DD;
				DD.U=pd;
				pd=DD;
				ph.S++;
			}
			pd.D=ph;//connect the D of the last element of this column with the column object 
			ph.U=pd;//connect the U of the column object with the last element of this column, which will form a cycle.
		}
		
		//left to right
		ph=(Column) Head.R;
		boolean flag=false;
		for(int i=0;i<M[0].length;i++)
		{
			Column pph;
			if (ph.R == Head) {
				pph = (Column) Head.R;
				flag = true;
			} else {
				pph = (Column) ph.R;
			}
			Data pd=ph.D;
			Data ppd=pph.D;
			for(int j=0;j<M.length;j++){
				pd.R=ppd;
				ppd.L=pd;
				pd=pd.D;
				ppd=ppd.D;
			}
			if(flag==true)
			{
				break;
			}
			ph=(Column) ph.R;
		}
		
		//remove all zero element according M
		ph=(Column) Head.R;
		Data dhead=ph.D;
		for(int i=0;i<M[0].length;i++){
			Data dCol=ph.D;
			for(int j=0;j<M.length;j++){
				if(M[j][i]==0){
					dCol=dCol.D;
				}
				else
				{
					ph.D=dCol;
					break;
				}
			}
			ph=(Column) ph.R;
		}
		for (int i = 0; i < M.length; i++) {
			Data dp = dhead;
			for (int j = 0; j < M[0].length; j++) {
				Data dptemp = dp.R;
				if (M[i][j] == 0) {
					dp.D.U = dp.U;
					dp.U.D = dp.D;
					dp.L.R = dp.R;
					dp.R.L = dp.L;
					dp.C.S--;
				}
				dp = dptemp;
			}
			dhead = dhead.D;
		}	
		this.H=Head;
	}
	//stock all Column into PQ, prepare for selection the column with the minimal size in ExactCover
	public PriorityQueue<Column> toPQ(){
		PriorityQueue<Column> pc=new PriorityQueue<>();
		Column cp=(Column)this.H.R;
		while(cp!=this.H){
			pc.add(cp);
			cp=(Column)cp.R;
		}
		return pc;
	}
	public LinkedList<LinkedList<Vector<Integer>>> exactCover(PriorityQueue<Column> pc){
		if(this.H.R==this.H)
			return null;
		LinkedList<LinkedList<Vector<Integer>>> setOfSolution=new LinkedList<>();
		Column x=pc.poll();
		x.coverColumn();
		Data t=x.U;
		while(t!=x){
			Vector<Integer> S=new Vector<>();
			S.add(Integer.parseInt(t.C.N)+1);//transfer name to its number,the size of X can not be bigger than 24;
			Data y=t.L;
			while(y!=t){
				S.add(Integer.parseInt(y.C.N)+1);
				y.C.coverColumn();
				pc.remove(y.C);//to remove the column object that is already be covered.it is a little bit different, becauce we stock 
				               //Column in priority queue, so we have to remove it too.
				y=y.L;
			}
			LinkedList<LinkedList<Vector<Integer>>> rec=this.exactCover(pc);
			if (rec == null) {
				LinkedList<Vector<Integer>> LVp=new LinkedList<>();
				LVp.add(S);
				setOfSolution.add(LVp);
			} else {
				for (LinkedList<Vector<Integer>> LVp : rec) {
					LVp.add(S);
					setOfSolution.add(LVp);
				}
			}
			y=t.R;
			while(y!=t){
				y.C.uncoverColumn();
				pc.add(y.C);//to restore the column object that is already be calculated for the next recursion;
				y=y.R;
			}
			t=t.U;
		}
		x.uncoverColumn();
		pc.add(x);
		return setOfSolution;
	}
	//I define toString for testing.
	public String toString(){
		String s = "";
		Column p = (Column) this.H.R;
		while (p != this.H) {
			s += "N:" + p.N + " S:" + p.S + " ";
			p = (Column) p.R;
		}
		s += "\n";
		p = (Column) this.H.R;
		while (p != this.H) {
			Data dp = p.D;
			while (dp != p) {
				s += "->        " + "\n";
				dp = dp.D;
			}
			s+="****"+"\n";
			p = (Column) p.R;
		}
		return s;
	}
}
