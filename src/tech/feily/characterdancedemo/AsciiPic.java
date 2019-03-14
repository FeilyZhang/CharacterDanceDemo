package tech.feily.characterdancedemo;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;  

import javax.imageio.ImageIO;  

/** 
 * @author ���� 2016��10��27�� 
 * 
 */  
public class AsciiPic {  

    /** 
     * @param path 
     *            ͼƬ·�� 
     */  
    public static String createAsciiPic(final String path, String target) {  
        //final String base = "\"@#&$%*o!;.";// �ַ����ɸ��ӵ���  
        String result = "";
        final String base = "#8XOHLTI)i=+;:,. ";// �ַ����ɸ��ӵ���  
        try {  
            final BufferedImage image = ImageIO.read(new File(path));  //��ȡͼƬ
            //�����ָ���ļ���
            final BufferedWriter fos = new BufferedWriter(new FileWriter(target,false));   //true��ʾ�Ƿ�׷��
            for (int y = 0; y < image.getHeight(); y += 2) {  
                for (int x = 0; x < image.getWidth(); x++) {  
                    final int pixel = image.getRGB(x, y);  
                    final int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;  
                    final float gray = 0.299f * r + 0.578f * g + 0.114f * b;  
                    final int index = Math.round(gray * (base.length() + 1) / 255); 
                    String s = index >= base.length() ? " " : String.valueOf(base.charAt(index));
                    System.out.print(s);
                    result += s;
                    fos.write(s);
                }  
                result += "\n";
                fos.newLine();
                System.out.println();
            } 
            fos.close();
            System.out.println(result);
        } catch (final IOException e) {  
            e.printStackTrace();  
        }  
        return result;
    }  

    /** 
     * test 
     * 
     * @param args 
     */  
    public static void main(final String[] args) {  
        for (int i = 0; i < 274; i++) {
            AsciiPic.createAsciiPic("E:\\video\\meipai\\piccpmpress\\" + String.valueOf(i) + ".jpg", "E:\\txt\\" + String.valueOf(i) + ".md");  
        }
    }  

}  