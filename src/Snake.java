import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;


public class Snake {

    public static final char UP = 'w';
    public static final char DOWN = 's';
    public static final char LEFT = 'a';
    public static final char RIGHT = 'd';

    public static char input ='w';

    public static int size =10;
    public static char board[][]= new char [size][size] ;

    public static int framesPerSecond=1;
    public static long nextFrameTime = System.currentTimeMillis();
    public  static long timeBetweenFrames = 1000/framesPerSecond;

    public static int length =2;

    public static int headi=size/2;
    public static int headj = size/2;

    public static int taili;
    public static int tailj;

    public static int applei;
    public static int applej;

    public static void main(String args[]) throws IOException, InterruptedException {

        //using jframe to get the inputs, I wanted to see if this would work or not
        //didn't want to use some other library
        JFrame t = new JFrame();
        t.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if(c!= input)
                    switch(c){
                        case UP: input = UP;break;
                        case DOWN:input = DOWN;break;
                        case LEFT:input = LEFT;break;
                        case RIGHT:input =RIGHT; break;
                        default:break;
                    }
                //System.out.println("got input");
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        t.setFocusableWindowState(true);
        t.setSize(0,0);
        t.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
        t.setVisible(true);

        System.out.println(t.isFocusable());

       board[headi+1][headj]=UP;
       taili=headi+1;
       tailj = headj;


        create();
        while(true) {
            nextFrameTime = System.currentTimeMillis() + timeBetweenFrames;
            moveHead(input);
            print();

            Thread.sleep(nextFrameTime - System.currentTimeMillis());
        }
    }



    public static void create(){
        boolean created=false;
        for(int i =0;i<size;i++) {
            applei = (int) (Math.random() * 10);
            applej = (int) (Math.random() * 10);
            if (board[applei][applej] == 0) {
                board[applei][applej] = 1;
                created=true;
                break;
            }
            else System.out.println("collision");
        }
        if(created){
            //System.out.println("created");
            return;
        }

        for(int i =0;i<size;i++){
            for(int j =0;j<size;j++){
                applei=i;
                applej=j;
                if (board[applei][applej] == 0) {
                    board[applei][applej] = 1;
                    return;
                }
            }
        }
    }

    public static void moveHead(char input){
        //tries to move the head, if it is out of bounds it ends the game
        try{

            switch (input){
                case UP:board[headi][headj]=UP;
                        if(board[--headi][headj] !=0 &&board[headi][headj]!=1) gameOver();
                        board[headi][headj]=UP;
                        break;


                case DOWN:board[headi][headj]=DOWN;
                        if(board[++headi][headj] !=0&&board[headi][headj]!=1) gameOver();
                        board[headi][headj]= DOWN;
                        break;


                case LEFT:board[headi][headj]=LEFT;
                        if(board[headi][--headj] !=0&&board[headi][headj]!=1) gameOver();
                        board[headi][headj]=LEFT;
                        break;


                case RIGHT:board[headi][headj]=RIGHT;
                        if(board[headi][++headj] !=0&&board[headi][headj]!=1)gameOver();
                        board[headi][headj]=RIGHT;
                        break;
                default:
            }
            if(headi == applei&&headj==applej){
                length++;
                System.out.println(length);
                create();
            }
            else{
                moveBody(input);
            }

        }
        catch (ArrayIndexOutOfBoundsException e){
            //e.printStackTrace();
            gameOver();
        }

    }

    public static void moveBody(char input){

        switch(board[taili][tailj]){
            case UP:board[taili--][tailj]=0;break;
            case DOWN:board[taili++][tailj]=0;break;
            case LEFT:board[taili][tailj--]=0;break;
            case RIGHT:board[taili][tailj++]=0;break;
            default:
        }


    }

    public static void print(){
        System.out.println("\n \n \n");
//        System.out.println("apple "+applei+","+applej);
//        System.out.println("head "+headi+","+headj);
//        System.out.println(length);
        int spaceBetweenNumbers=4;
        for(int i = 0;i<size;i++){
            for(int j  = 0;j<size;j++){

                if(board[i][j]=='\u0000') System.out.print(".");
                else System.out.print(board[i][j]);

                for(int k =0;k<spaceBetweenNumbers-(board[i][j]+"").length();k++){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public static void gameOver(){
        System.out.println("Score: "+length);
        System.out.println("game over");
        System.exit(0);
    }

}
