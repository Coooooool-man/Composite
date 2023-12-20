import java.awt.*;
import javax.imageio.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import ChooseFile.DialogFile;
import Image.MyImage;
import Resize.main.java.net.coobird.thumbnailator.Thumbnails;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import static java.awt.Frame.MAXIMIZED_BOTH;

public class Main {

    static public int brightness = 1;
    static boolean vis = true;

    public static BufferedImage scale(BufferedImage src, int w, int h)
    {
        BufferedImage img =
                new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        int[] ys = new int[h];
        for (y = 0; y < h; y++)
            ys[y] = y * hh / h;
        for (x = 0; x < w; x++) {
            int newX = x * ww / w;
            for (y = 0; y < h; y++) {
                int col = src.getRGB(newX, ys[y]);
                img.setRGB(x, y, col);
            }
        }
        return img;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) throws IOException {
        return Thumbnails.of(img).size(newW, newH).asBufferedImage();
    }


    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("PhotoEditor");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        GraphicsConfiguration gc = frame.getGraphicsConfiguration();
        Rectangle bounds = gc.getBounds();
        Insets insets = toolkit.getScreenInsets(gc);


        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyImage image = new MyImage(bounds.width,bounds.height);

        BufferedImage icon = ImageIO.read(new File("res\\icon2.png"));
        BufferedImage im = ImageIO.read(new File("res\\house.jpg"));


        im = resize(im, bounds.width, bounds.height);
        int height = im.getHeight(), width = im.getWidth();
        float height2 = icon.getHeight(), width2 = icon.getWidth();
        //int sizeInBytes = im.getData().getDataBuffer().getSize();
        float scale = width * height, scale2 = width2 * height2;

        float c = 1f, width3 = 0, height3 = 0;
        int c2 = 1;

        //width2 = width / 10;
        //height2 = height / 10;
        float scale3 = scale /500;

        if(scale3 < scale2)
        {
            while(scale2 > scale3)
            {
                width3 = width2 / c2;
                height3 = height2 / c2;
                scale2 = width3 * height3;
                c2++;
            }
        }
        else {
            while(scale2 < scale3)
            {
                width3 = width2 * c2;
                height3 = height2 * c2;
                scale2 = width3 * height3;
                c2++;
            }
        }

        width2 = width3;
        height2 = height3;
        icon = resize(icon, (int)width2, (int)height2);

        Image icon2 = icon.getScaledInstance((int)width2 , (int)height2 , Image.SCALE_DEFAULT);


        String timestamp = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"))
                .format(DateTimeFormatter.ofPattern("MM.dd.yyy, hh.mm.ss a"));
        String nameFile = "picture" + timestamp + ".jpg";

        im.getGraphics().drawImage(icon2, width - (80 / (int)c + (int)width2),
                height - (80 / (int)c + (int)height2), null); //(верхний левый угол изображения будет иметь это
        // frame->myimage->image->buffredimage
        image.SetImage(im);
        //DialogFile  df = new DialogFile(image);
        frame.add(image);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocus();
        frame.requestFocusInWindow();

        frame.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {

                if( e.getKeyCode() ==  KeyEvent.VK_A)
                {
                    System.out.println("1");

                    if(Main.brightness-- == 0)
                        Main.brightness = -1;

                    image.newBrightness(Main.brightness * 10);
                    image.repaint();
                    frame.add(image);
                    frame.setVisible(true);
                }

                if( e.getKeyCode() ==  KeyEvent.VK_D)
                {
                    System.out.println("2");

                    if(Main.brightness++ == 0)
                        Main.brightness = 1;

                    image.newBrightness(Main.brightness * 10);
                    image.repaint();
                    frame.add(image);
                    frame.setVisible(true);
                }

                /*if( e.getKeyCode() ==  KeyEvent.VK_H)
                {
                    JButton buttonMass[] =  df.GetButton();

                    buttonMass[0].setVisible(vis);
                    buttonMass[1].setVisible(vis);
                    buttonMass[2].setVisible(vis);


                    if(Main.vis)
                        Main.vis = false;
                    else
                        Main.vis = true;
                }*/
            }

        });

        frame.addWindowListener(new WindowAdapter()
        {

            public void windowClosing(WindowEvent e)  {

                try {
                    ImageIO.write(image.getImage(), "png",
                            new File("res2\\" + nameFile));
                }
                catch(IOException g)
                {
                    System.out.println("cannot save the file");
                }
            }
        });

    }
}