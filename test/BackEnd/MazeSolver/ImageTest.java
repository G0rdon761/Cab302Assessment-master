package BackEnd.MazeSolver;

import static org.junit.jupiter.api.Assertions.*;

import BackEnd.PictureObject;
import org.junit.jupiter.api.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.Buffer;

public class ImageTest {
    private String name = "Test";
    private int row = 0;
    private int column = 1;
    private BufferedImage image = new BufferedImage(1,1,1);
    private PictureObject imageClass;
    // test0, call the class instructor
    @BeforeEach @Test
    public void setUpIcon() throws IOException {
        imageClass = new PictureObject(name, row, column,image, 10);
    }

    //test1, get the name
    @Test
    public void getName(){
        assertEquals(name, imageClass.getName());
    }
    //test2, get the row number
    @Test
    public void getRow(){
        assertEquals(row, imageClass.getRow());
    }
    //test3, get the column number
    @Test
    public void getCol(){
        assertEquals(column, imageClass.getColumn());
    }

    //test4, get the image
    @Test
    public void getImage() throws IOException {
        byte[] actualImage = imageClass.getImageByte();
        //convert buffered image to byte stream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] expectedByteArray;
        ImageIO.write(image,"png",baos);
        expectedByteArray= baos.toByteArray();

        assertArrayEquals(expectedByteArray,actualImage);
    }
    //test5, convert the image from buffered image to byte array, check whether the length and width of the image are equals
    @Test
    public void testConvertor() throws IOException {
        byte [] byteImage = PictureObject.toByteArray(image, "png");
        BufferedImage convertedImage = PictureObject.toBufferedImage(byteImage);
        assertEquals(image.getHeight(), convertedImage.getHeight());
        assertEquals(image.getWidth(), convertedImage.getWidth());
    }
}
