package flogoduo.geoServer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        File file = new File("/home/sap/Pictures/GeoImages/photo.tif");
        try {
			printAllExifData(file);
		} catch (ImageReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        

        try
        {
            BufferedImage image = ImageIO.read(new File("/home/sap/Pictures/GeoImages/image1.jpg"));
            
            BufferedImage miniImage = image.getSubimage(900, 600,330, 330);
//            image = convert(miniImage, BufferedImage.TYPE_INT_RGB);
            
            ImageIO.write(miniImage, "jpg", new File("/home/sap/Pictures/GeoImages/test1.jpg"));
            System.out.println("done.");
            
            
            BufferedImage imageS = Sanselan.getBufferedImage(new File("/home/sap/Pictures/GeoImages/photo.tif"));
            
            BufferedImage miniImageS = imageS.getSubimage(0,0,330, 330);

            
            ImageIO.write(miniImageS, "jpg", new File("/home/sap/Pictures/GeoImages/test2.jpg"));
            System.out.println("done2");
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
       
        
        
        
        
    }
    /**
     * Log all Exif data of the file.
     *
     * @param imageFile the image file.
     * @throws ImageReadException thrown if the metadata cannot be read.
     * @throws IOException        thrown in case of other errors while reading metadata.
     */
    public static void printAllExifData(final File imageFile) throws ImageReadException, IOException {


        final IImageMetadata metadata = Sanselan.getMetadata(imageFile);
        
        TiffImageMetadata tiffImageMetadata;
        if (metadata instanceof JpegImageMetadata) {
            tiffImageMetadata = ((JpegImageMetadata) metadata).getExif();
        }
        else if (metadata instanceof TiffImageMetadata) {
            tiffImageMetadata = (TiffImageMetadata) metadata;
        }
        else {
            return;
        }

        @SuppressWarnings("unchecked")
        List<TiffImageMetadata.Item> items = (List<TiffImageMetadata.Item>) tiffImageMetadata.getItems();

        for (TiffImageMetadata.Item item : items) {
            //print
            System.out.println(item.getKeyword() + "  "+ item.getText());
        }
    }

public static BufferedImage convert(BufferedImage src, int bufImgType) {
    BufferedImage img= new BufferedImage(src.getWidth(), src.getHeight(), bufImgType);
    Graphics2D g2d= img.createGraphics();
    g2d.drawImage(src, 0, 0, null);
    g2d.dispose();
    return img;
}

}


