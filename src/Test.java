import java.util.*;

public class Test {

	public static void main(String[] args) {
		Vector<Integer> X=new Vector<>();
		LinkedList<Vector<Integer>> C=new LinkedList<>();
		
		//case 1;{ {1 4} {2 7} {3 5 6}} or{{1 4 7}{2 3 5 6}}, the same as the example
		/*for(int i=1;i<=7;i++){
			X.addElement(i);}
			
		Vector<Integer> A=new Vector<>();
		A.addElement(3);A.addElement(5);A.addElement(6);
		
		Vector<Integer> B=new Vector<>();
		B.addElement(1);B.addElement(4);B.addElement(7);
		
		Vector<Integer> G=new Vector<>();
		G.addElement(2);G.addElement(3);G.addElement(6);//G.addElement(7);
		
		Vector<Integer> D=new Vector<>();
		D.addElement(1);D.addElement(4);
		
		Vector<Integer> E=new Vector<>();
		E.addElement(2);E.addElement(7);
		
		Vector<Integer> F=new Vector<>();
		F.addElement(4);F.addElement(5);F.addElement(7);
		
		C.add(A);C.add(B);C.add(G);C.add(D);C.add(E);C.add(F);*/
		
		//Case 2
		/*for(int i=1;i<=7;i++){
			X.addElement(i);}
			
		Vector<Integer> A=new Vector<>();
		A.addElement(1);
		
		Vector<Integer> B=new Vector<>();
		B.addElement(2);
		
		Vector<Integer> G=new Vector<>();
		G.addElement(3);
		
		Vector<Integer> D=new Vector<>();
		D.addElement(4);
		
		Vector<Integer> E=new Vector<>();
		E.addElement(5);
		
		Vector<Integer> F=new Vector<>();
		F.addElement(6);
		
		Vector<Integer> H=new Vector<>();
		H.addElement(7);
		
		C.add(A);C.add(B);C.add(G);C.add(D);C.add(E);C.add(F);C.add(H);//if we remove H, it will test the situation that there is no exact cover.
		*/
		
		//Case 3 do nothing
		
		//Case 4 X={1,2,3};C={{1,3},{2},{3}}
		/*for(int i=1;i<=3;i++){
			X.addElement(i);}
		
		Vector<Integer> A=new Vector<>();
		A.addElement(1);A.addElement(3);
		
		Vector<Integer> B=new Vector<>();
		B.addElement(2);
		
		Vector<Integer> G=new Vector<>();
		G.addElement(3);
		
		Vector<Integer> D=new Vector<>();
		D.addElement(1);D.addElement(2);
		
		C.add(A);C.add(B);C.add(G);C.add(D);
		*/
		
		//case 5 test the situation that the scale of the background set raise.
		/*for(int i=1;i<=10;i++){
			X.addElement(i);}
			
		Vector<Integer> A=new Vector<>();
		A.addElement(2);A.addElement(4);A.addElement(6);A.addElement(8);
		
		Vector<Integer> B=new Vector<>();
		B.addElement(1);B.addElement(3);
		
		Vector<Integer> G=new Vector<>();
		G.addElement(5);G.addElement(7);G.addElement(9);G.addElement(10);
		
		Vector<Integer> D=new Vector<>();
		D.addElement(2);D.addElement(4);D.addElement(5);D.addElement(6);
		
		Vector<Integer> E=new Vector<>();
		E.addElement(1);E.addElement(3);E.addElement(7);
		
		Vector<Integer> F=new Vector<>();
		F.addElement(8);F.addElement(9);F.addElement(10);
		
		Vector<Integer> H=new Vector<>();
		H.addElement(4);H.addElement(5);H.addElement(7);
		
		Vector<Integer> I=new Vector<>();
		I.addElement(4);I.addElement(7);
		
		Vector<Integer> J=new Vector<>();
		J.addElement(4);J.addElement(8);J.addElement(9);
		
		Vector<Integer> K=new Vector<>();
		K.addElement(4);
		
		C.add(A);C.add(B);C.add(G);C.add(D);C.add(E);C.add(F);C.add(H);C.add(I);C.add(J);C.add(K);*/
		
		//Case 6 test the scale of the subset C raise
		/*for(int i=1;i<=4;i++){
			X.addElement(i);}
		
		Vector<Integer> A=new Vector<>();
		A.addElement(1);
		
		Vector<Integer> B=new Vector<>();
		B.addElement(2);
		
		Vector<Integer> G=new Vector<>();
		G.addElement(3);
		
		Vector<Integer> D=new Vector<>();
		D.addElement(4);
		
		Vector<Integer> E=new Vector<>();
		E.addElement(1);E.addElement(2);
		
		Vector<Integer> F=new Vector<>();
		F.addElement(1);F.addElement(3);
		
		Vector<Integer> H=new Vector<>();
		H.addElement(1);H.addElement(4);
		
		Vector<Integer> I=new Vector<>();
		I.addElement(2);I.addElement(3);
		
		Vector<Integer> J=new Vector<>();
		J.addElement(2);J.addElement(4);
		
		Vector<Integer> K=new Vector<>();
		K.addElement(3);K.addElement(4);
		
		Vector<Integer> L=new Vector<>();
		L.addElement(1);L.addElement(2);L.addElement(3);
		
		Vector<Integer> Q=new Vector<>();
		Q.addElement(1);Q.addElement(2);Q.addElement(4);
		
		Vector<Integer> N=new Vector<>();
		L.addElement(1);L.addElement(3);L.addElement(4);
		
		Vector<Integer> O=new Vector<>();
		O.addElement(2);O.addElement(3);O.addElement(4);
		
		Vector<Integer> P=new Vector<>();
		P.addElement(1);P.addElement(2);P.addElement(3);P.addElement(4);
		
		
		C.add(A);C.add(B);C.add(G);C.add(D);C.add(E);C.add(F);C.add(H);C.add(I);C.add(J);C.add(K);
		C.add(L);C.add(Q);C.add(N);C.add(O);C.add(P);*/
		
		//case 7 test for all subsets with the scale of background set is 5
		for(int i=1;i<=5;i++){
			X.addElement(i);}
		
		for(int i=1;i<=5;i++){
			if(i==1){
				for(int j=1;j<=5;j++)
				{
					Vector<Integer> t=new Vector<>();
					t.addElement(j);
					C.add(t);
				}	
			}
			if(i==2)
			{
				for(int j=1;j<=4;j++)
					for(int k=j+1;k<=5;k++)
					{
						Vector<Integer> t=new Vector<>();
						t.addElement(j);
						t.addElement(k);
						C.add(t);
					}
			}
			if (i == 3) {
				for (int j = 1; j <= 3; j++)
					for (int k = j + 1; k <= 4; k++)
						for (int l = k + 1; l <= 5; l++) {
							Vector<Integer> t = new Vector<>();
							t.addElement(j);
							t.addElement(k);
							t.addElement(l);
							C.add(t);
						}
			}
			if (i == 4) {
				for (int j = 1; j <= 2; j++)
					for (int k = j + 1; k <= 3; k++)
						for (int l = k + 1; l <= 4; l++)
							for (int m = l + 1; m <= 5; m++) {
								Vector<Integer> t = new Vector<>();
								t.addElement(j);
								t.addElement(k);
								t.addElement(l);
								t.addElement(m);
								C.add(t);
							}
			}
		}
		Vector<Integer> t=new Vector<>();
		t.addElement(1);t.addElement(2);t.addElement(3);t.addElement(4);t.addElement(5);
		C.add(t);
		//finish
		
		
		Exact_Cover EC=new Exact_Cover(X, C);// create an Exact_Cover object
		int [][] M=EC.toMatrix();//transfer it into a Matrix
		Dancing_Links dl=new Dancing_Links(M);// transfer it into Dancing_links structure
		PriorityQueue<Column> pc=dl.toPQ();//Create a priority queue in order to get easily the minimal column.
		long t1=System.currentTimeMillis();
		LinkedList<LinkedList<Vector<Integer>>> result=EC.exactCover();
		long t2=System.currentTimeMillis();
		System.out.println("Time for naïve implement="+(t2-t1));
		Exact_Cover.Afficher(result);
		long t3=System.currentTimeMillis();
		LinkedList<LinkedList<Vector<Integer>>> result_dl=dl.exactCover(pc);
		long t4=System.currentTimeMillis();
		System.out.println("Time for dancing_Link implement="+(t4-t3));
		System.out.println("Result==dlResult?"+(result.size()==result_dl.size()));
	}
}
