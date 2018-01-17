package chess;

public class Knight extends Piece {

    /**
     * Constructor.
     *
     * @param white Boolean that represents which team the piece is on.
     * @param type Char to indicate the type.
     * @param row Row number of the piece.
     * @param col Column number of the piece.
     */
    public Knight(boolean white, char type, int row, int col) {
        super(white, type, row, col);
        /*For special board initialization - if the piece is not being placed in
        its default position, then assume it has moved at least once       */
        if ((white && row != 7) || (!white && row != 0)
                && (col != 1 || col != 6)) {
            hasMoved = true;
            numMoves++;
        }
    }

    /**
     * Copy constructor - creates a deep copy of a Piece passed to it.
     *
     * @param p The piece being copied.
     */
    public Knight(Piece p) {
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
    @Override
    public Boolean isValid(int destX, int destY, Board board) {
        int numXMovement = this.getRow() - destX;
        int numYMovement = this.getCol() - destY;
        //Two movement cases for a knight - two vertical movements and one horizontal,
        //or two horizontal and one vertical
        if ((Math.abs(numXMovement) == 2 && Math.abs(numYMovement) == 1)
                || Math.abs(numXMovement) == 1 && Math.abs(numYMovement) == 2) {
            //Check if there is a piece at the destination
            if (board.occupied(destX, destY)) {
                return this.isWhite()
                        != board.getPieceAt(destX, destY).isWhite(); //Capture
            }
            return true;
        }
        return false;
    }

}
