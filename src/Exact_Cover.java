import java.util.*;

public class Exact_Cover {
	private Vector<Integer> X;
	private LinkedList<Vector<Integer>> C;
	
	public Exact_Cover(Vector<Integer> X, LinkedList<Vector<Integer>> C){
		this.X=X;
		this.C=C;
	}
	
	// transform the exactCover problem into Matrix structure
	public int[][] toMatrix(){
		int len_x=this.X.size();
		int len_c=this.C.size();
		int [][] M=new int[len_c][len_x];
		int i=0;
		for(Vector<Integer> Vp:this.C)
		{
			for(Integer Ip: Vp)
			{
				M[i][Ip-1]=1;
			}
			i++;
		}
		return M;
	}
	
	//transform the exactCover problem into dancing links data structure
	public Dancing_Links toDL(){
		int [][] M=this.toMatrix();
		Dancing_Links DL=new Dancing_Links(M);
		return DL;
	}	
	
	
	public Vector<Integer> Copy_X(){
		Vector<Integer> Xtemp=new Vector<>();
		for(Integer Ip:this.X)
			Xtemp.addElement(Ip);
		return Xtemp;
	}
	
	public LinkedList<Vector<Integer>> Copy_C(){
		LinkedList<Vector<Integer>> Ctemp=new LinkedList<>();
		for(Vector<Integer> Vp:this.C)
			Ctemp.add(Vp);
		return Ctemp;
	}
	
	public LinkedList<LinkedList<Vector<Integer>>> exactCover() {
		if (this.X.isEmpty())
			return null;
		int x = this.X.firstElement();//the naïve implemente
		//这里需要加一个第四题后面给的建议，不过直觉上来讲，如过要每次都检测x在S里地出现次数的话，会是一个o(n*(c中元素的总和))；如果放在一个file de priorité 
		//里地话，可以根据每个元素x在c中的出现次数，升序排列X，且以函数参数的形式输入，那么可以把时间复杂度降到o(c中元素总和)，因为只需遍历一次
		LinkedList<LinkedList<Vector<Integer>>> setOfSolution = new LinkedList<>();
		for (Vector<Integer> S : this.C) {
			if (S.contains(x)) {
				Vector<Integer> Xtemp = this.Copy_X();
				LinkedList<Vector<Integer>> Ctemp = this.Copy_C();
				Exact_Cover ECtemp = new Exact_Cover(Xtemp, Ctemp);
				for (Integer Y : S) {
					ECtemp.X.remove(Y);
					for (Vector<Integer> T : this.C) {
						if (T.contains(Y))
							ECtemp.C.remove(T);
					}
				}
				if (ECtemp.exactCover()==null) {
					LinkedList<Vector<Integer>> P = new LinkedList<>();
					P.add(S);
					setOfSolution.add(P);
				} else {
					for (LinkedList<Vector<Integer>> P : ECtemp.exactCover()) {
						P.add(S);
						setOfSolution.add(P);
					}
				}
			}

		}
		return setOfSolution;
	}
	
	public static void Afficher(LinkedList<LinkedList<Vector<Integer>>> s){
		if(s==null){
			System.out.println("System not initialized");
			return;}
		if (s.isEmpty()){
			System.out.println("No exact cover");
			return;}
		System.out.println("Solution");
		for(LinkedList<Vector<Integer>> LVp:s){
			System.out.print("{ ");
			for(Vector<Integer> Vp: LVp){
				System.out.print("{ ");
				for(Integer Ip: Vp){
					System.out.print(Ip+" ");
				}
				System.out.print("} ");
			}
			System.out.println("}");
		}
	}
}
