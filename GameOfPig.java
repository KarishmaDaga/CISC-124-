import java.util.Random;
import java.util.Scanner;

public class ASSIGNMENT1_20031646 {


	static int diceA;                 
	static int diceB;
		
	static String playerName;
	static boolean playerTurn;                       // keeps track of the current player
	static int playerScore = 0;						 // player's total score
													    
	static int computerScore = 0;                    // computer's current score
	final static int responseDelay = 500;           // for computer: delays decisions by 1 second to make game feel real
	public static int turnCounter = 0;				 // keeps track of the current round
	static boolean turnOver = false;				 // boolean for whether a turn is over for a player or not
	public static int turnScore = 0;	             // temporarily stores any player's score for the current turn

	public static int totalTurnSum = 0;				 // temporarily holds total points earned in a round for a player
		
		
		
		
	public static void main(String[] args) {
		welcomePlayer(); 							 // welcomes player and gets their name		
		runGame();				
	}

		
	/*
	 *  runGame runs the player and computer turns
	 */
	public static void runGame() {
			
		// checkPlayersScore returns TRUE if the game is over and FALSE if the game is not over
		while((checkPlayersScore(playerScore, computerScore)) == false) {  
			roundDivider(turnCounter); //declares the beginning of each round
				
			if (turnCounter % 2 == 0) {   // turn counter makes alternating between the two players possible
				playerTurn(playerScore);
				turnCounter++;
			}
				
			else if (turnCounter % 2 == 1){
				computerTurn(computerScore);
				turnCounter++;
			}
			}
			if (playerScore >= 100) {
				// the following 'game over' messages are personalized for a nice touch
				System.out.println();
				System.out.println();
				System.out.println("You win!!");
				System.out.println("(☞ﾟヮﾟ)☞ ☜(ﾟヮﾟ☜)");
			} else if (computerScore >= 100) {
				System.out.println();
				System.out.println();
				System.out.println("The Computer wins");
				System.out.println("(╯°□°）╯︵ ┻━┻");
			}
			System.out.println("GAME OVER");
		}
									
		/* This is for the computers turn*/
		public static void playerTurn(int playerScore) {
			
		getPlayerDivider(playerName);
		turnOver = false;
		totalTurnSum = 0; // totalTurnSum temporarily holds the round's value to handle the case when a single one appears
		while(turnOver == false) {  //while turnOver is not true
			diceA = rollDice();
			diceB = rollDice();
				
			sleep(responseDelay); // delays response time for 1 second
			playerRolledStat(diceA, diceB); // tells us what die were rolled
			turnScore = masterScoreCalculator(turnScore, diceA, diceB); //score for the current subround/roll
			totalTurnSum += turnScore;
			getTurnScore(turnScore); // gets score for the round and overall total score of player
				
			/* ends the turn if the a single one is rolled on, if the player does not want to roll
			 * and ends the round if the current round's score is greater than or equal to 100 */
			if (turnScore == 0 || rollAgain() == false || (playerScore + totalTurnSum) >= 100) {
				turnOver = true;
				System.out.println("Your turn is over. Your total score is " + updatePlayerScore(totalTurnSum) + 
										" and the Computer's score is " + computerScore);
			}
		}
	}
	/* This is for the computers turn*/
	public static void computerTurn(int computerScore) {
		getComputerDivider();
			
		turnOver = false;		
		totalTurnSum = 0; // totalTurnSum temporarily holds the round's value to handle the case when a single one appears
		while(turnOver == false) {  //while turnOver is not true
			diceA = rollDice();
			diceB = rollDice();
				
			sleep(responseDelay*2); // delays response time for 1 second
			getCompRolledStat(diceA, diceB); // tells us what die were rolled
			turnScore = masterScoreCalculator(turnScore, diceA, diceB); //score for the current subround/roll
			totalTurnSum += turnScore;
			getCompTurnScore(turnScore); // gets score for the round and overall total score of player
				
			/* ends the turn if the a single one is rolled on, strategically ends the round for self if points earned are greater
			 * than forty, and end the round if the current round's score is greater than or equal to 100 */
			if (turnScore == 0 || totalTurnSum >= 40 || (computerScore + totalTurnSum) >= 100) {
					turnOver = true;
					System.out.println("The Computer's turn is over. It's score is " + updateComputerScore(totalTurnSum) + 
							" and your score is " + playerScore);
				} else {
					continue;
			}
		}
	}
		
	/* Methods for returning information about the round (points earned in round, overall, etc.)
	 */
		
	/*  playerRolledStat returns the values the die landed on
	 */
	public static void playerRolledStat(int diceA, int diceB) {
		String newDiceA = diceToString(diceA);
		String newDiceB = diceToString(diceB);
			
		System.out.println("You rolled a " + newDiceA + " and a " + newDiceB);
	}
		
	/*
	 *  getTurnScoer returns the values earned during the round and overall
	 */
	public static void getTurnScore(int turnScore) {   // player score + total sum
		System.out.println("Your score this round is: " + totalTurnSum + " and your total score is: " + (playerScore + totalTurnSum));
	}
		
	/*
	 *  The following return the same values as the previous methods, but for the computer
	 */
	public static void getCompRolledStat(int diceA, int diceB) {
		String newDiceA = diceToString(diceA);
		String newDiceB = diceToString(diceB);
		System.out.println("The computer rolled a " + newDiceA + " and a " + newDiceB);
	}
		
	public static void getCompTurnScore(int turnScore) {
		System.out.println("The Computer's score this round is: " + totalTurnSum + " and the total score is: " + (computerScore + totalTurnSum));
	}
		
		
	/* Rolls a dice 
	 * 
	 * @ param: 
	 * 	@min -> die face of value 1
	 *  @max -> die face of value 6
	 * */
	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
		
	public static int rollDice() {
		return randInt(1, 6);
	}
		
	/*
	 *  Converts player dice number from an integer to a String
	 */
	public static String diceToString(int diceNum) {
		if (diceNum == 1) {
			return ("one");
		} else if (diceNum == 2) {
			return ("two");
		} else if (diceNum == 3) {
			return ("three");
		} else if (diceNum == 4) {
			return ("four");
		} else if (diceNum == 5) {
			return ("five");
		} else {
			return ("six");
		}
	}
		
		
	/* Checks if either players' scores exceed 100. 
	 * Returns FALSE if game is not over and TRUE if it is
	 */
	public static boolean checkPlayersScore(int playerScore, int computerScore) {
		boolean gameIsOver = false;
		if (playerScore >= 100 || computerScore >= 100) {
			gameIsOver = true;
			return gameIsOver;
		} else {
			return gameIsOver;
		}
	}
		
	/*  The following method calculates the points earned for a player based on the die values and returns the turn's score
	 */
	public static int masterScoreCalculator(int turnScore, int diceA, int diceB) {
		if (diceA == diceB) {          // if the die are doubles       
			if ((diceA == 1) && (diceB == 1)) {   
				turnScore = 25;
			} else {
				turnScore = (diceA + diceB) * 2;
			}
		} else if (diceA != diceB){   // if the die are not doubles
			if (diceA == 1 || diceB == 1) {
				turnScore = 0;
				totalTurnSum = 0;    
					
			} else {
				turnScore = diceA + diceB;
			}
		}
		return turnScore;
	}
		
	/* 
	 * Updates scores of current player 
	 * Takes in the score from the current round and adds it to the player's total score
	 * Returns the player's total score
	 */
	public static int updatePlayerScore(int turnScore) {
		playerScore += turnScore;
		return playerScore;
	}
		
	public static int updateComputerScore(int turnScore) {
		computerScore += turnScore;
		return computerScore;
	}
		
	/*
	 *  Asks player if they want to roll again or hold.
	 */
		
	public static boolean rollAgain() {
		// ask for user input, either the char 'y' for yes or 'n' for no
		boolean roll = true;          // the boolean roll is returned. It is used in a player method for when the dices are not doubles.
		String rollAgain = "y";
		String holdRoll = "n";
		Scanner userResponse = new Scanner(System.in); // creates the Scanner object 
			
		/* I only want to ask the player to roll again if their dices aren't doubles 
		 * or if the score they have so far in the round is less than 100*/
			
		if ((diceA != diceB) && ((playerScore + totalTurnSum) < 100)) {  
			System.out.println("Would you like to roll again? Enter 'y' to do so, or 'n' to end your turn: ");
			String response = userResponse.next(); // this is the user input variable of type string
				
				
			if (response.equals(rollAgain)) {
				return roll; 				// returns true if the player wants to roll again
			} else if (response.equals(holdRoll)) {
				return !roll;				// returns false if the player doesn't want to roll again
			} else {
				System.out.println("Please enter either 'y' to roll again or 'n' to end your turn: "); // if the player returns an invalid character
				rollAgain();
			}
		}
		return roll;
	}
		
		
	/* Methods for formatting and making things pretty */
		
	/* Creates divider to differentiate between the player's turn and the computer's turn */
	public static void getPlayerDivider(String playerName) {
		System.out.println();
		System.out.println(" -------- " + playerName + "'s Turn -------- ");
		System.out.println();
	}
		
	public static void getComputerDivider() {
		System.out.println();
		System.out.println("-------- Computer's Turn --------");
		System.out.println();
	}
		
	/* Welcomes player at the beginning of the game*/
	public static void welcomePlayer() {
		Scanner userResponse = new Scanner(System.in);
		System.out.println("Welcome to the wondrous Game of Pig! To start the game off on the right foot, what is your name?");
		playerName = userResponse.next();
		System.out.println("Hello " + playerName + "! Let the game . . . begin!");
	}
		
	/* Creates a divider for the beginning of each round of the game*/
	public static void roundDivider(int turnCounter) {
		if (turnCounter % 2 == 0) {
		System.out.println();
		System.out.println("                      ---------- Round " + (turnCounter / 2) + " ---------");
		}
	}
		
	/*
	 *  This provides a delay in the messages to make the game feel smoother and real
	 */
	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
}

