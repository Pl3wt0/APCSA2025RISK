package Files;
/**
 * Abstract class representing a board game.
 * Defines a common structure for board-based games like Tic-Tac-Toe, Checkers, etc.
 */
public abstract class BoardGame {
    protected char[][] board;
    protected int size;

    /**
     * Constructor to initialize the board.
     * @param size The size of the board (e.g., 3 for Tic-Tac-Toe)
     */
    public BoardGame(int size) {
        this.size = size;
        board = new char[size][size];

        // Initialize board with empty spaces
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '-';
            }
        }
    }

    /** Abstract method to place a piece on the board */
    public abstract boolean placePiece(int row, int col, char player);

    /** Abstract method to check if a player has won */
    public abstract boolean checkWin();

    /** Concrete method to display the board */
    public void displayBoard() {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}


