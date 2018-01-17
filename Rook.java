package chess;

public class Rook extends Piece {

    /**
     * Constructor.
     *
     * @param white Boolean that represents which team the piece is on.
     * @param type Char to indicate the type.
     * @param row Row number of the piece.
     * @param col Column number of the piece.
     */
    public Rook(boolean white, char type, int row, int col) {
        super(white, type, row, col);
        /*For special board initialization - if the piece is not being placed in
        its default position, then assume it has moved at least once       */
        if ((white && row != 7) || (!white && row != 0)
                && (col != 0 || col != 7)) {
            hasMoved = true;
            numMoves++;
        }
    }

    /**
     * Copy constructor - creates a deep copy of a Piece passed to it.
     *
     * @param p The piece being copied.
     */
    public Rook(Piece p) {
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
        //First check that piece is moving along row xor column
        if (destX == this.getRow() ^ destY == this.getCol()) {
            if (destX > this.getRow()) { //Movement is South
                //Check all spaces between current and destX (exclusive)
                for (int i = this.getRow() + 1; i < destX; i++) {
                    if (board.occupied(i, destY)) {
                        return false;
                    }
                }
            } else if (destX < this.getRow()) { //Movement is North
                //Check all spaces between current and destX (exclusive)
                for (int i = this.getRow() - 1; i > destX; i--) {
                    if (board.occupied(i, destY)) {
                        return false;
                    }
                }
            } else if (destY > this.getCol()) { //Movement is East
                //Check all spaces between current and destY (exclusive)
                for (int i = this.getCol() + 1; i < destY; i++) {
                    if (board.occupied(destX, i)) {
                        return false;
                    }
                }
            } else if (destY < this.getCol()) { //Movement is West
                //Check all spaces between current and destY (exclusive)
                for (int i = this.getCol() - 1; i > destY; i--) {
                    if (board.occupied(destX, i)) {
                        return false;
                    }
                }
            }
            //Lastly, check if there is a piece on the destination
            if (destX == this.getRow() && board.occupied(this.getRow(), destY)) {
                //Check if pieces are opponents
                if (this.isWhite()
                        != board.getPieceAt(this.getRow(), destY).isWhite()) {
                    return true; //Capture
                } //Check if piece at destination is own King
                else if (board.getPieceAt(this.getRow(), destY).getType()
                        == (this.isWhite() ? 'k' : 'K')) {
                    //Check that neither have moved
                    if (!this.hasMoved
                            && !board.getPieceAt(this.getRow(), destY).hasMoved) {
                        return true;
                    }
                } //Check if pieces are on the same team
                else {
                    return false;
                }
            } else if (destY == this.getCol()
                    && board.occupied(destX, this.getCol())) {
                //Check if pieces are opponents
                return this.isWhite() //Capture
                        != board.getPieceAt(destX, this.getCol()).isWhite();
                //Check if pieces are on the same team
            }
            return true;
        }
        /*If we get here, it means that the either the X and Y of the destination
        are the same as the current position, there is a piece in the way of the
        destination, or there is a friendly piece at the destination.       */
        return false;
    }
}
