package Image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class MyImage  extends JPanel {

    public MyImage(int x, int y)
    {
        this.x = x;
        this.y= y;

    }

    public void SetImage(BufferedImage picture)
    {
        this.im = picture;
        this.height = im.getHeight();
        this.width = im.getWidth();
        image = deepCopy(im);
    }

    public BufferedImage getImage()
    {
        return this.image;
    }

     private int Truncate(int value)
    {

        if (value < 0) {
            value = 0;
        }
        else if (value > 255) {
            value = 255;
        }
        return value;
    }
    public void newBrightness( int brightnessPercentage ) {

        int[] pixel = { 0, 0, 0, 0 };
        float[] hsbvals = { 0, 0, 0 };
        image = deepCopy(im);


        // recalculare every pixel, changing the brightness
        /*for ( int i = 0; i <  height; i++ ) {
            for (int j = 0; j < width; j++) {

                // get the pixel data
                image.getRaster().getPixel(j, i, pixel);

                // converts its data to hsb to change brightness
                Color.RGBtoHSB(pixel[0], pixel[1], pixel[2], hsbvals);

                // create a new color with the changed brightness
                Color c = new Color(Color.HSBtoRGB(hsbvals[0], hsbvals[1], hsbvals[2] * brightnessPercentage));

                // set the new pixel
                image.getRaster().setPixel(j, i, new int[]{c.getRed(), c.getGreen(), c.getBlue(), pixel[3]});

            }

        }*/

        int rgb[];

        for (int i = 0; i < image.getWidth(); i++) {

            // Inner loop for height of image
            for (int j = 0; j < image.getHeight(); j++) {

                rgb = image.getRaster().getPixel(
                        i, j, new int[3]);

                // Using(calling) method 1
                int red
                        = Truncate(rgb[0] + brightnessPercentage);
                int green
                        = Truncate(rgb[1] + brightnessPercentage);
                int blue
                        = Truncate(rgb[2] + brightnessPercentage);

                int arr[] = { red, green, blue };

                // Using setPixel() method
                image.getRaster().setPixel(i, j, arr);
            }
        }

    }

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, x / 2 - width / 2, y / 2 - height / 2, null);
        //g.drawImage(im, 100, 100, null );
    }
    private BufferedImage im, image;
    private int x, y;
    private int height, width;
}
