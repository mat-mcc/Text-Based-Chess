package chess;

public class Pawn extends Piece {

    // spellcheck pls
    boolean hasPasanted;

    // check if piece has is on its first move
    boolean firstMove;
    // check if piece did a double move 
    boolean justDoubleMoved;

    public Pawn(PieceType type, int rank, int file, Player player) {
        super(type, rank, file, player);
        this.hasPasanted = false;
        this.firstMove = true;
        this.justDoubleMoved = false;
    }

    public boolean isValidMove(int oldRank, int oldFile, int newRank, int newFile) {
        int rankdiff = Math.abs(oldRank-newRank);
        int filediff = Math.abs(oldFile-newFile);
        //System.out.println("is valid for pawn?");
        if (this.firstMove == true){
            if (this.player == Player.black && oldRank-newRank > 0 || this.player == Player.white && oldRank-newRank < 0){
            if (rankdiff < 3 && filediff == 0){
                this.firstMove = false;
                this.justDoubleMoved = true;
                return true;
            }
            }
        }
        if (this.firstMove == false){
            
            if (this.player == Player.black && oldRank-newRank > 0 || this.player == Player.white && oldRank-newRank < 0){
            if (rankdiff < 2 && filediff == 0){
                this.justDoubleMoved = false;
                return true;
            }
        }
        }

        return false;
    }


    public int isValidMoveCheck(int oldRank, int oldFile, int newRank, int newFile) {
        int rankdiff = Math.abs(oldRank-newRank);
        int filediff = Math.abs(oldFile-newFile);
        //System.out.println("is valid for pawn?");
        if (this.firstMove == true){
            if (this.player == Player.black && oldRank-newRank > 0 || this.player == Player.white && oldRank-newRank < 0){
            if (rankdiff < 3 && filediff == 0){
                return 1;
            }
                if (rankdiff == filediff && rankdiff == 1)  {
                return 2;
                }
            }
        }
        if (this.firstMove == false){
            
            if (this.player == Player.black && oldRank-newRank > 0 || this.player == Player.white && oldRank-newRank < 0){
            if (rankdiff < 2 && filediff == 0){
                return 1;
            }
                if (rankdiff == filediff && rankdiff == 1)  {;
                    return 2;
                }
        }
        }
        return 0;
}


    // makes a ReturnPiece object
    public ReturnPiece makeReturnPiece() {
        ReturnPiece Return = new ReturnPiece();

        if (this.getType() == PieceType.BP) {
            Return.pieceType = ReturnPiece.PieceType.BP;
        }
        if (this.getType() == PieceType.WP) {
            Return.pieceType = ReturnPiece.PieceType.WP;
        }

        Return.pieceRank = this.rank + 1;
        switch (file) {
            case 0:
                Return.pieceFile = ReturnPiece.PieceFile.a;
                break;
            case 1:
                Return.pieceFile = ReturnPiece.PieceFile.b;
                break;
            case 2:
                Return.pieceFile = ReturnPiece.PieceFile.c;
                break;
            case 3:
                Return.pieceFile = ReturnPiece.PieceFile.d;
                break;
            case 4:
                Return.pieceFile = ReturnPiece.PieceFile.e;
                break;
            case 5:
                Return.pieceFile = ReturnPiece.PieceFile.f;
                break;
            case 6:
                Return.pieceFile = ReturnPiece.PieceFile.g;
                break;
            case 7:
                Return.pieceFile = ReturnPiece.PieceFile.h;
                break;
        }
        return Return;
    }

}
