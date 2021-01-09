import java.util.Random;
import java.util.Scanner;
public class Game 
{
	private static final int NUM_MINES = 2;
	private static final int NUM_ROWS = 10;
	private static final int NUM_COLS = 10;
	private static final int FLAGS = 10;

	public static void main(String[] argsv)
	{
		
		boolean gameEnd = false;
		
		System.out.println("Welcome to Minesweeper Text Game!");
		System.out.println("Simply choose to make a guess or place a flag and enter the coordinates (row then column)" + "\n"); 
		while(gameEnd == false)
		{
			boolean gameOver = false;
			boolean winner = false;
			Square[][] board = new Square[NUM_ROWS][NUM_COLS];
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
			
			while(gameOver == false && winner == false)
			{
				boolean validGuess = false;
				int choice = 0;
				while(validGuess == false)
				{
					Scanner action = new Scanner(System.in);
					System.out.println("Would you like to place a flag, remove a flag or make a guess. (1 for flag and 2 for guess, 3 for removing a flag)");
					choice = action.nextInt();
					if(choice == 1 || choice == 2 || choice == 3)
					{
						validGuess = true;
					}
					else
					{
						System.out.println("Please enter a valid option. 1 for flag or 2 for guess");
						
					}
				}
				validGuess = false;
				if(choice == 2)
				{
					int row = 0;
					int column = 0;
					while(validGuess == false)
					{
						System.out.println("Please select a row");
						Scanner guessScan = new Scanner(System.in);
						row = guessScan.nextInt();
						System.out.println("Please select a column");
						column = guessScan.nextInt();
						System.out.println("Guess: " + row + " "+ column);
						
						if(row >= NUM_ROWS || column >= NUM_COLS)
						{
							System.out.println("Please enter a valid coordinate that is not out of bounds");
						}
						else
						{
							validGuess = true;
						}
					}
					gameOver = CheckGuess(row, column, board);
					if(gameOver == false)
					{
						Reveal(board, row, column);
					}
					//System.out.println(gameOver);
				}
				if(choice == 1)
				{
					if(flagCount < 10)
					{
						int fRow = 0;
						int fColumn = 0;
						while(validGuess == false)
						{
							System.out.println("Please select a row for the flag");
							Scanner guessScan = new Scanner(System.in);
							fRow = guessScan.nextInt();
							System.out.println("Please select a column for the flag");
							fColumn = guessScan.nextInt();
							System.out.println("flag: " + fRow + " "+ fColumn);
							if(fRow >= NUM_ROWS || fColumn >= NUM_COLS)
							{
								System.out.println("Please enter a valid coordinate that is not out of bounds");
							}
							else
							{
								validGuess = true;
							}
						}
						flagCount++;
						board[fRow][fColumn].setFlag(true);
					}
					else
					{
						System.out.println("Too many flags placed");
					}
				}
				if(choice == 3)
				{
					int fRRow = 0;
					int fRColumn = 0;
					while(validGuess == false)
					{
						System.out.println("Please select the row of the flag to remove");
						Scanner guessScan = new Scanner(System.in);
						fRRow = guessScan.nextInt();
						System.out.println("Please select the column of the flag to remove");
						fRColumn = guessScan.nextInt();
						System.out.println("flag: " + fRRow + " "+ fRColumn);
						if(fRRow >= NUM_ROWS || fRColumn >= NUM_COLS || board[fRRow][fRColumn].flag == false)
						{
							System.out.println("Please enter a valid coordinate that is not out of bounds and does contain a flag");
						}
						else
						{
							validGuess = true;
						}
					}
					flagCount--;
					board[fRRow][fRColumn].setFlag(false);
				}
				
				PrintBoard(board, false);
				winner = checkwinner(board);
				
				
				if(gameOver == true)
				{
					System.out.println("You exploded a Mine! Game Over.");
				}
				if(winner == true)
				{
					System.out.println("You have won the game!!!!");
					
				}
			}
			Scanner playAgain = new Scanner(System.in);
			System.out.println("Would you like to play again? type y for yes and n for no");
			boolean validInput = false;
			while(validInput == false)
			{
				String answer = playAgain.nextLine();
				if(answer.equalsIgnoreCase("y"))
				{
					validInput = true;
					gameEnd = false;
				}
				else if(answer.equalsIgnoreCase("n"))
				{
					validInput = true;
					gameEnd = true;
				}
				else
				{
					System.out.println("Invalid Input, please type 'y' for yes or 'n' for no");
				}
			}
		}
		
		
	}
	
	
	private static boolean checkwinner(Square[][] board) 
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
	private static void Reveal(Square[][] board, int row, int column)
	{
		PrintBoard(board, false);
		if(row >= 0 && row < NUM_ROWS &&  column >= 0 && column < NUM_COLS && board[row][column].seen == false)
		{
			board[row][column].symbol = board[row][column].Hidesymbol;
			board[row][column].seen = true;
			
			if(board[row][column].adjMines == 0)
			{
				int [] adjPoints = {-1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1};
				for(int i = 0; i < adjPoints.length; i++)
				{
					int adjRow = adjPoints[i];
					int adjCol = adjPoints[++i];
					
					int rowCheck = row + adjRow;
					int colCheck = column + adjCol;
					
					if(rowCheck >= 0 && rowCheck < NUM_ROWS &&  colCheck >= 0 && colCheck < NUM_COLS)
					{
						//Reveal(board, rowCheck, colCheck);
						board[rowCheck][colCheck].symbol = board[rowCheck][colCheck].Hidesymbol;
						//board[rowCheck][colCheck].seen = true;
						if(board[rowCheck][colCheck].adjMines == 0)
						{
							Reveal(board, rowCheck, colCheck);
						}
					}
				}
			}
		
		}
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
	
	public static boolean CheckGuess(int row, int col, Square[][] board)
	{
		if(board[row][col].isMine)
		{
			board[row][col].symbol = board[row][col].Hidesymbol;
			return true;
		}
		else
		{
			return false;
		}
		
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

