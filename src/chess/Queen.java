package chess;

public class Queen extends Piece {
    //constructor!
    public Queen(PieceType type, int rank, int file, Player player){
        super(type, rank, file, player);

    }
    

    // valid move override
    public boolean isValidMove(int oldRank, int oldFile, int newRank, int newFile){
        int rankdiff = Math.abs(oldRank-newRank);
        int filediff = Math.abs(oldFile-newFile);

        // CAN ONLY MOVE ONE TILE or any length diagonally
        if (rankdiff*filediff == 1 || rankdiff+filediff == 1 || rankdiff == 0 || filediff == 0 || rankdiff == filediff){
            //System.out.println("constraints ok!");
            return true;
        }
        //System.out.println("illegal move");
        return false;
    }

    // makes a ReturnPiece object
    public ReturnPiece makeReturnPiece(){
        ReturnPiece Return = new ReturnPiece();

        if (this.getType() == PieceType.BQ){
            Return.pieceType = ReturnPiece.PieceType.BQ;
        }
        if (this.getType() == PieceType.WQ){
            Return.pieceType = ReturnPiece.PieceType.WQ;
        }

        Return.pieceRank = this.rank+1;
        switch(file){
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
