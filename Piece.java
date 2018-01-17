package chess;

/* Abstract class representing a piece. Extending classes need only implement the
isValid() method.                                                           */
public abstract class Piece {

    boolean white;
    char type;
    int row;
    int col;
    boolean hasMoved;
    int numMoves;

    /**
     * Constructor for a Piece object.
     *
     * @param white Boolean that represents which team the piece is on.
     * @param type Char to indicate the type.
     * @param row Row number of the piece.
     * @param col Column number of the piece.
     */
    public Piece(boolean white, char type, int row, int col) {
        this.white = white;
        this.type = type;
        this.row = row;
        this.col = col;
        hasMoved = false;
        numMoves = 0;
    }

    /**
     * Copy constructor - creates a deep copy of a Piece passed to it.
     *
     * @param p The piece being copied.
     */
    public Piece(Piece p) {
        this.white = p.isWhite();
        this.type = p.getType();
        this.row = p.getRow();
        this.col = p.getCol();
        this.hasMoved = p.hasMoved;
        this.numMoves = p.getNumMoves();
    }

    /**
     * Checks if a move is valid.
     *
     * @param destX The Y coordinate of the destination.
     * @param destY The X coordinate of the destination.
     * @param board The board that the piece is on.
     * @return Boolean representing whether the move is valid.
     */
    public abstract Boolean isValid(int destX, int destY, Board board);

    /**
     * Returns boolean which represents which team a piece is on.
     *
     * @return
     */
    public boolean isWhite() {
        return this.white;
    }

    /**
     * Returns type char.
     *
     * @return
     */
    public char getType() {
        return this.type;
    }

    /**
     * Returns row coordinate of the piece.
     *
     * @return
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Returns column coordinate of the piece.
     *
     * @return
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Returns the number of moves this pawn has made (used for En Passant).
     *
     * @return
     */
    public int getNumMoves() {
        return this.numMoves;
    }

    /**
     * Returns this piece.
     *
     * @return
     */
    public Piece getPiece() {
        return this;
    }

    /**
     * Sets the position of the piece.
     *
     * @param row New row coordinate.
     * @param col New column coordinate.
     */
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
        hasMoved = true;
        numMoves++;
    }

    /**
     * Returns a string containing the team, type, and coordinates of a piece.
     *
     * @return
     */
    public String toString() {
        String s = "";
        s += (this.isWhite() ? "White " : "Black ");
        s += this.type + " at ";
        s += this.row + ", " + this.col;
        return s;
    }

}
