package tech.feily.characterdancedemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JDialog;

public class ImageGUI extends JComponent {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private BufferedImage image;
    private int size = 0;

    public ImageGUI() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (image == null) {
            g2d.setPaint(Color.BLACK);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        } else {
            g2d.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
            try {
                javax.imageio.ImageIO.write(image, "jpeg",
                        new FileOutputStream("E:\\video\\meipai\\pic\\" + size++ + ".jpg"));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("show frame...");
        }
    }

    public void createWin(String title) {
        JDialog ui = new JDialog();
        ui.setTitle(title);
        ui.getContentPane().setLayout(new BorderLayout());
        ui.getContentPane().add(this, BorderLayout.CENTER);
        ui.setSize(new Dimension(330, 240));
        ui.setVisible(true);
    }

    public void createWin(String title, Dimension size) {
        JDialog ui = new JDialog();
        ui.setTitle(title);
        ui.getContentPane().setLayout(new BorderLayout());
        ui.getContentPane().add(this, BorderLayout.CENTER);
        ui.setSize(size);
        ui.setVisible(true);
    }

    public void imshow(BufferedImage image) {
        this.image = image;
        this.repaint();
    }

    public void savePic(Image iamge, String imgName) {
        int w = iamge.getWidth(this);
        int h = iamge.getHeight(this);

//首先创建一个BufferedImage变量，因为ImageIO写图片用到了BufferedImage变量。 
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);

//再创建一个Graphics变量，用来画出来要保持的图片，及上面传递过来的Image变量 
        Graphics g = bi.getGraphics();
        try {
            g.drawImage(iamge, 0, 0, null);

//将BufferedImage变量写入文件中。 
            ImageIO.write(bi, "jpg", new File(imgName));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}