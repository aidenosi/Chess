package chess;

import java.util.ArrayList;

/* Class representing the board. Contains both players and the last move 
performed on the board.                                                      */
public class Board {
    
    Player whitePlayer;
    Player blackPlayer;
    Move lastMove; //To keep track of the last move applied to the board.

    /**
     * Constructor. Calls the initBoard() method to initialize the board.
     */
    public Board() {
        whitePlayer = new Player(true);
        blackPlayer = new Player(false);
        lastMove = null;
        initBoard();
    }

    /**
     * Copy constructor.
     *
     * @param b Board object to be copied.
     */
    public Board(Board b) {
        this.whitePlayer = new Player(b.getPlayer(true));
        this.blackPlayer = new Player(b.getPlayer(false));
    }

    /**
     * Returns the object at the specified location.
     *
     * @param row Row number of the location.
     * @param col Column number of the location.
     * @return Piece object at the given location.
     */
    public Piece getPieceAt(int row, int col) {
        //Check if whitePlayer player has a piece at the location
        Piece p = whitePlayer.getPieceAt(row, col);
        if (p == null) { //If whitePlayer player doesn't have a piece at the location
            //Check if blackPlayer player has a piece at the location
            p = blackPlayer.getPieceAt(row, col);
            return p;
        }
        return p;
    }

    /**
     * Returns a player, depending on the value of the parameter. True returns
     * the whitePlayer player, false returns the blackPlayer player.
     *
     * @param white Boolean indicating which player to return.
     * @return
     */
    public Player getPlayer(boolean white) {
        return (white ? whitePlayer : blackPlayer);
    }

    /**
     * Checks if a space on the board is occupied by a piece.
     *
     * @param row Row number of the location.
     * @param col Column number of the location.
     * @return Boolean which indicates whether the space is occupied.
     */
    public Boolean occupied(int row, int col) {
        Piece p = whitePlayer.getPieceAt(row, col);
        if (p == null) { //If there is not a white piece at the location
            p = blackPlayer.getPieceAt(row, col);
            //If there is neither a white nor black piece at the location
            if (p == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method for capturing a piece.
     *
     * @param p Piece being captured.
     */
    public void capturePiece(Piece p) {
        if (p.isWhite()) { //Remove from respective list
            whitePlayer.removePiece(p);
        } else {
            blackPlayer.removePiece(p);
        }
    }

    /**
     * Method that handles the movement (and capture) of pieces. This method
     * does not check for validity of a move, it is assumed that this has
     * already been assured.
     *
     * @param nextMove Move object containing the piece being moved, and the
     * coordinates of the destination.
     */
    public void move(Move nextMove) {
        if (this.lastMove == null) {
            this.lastMove = new Move(nextMove);
        }
        Piece p = getPieceAt(nextMove.getRow(), nextMove.getCol());
        int destX = nextMove.getDestRow();
        int destY = nextMove.getDestCol();
        //Check for En Passant
        if ((p.getType() == 'p' || p.getType() == 'P') && occupied(p.getRow(), destY)
                && p.getCol() != destY) {
            if (p.isWhite() != getPieceAt(p.getRow(), destY).isWhite()) {
                capturePiece(getPieceAt(p.getRow(), destY));
                p.setPosition(destX, destY); //Set p's new position
            }
        } //Check for castling
        else if ((p.getType() == 'r' || p.getType() == 'R') && !p.hasMoved
                && occupied(destX, destY) && destX == p.getRow()) {
            if ((getPieceAt(destX, destY).getType() == 'k'
                    || getPieceAt(destX, destY).getType() == 'K')
                    && !getPieceAt(destX, destY).hasMoved) {
                //Castling with West rook
                if (p.getCol() < getPieceAt(destX, destY).getCol()) {
                    p.setPosition(destX, destY - 1); //Set p's new position
                    //Call move on the King
                    move(new Move(getPieceAt(destX, destY), destX, destY - 2));
                } //Castling with East rook
                else if (p.getCol() > getPieceAt(destX, destY).getCol()) {
                    p.setPosition(destX, destY + 1); //Set p's new position
                    //Call move on the King
                    move(new Move(getPieceAt(destX, destY), destX, destY + 2));
                }
            }
        } //Check for capture 
        if (occupied(destX, destY)) {
            capturePiece(getPieceAt(destX, destY));
            p.setPosition(destX, destY); //Set p's new position  
        } else {
            p.setPosition(destX, destY); //Set p's new position 
        }
        lastMove = nextMove;
    }

    /**
     * Tests a move to see if it is safe, ie will not put the player in check,
     * or will move them out of check.
     *
     * @param p The piece being moved.
     * @param destX X coordinate of the destination.
     * @param destY Y coordinate of the destination.
     * @return Boolean indicating whether the move will put the player in check.
     */
    public boolean safeMove(Piece p, int destX, int destY) {
        Move m = new Move(p, destX, destY);
        Board testBoard = new Board(this);
        testBoard.move(m); //Process the move on a test board
        //Then check if it puts the king in check
        if (testBoard.getPlayer(p.isWhite()).inCheck(
                (p.isWhite() ? testBoard.getPlayer(false).getPiecesList()
                        : testBoard.getPlayer(true).getPiecesList()), testBoard)) {
            return false; //This move will put the player in check
        }
        return true; //This move will not put the player in check
    }

    /**
     * Checks if a checkmate has occurred.
     *
     * @param whitePlayer Boolean indicating which player to check.
     * @return Boolean indicating whether a checkmate has occurred.
     */
    public boolean checkmate(boolean whitePlayer) {
        //If there are no next possible boards, then checkmate has occured.
        return getNextBoards(whitePlayer).isEmpty();
    }

    /**
     * Method that returns all next possible boards for a player from a given
     * board.
     *
     * @param white The player to find all valid moves for.
     * @return ArrayList<Board>
     */
    public ArrayList<Board> getNextBoards(boolean white) {
        ArrayList<Board> nextBoards = new ArrayList<>();
        for (Piece p : getPlayer(white).getPiecesList()) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    //Check that move is valid and safe
                    if (p.isValid(x, y, this)) {
                        if (safeMove(p, x, y)) {
                            Board b = new Board(this); //Make a copy of the current board
                            //Apply the move
                            b.move(new Move(b.getPieceAt(p.getRow(), p.getCol()), x, y));
                            nextBoards.add(b);
                        }
                    }
                }
            }
        }
        return nextBoards;
    }

    /**
     * Promotes a pawn when necessary. Prompts user to select piece they wish to
     * promote the pawn to. AI player is randomly given a piece.
     *
     * @param p The pawn being promoted.
     * @return The new piece.
     */
    public Piece piecePromotion(Piece p) {
        if (p.isWhite()) { //If white pawn is being promoted, prompt user for input
            String read = " ";
            System.out.println("Pawn was Promoted! Select New Piece Type: R, H, B,or Q. ");
            while (read.equals(" ")) {
                System.out.print("Choice: ");
                try {
                    read = Main.scanner.nextLine();
                } catch (Exception e) {
                    read = " ";
                }
                if (read.equalsIgnoreCase("R") || read.equalsIgnoreCase("H")
                        || read.equalsIgnoreCase("B") || read.equalsIgnoreCase("Q")) {
                    switch (read.charAt(0)) {
                        case 'R':
                            return new Rook(p.isWhite(), 'r', p.getRow(), p.getCol());
                        case 'H':
                            return new Knight(p.isWhite(), 'h', p.getRow(), p.getCol());
                        case 'B':
                            return new Bishop(p.isWhite(), 'b', p.getRow(), p.getCol());
                        case 'Q':
                            return new Queen(p.isWhite(), 'q', p.getRow(), p.getCol());
                        default:
                            break;
                    }
                } else {
                    System.out.println("Error: Invalid Choice!");
                    read = " ";
                }
            }
        } else {
            //Get random number between 0 and 3
            int choice = (int) (Math.random() * ((3) + 1));
            switch (choice) {
                case 0:
                    return new Rook(p.isWhite(), 'r', p.getRow(), p.getCol());
                case 1:
                    return new Knight(p.isWhite(), 'h', p.getRow(), p.getCol());
                case 2:
                    return new Bishop(p.isWhite(), 'b', p.getRow(), p.getCol());
                case 3:
                    return new Queen(p.isWhite(), 'q', p.getRow(), p.getCol());
                default:
                    break;
            }
        }
        return new Queen(p.isWhite(), (p.isWhite() ? 'q' : 'Q'), p.getRow(), p.getCol());
    }

    /**
     * Board evaluation function.
     *
     * @param white Boolean indicating which player the board is to be evaluated
     * for.
     * @return Fitness of the board.
     */
    public int boardEval(boolean white) {
        int fitness = 0;
        for (Piece p : whitePlayer.getPiecesList()) {
            fitness += BoardEval.eval(p);
        }
        for (Piece p : blackPlayer.getPiecesList()) {
            fitness += BoardEval.eval(p);
        }
        if (!white) { //Get negative for black player
            fitness *= -1;
        }
        return fitness;
    }

    /**
     * Initializes board to default state.
     */
    public void initBoard() {
        for (int i = 0; i < 8; i++) {
            whitePlayer.addPiece(new Pawn(true, 'p', 6, i));
            blackPlayer.addPiece(new Pawn(false, 'P', 1, i));
        }
        int x, y;
        x = 7;
        y = 0;
        whitePlayer.addPiece(new Rook(true, 'r', x, y)); //White rooks
        y = 7;
        whitePlayer.addPiece(new Rook(true, 'r', x, y));
        y = 1;
        whitePlayer.addPiece(new Knight(true, 'h', x, y)); //White knights
        y = 6;
        whitePlayer.addPiece(new Knight(true, 'h', x, y));
        y = 2;
        whitePlayer.addPiece(new Bishop(true, 'b', x, y)); //White bishops
        y = 5;
        whitePlayer.addPiece(new Bishop(true, 'b', x, y));
        y = 3;
        whitePlayer.addPiece(new Queen(true, 'q', x, y)); //White queen
        y = 4;
        whitePlayer.addPiece(new King(true, 'k', x, y)); //White king

        x = 0;
        y = 0;
        blackPlayer.addPiece(new Rook(false, 'R', x, y)); //Black rooks
        y = 7;
        blackPlayer.addPiece(new Rook(false, 'R', x, y));
        y = 1;
        blackPlayer.addPiece(new Knight(false, 'H', x, y)); //Black knights
        y = 6;
        blackPlayer.addPiece(new Knight(false, 'H', x, y));
        y = 2;
        blackPlayer.addPiece(new Bishop(false, 'B', x, y)); //Black bishops
        y = 5;
        blackPlayer.addPiece(new Bishop(false, 'B', x, y));
        y = 3;
        blackPlayer.addPiece(new Queen(false, 'Q', x, y)); //Black queen
        y = 4;
        blackPlayer.addPiece(new King(false, 'K', x, y)); //Black king
    }

    /**
     * Prints current board to console.
     */
    public void printBoard() {
        System.out.println("Printing Board: ");
        System.out.println("     0   1   2   3   4   5   6   7 ");
        System.out.print("------------------------------------\n");
        
        for (int i = 0; i < 8; i++) { 	// COL
            System.out.print(i + "  ");
            for (int j = 0; j < 8; j++) {		// ROW
                if (occupied(i, j)) {
                    System.out.print("| " + (getPieceAt(i, j)).getType() + " ");
                } else {
                    System.out.print("|   ");
                }
            }
            System.out.print("|\n------------------------------------\n");
        }
    }
}
