
import javafx.scene.layout.*;
import java.util.Random;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.Parent;
import javafx.scene.input.*;

import java.util.Scanner;
public class Game extends Application  
{

    
    static boolean isWinner = false;
    private Scene scene;
    static Stage primaryStage1;
    
	private static final int NUM_MINES = 10;
	private static final int NUM_ROWS = 10;
	private static final int NUM_COLS = 10;
	private static final int FLAGS = 10;
	static int flagCount = 0;
	
	private static Square[][] board; 
	
	static int SIZE = 10;
    static int length = SIZE;
    static int width = SIZE;
    
	
    public Parent createContent()
    {
    	 GridPane root = new GridPane();    
    	 
         for(int x = 0; x < length; x++)
         {
             for(int y = 0; y < width; y++)
             {

                 int k = x;
                 int t = y;
 				
                 board[x][y].space.setOnMouseClicked(new EventHandler<MouseEvent>() {
                	    @Override
                	    public void handle(MouseEvent mouseEvent) {
                	    	
                	        if(mouseEvent.getButton().equals(MouseButton.SECONDARY)){
                	            System.out.println("Set flag on the button");
                	           
                	            if(flagCount < 10)
            					{
	                	            if(board[k][t].flag == true)
	                	            {
	                	            	flagCount--;
	                					board[k][t].setFlag(false);
	                					board[k][t].space.setStyle(null);
	                	            }
	                	            else
	                	            {
		        						board[k][t].setFlag(true);
		                	            board[k][t].space.setStyle("-fx-background-color: #00ff00");
		                	            flagCount++;
		                	            isWinner = checkwinner();
		                	            if(isWinner == true)
		                	            {
		                	            	GridPane root3 = new GridPane();
		                	    			//Stage primaryStage3 = new Stage();
		                	    			Scene scene2 = new Scene(root3);  
		                	    			
		                	    			Label text = new Label("You Flagged all of the mines! You Win!!");
		                	                root3.getChildren().add(text);
		                	    	        primaryStage1.setTitle("Game Over");
		                	    	        primaryStage1.setScene(scene2);
		                	    	        primaryStage1.show();
		                	            	
		                	            }
	                	            }
                	            
                	     
            					}
                	            else
                	            {
                	            	if(board[k][t].flag == true)
	                	            {
	                	            	flagCount--;
	                					board[k][t].setFlag(false);
	                					board[k][t].space.setStyle(null);
	                	            }
                	            }
                	        }
                	        else
                	        {
                	        	open(k, t);
                	        }
                	    }
                	});
          
                 // Iterate the Index using the loops
                 root.setRowIndex(board[x][y].space,x);
                 root.setColumnIndex(board[x][y].space,y);    
                 root.getChildren().add(board[x][y].space);
             }
         }
         return root;

    }


	protected void open(int k, int t) {
		
		 board[k][t].space.setText(board[k][t].Hidesymbol);
		 if(board[k][t].flag == true)
		 {
			 board[k][t].flag = false;
			 flagCount--;
		 }
		 if(board[k][t].isMine == true)
		 {
			board[k][t].space.setTextFill(Color.RED);
			GridPane root2 = new GridPane();
			//Stage primaryStage2 = new Stage();
			Scene scene2 = new Scene(root2);  
			
			Label text = new Label("You Exploded a Mine! Game Over.");
            root2.getChildren().add(text);
	        primaryStage1.setTitle("Game Over");
	        primaryStage1.setScene(scene2);
	        primaryStage1.show();
		 }
		 else if(board[k][t].adjMines == 0)
		 {
			 board[k][t].space.setTextFill(Color.ORANGE);
			 board[k][t].space.setStyle(null);
		 }
		 else if(board[k][t].adjMines == 1)
		 {
			 board[k][t].space.setTextFill(Color.BLUE);
			 board[k][t].space.setStyle(null);
		 }
		 else if(board[k][t].adjMines == 2)
		 {
			 board[k][t].space.setTextFill(Color.GREEN);
			 board[k][t].space.setStyle(null);
		 }
		 else if(board[k][t].adjMines == 3)
		 {
			 board[k][t].space.setTextFill(Color.DARKRED);
			 board[k][t].space.setStyle(null);
		 }
		 if(k >= 0 && k < NUM_ROWS &&  t >= 0 && t < NUM_COLS && board[k][t].seen == false)
		 {
			board[k][t].symbol = board[k][t].Hidesymbol;
			board[k][t].space.setText(board[k][t].Hidesymbol);
			board[k][t].seen = true;
			
			if(board[k][t].adjMines == 0)
			{
				int [] adjPoints = {-1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1};
				for(int i = 0; i < adjPoints.length; i++)
				{
					int adjRow = adjPoints[i];
					int adjCol = adjPoints[++i];
					
					int rowCheck = k + adjRow;
					int colCheck = t + adjCol;
					
					if(rowCheck >= 0 && rowCheck < NUM_ROWS &&  colCheck >= 0 && colCheck < NUM_COLS)
					{
						//Reveal(board, rowCheck, colCheck);
						board[rowCheck][colCheck].symbol = board[rowCheck][colCheck].Hidesymbol;
						//board[rowCheck][colCheck].seen = true;
						if(board[rowCheck][colCheck].adjMines == 0)
						{
							open(rowCheck, colCheck);
						}
					}
				}
			}
			
			}
		 
		}

	@Override
    public void start(Stage primaryStage) {
        this.primaryStage1 = primaryStage;
		scene = new Scene(createContent());    
        primaryStage1.setTitle("Minesweeper");
        primaryStage1.setScene(scene);
        primaryStage1.show();
    
}
	
	public static void main(String[] argsv)
	{
	
		
		
		boolean gameEnd = false;
		
		System.out.println("Welcome to Minesweeper Text Game!");
		System.out.println("Simply choose to make a guess or place a flag and enter the coordinates (row then column)" + "\n"); 
		//while(gameEnd == false)
		{
			boolean gameOver = false;
			boolean winner = false;
			board = new Square[NUM_ROWS][NUM_COLS];
			Random random = new Random();
			
			int flagCount = 0;
			//init
			for(int i = 0; i < board.length; i++)
			{
				for(int j = 0; j < board.length; j++)
				{
					
					board[i][j] = new Square();
				}
			}
			
			//rand mines
			for(int i = 0; i < NUM_MINES; i++)
			{
				int RandomRow = random.nextInt(10);
				int RandomCol = random.nextInt(10);
				System.out.println("Mines:" + RandomRow + " " + RandomCol);
				
				board[RandomRow][RandomCol].setMine(true);
				
			}
			
			
			for(int i = 0; i < board.length; i++)
			{
				for(int j = 0; j < board.length; j++)
				{
					if(board[i][j].isMine)
					{
						continue;
					}
					SetAdj(board, i, j);
				}
			}
			PrintBoard(board, true);
			System.out.println("");
			PrintBoard(board, false);
			System.out.println();
			
			
			
		
			launch(argsv);
			
			
	}
	}
	
	
	private static boolean checkwinner() 
	{
		int correct = 0;
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board.length; j++)
			{
				if(board[i][j].flag == true && board[i][j].isMine == true)
				{
					correct++;
				}
			}
		}
		if(correct == NUM_MINES)
		{
			return true;
		}
		return false;
	}
	
	private static void SetAdj(Square[][] board, int row, int column)
	{
		int [] adjPoints = {-1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1};
		int adjMines = 0; 
		for(int i = 0; i < adjPoints.length; i++)
		{
			int adjRow = adjPoints[i];
			int adjCol = adjPoints[++i];
			
			int rowCheck = row + adjRow;
			int colCheck = column + adjCol;
			
			if(rowCheck >= 0 && rowCheck < NUM_ROWS &&  colCheck >= 0 && colCheck < NUM_COLS && board[rowCheck][colCheck].isMine)
			{
				adjMines++;
			}
		}
		
		board[row][column].setAdj(adjMines);
	}
	
	
	public static void PrintBoard(Square[][] board, boolean cheat)
	{
		if(cheat == false)
		{
			for(int i = 0;  i < 2 ; i++)
			{
				for(int j = 0; j < board.length + 1; j++) 
				{
					if(i == 0)
					{
						int k = j - 1;
						if(k == -1)
						{
							System.out.print(" -  ");
						}
						else
						{
							System.out.print(" " + k + "  ");
						}
					}
					else
					{
						if(j == 0)
						{
							System.out.println();
						}
						System.out.print("----");
					}
				}
			}
			
			System.out.println();
			
			for(int i = 0; i < board.length; i++)
			{
				for(int j = 0; j < board.length; j++)
				{
					if(j == 0 && i == 0)
					{
						System.out.print(" " + i + " | ");
					}
					if(j % 10 == 9)
					{
						System.out.println(board[i][j].symbol);
						int k = i + 1;
						if(k < 10)
						{
							System.out.print(" " + k + " | ");
						}
					}
					else
					{
						System.out.print(board[i][j].symbol + " ");
					}
				}
			}
		}
		else
		{
			for(int i = 0;  i < 2 ; i++)
			{
				for(int j = 0; j < board.length + 1; j++) 
				{
					if(i == 0)
					{
						int k = j - 1;
						if(k == -1)
						{
							System.out.print(" -  ");
						}
						else
						{
							System.out.print(" " + k + "  ");
						}
					}
					else
					{
						if(j == 0)
						{
							System.out.println();
						}
						System.out.print("----");
					}
				}
			}
			
			System.out.println();
			
			for(int i = 0; i < board.length; i++)
			{
				for(int j = 0; j < board.length; j++)
				{
					if(j == 0 && i == 0)
					{
						System.out.print(" " + i + " | ");
					}
					if(j % 10 == 9)
					{
						System.out.println(board[i][j].Hidesymbol);
						int k = i + 1;
						if(k < 10)
						{
							System.out.print(" " + k + " | ");
						}
					}
					else
					{
						System.out.print(board[i][j].Hidesymbol + " ");
					}
				}
			}
		}
	}
	
}

