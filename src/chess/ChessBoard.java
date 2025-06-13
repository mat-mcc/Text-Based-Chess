package chess;

import chess.Chess.Player;
import chess.Piece.PieceType;

public class ChessBoard {
    private Piece[][] chessBoard;
    public PieceType lastCapturedPieceType;
    public int lastCapturedPieceRank;
    public int lastCapturedPieceFile;
    public Piece lastCapturedPieceReference;
    

    // constructor! 8x8 board!
    public ChessBoard() {
        chessBoard = new Piece[8][8];
        lastCapturedPieceType = null;
    }

    public void placePiece(Piece piece, int rank, int file) {
        chessBoard[rank][file] = piece;
        //System.out.println("There is a " + chessBoard[rank][file].type + " at " + rank + file);
    }

    public Piece getPiece(int rank, int file) {
        // debug printing
        // System.out.print("The thing at " + rank + ", " + file + " ");
        return chessBoard[rank][file];
    }

    public void removePiece(int rank, int file, PieceType capturedPieceType) {
        lastCapturedPieceType = capturedPieceType;
        lastCapturedPieceRank = rank;
        lastCapturedPieceFile = file;
        System.out.println("Removed " + capturedPieceType + " at " + lastCapturedPieceRank + lastCapturedPieceFile);
        lastCapturedPieceReference = this.getPiece(rank, file);
        chessBoard[rank][file] = null;
    }

    public void resetCapturedPieceStatus(){
        lastCapturedPieceType = null;
        lastCapturedPieceRank = -1;
        lastCapturedPieceFile = -1;
    }

    // a little overkill, but i don't want to take any chances!
    public void clearBoard() {
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                if (chessBoard[i][j] != null) {
                    chessBoard[i][j].type = null;
                    chessBoard[i][j].file = -1;
                    chessBoard[i][j].rank = -1;
                    chessBoard[i][j] = null;
                }
            }
        }
    }

    // will either have pieces between hosirzontally, vertically, or diagonally
    public boolean isPiecesBetween(int oldRank, int oldFile, int newRank, int newFile) {
        int rankdiff = Math.abs(oldRank - newRank);
        int filediff = Math.abs(oldFile - newFile);

        // checking vertically
        if (rankdiff != 0 && filediff == 0) {
            // moving up, vertically
            if (oldRank < newRank) {
                for (int i = oldRank + 1; i < newRank; i++) {
                    if (this.chessBoard[i][newFile] != null) {
                        return true;
                    }
                }
            }
            // moving down, vertically
            if (oldRank > newRank) {
                for (int i = oldRank - 1; i > newRank; i--) {
                    if (this.chessBoard[i][newFile] != null) {
                        return true;
                    }
                }
            }
        }

        // Check if there are pieces between old and new positions horizontally
        if (rankdiff == 0 && filediff != 0) {
            // Moving to the right horizontally
            if (oldFile < newFile) {
                for (int j = oldFile + 1; j < newFile; j++) {
                    if (this.chessBoard[oldRank][j] != null) {
                        return true;
                    }
                }
            }
            // Moving to the left horizontally
            if (oldFile > newFile) {
                for (int j = oldFile - 1; j > newFile; j--) {
                    if (this.chessBoard[oldRank][j] != null) {
                        return true;
                    }
                }
            }
        }
        // Check if there are pieces between old and new positions diagonally
        if (rankdiff == filediff) {
            // Moving diagonally up-right
            if (oldRank < newRank && oldFile < newFile) {
                for (int i = oldRank + 1, j = oldFile + 1; i < newRank && j < newFile; i++, j++) {
                    if (this.chessBoard[i][j] != null) {
                        return true;
                    }
                }
            }
            // Moving diagonally up-left
            if (oldRank < newRank && oldFile > newFile) {
                for (int i = oldRank + 1, j = oldFile - 1; i < newRank && j > newFile; i++, j--) {
                    if (this.chessBoard[i][j] != null) {
                        return true;
                    }
                }
            }
            // Moving diagonally down-right
            if (oldRank > newRank && oldFile < newFile) {
                for (int i = oldRank - 1, j = oldFile + 1; i > newRank && j < newFile; i--, j++) {
                    if (this.chessBoard[i][j] != null) {
                        return true;
                    }
                }
            }
            // Moving diagonally down-left
            if (oldRank > newRank && oldFile > newFile) {
                for (int i = oldRank - 1, j = oldFile - 1; i > newRank && j > newFile; i--, j--) {
                    if (this.chessBoard[i][j] != null) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // returns an int to chess to tell the ReturnPlay what message to print (if any)
    public int processMove(String oldPos, String newPos, Player currentPlayer, char promotion) {
        if (oldPos == newPos) {
            // duplicate move, not valid
        }
        // if new position puts piece out of bounds?

        // if a piece is in the way of a piece (maybe better per piece type)

        // format input into ONE INDEXED chess notation
        char oldPosFile = oldPos.charAt(0);
        // System.out.println("oldPosFile is " + oldPosFile);
        int oldPosRank = Integer.parseInt(oldPos.substring(1, 2));
        // System.out.println("oldPosRank is " + oldPosRank);
        char newPosFile = newPos.charAt(0);
        // System.out.println("oldPosFile is " + newPosFile);
        int newPosRank = Integer.parseInt(newPos.substring(1, 2));
        // System.out.println("oldPosRank is " + newPosRank);

        // also ZERO INDEXED matrix notation for convenience
        int oldPosFileZ = (int) oldPosFile - 97;
        // System.out.println("oldPosFileZ is " + oldPosFileZ);
        int oldPosRankZ = oldPosRank - 1;
        // System.out.println(oldPosRankZ);
        int newPosFileZ = (int) newPosFile - 97;
        // System.out.println("oldPosFileZ is " + newPosFileZ);
        int newPosRankZ = newPosRank - 1;

        Piece checkColor = this.chessBoard[oldPosRankZ][oldPosFileZ];
        if (currentPlayer == Player.white) {
            if (checkColor.player != Piece.Player.white) {
                //System.out.println("ONLY WHITE CAN MOVE WHITE PIECES");
                return -1;
            }
        }

        if (currentPlayer == Player.black) {
            if (checkColor.player != Piece.Player.black) {
                //System.out.println("ONLY BLACK CAN MOVE BLACK PIECES");
                return -1;
            }
        }

        // should show the king object! (on first turn)
        // System.out.println(this.getPiece(oldPosRankZ, oldPosFileZ).getType());
        return movePiece(oldPosRankZ, oldPosFileZ, newPosRankZ, newPosFileZ,promotion);
    }

    // move piece method
    // goals:
    // move the piece if possible
    // requires a lot of checks, maybe call a canMove function in pieces or
    // something
    public int movePiece(int oldRank, int oldFile, int newRank, int newFile,char promotion) {
        Piece piecetoMove = this.chessBoard[oldRank][oldFile];

        // if moving piece onto another piece
        // capturing functonality
        if (this.chessBoard[newRank][newFile] != null) {
            Piece destPiece = this.chessBoard[newRank][newFile];
            if (destPiece.player == piecetoMove.player) {
                return -1;
            }

            if (destPiece.player != piecetoMove.player) {
                // knight can jump over pieces
                if (piecetoMove instanceof Knight) {
                    removePiece(newRank, newFile, destPiece.type);
                    System.out.println("knight capture!");
                }
                if (!(piecetoMove instanceof Pawn)){
                if (isPiecesBetween(oldRank, oldFile, newRank, newFile) == false) {
                    removePiece(newRank, newFile, destPiece.type);
                    System.out.println("captured!");
                }
            }
            }
        }
        // special cases
        int rankdiff = Math.abs(oldRank - newRank);
        int filediff = Math.abs(oldFile - newFile);
        if (piecetoMove instanceof King) {
        King PieceKing = (King)(piecetoMove);
        if (PieceKing.firstMove == true){
            if ((filediff == 2 && rankdiff == 0) && piecetoMove instanceof King) {
                // castle!
                int castle = castling(oldRank, oldFile, newRank, newFile);
                PieceKing.firstMove = false;
                return castle;
            }
            }
        }




        // PAWN MOVEMENT CHECKS FOR CAPTURING AND EN PASSANT (WAY TOO MUCH WORK FOR THE PAWN)
if (rankdiff == filediff && piecetoMove instanceof Pawn){
            // is it not en passant? just capture the pawn like a plebian
            // Regular pawn capture
            if ((rankdiff == 1 || rankdiff == -1) && Math.abs(filediff) == 1 && this.chessBoard[newRank][newFile] != null) {
                System.out.println("Regular pawn capture");
                int pawncap = PawnCapture(oldRank, oldFile, newRank, newFile);
                return pawncap;
            }

        // En passant capture check
        // literally ripping my hair out

        /* edge cases are when file is 7 or 0 because then you only check adjacent relative to the inner part of the board (otherwise accessing outside of board matrix)
         * checks if adjacent pieces are null, if theyre not, checks if they're pawn pieces, if they are, checks movement is correct, if so, then it will perform enpassant
         */
    //Pawn checker = (Pawn)(this.chessBoard[oldRank][oldFile]);
    //if(checker.firstMove = false){

    if (oldFile == 7) {
        if (this.chessBoard[oldRank][oldFile -1] != null){
        if (((this.chessBoard[oldRank][oldFile - 1] instanceof Pawn) && this.chessBoard[oldRank][oldFile - 1].player != piecetoMove.player)
        && (Math.abs(rankdiff) == 1 && Math.abs(filediff) == 1)) {
            System.out.println("En passant");
            
            Pawn capturedPawn = null;
            capturedPawn = (Pawn) this.chessBoard[oldRank][oldFile - 1];
            if (capturedPawn != null) {

                removePiece(oldRank, capturedPawn.file, capturedPawn.type);
                Piece piecePassanting = this.chessBoard[oldRank][oldFile];
                piecePassanting.file = newFile;
                piecePassanting.rank = newRank;
                this.chessBoard[newRank][newFile] = piecePassanting;
                this.chessBoard[oldRank][oldFile] = null; // Clear the original square
                return 0;
                
            }
        }
    }
}

    if (oldFile ==0) {
        if (this.chessBoard[oldRank][oldFile + 1] != null){
        if (((this.chessBoard[oldRank][oldFile + 1] instanceof Pawn || this.chessBoard[oldRank][oldFile + 1] == null) && this.chessBoard[oldRank][oldFile + 1].player != piecetoMove.player)
        && (Math.abs(rankdiff) == 1 && Math.abs(filediff) == 1)) {
            System.out.println("En passant");
            
            // Identify the captured pawn
            Pawn capturedPawn = null;
            capturedPawn = (Pawn) this.chessBoard[oldRank][oldFile + 1];
            if (capturedPawn != null) {
                // Remove the captured pawn
                removePiece(oldRank, capturedPawn.file, capturedPawn.type);
                
                // Move the capturing pawn
                Piece piecePassanting = this.chessBoard[oldRank][oldFile];
                piecePassanting.file = newFile;
                piecePassanting.rank = newRank;
                this.chessBoard[newRank][newFile] = piecePassanting;
                this.chessBoard[oldRank][oldFile] = null; // Clear the original square
                return 0;

            }
        }
    }
}
    
    
    if (oldRank < 7 && oldRank >= 1 && oldFile < 7 && oldFile >= 1) {
    if ((this.chessBoard[oldRank][oldFile + 1] == null && this.chessBoard[oldRank][oldFile - 1] != null)){       
        if (((this.chessBoard[oldRank][oldFile - 1] instanceof Pawn || this.chessBoard[oldRank][oldFile - 1] == null)  
        && this.chessBoard[oldRank][oldFile - 1].player != piecetoMove.player) && (Math.abs(rankdiff) == 1 && Math.abs(filediff) == 1)) {
    System.out.println("En passant");
    
    // Identify the captured pawn
    Pawn capturedPawn = null;
    if (oldFile + 1 < 8 && oldFile + 1 >= 0 && this.chessBoard[oldRank][oldFile + 1] instanceof Pawn) {
        capturedPawn = (Pawn) this.chessBoard[oldRank][oldFile + 1];
    } else if (oldFile - 1 >= 0 && oldFile - 1 < 8 && this.chessBoard[oldRank][oldFile - 1] instanceof Pawn) {
        capturedPawn = (Pawn) this.chessBoard[oldRank][oldFile - 1];
    }
    
    if (capturedPawn != null) {
        // Remove the captured pawn
        removePiece(oldRank, capturedPawn.file, capturedPawn.type);
        
        // Move the capturing pawn
        Piece piecePassanting = this.chessBoard[oldRank][oldFile];
        piecePassanting.file = newFile;
        piecePassanting.rank = newRank;
        this.chessBoard[newRank][newFile] = piecePassanting;
        this.chessBoard[oldRank][oldFile] = null; // Clear the original square
        return 0;
    }
}}




    if ((this.chessBoard[oldRank][oldFile + 1] != null && this.chessBoard[oldRank][oldFile - 1] == null)){    
        if (((this.chessBoard[oldRank][oldFile + 1] instanceof Pawn || this.chessBoard[oldRank][oldFile + 1] == null)  
        && this.chessBoard[oldRank][oldFile + 1].player != piecetoMove.player) && (Math.abs(rankdiff) == 1 && Math.abs(filediff) == 1)) {
    System.out.println("En passant");
    
    // Identify the captured pawn
    Pawn capturedPawn = null;
    if (oldFile + 1 < 8 && oldFile + 1 >= 0 && this.chessBoard[oldRank][oldFile + 1] instanceof Pawn) {
        capturedPawn = (Pawn) this.chessBoard[oldRank][oldFile + 1];
    } else if (oldFile - 1 >= 0 && oldFile - 1 < 8 && this.chessBoard[oldRank][oldFile - 1] instanceof Pawn) {
        capturedPawn = (Pawn) this.chessBoard[oldRank][oldFile - 1];
    }
    
    if (capturedPawn != null) {
        // Remove the captured pawn
        removePiece(oldRank, capturedPawn.file, capturedPawn.type);
        
        // Move the capturing pawn
        Piece piecePassanting = this.chessBoard[oldRank][oldFile];
        piecePassanting.file = newFile;
        piecePassanting.rank = newRank;
        this.chessBoard[newRank][newFile] = piecePassanting;
        this.chessBoard[oldRank][oldFile] = null; // Clear the original square
        return 0;
    }
}}





    if ((this.chessBoard[oldRank][oldFile + 1] != null || this.chessBoard[oldRank][oldFile - 1] != null)){    
        if (((this.chessBoard[oldRank][oldFile + 1] instanceof Pawn || this.chessBoard[oldRank][oldFile + 1] == null)  && this.chessBoard[oldRank][oldFile + 1].player != piecetoMove.player)
        || ((this.chessBoard[oldRank][oldFile - 1] instanceof Pawn || this.chessBoard[oldRank][oldFile -1] == null) && this.chessBoard[oldRank][oldFile - 1].player != piecetoMove.player)
        && (Math.abs(rankdiff) == 1 && Math.abs(filediff) == 1)) {
    System.out.println("En passant");
    
    // Identify the captured pawn
    Pawn capturedPawn = null;
    if (oldFile + 1 < 8 && oldFile + 1 >= 0 && this.chessBoard[oldRank][oldFile + 1] instanceof Pawn) {
        capturedPawn = (Pawn) this.chessBoard[oldRank][oldFile + 1];
    } else if (oldFile - 1 >= 0 && oldFile - 1 < 8 && this.chessBoard[oldRank][oldFile - 1] instanceof Pawn) {
        capturedPawn = (Pawn) this.chessBoard[oldRank][oldFile - 1];
    }
    
    if (capturedPawn != null) {
        // Remove the captured pawn
        removePiece(oldRank, capturedPawn.file, capturedPawn.type);
        
        // Move the capturing pawn
        Piece piecePassanting = this.chessBoard[oldRank][oldFile];
        piecePassanting.file = newFile;
        piecePassanting.rank = newRank;
        this.chessBoard[newRank][newFile] = piecePassanting;
        this.chessBoard[oldRank][oldFile] = null; // Clear the original square
        return 0;
    }
}}

}
}


        
        
        // I dont think piecetoMove needs to be differniated by type because of object
        // overrides

        // check for valid move
        if (piecetoMove.isValidMove(oldRank, oldFile, newRank, newFile) == true) {

            if (piecetoMove instanceof Pawn){
                if (piecetoMove.player == Piece.Player.black && newRank == 0){
                    getPiece(oldRank, oldFile).file = -1;
                    getPiece(oldRank, oldFile).rank = -1;
                    removePiece(oldRank, oldFile, getPiece(oldRank, oldFile).type);
                    this.chessBoard[oldRank][oldFile] = null;
                    if (promotion == '_' || promotion == 'Q'){
                        System.out.println("promotion Queen!");
                        this.placePiece(new Queen(PieceType.BQ,newRank,newFile,Piece.Player.black), newRank, newFile);
                        return 0;
                    }
                    if (promotion == 'R'){
                        System.out.println("promotion Rook!");
                        this.placePiece(new Rook(PieceType.BR,newRank,newFile,Piece.Player.black), newRank, newFile);
                        return 0;
                    }
                    if (promotion == 'B'){
                        System.out.println("promotion Bishop!");
                        this.placePiece(new Bishop(PieceType.BB,newRank,newFile,Piece.Player.black), newRank, newFile);
                        return 0;
                    }
                    if (promotion == 'N'){
                        System.out.println("promotion Knight!");
                        this.placePiece(new Knight(PieceType.BN,newRank,newFile,Piece.Player.black), newRank, newFile);
                        return 0;
                    }
                }

                if (piecetoMove.player == Piece.Player.white && newRank == 7){
                    getPiece(oldRank, oldFile).file = -1;
                    getPiece(oldRank, oldFile).rank = -1;
                    removePiece(oldRank, oldFile, getPiece(oldRank, oldFile).type);
                    this.chessBoard[oldRank][oldFile] = null;
                    if (promotion == '_' || promotion == 'Q'){
                        System.out.println("promotion Queen!");
                        this.placePiece(new Queen(PieceType.WQ,newRank,newFile,Piece.Player.white), newRank, newFile);
                        return 0;
                    }
                    if (promotion == 'R'){
                        System.out.println("promotion Rook!");
                        this.placePiece(new Rook(PieceType.WR,newRank,newFile,Piece.Player.white), newRank, newFile);
                        return 0;
                    }
                    if (promotion == 'B'){
                        System.out.println("promotion Bishop!");
                        this.placePiece(new Bishop(PieceType.WB,newRank,newFile,Piece.Player.white), newRank, newFile);
                        return 0;
                    }
                    if (promotion == 'N'){
                        System.out.println("promotion Knight!");
                        this.placePiece(new Knight(PieceType.WN,newRank,newFile,Piece.Player.white), newRank, newFile);
                        return 0;
                    }

                }
            }

            if (isPiecesBetween(oldRank, oldFile, newRank, newFile) == false) {
                // change object data
                this.chessBoard[oldRank][oldFile].setfile(newFile);
                this.chessBoard[oldRank][oldFile].setrank(newRank);
                // change chessBoard data
                this.chessBoard[newRank][newFile] = this.chessBoard[oldRank][oldFile];
                this.chessBoard[oldRank][oldFile] = null;
                return 0;
            }
            // knight can jump over pieces
            if (piecetoMove instanceof Knight) {
                // change object data
                this.chessBoard[oldRank][oldFile].setfile(newFile);
                this.chessBoard[oldRank][oldFile].setrank(newRank);
                // change chessBoard data
                this.chessBoard[newRank][newFile] = this.chessBoard[oldRank][oldFile];
                this.chessBoard[oldRank][oldFile] = null;
                return 0;
            }
        }

        return -1;
    }


    // CASTLING
    // WILL RETURN -1 FOR AN ILLEGAL MOVE (HAS ALREADY CASTLED, PIECES IN WAY, ETC)
    public int castling(int oldRank, int oldFile, int newRank, int newFile) {
        int filediff = oldFile - newFile;
        // needs a check for no pieces between them and also if they've moved yet
        // king and (rook?) has a hascastled boolean that maybe would help here!
        // so not fully correct (but the moving of the pieces works)

        // white king?
        King thisKing = (King) this.chessBoard[oldRank][oldFile];
        if (thisKing.firstMove == true) {
            if (thisKing.type == Piece.PieceType.WK) {
                // castling to the right
                if (filediff < 0) {
                    if (isPiecesBetween(oldRank, oldFile, 0, 7) == false) {
                        this.chessBoard[oldRank][oldFile].setfile(newFile);
                        this.chessBoard[oldRank][oldFile].setrank(newRank);
                        this.chessBoard[newRank][newFile] = this.chessBoard[oldRank][oldFile];
                        this.chessBoard[oldRank][oldFile] = null;
                        this.chessBoard[5][0] = this.chessBoard[0][7];
                        this.chessBoard[5][0].file = 5;
                        this.chessBoard[5][0].rank = 0;
                        removePiece(0, 7, thisKing.type);
                        thisKing.firstMove = false;
                        return 0;
                    }
                    //System.out.println("pieces between");

                    return -1;
                    // castling to the left
                } else {
                    if (isPiecesBetween(oldRank, oldFile, 0, 0) == false) {
                        this.chessBoard[oldRank][oldFile].setfile(newFile);
                        this.chessBoard[oldRank][oldFile].setrank(newRank);
                        this.chessBoard[newRank][newFile] = this.chessBoard[oldRank][oldFile];
                        this.chessBoard[oldRank][oldFile] = null;
                        this.chessBoard[3][0] = this.chessBoard[0][0];
                        this.chessBoard[3][0].file = 3;
                        this.chessBoard[3][0].rank = 0;
                        removePiece(0, 0, thisKing.type);
                        thisKing.firstMove = false;
                        return 0;
                    }
                    return -1;
                }
            } // king type bracket
                // black king!
            if (thisKing.type == Piece.PieceType.BK) {
                // castling to the right
                if (filediff < 0) {
                    if (isPiecesBetween(oldRank, oldFile, 7, 7) == false) {
                        this.chessBoard[oldRank][oldFile].setfile(newFile);
                        this.chessBoard[oldRank][oldFile].setrank(newRank);
                        this.chessBoard[newRank][newFile] = this.chessBoard[oldRank][oldFile];
                        this.chessBoard[oldRank][oldFile] = null;
                        this.chessBoard[5][7] = this.chessBoard[7][7];
                        this.chessBoard[5][7].file = 5;
                        this.chessBoard[5][7].rank = 7;
                        removePiece(7, 7, thisKing.type);
                        thisKing.firstMove = false;
                        return 0;
                    }
                    return -1;
                    // castling to the left
                } else {
                    if (isPiecesBetween(oldRank, oldFile, 7, 0) == false) {
                        this.chessBoard[oldRank][oldFile].setfile(newFile);
                        this.chessBoard[oldRank][oldFile].setrank(newRank);
                        this.chessBoard[newRank][newFile] = this.chessBoard[oldRank][oldFile];
                        this.chessBoard[oldRank][oldFile] = null;
                        this.chessBoard[3][7] = this.chessBoard[7][0];
                        this.chessBoard[3][7].file = 3;
                        this.chessBoard[3][7].rank = 7;
                        removePiece(7, 0, thisKing.type);
                        thisKing.firstMove = false;
                        return 0;
                    }
                    return -1;
                }
            } // king type bracket

        } // hasCastled bracket
        //System.out.println("has castled already!");
        return -1;
    }



    public int PawnCapture(int oldRank, int oldFile, int newRank, int newFile) {
            if (this.chessBoard[newRank][newFile] != null){
            removePiece(newRank, newFile, this.chessBoard[newRank][newFile].getType());
            System.out.println("removed piece to cap");
            this.chessBoard[newRank][newFile] = this.chessBoard[oldRank][oldFile];
            // Update the rank and file of the capturing pawn
            this.chessBoard[newRank][newFile].setfile(newFile);
            this.chessBoard[newRank][newFile].setrank(newRank);
            // Return 0 to indicate successful capture
            return 0;
            }
            else {
                return -1;
            }
}
public boolean PawnCaptureBool(int oldRank, int oldFile, int newRank, int newFile) {
    int rankdiff = Math.abs(oldRank - newRank);
    int filediff = Math.abs(oldFile - newFile);
    if ((rankdiff == 1 || rankdiff == -1) && Math.abs(filediff) == 1 && this.chessBoard[newRank][newFile] != null && this.chessBoard[oldRank][oldFile].player != this.chessBoard[newRank][newFile].player) {
        System.out.println("Regular pawn capture");
        return true;
    }
    return false;
}

}
