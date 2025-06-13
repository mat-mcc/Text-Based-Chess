package chess;


// maybe this should be an abstract class otherwise moving every piece will be huge (and not good coding practice (nerd!)) - Matt

public abstract class Piece {
    // enum in ches.java? we're rocking with enums!!
    	static enum PieceType {WP, WR, WN, WB, WQ, WK, 
		            BP, BR, BN, BB, BK, BQ};
        	
	    static enum Player { white, black }            
        // data in a piece object
        public PieceType type;
        public Player player;
        public int file;
        public int rank;

        // constructor (type, color, row, col)
        public Piece(PieceType type, int rank, int file, Player player){
            this.type = type;
            this.file = file;
            this.rank = rank;
            this.player = player;
        }


        // get the piece type
        public PieceType getType(){

            //debug printing
            //System.out.println("it is a " + type);
            return type;
        }
        // get the piece row
        public int getfile(){
            return file;
        }

        // get the piece column
        public int getrank(){
            return rank;
        }

        public void setfile(int file){
            this.file = file;
        }
        public void setrank(int rank){
            this.rank = rank;
        }

        public boolean isValidMove(int oldRank, int oldFile, int newRank, int newFile){
            return false;
        }

        public ReturnPiece makeReturnPiece(){
            ReturnPiece returnPiece = new ReturnPiece();
            return returnPiece;
        }


}
