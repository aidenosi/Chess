package chess;

public class Bishop extends Piece {

    /**
     * Constructor.
     *
     * @param white Boolean that represents which team the piece is on.
     * @param type Char to indicate the type.
     * @param row Row number of the piece.
     * @param col Column number of the piece.
     */
    public Bishop(boolean white, char type, int row, int col) {
        super(white, type, row, col);
        /*For special board initialization - if the piece is not being placed in
        its default position, then assume it has moved at least once       */
        if ((white && row != 7) || (!white && row != 0)
                && (col != 2 || col != 5)) {
            hasMoved = true;
            numMoves++;
        }
    }

    /**
     * Copy constructor - creates a deep copy of a Piece passed to it.
     *
     * @param p The piece being copied.
     */
    public Bishop(Piece p) {
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
        //Check that piece is moving diagonally
        if (this.getRow() != destX && this.getCol() != destY
                && Math.abs(numXMovement) == Math.abs(numYMovement)) {
            //Moving Northwest
            if (this.getRow() > destX && this.getCol() > destY) {
                //Check all spaces between current position and destination (exclusive)
                for (int i = 1; i < Math.abs(numXMovement); i++) {
                    if (board.occupied(this.getRow() - i, this.getCol() - i)) {
                        return false;
                    }
                }
            } //Moving Northeast
            else if (this.getRow() > destX && this.getCol() < destY) {
                //Check all spaces between current position and destination (exclusive)
                for (int i = 1; i < Math.abs(numXMovement); i++) {
                    if (board.occupied(this.getRow() - i, this.getCol() + i)) {
                        return false;
                    }
                }
            } //Moving Southwest
            else if (this.getRow() < destX && this.getCol() > destY) {
                //Check all spaces between current position and destination (exclusive)
                for (int i = 1; i < Math.abs(numXMovement); i++) {
                    if (board.occupied(this.getRow() + i, this.getCol() - i)) {
                        return false;
                    }
                }
            } //Moving Southeast
            else if (this.getRow() < destX && this.getCol() < destY) {
                //Check all spaces between current position and destination (exclusive)
                for (int i = 1; i < Math.abs(numXMovement); i++) {
                    if (board.occupied(this.getRow() + i, this.getCol() + i)) {
                        return false;
                    }
                }
            }
            //Check if there is a piece at the destination
            if (board.occupied(destX, destY)) {
                //If the pieces are opponents
                return this.isWhite()
                        != board.getPieceAt(destX, destY).isWhite(); //Capture
            }
            return true;
        }
        return false;
    }

}
