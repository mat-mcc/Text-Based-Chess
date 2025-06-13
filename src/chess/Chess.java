

/*
 * 2-Player Chess
 * By Bhumit Patel & Matthew McCaughan
 * Software Methodology Spring 2024
 */

 package chess;
import java.util.ArrayList;

class ReturnPiece {
	static enum PieceType {
		WP, WR, WN, WB, WQ, WK,
		BP, BR, BN, BB, BK, BQ
	};

	static enum PieceFile {
		a, b, c, d, e, f, g, h
	};

	PieceType pieceType;
	PieceFile pieceFile;
	int pieceRank; // 1..8

	public String toString() {
		return "" + pieceFile + pieceRank + ":" + pieceType;
	}

	public boolean equals(Object other) {
		if (other == null || !(other instanceof ReturnPiece)) {
			return false;
		}
		ReturnPiece otherPiece = (ReturnPiece) other;
		return pieceType == otherPiece.pieceType &&
				pieceFile == otherPiece.pieceFile &&
				pieceRank == otherPiece.pieceRank;
	}
}

class ReturnPlay {
	enum Message {
		ILLEGAL_MOVE, DRAW,
		RESIGN_BLACK_WINS, RESIGN_WHITE_WINS,
		CHECK, CHECKMATE_BLACK_WINS, CHECKMATE_WHITE_WINS,
		STALEMATE
	};

	ArrayList<ReturnPiece> piecesOnBoard;
	Message message;
}

public class Chess {

	enum Player {
		white, black
	}

	// statically initialize the gameboard to house all the piece objects
	static ChessBoard gameBoard = new ChessBoard();

	// initialize player turn!
	// White starts
	static Player currentPlayer = Player.white;

	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for
	 *         details of
	 *         the contents of the returned ReturnPlay instance.
	 */

	/*
	 * Grading:
	 * 
	 * 
	 * All legitimate basic moves for all pieces
	 * 		done!
	 * 		all else should be good?
	 * Castling
	 * 		I think it's complete - Matt
	 * Enpassant
	 * 		took my soul but it should work now :-)  - Matt
	 * Promotion
	 * 		not implemented
	 * Identification of check
	 * 		not implemented (for every piece on the board, if the destiation of the king
	 * 		is a valid move & can move there, then the king would be in check)
	 * Identifcation of checkmate
	 * 		not implemented
	 * Identification of illegal move
	 * 		done for:
	 * 		pieces of same color
	 * 		pieces within their rules
	 * 		pieces moving over other pieces (except the knight, who can jump over)
	 * 		NOT done for:
	 *		within the boundaries of the board (if we need that, i think all inputs given
	 * 		will be valid)
	 * 		putting king deliberately into check
	 *			Maybe to check if we're actually moving a piece
	 			(moving a null will throw error and terminate program/ maybe just illegal move instead?)
	 * 
	 * Resign
	 * 		should work
	 * Draw
	 * 		also should work
	 */

	public static ReturnPlay play(String move) {
		/* FILL IN THIS METHOD */

		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */

		// create returnplay object and set it properly
		ReturnPlay moveResult = new ReturnPlay();
		moveResult.piecesOnBoard = new ArrayList<>();

		// TODO:
		// process String move and then pass it to a move method for every piece?

		if (move.equals("resign")) {
			// System.out.println("RESIGNING!");
			if (currentPlayer == Player.black) {
				moveResult.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
			} else {
				moveResult.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
			}
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (gameBoard.getPiece(i, j) == null) {
						continue;
					}
					moveResult.piecesOnBoard.add(gameBoard.getPiece(i, j).makeReturnPiece());
				}
			}
			return moveResult;

		}
		//remove leading & trailing spaces
		move = move.trim();

		char promotionModifier = '_';


		// break up string
		String oldPos = move.substring(0, 2);
		String newPos = move.substring(3);

		// System.out.println(oldPos);
		// System.out.println(newPos);

		// maybe change returntype of process move to also return something that will
		// tell moveResult the message to display?
		// DONE: RETURNS INT DEPENDING ON WHAT HAPPENS (needs more implementation
		// obviously)

		if (move.length() > 5){
			promotionModifier = move.charAt(6);
			//System.out.println(promotionModifier);
		}
		System.out.println(promotionModifier);
		int moveResultInt = gameBoard.processMove(oldPos, newPos, currentPlayer, promotionModifier);

		// CHANGE MESSAGE DEPENDING ON RETURN OF MOVERESULTINT
		// 0: valid/no problems (default)
		// -1: illegal move
		// -2: placeholder for castling (probably isn't needed but need to return int)

		switch (moveResultInt) {
			// illegal move
			case -1:
				moveResult.message = ReturnPlay.Message.ILLEGAL_MOVE;
				break;
			// castling, no problems here
			case -2:
				break;
			default:
			// check for a third parameter (draw in this such case)
			// good spot for checking for promotion maybe?
			if(move.length() > 8){
				if (move.substring(4, 8) != "draw?"){
				moveResult.message = ReturnPlay.Message.DRAW;			}
			}
				break;
		}
		 
		//See if the current move put the player themselves in check. So first swap players
		if (currentPlayer == Player.white) {
			currentPlayer = Player.black;
		} else {
			currentPlayer = Player.white;
		}
		
		//Then see if they are in check after moving. If they did, undo the move and set message to illegal move
		//Also undo the move

		// format input into ONE INDEXED chess notation
        char oldPosFile = oldPos.charAt(0);
        int oldPosRank = Integer.parseInt(oldPos.substring(1, 2));
        char newPosFile = newPos.charAt(0);
        int newPosRank = Integer.parseInt(newPos.substring(1, 2));

        // also ZERO INDEXED matrix notation for convenience
        int oldPosFileZ = (int) oldPosFile - 97;
        int oldPosRankZ = oldPosRank - 1;
        int newPosFileZ = (int) newPosFile - 97;
        int newPosRankZ = newPosRank - 1;

		if (moveResult.message != ReturnPlay.Message.ILLEGAL_MOVE && inCheck(currentPlayer, gameBoard) == true)
		{
			System.out.println("In Check after moving >_<!");
			moveResult.message = ReturnPlay.Message.ILLEGAL_MOVE;
			Chess.undoLastMove(moveResult, currentPlayer, oldPosFileZ, oldPosRankZ, newPosFileZ, newPosRankZ, gameBoard);
			for (ReturnPiece piece : moveResult.piecesOnBoard)
			{
				System.out.println("Pieces after undoing: "+piece.pieceType + piece.pieceRank + piece.pieceFile);
			}
		}
		//Swap players back to normal
		if (currentPlayer == Player.white) {
			currentPlayer = Player.black;
		} else {
			currentPlayer = Player.white;
		}



		// Starting checking if the move put in check here
		if (moveResult.message != ReturnPlay.Message.ILLEGAL_MOVE && inCheck(currentPlayer, gameBoard) == true)
		{
			moveResult.message = ReturnPlay.Message.CHECK;
		}

		//Check for checkmate here

		if(moveResult.message == ReturnPlay.Message.CHECK)
		{
			if (inCheckMate(currentPlayer, gameBoard, moveResult) == false)
			{
				System.out.println("In check, but not checkmate");
			}
			else
			{
				if (currentPlayer == Player.white) {
					moveResult.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
				} else {
					moveResult.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
				}
			}

		}
		// after every turn, go through the ChessBoard gameBoard and add it to the
		// ReturnPlay object to return
		// objects actually "persist" now (through gameBoard)
		// 0 indexed board
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (gameBoard.getPiece(i, j) == null) {
					continue;
				}
				//System.out.println("Added: " + gameBoard.getPiece(i, j).type + " at " + (char)(j+97) + (i+1));
				moveResult.piecesOnBoard.add(gameBoard.getPiece(i, j).makeReturnPiece());
			}
		}
		// Starting checking if the move put in check here
		if (moveResult.message != ReturnPlay.Message.ILLEGAL_MOVE && inCheck(currentPlayer, gameBoard) == true)
		{
			moveResult.message = ReturnPlay.Message.CHECK;
		}

		//Check for checkmate here
		if (moveResult.message == ReturnPlay.Message.CHECK && inCheckMate(currentPlayer, gameBoard, moveResult) == true && moveResult.message != ReturnPlay.Message.ILLEGAL_MOVE)
		{
			if (currentPlayer == Player.white) {
				moveResult.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
			} else {
				moveResult.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
			}
		}

		// PlayChess.printBoard(moveResult.piecesOnBoard);
		// moveResult.message = moveResult.message.DRAW;

		// change player!


		if (moveResult.message != ReturnPlay.Message.ILLEGAL_MOVE) {
			if (currentPlayer == Player.white) {
				currentPlayer = Player.black;
			} else {
				currentPlayer = Player.white;
			}
			System.out.println(currentPlayer + "'s turn!");
		}
		gameBoard.resetCapturedPieceStatus();
		return moveResult;
	}

	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {

		// resets the gameboard
		gameBoard.clearBoard();
		currentPlayer = Player.white;

		// create the ReturnPlay Object and List for just intiializng
		ReturnPlay moveResult = new ReturnPlay();
		moveResult.piecesOnBoard = new ArrayList<>();
		// adding with 0 indexed board
		// example
		// create king objects and add them to ReturnPlay
		gameBoard.placePiece(new King(Piece.PieceType.BK, 7, 4, Piece.Player.black), 7, 4);
		moveResult.piecesOnBoard.add(gameBoard.getPiece(7, 4).makeReturnPiece());
		gameBoard.placePiece(new King(Piece.PieceType.WK, 0, 4, Piece.Player.white), 0, 4);
		moveResult.piecesOnBoard.add(gameBoard.getPiece(0, 4).makeReturnPiece());

		// add Queens
		gameBoard.placePiece(new Queen(Piece.PieceType.BQ, 7, 3, Piece.Player.black), 7, 3);
		gameBoard.placePiece(new Queen(Piece.PieceType.WQ, 0, 3, Piece.Player.white), 0, 3);
		moveResult.piecesOnBoard.add(gameBoard.getPiece(7, 3).makeReturnPiece());
		moveResult.piecesOnBoard.add(gameBoard.getPiece(0, 3).makeReturnPiece());

		// add Rooks
		gameBoard.placePiece(new Rook(Piece.PieceType.BR, 7, 0, Piece.Player.black), 7, 0);
		gameBoard.placePiece(new Rook(Piece.PieceType.BR, 7, 7, Piece.Player.black), 7, 7);
		gameBoard.placePiece(new Rook(Piece.PieceType.WR, 0, 0, Piece.Player.white), 0, 0);
		gameBoard.placePiece(new Rook(Piece.PieceType.WR, 0, 7, Piece.Player.white), 0, 7);

		moveResult.piecesOnBoard.add(gameBoard.getPiece(7, 0).makeReturnPiece());
		moveResult.piecesOnBoard.add(gameBoard.getPiece(7, 7).makeReturnPiece());
		moveResult.piecesOnBoard.add(gameBoard.getPiece(0, 0).makeReturnPiece());
		moveResult.piecesOnBoard.add(gameBoard.getPiece(0, 7).makeReturnPiece());

		// add Bishops
		gameBoard.placePiece(new Bishop(Piece.PieceType.BB, 7, 2, Piece.Player.black), 7, 2);
		gameBoard.placePiece(new Bishop(Piece.PieceType.BB, 7, 5, Piece.Player.black), 7, 5);
		gameBoard.placePiece(new Bishop(Piece.PieceType.WB, 0, 2, Piece.Player.white), 0, 2);
		gameBoard.placePiece(new Bishop(Piece.PieceType.WB, 0, 5, Piece.Player.white), 0, 5);

		moveResult.piecesOnBoard.add(gameBoard.getPiece(7, 2).makeReturnPiece());
		moveResult.piecesOnBoard.add(gameBoard.getPiece(7, 5).makeReturnPiece());
		moveResult.piecesOnBoard.add(gameBoard.getPiece(0, 2).makeReturnPiece());
		moveResult.piecesOnBoard.add(gameBoard.getPiece(0, 5).makeReturnPiece());

		// add knights
		gameBoard.placePiece(new Knight(Piece.PieceType.BN, 7, 1, Piece.Player.black), 7, 1);
		gameBoard.placePiece(new Knight(Piece.PieceType.BN, 7, 6, Piece.Player.black), 7, 6);
		gameBoard.placePiece(new Knight(Piece.PieceType.WN, 0, 1, Piece.Player.white), 0, 1);
		gameBoard.placePiece(new Knight(Piece.PieceType.WN, 0, 6, Piece.Player.white), 0, 6);
		moveResult.piecesOnBoard.add(gameBoard.getPiece(7, 1).makeReturnPiece());
		moveResult.piecesOnBoard.add(gameBoard.getPiece(7, 6).makeReturnPiece());
		moveResult.piecesOnBoard.add(gameBoard.getPiece(0, 1).makeReturnPiece());
		moveResult.piecesOnBoard.add(gameBoard.getPiece(0, 6).makeReturnPiece());
		
		for (int i = 0; i < 8; i++){
			gameBoard.placePiece(new Pawn(Piece.PieceType.WP, 1, i, Piece.Player.white), 1, i);
			moveResult.piecesOnBoard.add(gameBoard.getPiece(1, i).makeReturnPiece());
		}
		for (int i = 0; i < 8; i++){
			gameBoard.placePiece(new Pawn(Piece.PieceType.BP, 6, i, Piece.Player.black), 6, i);
			moveResult.piecesOnBoard.add(gameBoard.getPiece(6, i).makeReturnPiece());
		}

		// just for testing object methods
		// gameBoard.getPiece returns the piece, then we use gettype method on it to get
		// the type (and other data points).
		// gameBoard.getPiece(7, 4).getType();
		// System.out.println((int)'a');

		// initialize board representation?
		// add pieces to correct spots on board
		PlayChess.printBoard(moveResult.piecesOnBoard);
	}

	public static void undoLastMove(ReturnPlay moveResult, Player currentPlayer, int oldPosFileZ, int oldPosRankZ, int newPosFileZ, int newPosRankZ, ChessBoard gameBoard)
	{
		System.out.println("Undoing Move");

		//System.out.println("NewRank " + newPosRankZ + " NewFile " + newPosFileZ);
		//System.out.println("Gameboard at that location " + gameBoard.getPiece(newPosRankZ, newPosFileZ).type);
		
		gameBoard.placePiece(gameBoard.getPiece(newPosRankZ, newPosFileZ), oldPosRankZ, oldPosFileZ);
		
		/////System.out.println("Placed " + destPiece.type + " onto " + oldPosRankZ + oldPosFileZ);
		///System.out.println("Gameboard at that location " + gameBoard.getPiece(oldPosRankZ, oldPosFileZ).type);
		 
		if (gameBoard.lastCapturedPieceType != null)
		{
			System.out.print("Replacing Captured piece: ");
			
			//gameBoard.removePiece(newPosRankZ, newPosFileZ, gameBoard.getPiece(newPosRankZ, newPosFileZ).type);
			System.out.println("Placing " + gameBoard.lastCapturedPieceReference.type + " at " + newPosRankZ + newPosFileZ);
			gameBoard.placePiece(gameBoard.lastCapturedPieceReference, newPosRankZ, newPosFileZ);
			return;
		}
		//System.out.println("Got to here");
		gameBoard.removePiece(newPosRankZ, newPosFileZ, gameBoard.getPiece(newPosRankZ, newPosFileZ).type);
		//gameBoard.getPiece(newPosRankZ, newPosFileZ).file = -1;
		//gameBoard.getPiece(newPosRankZ, newPosFileZ).rank = -1;
		System.out.println(gameBoard.getPiece(oldPosRankZ, oldPosFileZ).rank);
		gameBoard.getPiece(oldPosRankZ, oldPosFileZ).rank = oldPosRankZ;
		gameBoard.getPiece(oldPosRankZ, oldPosFileZ).file = oldPosFileZ;


	}

	public static boolean inCheck(Player currentPlayer, ChessBoard gameBoard)
	{
		//Find location of opposing king.
		int kingRank = 0;
		int kingFile  =0;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (gameBoard.getPiece(i, j) == null) {
					continue;
				}
				else if (gameBoard.getPiece(i, j).type == chess.Piece.PieceType.BK && currentPlayer == Player.white)
				{
					kingRank = i;
					kingFile = j;
					break;
				}
				else if (gameBoard.getPiece(i, j).type == chess.Piece.PieceType.WK && currentPlayer == Player.black)
				{
					kingRank = i;
					kingFile = j;
					break;
				}
			}
		}
		//System.out.println("King Location to Check for: " + kingRank +", " + kingFile);
		//Check if any piece on the opposing side has a valid move ending on the current location of king.
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (gameBoard.getPiece(i, j) == null) {
					continue;
				}
				if (gameBoard.getPiece(i, j).player == Piece.Player.white && currentPlayer == Player.white)
				{
					if ((gameBoard.getPiece(i, j).isValidMove(i, j, kingRank, kingFile)) == true && gameBoard.isPiecesBetween(i, j, kingRank, kingFile) == false)
					{
						//System.out.println("Saw Check on white players turn with " + gameBoard.getPiece(i, j).type + i + j);
						return true;
					}
				}
				if (gameBoard.getPiece(i, j).player == Piece.Player.black && currentPlayer == Player.black && gameBoard.isPiecesBetween(i, j, kingRank, kingFile) == false)
				{
					if (gameBoard.getPiece(i, j).isValidMove(i, j, kingRank, kingFile))
					{
						//System.out.println("Saw Check on black players turn with " + gameBoard.getPiece(i, j).type);
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void switchPlayer(Player currentplayer)
	{
		if (currentPlayer == Player.white) {
			currentPlayer = Player.black;
		} else {
			currentPlayer = Player.white;
		}
	}

	public static boolean inCheckMate(Player currentPlayer, ChessBoard gameBoard, ReturnPlay moveResult)
	{
		//Plan to go through every valid move of player in check to see if one can get them out of check. 
		//Once a move is tried, undo the move.
		if (currentPlayer == Player.white) {
			currentPlayer = Player.black;
		} else {
			currentPlayer = Player.white;
		}
		System.out.println("Seeing if: " + currentPlayer + " is in check and has a valid move");

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (gameBoard.getPiece(i, j) == null ) {
					continue;
				}
				else if (((gameBoard.getPiece(i, j).player == Piece.Player.black && currentPlayer != Player.black) || (gameBoard.getPiece(i, j).player == Piece.Player.white && currentPlayer != Player.white)))
				{
					continue;
				}
				else
				{
					for (int r = 0; r < 8; r++) {
						for (int f = 0; f < 8; f++) {
							///System.out.println("Checking: " + gameBoard.getPiece(i, j).type + i+j+" to "+ r + f);
							//If the starting piece is a pawn
							if ((gameBoard.getPiece(i, j).type == chess.Piece.PieceType.BP || gameBoard.getPiece(i, j).type == chess.Piece.PieceType.WP))
							{
								Pawn p = (Pawn)(gameBoard.getPiece(i, j));
								//Check if the move is valid without moving it
								int checkNum = p.isValidMoveCheck(i, j, r, f);
								if (checkNum!=0)
								{
									if(checkNum == 1)
									{
										System.out.println("Found Valid move C: " + gameBoard.getPiece(i, j).type + i+j+" to "+ r + f);

										Chess.switchPlayer(currentPlayer);
										if (Chess.inCheck(currentPlayer, gameBoard) == true)
										{
											System.out.println("Still in Check");
											Chess.switchPlayer(currentPlayer);
											undoLastMove(moveResult, currentPlayer, j, i, f, r, gameBoard);
											
										}
										else
										{
											System.out.println("Out of Check");
											Chess.switchPlayer(currentPlayer);
											undoLastMove(moveResult, currentPlayer, j, i, f, r, gameBoard);
											
										}
										continue;
									}
									if (checkNum == 2 && gameBoard.getPiece(r, f)!=null)
									{
										System.out.println("Found Valid move D: " + gameBoard.getPiece(i, j).type + i+j+" to "+ r + f);
										continue;
									}
									
									
								}
								else
								{
									continue;
								}
					
							}
							if (gameBoard.isPiecesBetween(i, j, r, f) == false && gameBoard.getPiece(i, j).isValidMove(i, j, r, f))
							{
								if (gameBoard.getPiece(r, f) != null)
								{
									if (((gameBoard.getPiece(r, f).player == Piece.Player.black && currentPlayer == Player.black) || (gameBoard.getPiece(r, f).player == Piece.Player.white && currentPlayer == Player.white)))
									{
										continue;
									}
									else
									{
										System.out.println("Found Valid move A: " + gameBoard.getPiece(i, j).type + i+j+" to "+ r + f);
										
									}
								}
								else
								{
									System.out.println("Found Valid move B: " + gameBoard.getPiece(i, j).type + i+j+" to "+ r + f);
								}
								
								
							}
						}
					}
					
				}
			}
		}
		if (currentPlayer == Player.white) {
			currentPlayer = Player.black;
		} else {
			currentPlayer = Player.white;
		}
		return false;
	}
}
