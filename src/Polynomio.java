import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Vector;

import org.omg.CORBA.SystemException;

import java.awt.*;

public class Polynomio {

	private LinkedList<Coords_rep> polyo;

	public Polynomio() {
		this.polyo = new LinkedList<Coords_rep>();
	}

	public Polynomio(LinkedList<Coords_rep> p) {
		this.polyo = p;
	}

	public Polynomio(String S, int ox, int oy) {
		LinkedList<Coords_rep> list = new LinkedList<Coords_rep>();
		String S1=S.substring(1, S.length()-1);
		String[] SubS1 = S1.split(",|\\(|\\)| ");
		for (int l = 1; l < SubS1.length; l = l + 5) {
			int i = Integer.parseInt("" + SubS1[l]);
			int j = Integer.parseInt("" + SubS1[l + 1]);
			Coords_rep Cr = new Coords_rep(ox + i, oy - j);// changer les
															// coordonnées
			list.add(Cr);
		}
		this.polyo = list;
	}

	public int getSize() {
		return this.polyo.size();
	}
	
	public LinkedList<Coords_rep> get_polyo(){
		return this.polyo;
	}

	public static LinkedList<Polynomio> toList(File file) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(file));
		LinkedList<Polynomio> list = new LinkedList<Polynomio>();
		int i = 0;
		while (true) {
			String s = bf.readLine();
			if (s == null)
				break;
			list.add(new Polynomio(s, 1 + i, 15));
			i += 6;
		}
		return list;
	}


	public Image2d toImage(Image2d img) {
		for (Coords_rep CrP : this.polyo)
			img.addPolygon(CrP.get_Xcoords(), CrP.get_Ycoords(), Color.RED);
		return img;
	}
	
	public Image2d toImageRandom(Image2d img, Color c){
		for (Coords_rep CrP : this.polyo)
			img.addPolygon(CrP.get_Xcoords(), CrP.get_Ycoords(), c);
		return img;
	}

	public Polynomio Translation(int i, int j) {// 将string变为一组小正方形的坐标几何，同时ox，oy代表正方形左下角原点，改变它就可以做平移
		LinkedList<Coords_rep> list = new LinkedList<>();
		for (Coords_rep cr : this.polyo) {
			Coords_rep co = cr.Translation(i, j);
			list.add(co);
		}
		return new Polynomio(list);
	}

	public Polynomio rotate(int deg) {
		LinkedList<Coords_rep> list = new LinkedList<>();
		int[] or = this.polyo.getFirst().get_Origine();
		for (Coords_rep cr : this.polyo) {
			Coords_rep co = cr.rotate(or[0], or[1], deg);
			list.add(co);
		}
		return new Polynomio(list);
	}

	public Polynomio Dilate(int r) {
		LinkedList<Coords_rep> list = new LinkedList<>();
		int[] or = this.polyo.getFirst().get_Origine();
		for (Coords_rep cr : this.polyo) {
			Coords_rep co = cr.Dilate(or[0], or[1], r);
			int[] or_co = co.get_Origine();
			for (int i = 0; i < r; i++) {
				for (int j = 0; j < r; j++) {
					Coords_rep co1 = new Coords_rep(or_co[0] + i, or_co[1] + j);
					list.add(co1);
				}
			}
		}
		return new Polynomio(list);
	}

	public Polynomio reflection(int axis) {
		LinkedList<Coords_rep> list = new LinkedList<>();
		int[] or = this.polyo.getFirst().get_Origine();
		for (Coords_rep cr : this.polyo) {
			Coords_rep co = cr.reflection(or[0], or[1], axis);
			list.add(co);
		}
		return new Polynomio(list);
	}
	
	public String toString() {
		String s = "";
		for (Coords_rep c : this.polyo) {
			s = s + c.toString();
		}
		return s;
	}
	
	/******** methode naive ************/
	public static void enumerate_naive(LinkedList<Polynomio> list, Polynomio init, int ox, int oy, int P){
		if (init.polyo.size() < P) {
			LinkedList<Coords_rep> visited = new LinkedList<>();
			for (Coords_rep u : init.polyo){
				visited.add(u);
			}
			for (Coords_rep u : visited){
				for (Coords_rep c : Coords_rep.successors(u)) {
					if (!visited.contains(c)){
						init.polyo.add(c);
						Polynomio p = new Polynomio();// 由于add的时候是加地址的，所以必须新建，不然之后会被更改
						for (Coords_rep cp : init.polyo)
							p.polyo.add(cp);
						list.add(p);
						enumerate_naive(list,init,ox,oy,P);
						init.polyo.removeLast();
					}
				}
			}
		}
	}
	
	public static LinkedList<Polynomio> generate_fix_naive(LinkedList<Polynomio> list, int size){
		LinkedList<Polynomio> l = new LinkedList<>();
		for(Polynomio Cp:list)
			if(Cp.getSize() == size){
				if (!l.contains(Cp)){
					l.add(Cp);
				}
			}
		return l;
	}
	
	public LinkedList<Polynomio> all_trans()//为了计算free而定义的函数，产生this的所有变化
	{
		LinkedList<Polynomio> l_trans=new LinkedList<>();
		l_trans.add(this);
		l_trans.add(this.rotate(1));
		l_trans.add(this.rotate(2));
		l_trans.add(this.rotate(3));
		l_trans.add(this.reflection(1));
		l_trans.add(this.reflection(2));
		l_trans.add(this.reflection(3));
		l_trans.add(this.reflection(4));
		return l_trans;
	}
	
	public static LinkedList<Polynomio> generate_free(LinkedList<Polynomio> Poly_fix) {//根据fix产生free，主要想法就是变换之后相同的都去掉
		LinkedList<Polynomio> Poly_free = new LinkedList<>();
		Poly_free.add(Poly_fix.getFirst());
		for (Polynomio Cp : Poly_fix) {
			LinkedList<Polynomio> l_trans = Cp.all_trans();
			boolean flag = false;
			while (!l_trans.isEmpty()) {
				Polynomio P = l_trans.poll();
				if (Poly_free.contains(P)){
					flag = true;
					break;
				}
			}
			if (flag == false)
				Poly_free.add(Cp);
		}
		return Poly_free;
	}
	/********* fin de methode naive ************/
	
	
	/******************methode implemente*******************/
	public static void enumerate(LinkedList<Polynomio> list, LinkedList<Coords_rep> neighbours, Polynomio parent,
			LinkedList<Coords_rep> untried, int ox, int oy, int P) {
		while (!untried.isEmpty()) {// 当untried不是空集时；
			Coords_rep cr = untried.pop();
			parent.polyo.add(cr);
			Polynomio p = new Polynomio();// 由于add的时候是加地址的，所以必须新建，不然之后会被更改
			for (Coords_rep cp : parent.polyo)
				p.polyo.add(cp);
			list.add(p);// 将这个点加到总的list中
			if (parent.polyo.size() < P) {
				int n = 0;
				for (Coords_rep c : Coords_rep.successors(cr)) {
					int[] or = c.get_Origine();
					if (neighbours.contains(c)) {// 由于contain是找有没有相同的地址在list中，这不是我们要的，我们需要真实的比较，所以需要重新定义比较的方法
						continue;
					} else if ((oy - or[1]) < 0 || ((oy - or[1]) == 0 && (or[0] - ox) >= 0)) {
						neighbours.add(c);
						untried.addLast(c);
						n++;
					}
				}
				// Polynomio parent_new = new Polynomio(parent.polyo);
				LinkedList<Coords_rep> untried_new = new LinkedList<Coords_rep>();
				for (Coords_rep c : untried) {
					untried_new.add(c);
				}
				enumerate(list, neighbours, parent, untried_new, ox, oy, P);
				for (int i = 0; i < n; i++) {
					untried.removeLast();
					neighbours.removeLast();// 新加的********
				}
			}
			parent.polyo.removeLast();
		}
	}
	
	public static LinkedList<Polynomio> generate_fix(LinkedList<Polynomio> list, int size)
	{
		LinkedList<Polynomio> l=new LinkedList<>();
		for(Polynomio Cp:list)
			if(Cp.getSize()==size)
				l.add(Cp);
		return l;
	}
	
	public static int HX_prime(LinkedList<Polynomio> list, int p){
		if (p%2 != 0) return 0;
		else return generate_fix(list, p/2).size();
	}
	
	public static void HI_enumerate(LinkedList<Polynomio> list, LinkedList<Coords_rep> neighbours, Polynomio parent,
			LinkedList<Coords_rep> untried, int ox, int oy, int P){
		while (!untried.isEmpty()) {// 当untried不是空集时；
			Coords_rep cr = untried.pop();
			parent.polyo.add(cr);
			Polynomio p = new Polynomio();// 由于add的时候是加地址的，所以必须新建，不然之后会被更改
			for (Coords_rep cp : parent.polyo)
				p.polyo.add(cp);
			list.add(p);// 将这个点加到总的list中
			int size = 0;
			for (Coords_rep cp : parent.polyo){
				if (cp.get_Origine()[1] == oy) size += 1;
				else size += 2;
			}
			if (size < P) {
				int n = 0;
				for (Coords_rep c : Coords_rep.successors(cr)) {
					int[] or = c.get_Origine();
					if (neighbours.contains(c)) {// 由于contain是找有没有相同的地址在list中，这不是我们要的，我们需要真实的比较，所以需要重新定义比较的方法
						continue;
					} else if ((oy - or[1]) < 0 || ((oy - or[1]) == 0 && (or[0] - ox) >= 0)) {
						neighbours.add(c);
						untried.addLast(c);
						n++;
					}
				}
				// Polynomio parent_new = new Polynomio(parent.polyo);
				LinkedList<Coords_rep> untried_new = new LinkedList<Coords_rep>();
				for (Coords_rep c : untried) {
					untried_new.add(c);
				}
				enumerate(list, neighbours, parent, untried_new, ox, oy, P);
				for (int i = 0; i < n; i++) {
					untried.removeLast();
					neighbours.removeLast();// 新加的********
				}
			}
			parent.polyo.removeLast();
		}
	}
	
	public static int HI_prime(LinkedList<Polynomio> list, int p){
		LinkedList<Polynomio> HI = generate_fix(list,p);
		return HI.size();
	}
	
	public static void A_enumerate(LinkedList<Polynomio> list, LinkedList<Coords_rep> neighbours, Polynomio parent,
			LinkedList<Coords_rep> untried, int ox, int oy, int P){
		while (!untried.isEmpty()) {// 当untried不是空集时；
			Coords_rep cr = untried.pop();
			parent.polyo.add(cr);
			Polynomio p = new Polynomio();// 由于add的时候是加地址的，所以必须新建，不然之后会被更改
			for (Coords_rep cp : parent.polyo)
				p.polyo.add(cp);
			list.add(p);// 将这个点加到总的list中
			int size = 0;
			for (Coords_rep cp : parent.polyo){
				if (cp.get_Origine()[0] == cp.get_Origine()[1]) size += 1;
				else size += 2;
			}
			if (size < P) {
				int n = 0;
				for (Coords_rep c : Coords_rep.successors(cr)) {
					int[] or = c.get_Origine();
					if (neighbours.contains(c)) {// 由于contain是找有没有相同的地址在list中，这不是我们要的，我们需要真实的比较，所以需要重新定义比较的方法
						continue;
					} else if ((oy - or[1]) < 0 || ((oy - or[1]) == 0 && (or[0] - ox) >= 0)) {
						neighbours.add(c);
						untried.addLast(c);
						n++;
					}
				}
				// Polynomio parent_new = new Polynomio(parent.polyo);
				LinkedList<Coords_rep> untried_new = new LinkedList<Coords_rep>();
				for (Coords_rep c : untried) {
					untried_new.add(c);
				}
				enumerate(list, neighbours, parent, untried_new, ox, oy, P);
				for (int i = 0; i < n; i++) {
					untried.removeLast();
					neighbours.removeLast();// 新加的********
				}
			}
			parent.polyo.removeLast();
		}
	}
	
	public static int A_prime(LinkedList<Polynomio> list, int p){
		LinkedList<Polynomio> A = generate_fix(list,p);
		return A.size();
	}
	/****************fin de methode implemente*******************/

	
	public static LinkedList<LinkedList<Polynomio>> exactCover1(LinkedList<Coords_rep> X, LinkedList<Polynomio> C, int oy){
		LinkedList<LinkedList<Polynomio>> setOfSolution = new LinkedList<>();
		for (Coords_rep co : X){
			int x = co.get_Origine()[0];
			int y = co.get_Origine()[1];
			co.set_Origine(x, 2*oy-y);
		}
		Coords_rep[] map = new Coords_rep[X.size()];
		int k = 0;
		for (Coords_rep c : X){
			map[k] = c;
			k++;
		}
		
		LinkedList<Polynomio> Cvrai = new LinkedList<>();
		LinkedList<Polynomio> Ctrans = new LinkedList<>();
		for (Polynomio p : C){
			LinkedList<Polynomio> l = p.all_trans();
			for (Polynomio pp : l){
				if (!Ctrans.contains(pp)) Ctrans.add(pp);
			}
		}
		System.out.println(Ctrans.size());
		
		for (Polynomio p : Ctrans){
			for (int i = -10;i < 60;i++){
				for (int j = -10;j < 60; j++){
					boolean flag = true;
					Polynomio po = p.Translation(i, -j);
					for (Coords_rep c : po.polyo){
						if (!X.contains(c)){ 
							flag = false;
						}
					}
					if (flag == true) Cvrai.add(po);
				}
			}
		}
		System.out.println(Cvrai.size());
		
		int[][] M = new int[Cvrai.size()][X.size()];
		int ii = 0;
		for (Polynomio p : Cvrai){
			for (Coords_rep c : p.polyo){
				for (int j = 0; j < map.length; j++){
					if (map[j].equals(c)){
						M[ii][j] = 1;
					}
				}
			}
			ii++;
		}
		Dancing_Links dl=new Dancing_Links(M);
		PriorityQueue<Column> pc=dl.toPQ();
		LinkedList<LinkedList<Vector<Integer>>> dlResult=dl.exactCover(pc);
		for (LinkedList<Vector<Integer>> l : dlResult){
			LinkedList<Polynomio> l2 = new LinkedList<>();
			for (Vector<Integer> vec : l){
				LinkedList<Coords_rep> l1 = new LinkedList<>();
				for (Integer itg : vec){
					l1.add(map[itg-1]);
				}
				Polynomio poly = new Polynomio(l1);
				l2.add(poly);
			}
			setOfSolution.add(l2);
		}
		return setOfSolution;
	}
	
	public static LinkedList<LinkedList<Polynomio>> exactCover2_fix(int n, LinkedList<Coords_rep> X, int ox, int oy){
		LinkedList<LinkedList<Polynomio>> setOfSolution = new LinkedList<>();
		LinkedList<Polynomio> list = new LinkedList<Polynomio>();
		LinkedList<Coords_rep> neighbours = new LinkedList<>();
		LinkedList<Coords_rep> untried = new LinkedList<>();
		Coords_rep co = new Coords_rep(ox, oy);
		untried.add(co);
		neighbours.add(co);
		enumerate(list, neighbours, new Polynomio(), untried, ox, oy, n);
		LinkedList<Polynomio> C = generate_fix(list, n);
		System.out.println(C.size());
		for (Coords_rep c : X){
			int x = c.get_Origine()[0];
			int y = c.get_Origine()[1];
			c.set_Origine(x, 2*oy-y);
		}
		Coords_rep[] map = new Coords_rep[X.size()];
		int k = 0;
		for (Coords_rep c : X){
			map[k] = c;
			k++;
		}
		LinkedList<Polynomio> Cvrai = new LinkedList<>();
		for (Polynomio p : C){
			for (int i = 0;i < 10;i++){
				for (int j = 0;j < 10; j++){
					boolean flag = true;
					Polynomio po = p.Translation(i, -j);
					for (Coords_rep c : po.polyo){
						if (!X.contains(c)){ 
							flag = false;
						}
					}
					if (flag == true) Cvrai.add(po);
				}
			}
		}
		System.out.println(Cvrai.size());
		int[][] M = new int[Cvrai.size()][X.size()];
		int ii = 0;
		for (Polynomio p : Cvrai){
			for (Coords_rep c : p.polyo){
				for (int j = 0; j < map.length; j++){
					if (map[j].equals(c)){
						M[ii][j] = 1;
					}
				}
			}
			ii++;
		}
		Dancing_Links dl=new Dancing_Links(M);
		PriorityQueue<Column> pc=dl.toPQ();
		LinkedList<LinkedList<Vector<Integer>>> dlResult=dl.exactCover(pc);
		for (LinkedList<Vector<Integer>> l : dlResult){
			LinkedList<Polynomio> l2 = new LinkedList<>();
			for (Vector<Integer> vec : l){
				LinkedList<Coords_rep> l1 = new LinkedList<>();
				for (Integer itg : vec){
					l1.add(map[itg-1]);
				}
				Polynomio poly = new Polynomio(l1);
				l2.add(poly);
			}
			setOfSolution.add(l2);
		}
		return setOfSolution;
	}
	
	public static LinkedList<LinkedList<Polynomio>> exactCover2_free(int n, LinkedList<Coords_rep> X, int ox, int oy){
		LinkedList<LinkedList<Polynomio>> setOfSolution = new LinkedList<>();
		LinkedList<Polynomio> list = new LinkedList<Polynomio>();
		LinkedList<Coords_rep> neighbours = new LinkedList<>();
		LinkedList<Coords_rep> untried = new LinkedList<>();
		Coords_rep co = new Coords_rep(ox, oy);
		untried.add(co);
		neighbours.add(co);
		enumerate(list, neighbours, new Polynomio(), untried, ox, oy, n);
		LinkedList<Polynomio> Poly_fix = generate_fix(list, n);
		LinkedList<Polynomio> C = generate_free(Poly_fix);
		for (Coords_rep c : X){
			int x = c.get_Origine()[0];
			int y = c.get_Origine()[1];
			c.set_Origine(x, 2*oy-y);
		}
		Coords_rep[] map = new Coords_rep[X.size()];
		int k = 0;
		for (Coords_rep c : X){
			map[k] = c;
			k++;
		}
		LinkedList<Polynomio> Cvrai = new LinkedList<>();
		LinkedList<Polynomio> Ctrans = new LinkedList<>();
		for (Polynomio p : C){
			LinkedList<Polynomio> l = p.all_trans();
			for (Polynomio pp : l){
				if (!Ctrans.contains(pp)) Ctrans.add(pp);
			}
		}
		System.out.println(Ctrans.size());
		
		for (Polynomio p : Ctrans){
			for (int i = -3;i < 15;i++){
				for (int j = -3;j < 15; j++){
					boolean flag = true;
					Polynomio po = p.Translation(i, -j);
					for (Coords_rep c : po.polyo){
						if (!X.contains(c)){ 
							flag = false;
						}
					}
					if (flag == true) Cvrai.add(po);
				}
			}
		}
		System.out.println(Cvrai.size());
		int[][] M = new int[Cvrai.size()][X.size()];
		int ii = 0;
		for (Polynomio p : Cvrai){
			for (Coords_rep c : p.polyo){
				for (int j = 0; j < map.length; j++){
					if (map[j].equals(c)){
						M[ii][j] = 1;
					}
				}
			}
			ii++;
		}
		Dancing_Links dl=new Dancing_Links(M);
		PriorityQueue<Column> pc=dl.toPQ();
		LinkedList<LinkedList<Vector<Integer>>> dlResult=dl.exactCover(pc);
		for (LinkedList<Vector<Integer>> l : dlResult){
			LinkedList<Polynomio> l2 = new LinkedList<>();
			for (Vector<Integer> vec : l){
				LinkedList<Coords_rep> l1 = new LinkedList<>();
				for (Integer itg : vec){
					l1.add(map[itg-1]);
				}
				Polynomio poly = new Polynomio(l1);
				l2.add(poly);
			}
			setOfSolution.add(l2);
		}
		return setOfSolution;
	}
	
	@Override
	public boolean equals(Object o) {//重新定义equals，某polynomio的每一个方块都可以由另一个polynomio的对应的方块做相同的平移得到，那么就视作相等
		Polynomio P = (Polynomio) o;
		if (this == null || P == null)
			return false;
		if (this.polyo.size() != P.polyo.size())
			return false;
		LinkedList<Coords_rep> CoR_p = new LinkedList<Coords_rep>();
		for (Coords_rep CrP : P.polyo) {
			CoR_p.add(CrP);
			Collections.sort(CoR_p);//排序的目的是为了确保每次比较的都是相对应的小方块
		}
		LinkedList<Coords_rep> CoR_t = new LinkedList<Coords_rep>();
		for (Coords_rep CrP : this.polyo) {
			CoR_t.add(CrP);
			Collections.sort(CoR_t);
		}
		Coords_rep Cp = CoR_p.poll();
		Coords_rep Ct = CoR_t.poll();
		int dx = Cp.get_dist_X(Ct);
		int dy = Cp.get_dist_Y(Ct);
		while (!CoR_p.isEmpty()) {
			Cp = CoR_p.poll();
			Ct = CoR_t.poll();
			if ((dx != Cp.get_dist_X(Ct)) || (dy != Cp.get_dist_Y(Ct)))
				return false;
		}
		return true;
	}
	
}