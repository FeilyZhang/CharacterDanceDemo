package tech.feily.characterdancedemo;

import java.awt.Graphics2D;  
import java.awt.Rectangle;  
import java.awt.RenderingHints;  
import java.awt.geom.AffineTransform;  
import java.awt.image.BufferedImage;  
import java.awt.image.ColorModel;  
import java.awt.image.WritableRaster;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream;  

import javax.imageio.ImageIO;  

/**   
 * ͼƬ�����࣬���ͼƬ�Ľ�ȡ 
 * ���з�������ֵ��δboolean��   
 */    
public class ImageHelper {  
      /**   
     * ʵ��ͼ��ĵȱ�����   
     * @param source   
     * @param targetW   
     * @param targetH   
     * @return   
     */    
    private static BufferedImage resize(BufferedImage source, int targetW,     
            int targetH) {     
        // targetW��targetH�ֱ��ʾĿ�곤�Ϳ�     
        int type = source.getType();     
        BufferedImage target = null;     
        double sx = (double) targetW / source.getWidth();     
        double sy = (double) targetH / source.getHeight();     
        // ������ʵ����targetW��targetH��Χ��ʵ�ֵȱ����š��������Ҫ�ȱ�����     
        // �������if else���ע�ͼ���     
        if (sx < sy) {     
            sx = sy;     
            targetW = (int) (sx * source.getWidth());     
        } else {     
            sy = sx;     
            targetH = (int) (sy * source.getHeight());     
        }     
        if (type == BufferedImage.TYPE_CUSTOM) { // handmade     
            ColorModel cm = source.getColorModel();     
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW,     
                    targetH);     
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();     
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);     
        } else    
            target = new BufferedImage(targetW, targetH, type);     
        Graphics2D g = target.createGraphics();     
        // smoother than exlax:     
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,     
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);     
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));     
        g.dispose();     
        return target;     
    }     

    /**   
     * ʵ��ͼ��ĵȱ����ź����ź�Ľ�ȡ, ����ɹ�����true, ���򷵻�false   
     * @param inFilePath Ҫ��ȡ�ļ���·��   
     * @param outFilePath ��ȡ�������·��   
     * @param width Ҫ��ȡ���   
     * @param hight Ҫ��ȡ�ĸ߶�   
     * @throws Exception   
     */    
    public static boolean compress(String inFilePath, String outFilePath,     
            int width, int hight) {  
        boolean ret = false;  
        File file = new File(inFilePath);  
        File saveFile = new File(outFilePath);  
        InputStream in = null;  
        try {  
            in = new FileInputStream(file);  
            ret = compress(in, saveFile, width, hight);  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            ret = false;  
        } finally{  
            if(null != in){  
                try {  
                    in.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  

        return ret;  
    }   

    /**   
     * ʵ��ͼ��ĵȱ����ź����ź�Ľ�ȡ, ����ɹ�����true, ���򷵻�false   
     * @param in Ҫ��ȡ�ļ��� 
     * @param outFilePath ��ȡ�������·��   
     * @param width Ҫ��ȡ���   
     * @param hight Ҫ��ȡ�ĸ߶�   
     * @throws Exception   
     */    
    public static boolean compress(InputStream in, File saveFile,     
            int width, int hight) {  
//     boolean ret = false;  
        BufferedImage srcImage = null;  
        try {  
            srcImage = ImageIO.read(in);  
        } catch (IOException e) {  
            e.printStackTrace();  
            return false;  
        }  

        if (width > 0 || hight > 0) {  
            // ԭͼ�Ĵ�С  
            int sw = srcImage.getWidth();  
            int sh = srcImage.getHeight();  
            // ���ԭͼ��Ĵ�СС��Ҫ���ŵ�ͼ���С��ֱ�ӽ�Ҫ���ŵ�ͼ���ƹ�ȥ  
            if (sw > width && sh > hight) {  
                srcImage = resize(srcImage, width, hight);  
            } else {  
                String fileName = saveFile.getName();  
                String formatName = fileName.substring(fileName  
                        .lastIndexOf('.') + 1);  
                try {  
                    ImageIO.write(srcImage, formatName, saveFile);  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    return false;  
                }  
                return true;  
            }  
        }  
        // ���ź��ͼ��Ŀ�͸�  
        int w = srcImage.getWidth();  
        int h = srcImage.getHeight();  
        // ������ź��ͼ���Ҫ���ͼ����һ�����Ͷ����ŵ�ͼ��ĸ߶Ƚ��н�ȡ  
        if (w == width) {  
            // ����X������  
            int x = 0;  
            int y = h / 2 - hight / 2;  
            try {  
                saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);  
            } catch (IOException e) {  
                e.printStackTrace();  
                return false;  
            }  
        }  
        // ������������ź��ͼ��ĸ߶Ⱥ�Ҫ���ͼ��߶�һ�����Ͷ����ź��ͼ��Ŀ�Ƚ��н�ȡ  
        else if (h == hight) {  
            // ����X������  
            int x = w / 2 - width / 2;  
            int y = 0;  
            try {  
                saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);  
            } catch (IOException e) {  
                e.printStackTrace();  
                return false;                  
            }  
        }  

        return true;  
    }  

    /** 
     * ʵ��ͼ��ĵȱ����ź����ź�Ľ�ȡ, ����ɹ�����true, ���򷵻�false 
     * @param in ͼƬ������ 
     * @param saveFile ѹ�����ͼƬ����� 
     * @param proportion ѹ���� 
     * @throws Exception 
     */  
    public static boolean compress(InputStream in, File saveFile, int proportion) {  
        if(null == in  
                ||null == saveFile  
                ||proportion < 1){// ��������Ч��  
            //LoggerUtil.error(ImageHelper.class, "--invalid parameter, do nothing!");  
            return false;  
        }  

        BufferedImage srcImage = null;  
        try {  
            srcImage = ImageIO.read(in);  
        } catch (IOException e) {  
            e.printStackTrace();  
            return false;  
        }  
        // ԭͼ�Ĵ�С  
        int width = srcImage.getWidth() / proportion;  
        int hight = srcImage.getHeight() / proportion;  

        srcImage = resize(srcImage, width, hight);  

        // ���ź��ͼ��Ŀ�͸�  
        int w = srcImage.getWidth();  
        int h = srcImage.getHeight();  
        // ������ź��ͼ���Ҫ���ͼ����һ�����Ͷ����ŵ�ͼ��ĸ߶Ƚ��н�ȡ  
        if (w == width) {  
            // ����X������  
            int x = 0;  
            int y = h / 2 - hight / 2;  
            try {  
                saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);  
            } catch (IOException e) {  
                e.printStackTrace();  
                return false;  
            }  
        }  
        // ������������ź��ͼ��ĸ߶Ⱥ�Ҫ���ͼ��߶�һ�����Ͷ����ź��ͼ��Ŀ�Ƚ��н�ȡ  
        else if (h == hight) {  
            // ����X������  
            int x = w / 2 - width / 2;  
            int y = 0;  
            try {  
                saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);  
            } catch (IOException e) {  
                e.printStackTrace();  
                return false;  
            }  
        }  

        return true;  
    }  

    /** 
     * ʵ�����ź�Ľ�ͼ 
     * @param image ���ź��ͼ�� 
     * @param subImageBounds Ҫ��ȡ����ͼ�ķ�Χ 
     * @param subImageFile Ҫ������ļ� 
     * @throws IOException  
     */    
    private static void saveSubImage(BufferedImage image,     
            Rectangle subImageBounds, File subImageFile) throws IOException {     
        if (subImageBounds.x < 0 || subImageBounds.y < 0    
                || subImageBounds.width - subImageBounds.x > image.getWidth()     
                || subImageBounds.height - subImageBounds.y > image.getHeight()) {     
            //LoggerUtil.error(ImageHelper.class, "Bad subimage bounds");     
            return;     
        }     
        BufferedImage subImage = image.getSubimage(subImageBounds.x,subImageBounds.y, subImageBounds.width, subImageBounds.height);     
        String fileName = subImageFile.getName();     
        String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);     
        ImageIO.write(subImage, formatName, subImageFile);  
    }     

   public static void main(String[] args) throws Exception {  
       /** 
        * saveSubImage ��ͼ���ʹ�� 
        * srcImage ΪBufferedImage���� 
        * Rectangle    Ϊ��Ҫ��ͼ�ĳ��������� 
        * saveFile ��Ҫ�����·�������� 
        * **/  
        //��Ҫ��ͼ�ĳ���������  
        /*Rectangle rect =new Rectangle(); 
        rect.x=40; 
        rect.y=40; 
        rect.height=160; 
        rect.width=160; 

        InputStream in = null; 
        //��Ҫ�����·�������� 
        File saveFile = new File("d:\\ioc\\files\\aaa2.jpg"); 
        //��Ҫ���д����ͼƬ��·�� 
        in = new FileInputStream(new File("d:\\ioc\\files\\aaa.jpg")); 
        BufferedImage srcImage = null; 
        //�����������תΪBufferedImage���� 
        srcImage = ImageIO.read(in); 

        ImageHelper img=new ImageHelper(); 
        img.saveSubImage(srcImage, rect, saveFile);*/  

       /** 
        * compress ͼƬ�������ʹ��(����ͼ) 
        * srcImage ΪInputStream���� 
        * Rectangle    Ϊ��Ҫ��ͼ�ĳ��������� 
        * proportion Ϊѹ������ 
        * **/  
        InputStream in = null;  
        //���ź���Ҫ�����·��  
        for (int i = 0; i < 274; i++) {
            File saveFile = new File("E:\\video\\meipai\\piccpmpress\\" + String.valueOf(i) + ".jpg");  
            try {  
                //ԭͼƬ��·��  
                in = new FileInputStream(new File("E:\\video\\meipai\\pic\\" + String.valueOf(i) + ".jpg"));  
                if(compress(in, saveFile, 2)){  
                    System.out.println(i);  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                in.close();  
            }  
        }
    }  
} 