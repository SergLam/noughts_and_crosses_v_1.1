import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

public class Main {

    public JButton[] grid = new JButton[9];
    public ImageIcon xIcon, oIcon, frame_icon;

    public int[] board = new int[9];
    public boolean won  = false;

    public int currentPlayer = 1;


    public void play(int id){
        if(attepmtChange(id)){
           currentPlayer =(currentPlayer ==1) ? 2 : 1;
            checkWin();
        }
    }

    public boolean attepmtChange(int square){
        if(board[square]!=0){
           return false;
        }
        board[square]=currentPlayer;
        grid[square].setIcon(currentPlayer == 1 ? xIcon : oIcon);
        return true;
    }

    public void checkWin(){
        // Check horizontal lines
        if((board[0]==board[1]) && (board[1]==board[2]) && board[2]!=0){
           win(0);
        }
        if((board[3]==board[4]) && (board[4]==board[5]) && board[5]!=0){
            win(5);
        }
        if((board[6]==board[7]) && (board[7]==board[8]) && board[8]!=0){
            win(8);
        }
        // Check vertical lines
        if((board[0]==board[3]) && (board[3]==board[6]) && board[6]!=0){
            win(6);
        }
        if((board[1]==board[4]) && (board[4]==board[7]) && board[7]!=0){
            win(7);
        }
        if((board[2]==board[5]) && (board[5]==board[8]) && board[8]!=0){
            win(8);
        }
        // Check cross lines
        if((board[0]==board[4]) && (board[4]==board[8]) && board[8]!=0){
            win(8);
        }
        if((board[6]==board[4]) && (board[4]==board[2]) && board[2]!=0){
            win(2);
        }
        // Check draw condition
        for(int i =0;i<board.length;i++){
            if(board[i]==0){
                return;
            }
        }
        if (!won){
            draw();
        }
    }

    public void win(int square){
       won = true;
        String winner = board[square]==1 ? "X" : "O";
        int pane = JOptionPane.showConfirmDialog(null,"Player "+winner+" has won! Would you like to play again?","Victory!",JOptionPane.YES_NO_OPTION);
        if(pane==JOptionPane.YES_OPTION){
          restart();
        }
        else
            System.exit(0);
    }

    public void  restart(){
        won = false;
        currentPlayer=1;
        for(int i =0;i<9;i++){
            board[i] = 0;
            grid[i].setIcon(null);
        }
    }

    public void draw(){
        int pane = JOptionPane.showConfirmDialog(null,"It's a draw! Would you like to play again?","Draw!",JOptionPane.YES_NO_OPTION);
        if(pane==JOptionPane.YES_OPTION){
            restart();
        }
        else
            System.exit(0);
    }

    public void init_components(){
       JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setSize(450,450);
        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        frame.setTitle("noughts_and_crosses");
        panel.setLayout(new GridLayout(3, 3, 0, 0));
        panel.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                init_icons();
            }

            public void componentMoved(ComponentEvent e) {

            }

            public void componentShown(ComponentEvent e) {

            }

            public void componentHidden(ComponentEvent e) {

            }
        });

        for(int i=0;i<9;i++){
            final  int pos=i;
            grid[i] = new JButton();
            grid[i].addActionListener(new ActionListener() {
                int id = pos;
                public void actionPerformed(ActionEvent e) {
                    play(id);
                }
            });
            panel.add(grid[i]);
        }
        try {
            Image ff = ImageIO.read(Main.class.getResource("For_title.jpg"));
            frame_icon = new ImageIcon(ff);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        frame.setIconImage(frame_icon.getImage());
        frame.setSize(450,450);
    }

    public void init_icons(){
     try{
         Image x = ImageIO.read(Main.class.getResource("X.jpg"));
         Image o = ImageIO.read(Main.class.getResource("O.jpg"));

         xIcon = new ImageIcon(x.getScaledInstance(grid[0].getWidth(),grid[0].getHeight(),Image.SCALE_SMOOTH));
         oIcon = new ImageIcon(o.getScaledInstance(grid[0].getWidth(),grid[0].getHeight(),Image.SCALE_SMOOTH));

         for(int i=0;i<9;i++){
            if(grid[i].getIcon()!=null){
               grid[i].setIcon(board[i]==1 ? xIcon : oIcon);
            }
         }
     }
     catch (IOException e) {
         e.printStackTrace();
     }


    }

    public static void main(String[] args) {
       Main m = new Main();
        m.init_components();
        m.init_icons();
    }
}
