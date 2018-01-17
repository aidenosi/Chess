package chess;

public class King extends Piece {

    /**
     * Constructor.
     *
     * @param white Boolean that represents which team the piece is on.
     * @param type Char to indicate the type.
     * @param row Row number of the piece.
     * @param col Column number of the piece.
     */
    public King(boolean white, char type, int row, int col) {
        super(white, type, row, col);
        /*For special board initialization - if the piece is not being placed in
        its default position, then assume it has moved at least once       */
        if ((white && row != 7) || (!white && row != 0) && col != 4) {
            hasMoved = true;
            numMoves++;
        }
    }

    /**
     * Copy constructor - creates a deep copy of a Piece passed to it.
     *
     * @param p The piece being copied.
     */
    public King(Piece p) {
        super(p);
    }

    /**
     * Checks if a move is valid.
     *
     * @param destX The Y coordinate of the destination.
     * @param destY The X coordinate of the destination.
     * @param board The board that the piece is on.
     * @return Boolean representing whether the move is valid.
     */
    public Boolean isValid(int destX, int destY, Board board) {
        int numXMovement = this.getRow() - destX;
        int numYMovement = this.getCol() - destY;
        //First check if piece is moving one space along row xor column
        if (destX == this.getRow() ^ destY == this.getCol()
                && Math.abs(numXMovement) == 1 ^ Math.abs(numYMovement) == 1) {
            //Check if there is a piece on the destination
            if (destX == this.getRow() && board.occupied(destX, destY)) {
                //Check if pieces are opponents
                return this.isWhite() != board.getPieceAt(destX, destY).isWhite(); //Capture
            } else if (destY == this.getCol() && board.occupied(destX, destY)) {
                //Check if pieces are opponents
                return this.isWhite() != board.getPieceAt(destX, destY).isWhite(); //Capture
            }
            return true;
        } //Check if movement is one space on a diagonal 
        else if (this.getRow() != destX && this.getCol() != destY
                && Math.abs(numXMovement) == Math.abs(numYMovement)
                && Math.abs(numXMovement) == 1) {
            //Check if there is a piece at the destination
            if (board.occupied(destX, destY)) {
                //If the pieces are opponents
                if (this.isWhite() != board.getPieceAt(destX, destY).isWhite()) {
                    return true; //Capture
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
