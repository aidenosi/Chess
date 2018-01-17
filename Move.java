package chess;

/* Class representing a move. Contains the coordinates of the piece being moved,
the piece's type, and the coordinates of the destination.                   */
public class Move {

    private final char type;
    private final int row;
    private final int col;
    private final int destRow;
    private final int destCol;

    /**
     * Constructor.
     *
     * @param piece The piece being moved.
     * @param row The destination row.
     * @param col The destination column.
     */
    public Move(Piece piece, int row, int col) {
        this.type = piece.getType();
        this.row = piece.getRow();
        this.col = piece.getCol();
        this.destRow = row;
        this.destCol = col;
    }

    /**
     * Copy constructor.
     *
     * @param m The move being copied.
     */
    public Move(Move m) {
        this.type = m.getType();
        this.row = m.getRow();
        this.col = m.getCol();
        this.destRow = m.getDestRow();
        this.destCol = m.getDestCol();
    }

    /**
     * Returns piece's type.
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
     * Returns the column coordinate of the piece.
     *
     * @return
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Returns the x coordinate of the destination.
     *
     * @return
     */
    public int getDestRow() {
        return this.destRow;
    }

    /**
     * Returns the y coordinate of the destination.
     *
     * @return
     */
    public int getDestCol() {
        return this.destCol;
    }

    /**
     * Returns a string containing the piece being moved, and the destination
     * coordinates.
     *
     * @return
     */
    @Override
    public String toString() {
        String s = "";
        s += (Character.isLowerCase(type) ? "White " : "Black ");
        s += Character.toString(type);
        s += " at " + row + ", " + col;
        s += "\t\t" + destRow + ", " + destCol;
        return s;
    }

}
