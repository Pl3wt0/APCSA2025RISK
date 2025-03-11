package Files;

public class TicTacToe extends BoardGame implements Playable{
    public TicTacToe(){
        super(3);
    }

    public boolean placePiece(int row,int col, char player){
        if(board[row][col] == '-'){
            if(player == 'B'){
                board[row][col] = 'B';
                return true;
            } else if(player == 'W'){
                board[row][col] = 'W';
                return true;
            }
        }
            return false;
        
    }

    public boolean checkWin(char player){
        boolean win = false;
        for(int i = 0;i<board.length;i++){
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] == board[i][2]){
                if(player == board[i][0]){
                    return true;
                }
                
            }
        }
        for(int i = 0; i<board[0].length;i++){
            if(board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] == board[2][i]){
                if(player == board[0][i]){
                    return true;
                }
            }
        }
        if(board[0][0] == board[1][1]&&board[1][1]==board[2][2]&&board[0][0]==board[1][1]){
            if(player == board[0][0]){
                return true;
            }
        
        
        }
        return false;
        
    }

    public void startGame(){
        
    }
    
}
