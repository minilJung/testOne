package com.ebc.ecard.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;

import org.marvinproject.image.transform.scale.Scale;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import marvin.image.MarvinImage;

public class ImageUtil {

    public static int getOrientation(InputStream is) throws IOException {
        int orientation = 1;
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(is);
            Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            if (directory == null) {
                return -1;
            }

            try {
                orientation = directory
                    .getInt(ExifIFD0Directory. TAG_ORIENTATION);
            } catch (MetadataException me) {
                System. out.println("Could not get orientation" );
            }
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        }
        return orientation;
    }

    public static BufferedImage rotateImageForMobile(InputStream is, int orientation) throws IOException {
        BufferedImage bi = ImageIO. read(is);
        if(orientation == 6){ //정위치
            return rotateImage(bi, 90);
        } else if (orientation == 1){ //왼쪽으로 눞였을때
            return bi;
        } else if (orientation == 3){//오른쪽으로 눞였을때
            return rotateImage(bi, 180);
        } else if (orientation == 8){//180도
            return rotateImage(bi, 270);
        } else{
            return bi;
        }
    }

    public static BufferedImage rotateImage(BufferedImage orgImage,int radians) {
        BufferedImage newImage;
        if(radians==90 || radians==270){
            newImage = new BufferedImage(orgImage.getHeight(),orgImage.getWidth(),orgImage.getType());
        } else if (radians==180){
            newImage = new BufferedImage(orgImage.getWidth(),orgImage.getHeight(),orgImage.getType());
        } else{
            return orgImage;
        }
        Graphics2D graphics = (Graphics2D) newImage.getGraphics();
        graphics.rotate(Math. toRadians(radians), newImage.getWidth() / 2, newImage.getHeight() / 2);
        graphics.translate((newImage.getWidth() - orgImage.getWidth()) / 2, (newImage.getHeight() - orgImage.getHeight()) / 2);
        graphics.drawImage(orgImage, 0, 0, orgImage.getWidth(), orgImage.getHeight(), null );

        return newImage;
    }

    public static byte[] toByteArray(BufferedImage image, String extension) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(image, extension, baos);

        return baos.toByteArray();
    }

    public static InputStream resizeImage(String fileFormatName, BufferedImage image, int targetWidth) {
        try {
            int originWidth = image.getWidth();
            int originHeight = image.getHeight();

            MarvinImage imageMarvin = new MarvinImage(image);
            if(originWidth > targetWidth) {
                Scale scale = new ECardScale();
                scale.load();
                scale.setAttribute("newWidth", targetWidth);
                scale.setAttribute("newHeight", targetWidth * originHeight / originWidth);
                scale.process(imageMarvin.clone(), imageMarvin, null, null, false);
            }

            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageNoAlpha, fileFormatName, baos);

            return new ByteArrayInputStream(baos.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}