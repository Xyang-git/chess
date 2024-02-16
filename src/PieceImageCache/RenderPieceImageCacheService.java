package PieceImageCache;

import Pieces.ChessPiece;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;


public class RenderPieceImageCacheService {
    public HashMap<String, Image> cache = new HashMap<>();
    public Image getImage(String piece){
        if(!cache.containsValue(piece)){
            cache.put(piece, readPieceFromFile(piece));
        }
        /*

        if hashmap doesn't contain type:
            hashmap.insert(type, readPieceFromFile(pieceType);
        return hashmap.get(typpe)


         */
        return cache.get(piece);
    }
    public Image readPieceFromFile(String piece){
        try {
            String imageResource = "piece_images/" + piece + ".png";
            Image image = ImageIO.read(getClass().getResourceAsStream(imageResource));
            //File file = new File("piece_images/" + chessPiece + ".png");
            //            Image output = piece.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
//            BufferedImage bufferedImage = new BufferedImage(100,100,BufferedImage.TYPE_4BYTE_ABGR);
//            Graphics2D g = bufferedImage.createGraphics();
//            g.drawImage(output, 0, 0,null);
//            g.dispose();
//            ImageIO.write( bufferedImage, "png", file);
            //return ImageIO.read(file);
            return image;
        } catch (IOException e) {
            System.out.println("Fail to read image from file, Error: " + e);
            return null;
        }
    }

}
