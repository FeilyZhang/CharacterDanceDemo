package tech.feily.characterdancedemo;
import java.io.File;
import java.util.ArrayList;

import org.jim2mov.core.DefaultMovieInfoProvider;
import org.jim2mov.core.FrameSavedListener;
import org.jim2mov.core.ImageProvider;
import org.jim2mov.core.Jim2Mov;
import org.jim2mov.core.MovieInfoProvider;
import org.jim2mov.core.MovieSaveException;
import org.jim2mov.utils.MovieUtils;

public class PicToAviUtil implements ImageProvider, FrameSavedListener{
    // �ļ�����
    private ArrayList<String> fileArray = null;
    // �ļ�����
    private int type = MovieInfoProvider.TYPE_QUICKTIME_JPEG;
    // ������
    public static void main(String[] args) throws MovieSaveException {
        ArrayList<String> fileArray = new ArrayList<>();
        File[] listFiles = new File("E:\\video\\meipai\\pic").listFiles();
        
        for (int i = 0; i < listFiles.length; i++) {
            fileArray.add(listFiles[i].getAbsolutePath());
        }
        new PicToAviUtil(fileArray, MovieInfoProvider.TYPE_QUICKTIME_JPEG, "Test.mov");
    }
    
    /**
     * ͼƬת��Ƶ
     * @param filePaths �ļ�·������
     * @param type ��ʽ
     * @param path �ļ���
     * @throws MovieSaveException 
     */
    public PicToAviUtil(ArrayList<String> fileArray, int type, String path) throws MovieSaveException {
        this.fileArray = fileArray;
        this.type = type;
        DefaultMovieInfoProvider dmip = new DefaultMovieInfoProvider(path);
        // ����֡Ƶ��
        dmip.setFPS(1);
        // ����֡��--һ��ͼƬһ֡
        dmip.setNumberOfFrames(fileArray.size());
        // ������Ƶ�߶�
        dmip.setMWidth(320);
        // ������Ƶ���
        dmip.setMHeight(240);
        new Jim2Mov(this, dmip, this).saveMovie(this.type);;
    }

    @Override
    public void frameSaved(int frameNumber) {
        System.out.println("Saved frame: " + frameNumber);
    }

    @Override
    public byte[] getImage(int frame) {
        try {
            return MovieUtils.convertImageToJPEG(new File(fileArray.get(frame)), 1.0f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
