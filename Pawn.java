package chess;

public class Pawn extends Piece {

    /**
     * Constructor.
     *
     * @param white Boolean that represents which team the piece is on.
     * @param type Char to indicate the type.
     * @param row Row number of the piece.
     * @param col Column number of the piece.
     */
    public Pawn(boolean white, char type, int row, int col) {
        super(white, type, row, col);
        /*For special board initialization - if the piece is not being placed in
        its default position, then assume it has moved at least once       */
        if ((white && row != 6) || (!white && row != 1)) {
            hasMoved = true;
            numMoves++;
        }
    }

    /**
     * Copy constructor - creates a deep copy of a Piece passed to it.
     *
     * @param p The piece being copied.
     */
    public Pawn(Piece p) {
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
        //Check if move is two spaces forward - must be first move
        if (!hasMoved && Math.abs(numXMovement) == 2 && numYMovement == 0
                && board.occupied(destX, destY) == false) {
            /*Check that space immediately in front is not occupied, and that 
            piece is moving in the correct direction (North for white pieces, 
            South for black)                                                */
            if (this.isWhite() && numXMovement == 2
                    && board.occupied(destX + 1, destY) == false) {
                return true;
            } else if (!this.isWhite() && numXMovement == -2
                    && board.occupied(destX - 1, destY) == false) {
                return true;
            }
        } //Checking if move is one space forward
        else if (board.occupied(destX, destY) == false && Math.abs(numXMovement) == 1
                && numYMovement == 0) {
            //Check that piece is being moved in correct direction
            if (this.isWhite() && numXMovement == 1 || !this.isWhite()
                    && numXMovement == -1) {
                return true;
            }
        } //Checking if move is capturing an enemy piece
        //First check that piece is moving diagonally forward, and that there is a piece
        //at the destination.
        else if (Math.abs(numXMovement) == 1 && Math.abs(numYMovement) == 1
                && board.occupied(destX, destY) == true) {
            //Check that piece at destination is black if this piece is white
            if (this.isWhite() && !board.getPieceAt(destX, destY).isWhite()) {
                //Check that piece is moving in the correct direction
                if (numXMovement == 1) {
                    return true;
                }
            } //Check that piece at destination is white if this piece is black
            else if (!this.isWhite() && board.getPieceAt(destX, destY).isWhite()) {
                //Check that piece is moving in the correct direction
                if (numXMovement == -1) {
                    return true;
                }
            }
        } /*En Passant check - Check that move is diagonally forward, that there is
        an enemy piece on the same row as this piece that has only moved once,
        and that the enemy piece has moved two spaces.
         */ else if (Math.abs(numXMovement) == 1 && Math.abs(numYMovement) == 1
                && board.occupied(this.getRow(), destY) == true) {
            if (this.isWhite() && this.getRow() == 3
                    && !board.getPieceAt(this.getRow(), destY).isWhite()
                    && board.getPieceAt(this.getRow(), destY).getType() == 'P') {
                if (numXMovement == 1
                        && board.getPieceAt(this.getRow(), destY).getNumMoves()
                        / 2 == 1) {
                    return true;
                }
            } else if (!this.isWhite() && this.getRow() == 4
                    && board.getPieceAt(this.getRow(), destY).isWhite()
                    && board.getPieceAt(this.getRow(), destY).getType() == 'p') {
                if (numXMovement == -1
                        && board.getPieceAt(this.getRow(), destY).getNumMoves()
                        / 2 == 1) {
                    return true;
                }
            }
        }
        return false;
    }

}
