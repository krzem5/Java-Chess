package com.krzem.chess;



import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;



public class Constants{
	public static final int DISPLAY_ID=0;
	public static final GraphicsDevice SCREEN=GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[DISPLAY_ID];
	public static final Rectangle WINDOW_SIZE=SCREEN.getDefaultConfiguration().getBounds();
	public static final int MAX_FPS=60;

	public static final boolean BLACK_AI=true;
	public static final int AI_MINMAX_DEPTH=5;

	public static final double[][] AI_EVAL_BOARD_PAWN_W=new double[][]{{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0},{1.0,1.0,2.0,3.0,3.0,2.0,1.0,1.0},{0.5,0.5,1.0,2.5,2.5,1.0,0.5,0.5},{0.0,0.0,0.0,2.0,2.0,0.0,0.0,0.0},{0.5,-0.5,-1.0,0.0,0.0,-1.0,-0.5,0.5},{0.5,1.0,1.0,-2.0,-2.0,1.0,1.0,0.5},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}};
	public static final double[][] AI_EVAL_BOARD_PAWN_B=new double[][]{{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.5,1.0,1.0,-2.0,-2.0,1.0,1.0,0.5},{0.5,-0.5,-1.0,0.0,0.0,-1.0,-0.5,0.5},{0.0,0.0,0.0,2.0,2.0,0.0,0.0,0.0},{0.5,0.5,1.0,2.5,2.5,1.0,0.5,0.5},{1.0,1.0,2.0,3.0,3.0,2.0,1.0,1.0},{5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}};
	public static final double[][] AI_EVAL_BOARD_KNIGHT=new double[][]{{-5.0,-4.0,-3.0,-3.0,-3.0,-3.0,-4.0,-5.0},{-4.0,-2.0,0.0,0.0,0.0,0.0,-2.0,-4.0},{-3.0,0.0,1.0,1.5,1.5,1.0,0.0,-3.0},{-3.0,0.5,1.5,2.0,2.0,1.5,0.5,-3.0},{-3.0,0.0,1.5,2.0,2.0,1.5,0.0,-3.0},{-3.0,0.5,1.0,1.5,1.5,1.0,0.5,-3.0},{-4.0,-2.0,0.0,0.5,0.5,0.0,-2.0,-4.0},{-5.0,-4.0,-3.0,-3.0,-3.0,-3.0,-4.0,-5.0}};
	public static final double[][] AI_EVAL_BOARD_BISHOP_W=new double[][]{{-2.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-2.0},{-1.0,0.0,0.0,0.0,0.0,0.0,0.0,-1.0},{-1.0,0.0,0.5,1.0,1.0,0.5,0.0,-1.0},{-1.0,0.5,0.5,1.0,1.0,0.5,0.5,-1.0},{-1.0,0.0,1.0,1.0,1.0,1.0,0.0,-1.0},{-1.0,1.0,1.0,1.0,1.0,1.0,1.0,-1.0},{-1.0,0.5,0.0,0.0,0.0,0.0,0.5,-1.0},{-2.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-2.0}};
	public static final double[][] AI_EVAL_BOARD_BISHOP_B=new double[][]{{-2.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-2.0},{-1.0,0.5,0.0,0.0,0.0,0.0,0.5,-1.0},{-1.0,1.0,1.0,1.0,1.0,1.0,1.0,-1.0},{-1.0,0.0,1.0,1.0,1.0,1.0,0.0,-1.0},{-1.0,0.5,0.5,1.0,1.0,0.5,0.5,-1.0},{-1.0,0.0,0.5,1.0,1.0,0.5,0.0,-1.0},{-1.0,0.0,0.0,0.0,0.0,0.0,0.0,-1.0},{-2.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-2.0}};
	public static final double[][] AI_EVAL_BOARD_ROOK_W=new double[][]{{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.5,1.0,1.0,1.0,1.0,1.0,1.0,0.5},{-0.5,0.0,0.0,0.0,0.0,0.0,0.0,-0.5},{-0.5,0.0,0.0,0.0,0.0,0.0,0.0,-0.5},{-0.5,0.0,0.0,0.0,0.0,0.0,0.0,-0.5},{-0.5,0.0,0.0,0.0,0.0,0.0,0.0,-0.5},{-0.5,0.0,0.0,0.0,0.0,0.0,0.0,-0.5},{0.0,0.0,0.0,0.5,0.5,0.0,0.0,0.0}};
	public static final double[][] AI_EVAL_BOARD_ROOK_B=new double[][]{{0.0,0.0,0.0,0.5,0.5,0.0,0.0,0.0},{-0.5,0.0,0.0,0.0,0.0,0.0,0.0,-0.5},{-0.5,0.0,0.0,0.0,0.0,0.0,0.0,-0.5},{-0.5,0.0,0.0,0.0,0.0,0.0,0.0,-0.5},{-0.5,0.0,0.0,0.0,0.0,0.0,0.0,-0.5},{-0.5,0.0,0.0,0.0,0.0,0.0,0.0,-0.5},{0.5,1.0,1.0,1.0,1.0,1.0,1.0,0.5},{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}};
	public static final double[][] AI_EVAL_BOARD_QUEEN=new double[][]{{-2.0,-1.0,-1.0,-0.5,-0.5,-1.0,-1.0,-2.0},{-1.0,0.0,0.0,0.0,0.0,0.0,0.0,-1.0},{-1.0,0.0,0.5,0.5,0.5,0.5,0.0,-1.0},{-0.5,0.0,0.5,0.5,0.5,0.5,0.0,-0.5},{0.0,0.0,0.5,0.5,0.5,0.5,0.0,-0.5},{-1.0,0.5,0.5,0.5,0.5,0.5,0.0,-1.0},{-1.0,0.0,0.5,0.0,0.0,0.0,0.0,-1.0},{-2.0,-1.0,-1.0,-0.5,-0.5,-1.0,-1.0,-2.0}};
	public static final double[][] AI_EVAL_BOARD_KING_W=new double[][]{{-3.0,-4.0,-4.0,-5.0,-5.0,-4.0,-4.0,-3.0},{-3.0,-4.0,-4.0,-5.0,-5.0,-4.0,-4.0,-3.0},{-3.0,-4.0,-4.0,-5.0,-5.0,-4.0,-4.0,-3.0},{-3.0,-4.0,-4.0,-5.0,-5.0,-4.0,-4.0,-3.0},{-2.0,-3.0,-3.0,-4.0,-4.0,-3.0,-3.0,-2.0},{-1.0,-2.0,-2.0,-2.0,-2.0,-2.0,-2.0,-1.0},{2.0,2.0,0.0,0.0,0.0,0.0,2.0,2.0},{2.0,3.0,1.0,0.0,0.0,1.0,3.0,2.0}};
	public static final double[][] AI_EVAL_BOARD_KING_B=new double[][]{{2.0,3.0,1.0,0.0,0.0,1.0,3.0,2.0},{2.0,2.0,0.0,0.0,0.0,0.0,2.0,2.0},{-1.0,-2.0,-2.0,-2.0,-2.0,-2.0,-2.0,-1.0},{-2.0,-3.0,-3.0,-4.0,-4.0,-3.0,-3.0,-2.0},{-3.0,-4.0,-4.0,-5.0,-5.0,-4.0,-4.0,-3.0},{-3.0,-4.0,-4.0,-5.0,-5.0,-4.0,-4.0,-3.0},{-3.0,-4.0,-4.0,-5.0,-5.0,-4.0,-4.0,-3.0},{-3.0,-4.0,-4.0,-5.0,-5.0,-4.0,-4.0,-3.0}};

	public static final Color BG_COLOR=new Color(255,255,255);

	public static final Color BOARD_OUTLINE_COLOR=new Color(125,92,57);
	public static final int BOARD_OUTLINE_SIZE=30;
	public static final int BOARD_DESTROYED_PIECE_OFFSET=100;

	public static final int TILE_SIZE=100;
	public static final Color TILE_COLOR_A=new Color(83,59,36);
	public static final Color TILE_COLOR_B=new Color(169,129,77);

	public static final Color PIECE_COLOR_WHITE=new Color(230,230,230);
	public static final Color PIECE_COLOR_BLACK=new Color(20,20,20);
	public static final Font PIECE_FONT=new Font("monospaced",Font.BOLD,60);
	public static final float PIECE_SELECTED_FONT_SIZE=80f;
	public static final Color PIECE_NORMAL_MOVE_COLOR=new Color(30,245,30,50);
	public static final Color PIECE_DEADLY_MOVE_COLOR=new Color(245,30,30,50);
}