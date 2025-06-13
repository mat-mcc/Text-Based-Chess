package chess;

public class Bishop extends Piece {

    // constructor
    public Bishop(PieceType type, int rank, int file, Player player){
        super(type, rank, file, player);
}

public boolean isValidMove(int oldRank, int oldFile, int newRank, int newFile){
    int rankdiff = Math.abs(oldRank-newRank);
    int filediff = Math.abs(oldFile-newFile);
    if (rankdiff == filediff){
       //System.out.println("constraints ok!");
        return true;
    }
    //System.out.println("illegal move");
    return false;
}

public ReturnPiece makeReturnPiece(){
    ReturnPiece Return = new ReturnPiece();

    if (this.getType() == PieceType.BB){
        Return.pieceType = ReturnPiece.PieceType.BB;
    }
    if (this.getType() == PieceType.WB){
        Return.pieceType = ReturnPiece.PieceType.WB;
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