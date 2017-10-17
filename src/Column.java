
public class Column extends Data implements Comparable<Object>{
	public  int S;
	public  String N;
	
	//the Column object extends Data object ,the U field and the D field are necessary for coverColumn
	
	public Column(String N){
		this.S=0;
		this.N=N;
	}
	
	public void coverColumn(){
		this.R.L=this.L;
		this.L.R=this.R;
		Data t=this.D;
		while(t!=this){
			Data y=t.R;
			while(y!=t){
				y.D.U=y.U;
				y.U.D=y.D;
				y.C.S--;
				y=y.R;
			}
			t=t.D;
		}
	}
	
	public void uncoverColumn(){
		this.R.L=this;
		this.L.R=this;
		Data t=this.U;
		while(t!=this){
			Data y=t.L;
			while(y!=t){
				y.D.U=y;
				y.U.D=y;
				y.C.S++;
				y=y.L;
			}
			t=t.U;
		}
	}
	
	//we need comparator when we use the PriorityQueue
	//we consider the order of the size field firstly, then if they are the same, we compare their name
	@Override
	public int compareTo(Object o){
		Column oc=(Column) o;
		if(this.S<oc.S)
			return -1;
		else if(this.S==oc.S)
		{
			if(Integer.parseInt(this.N)<Integer.parseInt(oc.N))
				return-1;
			else if(Integer.parseInt(this.N)==Integer.parseInt(oc.N))
				return 0;
			else
				return 1;
		}
		else
			return 1;
	}
	
}
