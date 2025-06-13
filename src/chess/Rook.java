package chess;

public class Rook extends Piece {

    boolean hasCastled;
    // constructor
    public Rook(PieceType type, int rank, int file, Player player){
        super(type, rank, file, player);
        this.hasCastled = false;
}

public boolean isValidMove(int oldRank, int oldFile, int newRank, int newFile){
    int rankdiff = Math.abs(oldRank-newRank);
    int filediff = Math.abs(oldFile-newFile);
    if (rankdiff*filediff == 0){
        //System.out.println("constraints ok!");
        return true;
    }
    //System.out.println("illegal move");
    return false;
}

public ReturnPiece makeReturnPiece(){
    ReturnPiece Return = new ReturnPiece();

    if (this.getType() == PieceType.BR){
        Return.pieceType = ReturnPiece.PieceType.BR;
    }
    if (this.getType() == PieceType.WR){
        Return.pieceType = ReturnPiece.PieceType.WR;
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