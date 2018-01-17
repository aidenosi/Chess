package chess;

import java.util.*;

/*
Name: Aiden Osipenko
Student #: 5853874
 */
/**
 * *IF YOU WANT TO CHANGE WHERE THE PIECES ARE INITIALIZED TO*** Go to the
 * initBoard() method in the Board class (line 286) and change x and y for
 * whatever piece you want to move to the coordinates you want it to be placed.
 * I figured this would be easier for you than entering the coordinates for all
 * 32 pieces, one at a time (and easier for me than trying to figure out a
 * non-stupid way of doing file input).
 */
public class Main {

    //Declaring variables
    boolean whiteTurn;
    static Scanner scanner;
    static Board board;
    Player currentPlayer;

    public Main() {
        whiteTurn = true; //Initializing variables
        scanner = new Scanner(System.in);
        setMinimaxDepth();
        helpMenu();
        boolean swapTurns = true;
        Piece p;
        board = new Board();
        board.printBoard();
        for (;;) {
            p = null;
            swapTurns = true;
            //Checkmate?
            currentPlayer = board.getPlayer(whiteTurn);
            if (currentPlayer.inCheck(board.getPlayer(!whiteTurn).getPiecesList(), board)) {
                Board testBoard = new Board(board);
                if (testBoard.checkmate(whiteTurn)) {
                    break;
                }
            }

            if (whiteTurn) { //Human player
                //Reading Input for the Piece to be Moved
                while (true) {
                    System.out.println("Please enter the row and column for the"
                            + " piece you wish to move: ");
                    int row = enterRowNum();
                    int col = enterColNum();
                    try {
                        p = board.getPieceAt(row, col);
                    } catch (Exception e) {
                    }
                    if (p != null && (whiteTurn == p.isWhite())) {
                        break;
                    } else {
                        System.out.println("Error: You don't have a piece at the"
                                + " chosen location.");
                    }
                }

                OUTER_2:
                while (true) {
                    System.out.println("Please enter the row and column for the"
                            + " piece you wish to move: ");
                    int destX = enterRowNum();
                    int destY = enterColNum();
                    if (p != null) {
                        if (!p.isValid(destX, destY, board)) {
                            System.out.println("Invalid destination.");
                            if (reselectPiece()) {
                                swapTurns = false; //Don't switch players
                                break;
                            } else {
                            }
                        } else if (p.isValid(destX, destY, board)) {
                            if (board.safeMove(p, destX, destY)) {
                                Move nextMove = new Move(p, destX, destY);
                                switch (p.getType()) {
                                    case 'p':
                                    case 'P':
                                        board.move(nextMove); //Apply move
                                        //Check for piece promotion
                                        if (p.getRow() == 0) {
                                            p = board.piecePromotion(p);
                                        }
                                        break OUTER_2;
                                    case 'r':
                                    case 'R':
                                    case 'h':
                                    case 'H':
                                    case 'b':
                                    case 'B':
                                    case 'q':
                                    case 'Q':
                                    case 'k':
                                    case 'K':
                                        board.move(nextMove); //Apply move
                                        System.out.println(nextMove);
                                        break OUTER_2;
                                    default:
                                        break;
                                }
                            } else {
                                System.out.println("Invalid move: Your king will"
                                        + " be in check.");
                            }
                        } else {
                            System.out.println("Invalid move.");
                        }
                    }
                }
                if (swapTurns) { //Switch player 
                    whiteTurn = !whiteTurn;
                    board.printBoard();
                }
            } else { //AI player
                Move nextMove = currentPlayer.nextMove(); //Get next move
                board.move(nextMove); //Apply move
                p = board.getPieceAt(nextMove.getDestRow(), nextMove.getDestCol());
                if (p.getType() == 'P') { //Check for piece promotion
                    p = board.piecePromotion(p);
                }
                System.out.println(nextMove);
                board.printBoard();
                whiteTurn = !whiteTurn;
            }
        }
        System.out.println("Checkmate. "
                + (!whiteTurn ? "White " : "Black ") + "has won.");
        scanner.close();
    }

    /**
     * Method for setting depth of game tree to search for minimax algorithm.
     */
    public static void setMinimaxDepth() {
        while (true) {
            System.out.print("Enter depth of minimax search: ");
            int depth = scanner.nextInt();
            if (depth < 1) {
                System.out.println("Error: Invalid depth.");
            } else {
                Minimax.setDepth(depth);
                break;
            }
        }
    }

    /**
     * Method that asks user whether they wish to see the instructions for this
     * program.
     */
    public static void helpMenu() {
        int input = -1;
        OUTER:
        while (true) {
            System.out.print("Would you like a brief instruction of how to use "
                    + "this program? (Y = 0, N = 1): ");
            try {
                input = scanner.nextInt();
            } catch (Exception e) {
                input = -1;
            }
            switch (input) {
                case 0:
                    System.out.println("The board for this program is 0-indexed "
                            + "- the rows and columns are numbered 0-7.");
                    scanner.nextLine();
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    System.out.println("You will first be asked to select a "
                            + "piece to move by entering first the row, then the "
                            + "column coordinate of the piece.");
                    scanner.nextLine();
                    System.out.println("Then, you will be asked to enter the row "
                            + "and column coordinates of the destination to "
                            + "where you wish to move the piece. ");
                    scanner.nextLine();
                    System.out.println("The program will check for any sort of "
                            + "incorrect input, and will allow you to correct "
                            + "any input mistakes you may have made, so don't worry.");
                    scanner.nextLine();
                    break;
                case 1:
                    break OUTER;
                default:
                    System.out.println("Error: invalid selection.");
                    break;
            }
        }
    }

    /**
     * Prompts user to decide whether to select a new piece to move.
     *
     * @return Boolean indicating user's decision.
     */
    public static boolean reselectPiece() {
        int input = -1;
        while (true) {
            System.out.print("Reselect piece to move? (Y = 0, N = 1): ");
            try {
                input = scanner.nextInt();
            } catch (Exception e) {
                input = -1;
            }
            if (input == 0 || input == 1) {
                break;
            } else {
                System.out.println("Error: invalid selection.");
            }
        }
        return input == 0;
    }

    /**
     * Prompts user to enter a row number.
     *
     * @return Row number entered.
     */
    public static int enterRowNum() {
        int row = -1;
        while (true) {
            System.out.print("Row: ");
            try {
                row = Integer.valueOf(scanner.next());
                if (row >= 0 && row <= 7) {
                    return row;
                } else {
                    System.out.println("Error: Invalid row number.");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid row number.");
            }
        }
    }

    /**
     * Prompts user to enter a column number.
     *
     * @return Column number entered.
     */
    public static int enterColNum() {
        int col = -1;
        while (true) {
            System.out.print("Col: ");
            try {
                col = Integer.valueOf(scanner.next());
                if (col >= 0 && col <= 7) {
                    return col;
                } else {
                    System.out.println("Error: Invalid column number.");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid row number.");
            }
        }
    }

    public static void main(String[] args) {
        Main m = new Main();
    }

}
