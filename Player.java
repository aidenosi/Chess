package chess;

import java.util.ArrayList;

/* Class representing a player. Players manage their own list of pieces and list
of captured pieces, however any changes made to these lists are overseen by the 
board.                                                                      */
public class Player {

    private boolean white;
    private ArrayList<Piece> pieces;
    public ArrayList<Piece> capturedPieces;
    private Piece king;

    /**
     * Constructor.
     *
     * @param white Boolean indicating which colour this player is.
     */
    public Player(boolean white) {
        this.white = white;
        pieces = new ArrayList<>();
        capturedPieces = new ArrayList<>();
        king = null;
    }

    /**
     * Copy constructor - creates a deep copy of a player.
     *
     * @param p Player to create a deep copy of.
     */
    public Player(Player p) {
        this.white = p.isWhite();
        this.pieces = new ArrayList<>();
        this.capturedPieces = new ArrayList<>();
        for (Piece q : p.getPiecesList()) {
            switch (q.getType()) {
                case 'p':
                case 'P':
                    this.pieces.add(new Pawn(q));
                    break;
                case 'r':
                case 'R':
                    this.pieces.add(new Rook(q));
                    break;
                case 'h':
                case 'H':
                    this.pieces.add(new Knight(q));
                    break;
                case 'b':
                case 'B':
                    this.pieces.add(new Bishop(q));
                    break;
                case 'q':
                case 'Q':
                    this.pieces.add(new Queen(q));
                    break;
                case 'k':
                case 'K':
                    this.pieces.add(new King(q));
                    break;
                default:
                    break;
            }
        }
        for (Piece q : p.getCapturedPiecesList()) {
            switch (q.getType()) {
                case 'p':
                case 'P':
                    this.capturedPieces.add(new Pawn(q));
                    break;
                case 'r':
                case 'R':
                    this.capturedPieces.add(new Rook(q));
                    break;
                case 'h':
                case 'H':
                    this.capturedPieces.add(new Knight(q));
                    break;
                case 'b':
                case 'B':
                    this.capturedPieces.add(new Bishop(q));
                    break;
                case 'q':
                case 'Q':
                    this.capturedPieces.add(new Queen(q));
                    break;
                case 'k':
                case 'K':
                    this.capturedPieces.add(new King(q));
                    break;
                default:
                    break;
            }
        }
        this.king = new King(p.getKing());
    }

    /**
     * Returns a boolean representing which colour this player is.
     *
     * @return
     */
    public boolean isWhite() {
        return this.white;
    }

    /**
     * Adds a piece to this player's list of pieces.
     *
     * @param p Piece to be added.
     */
    public void addPiece(Piece p) {
        pieces.add(p);
    }

    /**
     * Removes a piece from this player's list of pieces.
     *
     * @param p Piece to be removed.
     */
    public void removePiece(Piece p) {
        pieces.remove(p);
        capturedPieces.add(p);
    }

    /**
     * Searches this player's list for a piece at the given coordinates.
     *
     * @param row
     * @param col
     * @return Piece at the coordinates (or null).
     */
    public Piece getPieceAt(int row, int col) {
        for (Piece p : this.pieces) {
            if (p.getRow() == row && p.getCol() == col) {
                return p;
            }
        }
        return null;
    }

    /**
     * Returns this player's king.
     *
     * @return
     */
    public Piece getKing() {
        //Find the king in this player's list of pieces
        for (Piece p : this.pieces) {
            if (p.getType() == (this.white ? 'k' : 'K')) {
                king = p; //Point king to p
                break;
            }
        }
        return king;
    }

    /**
     * Returns this player's list of pieces.
     *
     * @return
     */
    public ArrayList<Piece> getPiecesList() {
        return this.pieces;
    }

    /**
     * Returns this player's list of captures pieces.
     *
     * @return
     */
    public ArrayList<Piece> getCapturedPiecesList() {
        return this.capturedPieces;
    }

    /**
     * Performs an analysis to determine whether this player is in check.
     *
     * @param otherPlayerPieces Other player's list of pieces.
     * @param board The board that the player is playing on.
     * @return True if player is in check, false otherwise.
     */
    public boolean inCheck(ArrayList<Piece> otherPlayerPieces, Board board) {
        for (Piece p : otherPlayerPieces) {
            //Check if any of the opponents pieces could capture this player's king
            if (p.isValid(getKing().getRow(), getKing().getCol(), board)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Find and return the player's next move using the minimax algorithm.
     *
     * @return
     */
    public Move nextMove() {
        Move nextMove = Minimax.getNextMove(Main.board, this);
        return nextMove;
    }

}
