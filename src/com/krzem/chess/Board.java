package com.krzem.chess;



import java.awt.Graphics2D;



public class Board extends Constants{
	public Rectangle RECT=new Rectangle(WINDOW_SIZE.width/2-TILE_SIZE*4,WINDOW_SIZE.height/2-TILE_SIZE*4,TILE_SIZE*8,TILE_SIZE*8);
	public Main cls;
	public Piece[][] board;
	public int[] score;
	public boolean _sel=false;
	public boolean _csel=false;
	public int _turn=1;
	public int[] _mv;
	public Piece[][] _dpl;
	public AI ai;



	public Board(Main cls){
		this.cls=cls;
		this.score=new int[]{0,0};
		this._dpl=new Piece[2][16];
		this._default();
		this.ai=(BLACK_AI==true?new AI(this.cls,this):null);
	}



	public void update(){
		for (int y=0;y<8;y++){
			for (int x=0;x<8;x++){
				if (this.board[y][x]!=null){
					this.board[y][x].update();
				}
			}
		}
		if (this._csel==true){
			this._csel=false;
			this._sel=false;
		}
		if (this._mv!=null){
			if (this.board[this._mv[3]][this._mv[2]]!=null){
				Piece dp=this.board[this._mv[3]][this._mv[2]];
				this.score[this._turn]+=dp._value();
				for (int i=0;i<16;i++){
					if (this._dpl[this._turn][i]==null){
						this._dpl[this._turn][i]=dp;
						break;
					}
				}
			}
			this.board=this._move(this.board,this._mv);
			this._mv=null;
			this._turn=1-this._turn;
			for (int y=0;y<8;y++){
				for (int x=0;x<8;x++){
					if (this.board[y][x]!=null){
						this.board[y][x].update();
					}
				}
			}
			if (this._turn==0){
				if (this.ai!=null){
					this.ai.move();
				}
			}
		}
	}



	public void draw(Graphics2D g){
		g.setColor(BG_COLOR);
		g.fillRect(0,0,WINDOW_SIZE.width,WINDOW_SIZE.height);
		g.setColor(BOARD_OUTLINE_COLOR);
		g.fillRect(RECT.x-BOARD_OUTLINE_SIZE,RECT.y-BOARD_OUTLINE_SIZE,RECT.w+BOARD_OUTLINE_SIZE*2,RECT.h+BOARD_OUTLINE_SIZE*2);
		g.fillRect(RECT.x-BOARD_OUTLINE_SIZE,RECT.y-BOARD_OUTLINE_SIZE*3-BOARD_DESTROYED_PIECE_OFFSET-TILE_SIZE*2,RECT.w+BOARD_OUTLINE_SIZE*2,TILE_SIZE*2+BOARD_OUTLINE_SIZE*2);
		g.fillRect(RECT.x-BOARD_OUTLINE_SIZE,RECT.y+RECT.h+BOARD_DESTROYED_PIECE_OFFSET,RECT.w+BOARD_OUTLINE_SIZE*2,TILE_SIZE*2+BOARD_OUTLINE_SIZE*2);
		g.setColor(TILE_COLOR_A);
		g.fillRect(RECT.x,RECT.y-BOARD_OUTLINE_SIZE*2-BOARD_DESTROYED_PIECE_OFFSET-TILE_SIZE*2,RECT.w,TILE_SIZE*2);
		g.setColor(TILE_COLOR_B);
		g.fillRect(RECT.x,RECT.y+RECT.h+BOARD_OUTLINE_SIZE+BOARD_DESTROYED_PIECE_OFFSET,RECT.w,TILE_SIZE*2);
		for (int y=0;y<8;y++){
			for (int x=0;x<8;x++){
				if ((y*9+x)%2==1){
					g.setColor(TILE_COLOR_A);
				}
				else{
					g.setColor(TILE_COLOR_B);
				}
				g.fillRect(RECT.x+x*TILE_SIZE,RECT.y+y*TILE_SIZE,TILE_SIZE,TILE_SIZE);
			}
		}
		for (int y=0;y<8;y++){
			for (int x=0;x<8;x++){
				if (this.board[y][x]!=null){
					this.board[y][x].draw_moves(g);
				}
			}
		}
		for (int y=0;y<8;y++){
			for (int x=0;x<8;x++){
				if (this.board[y][x]!=null){
					this.board[y][x].draw(g);
				}
			}
		}
		int wx=RECT.x+0;
		int wy=RECT.y+RECT.h+BOARD_OUTLINE_SIZE+BOARD_DESTROYED_PIECE_OFFSET;
		for (int i=0;i<16;i++){
			Piece p=this._dpl[1][i];
			if (p==null){
				break;
			}
			p.draw(g,wx+(i%8)*TILE_SIZE+TILE_SIZE/2,wy+(i/8)*TILE_SIZE+TILE_SIZE/2);
		}
		int bx=RECT.x+0;
		int by=RECT.y-BOARD_OUTLINE_SIZE*2-BOARD_DESTROYED_PIECE_OFFSET-TILE_SIZE;
		for (int i=0;i<16;i++){
			Piece p=this._dpl[0][i];
			if (p==null){
				break;
			}
			p.draw(g,bx+(i%8)*TILE_SIZE+TILE_SIZE/2,by-(i/8)*TILE_SIZE+TILE_SIZE/2);
		}
	}



	public Piece[][] _move(Piece[][] b,int[] mv){
		Piece p=b[mv[1]][mv[0]];
		p.x=mv[2];
		p.y=mv[3];
		if ((p.y==7&&p.cl==0)||(p.y==0&&p.cl==1)){
			p.type="queen";
		}
		b[mv[3]][mv[2]]=p;
		b[mv[1]][mv[0]]=null;
		return b;
	}



	private void _default(){
		this.board=new Piece[8][8];
		String[] fl={"rook","knight","bishop","queen","king","bishop","knight","rook"};
		int x=0;
		for (String fig:fl){
			this.board[0][x]=new Piece(this.cls,this,0,fig,x,0);
			this.board[7][x]=new Piece(this.cls,this,1,fig,x,7);
			x++;
		}
		for (x=0;x<8;x++){
			this.board[1][x]=new Piece(this.cls,this,0,"pawn",x,1);
			this.board[6][x]=new Piece(this.cls,this,1,"pawn",x,6);
		}
	}
}
