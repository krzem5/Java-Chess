package com.krzem.chess;



import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;



public class Piece extends Constants{
	public Main cls;
	public Board b;
	public int cl;
	public String type;
	public int x;
	public int y;
	private Vector[] _mvl;
	private Vector[] _dmvl;
	private boolean _sel=false;
	private boolean _md=false;



	public Piece(Main cls,Board b,int cl,String type,int x,int y){
		this.cls=cls;
		this.b=b;
		this.cl=cl;
		this.type=type;
		this.x=x;
		this.y=y;
	}



	@Override
	public Piece clone(){
		return new Piece(this.cls,this.b,this.cl,this.type,this.x,this.y);
	}



	public void update(){
		if (this.cls.MOUSE==1&&this.cls.MOUSE_BUTTON==1&&this.cls.MOUSE_COUNT==1&&this._md==false&&((this.b._turn==0&&this.b.ai==null)||this.b._turn!=0)){
			this._md=true;
			if (this._sel==false&&this.b._turn==this.cl&&this.b._sel==false){
				int x=this.b.RECT.x+this.x*TILE_SIZE;
				int y=this.b.RECT.y+this.y*TILE_SIZE;
				if (x<=this.cls.MOUSE_POS.x&&this.cls.MOUSE_POS.x<x+TILE_SIZE&&y<=this.cls.MOUSE_POS.y&&this.cls.MOUSE_POS.y<y+TILE_SIZE){
					this._sel=true;
					this.b._sel=true;
				}
			}
			if (this._sel==true){
				int x=(this.cls.MOUSE_POS.x-this.b.RECT.x)/TILE_SIZE;
				int y=(this.cls.MOUSE_POS.y-this.b.RECT.y)/TILE_SIZE;
				if (x==this.x&&y==this.y){

				}
				else{
					boolean ok=false;
					for (Vector v:this._mvl){
						if (v.x==x&&v.y==y){
							this.b._mv=new int[]{this.x,this.y,v.x,v.y};
							ok=true;
							break;
						}
					}
					if (ok==false){
						for (Vector v:this._dmvl){
							if (v.x==x&&v.y==y){
								this.b._mv=new int[]{this.x,this.y,v.x,v.y};
								ok=true;
								break;
							}
						}
					}
					this.b._csel=true;
					this._sel=false;
				}
			}
		}
		if (this.cls.MOUSE==0&&this._md==true){
			this._md=false;
		}
		this._mvl=new Vector[0];
		this._dmvl=new Vector[0];
		if (this.b._turn==this.cl){
			Vector[][] mv=this._valid_moves(this.b.board);
			this._mvl=mv[0];
			this._dmvl=mv[1];
		}
	}



	public void draw(Graphics2D g){
		this.draw(g,this.b.RECT.x+this.x*TILE_SIZE+TILE_SIZE/2,this.b.RECT.y+this.y*TILE_SIZE+TILE_SIZE/2);
	}



	public void draw(Graphics2D g,int x,int y){
		if (this.cl==1){
			g.setColor(PIECE_COLOR_WHITE);
		}
		else{
			g.setColor(PIECE_COLOR_BLACK);
		}
		if (this._sel==true){
			g.setFont(PIECE_FONT.deriveFont(PIECE_SELECTED_FONT_SIZE));
		}
		else{
			g.setFont(PIECE_FONT);
		}
		String sh=this._gen_sh();
		FontMetrics fm=g.getFontMetrics();
		x-=fm.stringWidth(sh)/2;
		y+=fm.getMaxDescent();
		g.drawString(sh,x,y);
	}



	public void draw_moves(Graphics2D g){
		if (this._sel==true){
			g.setColor(PIECE_NORMAL_MOVE_COLOR);
			for (Vector v:this._mvl){
				int mx=this.b.RECT.x+v.x*TILE_SIZE;
				int my=this.b.RECT.y+v.y*TILE_SIZE;
				g.fillRect(mx,my,TILE_SIZE,TILE_SIZE);
			}
			g.setColor(PIECE_DEADLY_MOVE_COLOR);
			for (Vector v:this._dmvl){
				int mx=this.b.RECT.x+v.x*TILE_SIZE;
				int my=this.b.RECT.y+v.y*TILE_SIZE;
				g.fillRect(mx,my,TILE_SIZE,TILE_SIZE);
			}
		}
	}



	public int _value(){
		if (this.type.equals("king")){
			return 100;
		}
		if (this.type.equals("queen")){
			return 9;
		}
		if (this.type.equals("rook")){
			return 5;
		}
		if (this.type.equals("bishop")){
			return 3;
		}
		if (this.type.equals("knight")){
			return 3;
		}
		return 1;
	}



	public double _eval_value(int x,int y){
		double mlt=1;
		if (this.cl==1){
			if (this.type.equals("king")){
				mlt=AI_EVAL_BOARD_KING_W[y][x];
			}
			else if (this.type.equals("queen")){
				mlt=AI_EVAL_BOARD_QUEEN[y][x];
			}
			else if (this.type.equals("rook")){
				mlt=AI_EVAL_BOARD_ROOK_W[y][x];
			}
			else if (this.type.equals("bishop")){
				mlt=AI_EVAL_BOARD_BISHOP_W[y][x];
			}
			else if (this.type.equals("knight")){
				mlt=AI_EVAL_BOARD_KNIGHT[y][x];
			}
			else{
				mlt=AI_EVAL_BOARD_PAWN_W[y][x];
			}
		}
		else{
			if (this.type.equals("king")){
				mlt=AI_EVAL_BOARD_KING_B[y][x];
			}
			else if (this.type.equals("queen")){
				mlt=AI_EVAL_BOARD_QUEEN[y][x];
			}
			else if (this.type.equals("rook")){
				mlt=AI_EVAL_BOARD_ROOK_B[y][x];
			}
			else if (this.type.equals("bishop")){
				mlt=AI_EVAL_BOARD_BISHOP_B[y][x];
			}
			else if (this.type.equals("knight")){
				mlt=AI_EVAL_BOARD_KNIGHT[y][x];
			}
			else{
				mlt=AI_EVAL_BOARD_PAWN_B[y][x];
			}
		}
		return this._value()*10*mlt;
	}



	public Vector[] _moves(Piece[][] b){
		Vector[][] mv=this._valid_moves(b);
		Vector[] o=new Vector[mv[0].length+mv[1].length];
		System.arraycopy(mv[0],0,o,0,mv[0].length);
		System.arraycopy(mv[1],0,o,mv[0].length,mv[1].length);
		return o;
	}



	private String _gen_sh(){
		if (this.type.equals("king")){
			return "K";
		}
		if (this.type.equals("queen")){
			return "Q";
		}
		if (this.type.equals("rook")){
			return "R";
		}
		if (this.type.equals("bishop")){
			return "B";
		}
		if (this.type.equals("knight")){
			return "Kn";
		}
		return "P";
	}



	private boolean _on_board(int x,int y){
		return (x>=0&&x<8&&y>=0&&y<8);
	}



	private boolean _empty(Piece[][] b,int x,int y){
		if (this._on_board(x,y)==false){
			return false;
		}
		return (b[y][x]==null);
	}



	private int _color(Piece[][] b,int x,int y){
		if (b[y][x]==null){
			return -1;
		}
		return b[y][x].cl;
	}



	private void _horizontal(Piece[][] b,ArrayList<Vector> mvl,ArrayList<Vector> dmvl){
		for (int x=this.x+1;x<8;x++){
			if (this._empty(b,x,this.y)==true){
				mvl.add(new Vector(x,this.y));
			}
			else{
				if (this._color(b,x,this.y)!=this.cl){
					dmvl.add(new Vector(x,this.y));
				}
				break;
			}
		}
		for (int x=this.x-1;x>=0;x--){
			if (this._empty(b,x,this.y)==true){
				mvl.add(new Vector(x,this.y));
			}
			else{
				if (this._color(b,x,this.y)!=this.cl){
					dmvl.add(new Vector(x,this.y));
				}
				break;
			}
		}
		for (int y=this.y+1;y<8;y++){
			if (this._empty(b,this.x,y)==true){
				mvl.add(new Vector(this.x,y));
			}
			else{
				if (this._color(b,this.x,y)!=this.cl){
					dmvl.add(new Vector(this.x,y));
				}
				break;
			}
		}
		for (int y=this.y-1;y>=0;y--){
			if (this._empty(b,this.x,y)==true){
				mvl.add(new Vector(this.x,y));
			}
			else{
				if (this._color(b,this.x,y)!=this.cl){
					dmvl.add(new Vector(this.x,y));
				}
				break;
			}
		}
	}



	private void _diagonal(Piece[][] b,ArrayList<Vector> mvl,ArrayList<Vector> dmvl){
		int x=this.x+0;
		int y=this.y+0;
		while (true){
			x+=1;
			y+=1;
			if (!this._on_board(x,y)){
				break;
			}
			if (this._empty(b,x,y)==true){
				mvl.add(new Vector(x,y));
			}
			else{
				if (this._color(b,x,y)!=this.cl){
					dmvl.add(new Vector(x,y));
				}
				break;
			}
		}
		x=this.x+0;
		y=this.y+0;
		while (true){
			x-=1;
			y+=1;
			if (!this._on_board(x,y)){
				break;
			}
			if (this._empty(b,x,y)==true){
				mvl.add(new Vector(x,y));
			}
			else{
				if (this._color(b,x,y)!=this.cl){
					dmvl.add(new Vector(x,y));
				}
				break;
			}
		}
		x=this.x+0;
		y=this.y+0;
		while (true){
			x-=1;
			y-=1;
			if (!this._on_board(x,y)){
				break;
			}
			if (this._empty(b,x,y)==true){
				mvl.add(new Vector(x,y));
			}
			else{
				if (this._color(b,x,y)!=this.cl){
					dmvl.add(new Vector(x,y));
				}
				break;
			}
		}
		x=this.x+0;
		y=this.y+0;
		while (true){
			x+=1;
			y-=1;
			if (!this._on_board(x,y)){
				break;
			}
			if (this._empty(b,x,y)==true){
				mvl.add(new Vector(x,y));
			}
			else{
				if (this._color(b,x,y)!=this.cl){
					dmvl.add(new Vector(x,y));
				}
				break;
			}
		}
	}



	private Vector[][] _valid_moves(Piece[][] b){
		ArrayList<Vector> mvl=new ArrayList<Vector>();
		ArrayList<Vector> dmvl=new ArrayList<Vector>();
		if (this.type.equals("king")){
			for (int i=-1;i<=1;i++){
				for (int j=-1;j<=1;j++){
					if ((i==0&&j==0)||!this._on_board(this.x+i,this.y+j)){
						continue;
					}
					if (this._empty(b,this.x+i,this.y+j)){
						mvl.add(new Vector(this.x+i,this.y+j));
					}
					else{
						if (this._color(b,this.x+i,this.y+j)!=this.cl){
							dmvl.add(new Vector(this.x+i,this.y+j));
						}
					}
				}
			}
		}
		else if (this.type.equals("queen")){
			this._horizontal(b,mvl,dmvl);
			this._diagonal(b,mvl,dmvl);
		}
		else if (this.type.equals("rook")){
			this._horizontal(b,mvl,dmvl);
		}
		else if (this.type.equals("bishop")){
			this._diagonal(b,mvl,dmvl);
		}
		else if (this.type.equals("knight")){
			if (this._on_board(this.x+2,this.y+1)&&this._color(b,this.x+2,this.y+1)!=this.cl){
				if (this._empty(b,this.x+2,this.y+1)){
					mvl.add(new Vector(this.x+2,this.y+1));
				}
				else{
					dmvl.add(new Vector(this.x+2,this.y+1));
				}
			}
			if (this._on_board(this.x+2,this.y-1)&&this._color(b,this.x+2,this.y-1)!=this.cl){
				if (this._empty(b,this.x+2,this.y-1)){
					mvl.add(new Vector(this.x+2,this.y-1));
				}
				else{
					dmvl.add(new Vector(this.x+2,this.y-1));
				}
			}
			if (this._on_board(this.x-2,this.y+1)&&this._color(b,this.x-2,this.y+1)!=this.cl){
				if (this._empty(b,this.x-2,this.y+1)){
					mvl.add(new Vector(this.x-2,this.y+1));
				}
				else{
					dmvl.add(new Vector(this.x-2,this.y+1));
				}
			}
			if (this._on_board(this.x-2,this.y-1)&&this._color(b,this.x-2,this.y-1)!=this.cl){
				if (this._empty(b,this.x-2,this.y-1)){
					mvl.add(new Vector(this.x-2,this.y-1));
				}
				else{
					dmvl.add(new Vector(this.x-2,this.y-1));
				}
			}
			if (this._on_board(this.x+1,this.y+2)&&this._color(b,this.x+1,this.y+2)!=this.cl){
				if (this._empty(b,this.x+1,this.y+2)){
					mvl.add(new Vector(this.x+1,this.y+2));
				}
				else{
					dmvl.add(new Vector(this.x+1,this.y+2));
				}
			}
			if (this._on_board(this.x-1,this.y+2)&&this._color(b,this.x-1,this.y+2)!=this.cl){
				if (this._empty(b,this.x-1,this.y+2)){
					mvl.add(new Vector(this.x-1,this.y+2));
				}
				else{
					dmvl.add(new Vector(this.x-1,this.y+2));
				}
			}
			if (this._on_board(this.x+1,this.y-2)&&this._color(b,this.x+1,this.y-2)!=this.cl){
				if (this._empty(b,this.x+1,this.y-2)){
					mvl.add(new Vector(this.x+1,this.y-2));
				}
				else{
					dmvl.add(new Vector(this.x+1,this.y-2));
				}
			}
			if (this._on_board(this.x-1,this.y-2)&&this._color(b,this.x-1,this.y-2)!=this.cl){
				if (this._empty(b,this.x-1,this.y-2)){
					mvl.add(new Vector(this.x-1,this.y-2));
				}
				else{
					dmvl.add(new Vector(this.x-1,this.y-2));
				}
			}
		}
		else{
			if (this.cl==1&&this._on_board(this.x,this.y-1)&&this._empty(b,this.x,this.y-1)){
				mvl.add(new Vector(this.x,this.y-1));
			}
			if (this.cl==1&&this.y==6&&this._empty(b,this.x,this.y-1)&&this._empty(b,this.x,this.y-2)){
				mvl.add(new Vector(this.x,this.y-2));
			}
			if (this.cl==1&&this._on_board(this.x+1,this.y-1)&&!this._empty(b,this.x+1,this.y-1)&&this._color(b,this.x+1,this.y-1)==0){
				dmvl.add(new Vector(this.x+1,this.y-1));
			}
			if (this.cl==1&&this._on_board(this.x-1,this.y+1)&&!this._empty(b,this.x-1,this.y-1)&&this._color(b,this.x-1,this.y-1)==0){
				dmvl.add(new Vector(this.x-1,this.y-1));
			}
			if (this.cl==0&&this._on_board(this.x,this.y+1)&&this._empty(b,this.x,this.y+1)){
				mvl.add(new Vector(this.x,this.y+1));
			}
			if (this.cl==0&&this.y==1&&this._empty(b,this.x,this.y+1)&&this._empty(b,this.x,this.y+2)){
				mvl.add(new Vector(this.x,this.y+2));
			}
			if (this.cl==0&&this._on_board(this.x+1,this.y+1)&&!this._empty(b,this.x+1,this.y+1)&&this._color(b,this.x+1,this.y+1)==1){
				dmvl.add(new Vector(this.x+1,this.y+1));
			}
			if (this.cl==0&&this._on_board(this.x-1,this.y+1)&&!this._empty(b,this.x-1,this.y+1)&&this._color(b,this.x-1,this.y+1)==1){
				dmvl.add(new Vector(this.x-1,this.y+1));
			}
		}
		return new Vector[][]{mvl.toArray(new Vector[mvl.size()]),dmvl.toArray(new Vector[dmvl.size()])};
	}
}
