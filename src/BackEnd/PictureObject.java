package BackEnd;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class PictureObject implements Serializable{
    private String name;
    private byte[] image;
    private int row;
    private int column;
    private int xLen;
    private int yLen;
    private int tileSize;

    /**
     * This is the class which contains the Images used in a maze
     * @param name The category of the image(Icon, Start, End)
     * @param row The row location of the Image being shown on screen
     * @param column The column location of the Image being shown on screen
     * @param image The image in byte array
     * @param tileSize The size of the image
     */
    public PictureObject(String name, int row, int column, BufferedImage image, int tileSize) {
        try {
            this.name = name;
            this.row = row;
            this.column = column;
            this.image = toByteArray(image, "png");
            this.tileSize = tileSize;
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * This is the class constructor which accepts the iamge only
     * @param image image
     */
    public PictureObject(BufferedImage image) {
        try{
            this.image = toByteArray(image, "png");
        }catch(IOException e){
        }

    }

    /**
     * This is te getter method for the name value
     * @return name
     */
    public String getName(){
        return name;
    }
    //https://mkyong.com/java/how-to-convert-bufferedimage-to-byte-in-java/

    /**
     * This function converts the image from BufferedImage type to byte
     * @param bi the image
     * @param format image file type such as .jpg, .png, .gif
     * @return image in byte array
     * @throws IOException
     */
    public static byte[] toByteArray(BufferedImage bi, String format)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, format, baos);
        }catch(IOException e){

        }
        byte[] bytes = baos.toByteArray();
        return bytes;


    }

    /**
     * This function converts the image from BufferedImage type to byte
     * @param bytes the image
     * @return image in BufferedImage type
     *
     */
    public static BufferedImage toBufferedImage(byte[] bytes) {

        InputStream is = new ByteArrayInputStream(bytes);
        try {
            BufferedImage bi = ImageIO.read(is);
            return bi;
        }
        catch(IOException e){
            return null;
        }


    }

    /**
     * This function set the row value
     * @param row The row the image at
     */
    public void setRow(int row){this.row = row;}
    /**
     * This function set the column value
     * @param column The column the image at
     */
    public void setColumn(int column){this.column = column;}
    /**
     * This function return the row value
     * @return The row the image at
     */
    public int getRow () {
        return row;
    }
    /**
     * This function return the column value
     * @return  The column the image at
     */
    public int getColumn(){
        return column;
    }
    /**
     * This function return the tile size
     * @return  The tile size
     */
    public int getTileSize(){
        return tileSize;
    }

    /**
     * This function return the image
     * @return image
     */
    public BufferedImage getImage() {
        try {
            return toBufferedImage(this.image);
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    /**
     * This function update the image
     * @param image the image
     */
    public void updateImg(BufferedImage image){
        try{
            this.image = toByteArray(image, "png");
        } catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * This function return the image in byte array
     * @return image
     */
    public byte[] getImageByte(){
        return this.image;
    }

}
