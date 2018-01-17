package chess;
import java.util.ArrayList;

/* Class containing the minimax algorithm and other methods necessary for the 
algorithm.                                                                  */
public class Minimax {

    static Move nextMove;
    static int DEPTH;
    static int counter;

    /**
     * Sets depth of decision tree.
     *
     * @param depth
     */
    public static void setDepth(int depth) {
        Minimax.DEPTH = depth;
    }

    /**
     * Returns the next move as determined by the minimax algorithm.
     *
     * @param board The board object from the Main class.
     * @param player The player to find the next move for.
     * @return Move object containing the next move for the player.
     */
    public static Move getNextMove(Board board, Player player) {
        counter = 0;
        minimax(board, player.isWhite(), true, Integer.MIN_VALUE,
                Integer.MAX_VALUE, DEPTH);
        System.out.println("Boards evaluated: " + counter);
        return nextMove;
    }

    /**
     * Recursive minimax method (with alpha-beta pruning).
     *
     * @param board Current board.
     * @param white Boolean indicating which player we are performing the
     * minimax algorithm to determine the next move for.
     * @param maximize Boolean indicating whether we are in a maximizing or
     * minimizing iteration.
     * @param alpha Alpha value (for alpha-beta pruning).
     * @param beta Beta value (for alpha-beta pruning).
     * @param depth Current depth of the decision tree.
     */
    private static int minimax(Board board, boolean white, boolean maximize,
            int alpha, int beta, int depth) {
        counter++;
        if (depth == 0) { //Base case
            return board.boardEval(white);
        }
        int bestFitness = 0;
        int newFitness = 0;
        if (maximize) {
            bestFitness = Integer.MIN_VALUE;
            //Check all next possible boards
            for (Board nextBoard : getNextBoards(board, white)) {
                //Recursive call
                newFitness = minimax(nextBoard, white, !maximize, alpha, beta, depth - 1);
                if (newFitness > bestFitness) { //Check if we have new max
                    bestFitness = newFitness;
                    //Only set the next move if we are at the top level of the 
                    //decision tree
                    if (depth == DEPTH) {
                        nextMove = new Move(nextBoard.lastMove);
                    }
                }
                //Check if we need to change alpha value
//                alpha = Math.max(alpha, bestFitness);
//                if (beta <= alpha) { //Useless branch - don't explore
//                    return bestFitness;
//                }
            }
            return bestFitness;
        } else {
            bestFitness = Integer.MAX_VALUE;
            //Check all next possible boards
            for (Board nextBoard : getNextBoards(board, !white)) {
                //Recursive call
                newFitness = minimax(nextBoard, white, !maximize, alpha, beta, depth - 1);
                if (newFitness < bestFitness) { //Check if we have a new min
                    bestFitness = newFitness;
                }
                //Check if we need to change beta value
//                beta = Math.min(beta, bestFitness);
//                if (beta <= alpha) { //Useless branch - don't explore
//                    return bestFitness;
//                }
            }
            return bestFitness;
        }
    }

    /**
     * Method that returns an array list containing all next possible boards
     * from a given board for the given player.
     *
     * @param board The starting/root board.
     * @param white Boolean indicating which player to find the next possible
     * boards for.
     * @return
     */
    public static ArrayList<Board> getNextBoards(Board board, boolean white) {
        ArrayList<Board> nextBoards = new ArrayList<>();
        //For all pieces, for all spaces on the board: 
        for (Piece p : board.getPlayer(white).getPiecesList()) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    //Check that move is valid and safe
                    if (p.isValid(x, y, board)) {
                        Board b = new Board(board);
                        if (b.safeMove(b.getPieceAt(p.getRow(), p.getCol()), x, y)) {
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
}