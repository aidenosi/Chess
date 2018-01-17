package chess;

/* Class containing arrays which have modifying values for each piece, depending 
on the piece's location on the board. Inspiration taken from - 
https://medium.freecodecamp.org/simple-chess-ai-step-by-step-1d55a9266977 */
public class BoardEval {

    static int pawnValue = 10;
    static int[][] pawn = {{0, 0, 0, 0, 0, 0, 0, 0},
    {50, 50, 50, 50, 50, 50, 50, 50},
    {10, 10, 20, 30, 30, 20, 10, 10},
    {5, 5, 10, 25, 25, 10, 5, 5},
    {0, 0, 0, 20, 20, 0, 0, 0},
    {5, -5, -10, 0, 0, -10, -5, 5},
    {5, 10, 10, -20, -20, 10, 10, 5},
    {0, 0, 0, 0, 0, 0, 0, 0}};

    static int rookValue = 50;
    static int[][] rook = {{0, 0, 0, 0, 0, 0, 0, 0},
    {5, 10, 10, 10, 10, 10, 10, 5},
    {-5, 0, 0, 0, 0, 0, 0, -5},
    {-5, 0, 0, 0, 0, 0, 0, -5},
    {-5, 0, 0, 0, 0, 0, 0, -5},
    {-5, 0, 0, 0, 0, 0, 0, -5},
    {-5, 0, 0, 0, 0, 0, 0, -5},
    {0, 0, 0, 5, 5, 0, 0, 0}};

    static int bishopValue = 30;
    static int[][] bishop = {{-20, -10, -10, -10, -10, -10, -10, -20},
    {-10, 0, 0, 0, 0, 0, 0, -10},
    {-10, 0, 5, 10, 10, 5, 0, -10},
    {-10, 5, 5, 10, 10, 5, 5, -10},
    {-10, 0, 10, 10, 10, 10, 0, -10},
    {-10, 10, 10, 10, 10, 10, 10, -10},
    {-10, 5, 0, 0, 0, 0, 5, -10},
    {-20, -10, -10, -10, -10, -10, -10, -20}};

    static int knightValue = 30;
    static int[][] knight = {{-50, -40, -30, -30, -30, -30, -40, -50},
    {-40, -20, 0, 0, 0, 0, -20, -40},
    {-30, 0, 10, 15, 15, 10, 0, -30},
    {-30, 5, 15, 20, 20, 15, 5, -30},
    {-30, 0, 15, 20, 20, 15, 0, -30},
    {-30, 5, 10, 15, 15, 10, 5, -30},
    {-40, -20, 0, 5, 5, 0, -20, -40},
    {-50, -40, -30, -30, -30, -30, -40, -50}};

    static int queenValue = 90;
    static int[][] queen = {{-20, -10, -10, -5, -5, -10, -10, -20},
    {-10, 0, 0, 0, 0, 0, 0, -10},
    {-10, 0, 5, 5, 5, 5, 0, -10},
    {-5, 0, 5, 5, 5, 5, 0, -5},
    {0, 0, 5, 5, 5, 5, 0, -5},
    {-10, 5, 5, 5, 5, 5, 0, -1},
    {-10, 0, 5, 0, 0, 0, 0, -10},
    {-20, -10, -10, -5, -5, -10, -10, -20}};

    static int kingValue = 900;
    static int[][] king = {{-30, -40, -40, -50, -50, -40, -40, -30},
    {-30, -40, -40, -50, -50, -40, -40, -30},
    {-30, -40, -40, -50, -50, -40, -40, -30},
    {-30, -40, -40, -50, -50, -40, -40, -30},
    {-20, -30, -30, -40, -40, -30, -30, -20},
    {-10, -20, -20, -20, -20, -20, -20, -10},
    {20, 20, 0, 0, 0, 0, 20, 20},
    {20, 30, 10, 0, 0, 10, 30, 20}};

    /**
     * Evaluate using the board evaluation function, which takes static into
     * account the piece's type and coordinate.
     *
     * @param p The piece to be evaluated.
     * @return The value of the piece in regards to the board evaluation
     * function.
     */
    public static int eval(Piece p) {
        int E = 0;
        switch (p.getType()) {
            case 'p': //White pawn
                E += pawnValue + pawn[p.getRow()][p.getCol()];
                break;
            case 'r': //White rook
                E += rookValue + rook[p.getRow()][p.getCol()];
                break;
            case 'b': //White bishop
                E += bishopValue + bishop[p.getRow()][p.getCol()];
                break;
            case 'h': //White knight
                E += knightValue + knight[p.getRow()][p.getCol()];
                break;
            case 'q': //White queen
                E += queenValue + queen[p.getRow()][p.getCol()];
                break;
            case 'k': //White king
                E += kingValue + king[p.getRow()][p.getCol()];
                break;
            case 'P': //Black pawn
                E += -pawnValue + pawn[7 - p.getRow()][7 - p.getCol()];
                break;
            case 'R': //Black rook
                E += -rookValue + rook[7 - p.getRow()][7 - p.getCol()];
                break;
            case 'B': //Black bishop
                E += -bishopValue + bishop[7 - p.getRow()][7 - p.getCol()];
                break;
            case 'H': //Black knight
                E += -knightValue + knight[7 - p.getRow()][7 - p.getCol()];
                break;
            case 'Q': //Black queen
                E += -queenValue + queen[7 - p.getRow()][7 - p.getCol()];
                break;
            case 'K': //Black king
                E += -kingValue + king[7 - p.getRow()][7 - p.getCol()];
                break;
        }
        return E;
    }
}
