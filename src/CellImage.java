import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

/**
 * Created by USER on 5/13/2017.
 */

public class CellImage extends JPanel implements ActionListener {
	private BoardGui board;
    public final static String Box="b";
    public final static String Player="p";
    public final static String  Storage="s";
    public final static String BoxStorage="bs";
    public final static String Wall="w";
    public final static String  Empty="g";

   //images
    public static Image boxImage;
    public static Image playerImage;
    public static Image playerImage1;
    public static Image playerImage2;
    public static Image playerImage3;
    public static Image playerImage4;
    public static Image playerImage5;
    public static Image storageImage;
    public static Image storageImage1;
    public static Image storageImage2;
    public static Image boxStorageImage;
    public static Image wallImage;
    public static Image grassImage;
    

    private static int numberPlayer;
    private static boolean isUp;
    private Image icon;
    private Image image;
    private static Timer timer=null;
    private JLabel imageLabel;
    private String _type;

    public CellImage(String type) {


        _type=type;
        //reload images first time
        if(playerImage1==null)
        {


            timer = new Timer(150,this);
            timer.setRepeats(true);
            timer.start();

            numberPlayer=3;
            isUp=false;
            try{

                String BoxSrc ="/box.png";
                String PlayerSrc="/player3.png";
                String Player1Src="/player1.png";
                String Player2Src="/player2.png";
                String Player4Src="/player4.png";
                String Player5Src="/player5.png";
                String StorageSrc="/storage.png";
                String Storage2Src="/storage2.png";
                String BoxStorageSrc="/storageBox.png";
                String WallSrc="/wall.jpg";
                String EmptySrc="/grass.jpg";

                icon= new ImageIcon(getClass().getResource(BoxSrc)).getImage();
                boxImage=icon.getScaledInstance(70, 70,Image.SCALE_SMOOTH);
                icon= new ImageIcon(getClass().getResource(Player1Src)).getImage();
                playerImage1=icon.getScaledInstance(70, 70,Image.SCALE_SMOOTH);
                icon= new ImageIcon(getClass().getResource(Player2Src)).getImage();
                playerImage2=icon.getScaledInstance(70, 70,Image.SCALE_SMOOTH);
                icon= new ImageIcon(getClass().getResource(PlayerSrc)).getImage();
                playerImage3=icon.getScaledInstance(70, 70,Image.SCALE_SMOOTH);
                icon= new ImageIcon(getClass().getResource(Player4Src)).getImage();
                playerImage4=icon.getScaledInstance(70, 70,Image.SCALE_SMOOTH);
                icon= new ImageIcon(getClass().getResource(Player5Src)).getImage();
                playerImage5=icon.getScaledInstance(70, 70,Image.SCALE_SMOOTH);
                icon= new ImageIcon(getClass().getResource(StorageSrc)).getImage();
                storageImage1=icon.getScaledInstance(70, 70,Image.SCALE_SMOOTH);
                icon= new ImageIcon(getClass().getResource(Storage2Src)).getImage();
                storageImage2=icon.getScaledInstance(70, 70,Image.SCALE_SMOOTH);
                icon= new ImageIcon(getClass().getResource(BoxStorageSrc)).getImage();
                boxStorageImage=icon.getScaledInstance(70, 70,Image.SCALE_SMOOTH);
                playerImage=playerImage3;
                storageImage=storageImage1;

                icon= new ImageIcon(getClass().getResource(WallSrc)).getImage();
                wallImage=icon;
                icon= new ImageIcon(getClass().getResource(EmptySrc)).getImage();
                grassImage=icon;
            }
            catch(Exception e){

            }

        }
        //build as background
        imageLabel=new JLabel();
        add(imageLabel);

            if(type.equals(Wall))
            {
                image = wallImage;
            }
            else {
                image = grassImage;
                //add icon if exist
                timer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(_type.equals(Storage))
                            imageLabel.setIcon(new ImageIcon(storageImage));
                    }
                });
               setIcon(type);
            }

    }
    public void actionPerformed(ActionEvent e) {

        if(isUp)
          numberPlayer++;
        else
            numberPlayer--;

        if(numberPlayer==6){
            numberPlayer=4;
            isUp=false;
        }

        if(numberPlayer==0){
            numberPlayer=2;
            isUp=true;
        }
       updateImg();
    }

    private void updateImg(){
        Image player=null;
        Image storage=null;
        switch (numberPlayer){
            case 1:player= playerImage1;
                   storage=storageImage1;
                   break;
            case 2:player= playerImage2;
                storage=storageImage1;
                break;
            case 3:player= playerImage3;
                storage=storageImage1;
                break;
            case 4:player= playerImage4;
                   storage=storageImage2;
                   break;
            case 5 :player= playerImage5;
                storage=storageImage1;
                break;
        }
        playerImage = player;
        storageImage = storage;
    }

    public void setIcon(String type)
    {
        _type=type;
        switch (type) {
		case Box:
            imageLabel.setIcon(new ImageIcon(boxImage));
            break;
		case Player:
            imageLabel.setIcon(new ImageIcon(playerImage));
            break;
		case Storage:
			imageLabel.setIcon(new ImageIcon(storageImage));
            break;
		case Empty:
			imageLabel.setIcon(null);
			break;
		case BoxStorage:
			imageLabel.setIcon(new ImageIcon(boxStorageImage));
			break;
		default:
			break;
		}
    }


    //build background
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
    }
}
