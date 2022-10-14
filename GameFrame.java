import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class GameFrame extends JFrame{
    final int FRAME_WIDTH = 1000;
    final int FRAME_HEIGHT = 700;


    JButton rock_btn = new JButton();
    JButton paper_btn = new JButton();
    JButton scissors_btn = new JButton();
    JButton lizard_btn = new JButton();
    JButton spock_btn = new JButton();
    JButton playMusic_btn = new JButton();
    JButton stopMusic_btn = new JButton();

    ImageIcon sound_img = new ImageIcon("Imgs\\sound.png");
    ImageIcon mute_img = new ImageIcon("Imgs\\mute.png");
    ImageIcon rock_img = new ImageIcon("Imgs\\Rock.png");
    ImageIcon paper_img = new ImageIcon("Imgs\\paper.png");
    ImageIcon scissors_img = new ImageIcon("Imgs\\scissors.png");
    ImageIcon lizard_img = new ImageIcon("Imgs\\lizard.png");
    ImageIcon spock_img = new ImageIcon("Imgs\\spock.png");
    Image star_img = new ImageIcon("Imgs\\star.png").getImage();

    JLabel rock_lb = new JLabel();
    JLabel paper_lb = new JLabel();
    JLabel scissors_lb = new JLabel();
    JLabel lizard_lb = new JLabel();
    JLabel spock_lb = new JLabel();

    JLabel title_lb = new JLabel();
    JLabel rules_lb = new JLabel();
    JLabel waiting_lb = new JLabel();
    JLabel lost_lb = new JLabel();
    JLabel win_lb = new JLabel();
    JLabel tie_lb = new JLabel();


    File audio_file;
    AudioInputStream audioStream;
    Clip clip;
  
    int playerID;
    int playerChoice;
    static int result1;
    int star_x = 0;
    int star_y = 0;
    int star_xVelocity = 20;
    int star_yVelocity = 20;

    Timer timer;

    private ClientSideConnection csc;
   
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        GameFrame p = new GameFrame();
        p.connectionToServer();
        p.setUpGUI();
        p.setUpButtons();
        p.showResult(); 
    }

    public GameFrame(){
    }

    public void setUpGUI() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
         //Setting Background Audio 
         audio_file = new File("Audio\\background.wav");
         audioStream = AudioSystem.getAudioInputStream(audio_file);
         clip = AudioSystem.getClip();
         clip.open(audioStream);
 
         //Setting Bounds for Labels
         rock_lb.setText("You have chosen Rock!");
         rock_lb.setIcon(rock_img);
         rock_lb.setHorizontalTextPosition(JLabel.LEFT);
         rock_lb.setBounds(350, 500, 500,130);
         rock_lb.setVisible(false);
 
         paper_lb.setText("You have chosen Paper!");
         paper_lb.setIcon(paper_img);
         paper_lb.setHorizontalTextPosition(JLabel.LEFT);
         paper_lb.setBounds(350, 500, 500,130);
         paper_lb.setVisible(false);
 
         scissors_lb.setText("You have chosen Scissors!");
         scissors_lb.setIcon(scissors_img);
         scissors_lb.setHorizontalTextPosition(JLabel.LEFT);
         scissors_lb.setBounds(350, 500, 500,130);
         scissors_lb.setVisible(false);
 
         lizard_lb.setText("You have chosen Lizard!");
         lizard_lb.setIcon(lizard_img);
         lizard_lb.setHorizontalTextPosition(JLabel.LEFT);
         lizard_lb.setBounds(350, 500, 500,130);
         lizard_lb.setVisible(false);
 
         spock_lb.setText("You have chosen Spock!");
         spock_lb.setIcon(spock_img);
         spock_lb.setHorizontalTextPosition(JLabel.LEFT);
         spock_lb.setBounds(350, 500, 500,150);
         spock_lb.setVisible(false);
 
         title_lb.setText("<html>Welcome to Rock Paper Scissors Lizard Spock!<br>You are Player #" + playerID + "<html>");
         title_lb.setBounds(350, 0, 500,50);
         title_lb.setVisible(true);
 
         rules_lb.setText("<html>Rules:<br>Scissors cuts Paper<br>Paper covers Rock<br>Rock crushes Lizard<br>Lizard poisons Spock<br>Spock smashes Scissors<br>Scissors decapitates Lizard<br>Lizard eats Paper<br>Paper disproves Spock<br>Spock vaporizes Rock<br>Rock crushes Scissors<html>");
         rules_lb.setBounds(10, 0, 200,400);
         rules_lb.setVisible(true);
 
         waiting_lb.setText(".....Waiting for Other Players......");
         waiting_lb.setBounds(380, 200, 500,50);
         waiting_lb.setVisible(false);
 
         lost_lb.setText(".....You Have Lost!......");
         lost_lb.setBounds(380, 200, 500,50);
         lost_lb.setVisible(false);
 
         win_lb.setText(".....You Have Won!......");
         win_lb.setBounds(380, 200, 500,50);
         win_lb.setVisible(false);
 
         tie_lb.setText(".....Its a Tie! Try Again!......");
         tie_lb.setBounds(380, 200, 500,50);
         tie_lb.setVisible(false);
 
 
         //Setting Bounds for Buttons
         rock_btn.setBounds(200, 400, 90, 50); 
         rock_btn.setText("Rock"); 
         rock_btn.setFocusable(false); 
         
         paper_btn.setBounds(310, 400, 90, 50); 
         paper_btn.setText("Paper"); 
         paper_btn.setFocusable(false); 
         
         scissors_btn.setBounds(420, 400, 90, 50); 
         scissors_btn.setText("Scissors"); 
         scissors_btn.setFocusable(false); 
        
         lizard_btn.setBounds(530, 400, 90, 50); 
         lizard_btn.setText("Lizard"); 
         lizard_btn.setFocusable(false); 
         
         spock_btn.setBounds(640, 400, 90, 50); 
         spock_btn.setText("Spock"); 
         spock_btn.setFocusable(false); 

         stopMusic_btn.setBounds(850,10,50,50);
         stopMusic_btn.setIcon(sound_img);
         stopMusic_btn.setFocusable(false);

         playMusic_btn.setBounds(850,10,50,50);
         playMusic_btn.setIcon(mute_img);
         playMusic_btn.setVisible(false);
         playMusic_btn.setFocusable(false);

         timer = new Timer(100, null);
 
         //Setting Bounds for the JFrame
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setResizable(false);
         this.setLayout(null);
         this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
         
         //Allows the JFrame to appear in the  middle of the screeen
         setLocationRelativeTo(null);
 
         //Allows the JFrame to be Visible
         setVisible(true); 
 
         //adding buttons to Frame
         this.add(rock_btn);
         this.add(paper_btn);
         this.add(scissors_btn);
         this.add(lizard_btn);
         this.add(spock_btn);
         this.add(stopMusic_btn);
         this.add(playMusic_btn);
 
         //adding labels to Frame
         this.add(rock_lb);
         this.add(paper_lb);
         this.add(scissors_lb);
         this.add(lizard_lb);
         this.add(spock_lb);
         this.add(title_lb);
         this.add(rules_lb);
         this.add(waiting_lb);
         this.add(win_lb);
         this.add(lost_lb);
         this.add(tie_lb);
 
         //Starting Background Audio once JFrame is opened
         clip.start();
    }

    public void setUpButtons(){
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(e.getSource() == rock_btn){ //if button is pressed by user
            
                    buttonsOff();//calls buttonsOff method
                    tie_lb.setVisible(false);
                    rock_lb.setVisible(true); //shows label in Jframe
                    waiting_lb.setVisible(true);
                    playerChoice = 1;
                    csc.sendChoice(playerChoice);

                }else if(e.getSource() == paper_btn){ 
                    
                    buttonsOff();//calls buttonsOff method
                    tie_lb.setVisible(false);
                    paper_lb.setVisible(true);//shows label in Jframe
                    waiting_lb.setVisible(true);
                    playerChoice = 2;
                    csc.sendChoice(playerChoice);
        
                }else if(e.getSource() == scissors_btn){ 
                    
                    buttonsOff();
                    tie_lb.setVisible(false);
                    scissors_lb.setVisible(true);//shows label in Jframe
                    waiting_lb.setVisible(true);
                    playerChoice = 3;
                    csc.sendChoice(playerChoice);
        
                }else if(e.getSource() == lizard_btn){ 
                    
                    buttonsOff();//calls buttonsOff method
                    tie_lb.setVisible(false);
                    lizard_lb.setVisible(true);//shows label in Jframe
                    waiting_lb.setVisible(true);
                    playerChoice = 4;
                    csc.sendChoice(playerChoice);
        
                }else if(e.getSource() == spock_btn){ 
                    
                    buttonsOff();//calls buttonsOff method
                    tie_lb.setVisible(false);
                    spock_lb.setVisible(true);//shows label in Jframe
                    waiting_lb.setVisible(true);
                    playerChoice = 5;
                    csc.sendChoice(playerChoice);

                }else if(e.getSource() == stopMusic_btn){
                    clip.stop();
                    stopMusic_btn.setVisible(false);
                    playMusic_btn.setVisible(true);
                }else if(e.getSource() == playMusic_btn){
                    clip.start();
                    playMusic_btn.setVisible(false);
                    stopMusic_btn.setVisible(true);
                }
                
                if(star_x>=FRAME_WIDTH-star_img.getWidth(null)){
                    star_x = star_x - star_xVelocity;
                }
                if(star_x>=FRAME_HEIGHT-star_img.getHeight(null)){
                    star_y = star_y - star_yVelocity;
                }

                star_x = star_x + star_xVelocity;
                star_y = star_y + star_yVelocity;

                repaint();

            }
        };

        rock_btn.addActionListener(al);
        paper_btn.addActionListener(al);
        scissors_btn.addActionListener(al);
        lizard_btn.addActionListener(al);
        spock_btn.addActionListener(al);
        stopMusic_btn.addActionListener(al);
        playMusic_btn.addActionListener(al);
    }

    @Override
    public void paint(Graphics g){
        
        //calls on the super class to enable repaint method
        super.paint(g);

        //casts Graphics variable g as Graphics2D. 
        Graphics2D g2D = (Graphics2D) g;

        g2D.drawImage(star_img, 0,0, null);
        
    }

    public void connectionToServer(){
        csc = new ClientSideConnection();
    }

    public class ClientSideConnection{ 
        private DataInputStream dis;
        private DataOutputStream dos;
        private Socket socket;

        public ClientSideConnection(){

            System.out.println("----Client---");
            try {
                socket = new Socket("localhost",51734);
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

                playerID = dis.readInt();
                
            
            } catch (IOException e) {}
        }

        public void sendChoice(int n){
            try{
                dos.writeInt(n);
                dos.flush();
            }catch(IOException e){}
        }

        public void getResult(){
            try { 
                result1 = dis.readInt();

                if(result1 == -1){
                    waiting_lb.setVisible(false);
                    tie_lb.setVisible(true);
                    buttionsOn();

                }else if(result1 == 1){
                    System.out.println(result1);
                    waiting_lb.setVisible(false);
                    win_lb.setVisible(true);

                }else if(result1 == 0){
                    System.out.println(result1);
                    waiting_lb.setVisible(false);
                    lost_lb.setVisible(true);
                }

            } catch (IOException e) {
                System.out.println("Error in getResult()");
            }
        }
    }

    public void showResult(){
        while(true){
            csc.getResult();
        }
    }


    //Method to Disable all Buttons
    public void buttonsOff(){
        rock_btn.setEnabled(false);
        paper_btn.setEnabled(false);
        scissors_btn.setEnabled(false);
        lizard_btn.setEnabled(false);
        spock_btn.setEnabled(false);
    }

    //Method to enable all Buttons
    public void buttionsOn(){
        //sets buttons to be enabled
        rock_btn.setEnabled(true);
        paper_btn.setEnabled(true);
        scissors_btn.setEnabled(true);
        lizard_btn.setEnabled(true);
        spock_btn.setEnabled(true);

        //clears any previous user choice
        rock_lb.setVisible(false);
        paper_lb.setVisible(false);
        scissors_lb.setVisible(false);
        lizard_lb.setVisible(false);
        spock_lb.setVisible(false);
        waiting_lb.setVisible(false);
        win_lb.setVisible(false);
        lost_lb.setVisible(false);
    }
}