package com.krzem.chess;



import java.awt.Graphics2D;
import java.lang.Math;
import java.lang.Runnable;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;



public class AI extends Constants{
	public Main cls;
	public Board b;
	private boolean _first=true;
	private Map<int[],Double> _scl=new HashMap<int[],Double>();
	private int _mv_left=0;



	public AI(Main cls,Board b){
		this.cls=cls;
		this.b=b;
	}



	public void move(){
		ArrayList<int[]> pm=this._possible_moves(this.b.board,0);
		if (this._first==true){
			this._first=false;
			this.b._mv=pm.get(new Random().nextInt(pm.size()));
			return;
		}
		this._scl.clear();
		this._mv_left=0;
		ArrayList<Piece[][]> pmb=new ArrayList<Piece[][]>();
		for (int[] mv:pm){
			pmb.add(this._clone_make_move(this.b.board,mv));
		}
		for (int i=0;i<pm.size();i++){
			this._minmax_env(pm.get(i),pmb.get(i));
			this._mv_left++;
			// double s=this._minmax(pmb.get(i),Double.MIN_VALUE,Double.MAX_VALUE,AI_MINMAX_DEPTH,1);
			// if(s>=bms){
			// 	bm=pm.get(i);
			// 	bms=s;
			// }
		}
		// this.b._mv=bm;
	}



	private void _minmax_env(int[] mv,Piece[][] b){
		AI ths=this;
		new Thread(new Runnable(){
			@Override
			public void run(){
				ths._minmax_root(mv,b);
			}
		}).start();
	}



	private void _minmax_root(int[] mv,Piece[][] b){
		this._scl.put(mv,this._minmax(b,Double.MIN_VALUE,Double.MAX_VALUE,AI_MINMAX_DEPTH,1));
		synchronized (this){
			this._mv_left--;
			if (this._mv_left==0){
				Iterator it=this._scl.entrySet().iterator();
				int[] bm=null;
				double bms=Double.MIN_VALUE;
				while (it.hasNext()){
					Map.Entry<int[],Double> e=(Map.Entry<int[],Double>)it.next();
					double sc=(double)e.getValue();
					if (sc>=bms){
						bms=sc;
						bm=e.getKey();
					}
					System.out.println(sc);
					it.remove();
				}
				this.b._mv=bm;
			}
			System.out.println(this._mv_left);
		}
	}



	private double _minmax(Piece[][] b,double ao,double bo,int d,int cl){
		// [(a+1)**1.5/(6**1.5) for a in range(5,-1,-1)]
		if (d==0){
			return this._eval_board(b);
		}
		if (cl==1){
			ArrayList<int[]> pm=this._possible_moves(b,1);
			double bm=bo+0;
			for (int[] mv:pm){
				bm=Math.min(bm,this._minmax(this._clone_make_move(b,mv),ao,bo,d-1,0));
				bo=Math.min(bo,bm);
				if (bo<=ao){
					return bm;
				}
			}
			return bm;
		}
		else{
			ArrayList<int[]> pm=this._possible_moves(b,0);
			double bm=ao+0;
			for(int[] mv:pm){
				bm=Math.max(bm,this._minmax(this._clone_make_move(b,mv),ao,bo,d-1,1));
				ao=Math.max(ao,bm);
				if (bo<=ao){
					return bm;
				}
			}
			return bm;
		}
	}



	private Piece[][] _clone_b(Piece[][] b){
		Piece[][] nb=new Piece[8][8];
		for (int y=0;y<8;y++){
			for (int x=0;x<8;x++){
				if (b[y][x]!=null){
					nb[y][x]=b[y][x].clone();
				}
			}
		}
		return nb;
	}



	private Piece[][] _clone_make_move(Piece[][] b,int[] mv){
		return this.b._move(this._clone_b(b),mv);
	}



	private ArrayList<int[]> _possible_moves(Piece[][] b,int cl){
		ArrayList<int[]> pm=new ArrayList<int[]>();
		for (int y=0;y<8;y++){
			for (int x=0;x<8;x++){
				Piece p=b[y][x];
				if (p!=null&&p.cl==cl){
					Vector[] mvl=p._moves(b);
					for (Vector mv:mvl){
						pm.add(new int[]{x,y,mv.x,mv.y});
					}
				}
			}
		}
		return pm;
	}



	private double _eval_board(Piece[][] b){
		double sc=0;
		for (int y=0;y<8;y++){
			for (int x=0;x<8;x++){
				Piece p=b[y][x];
				if (p!=null){
					sc+=p._eval_value(x,y)*(p.cl==0?1:-1);
				}
			}
		}
		return sc;
	}
}