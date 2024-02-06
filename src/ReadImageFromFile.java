import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadImageFromFile {
//    public static Image
//            Bishop_black,
//            Bishop_white,
//            King_black,
//            King_white,
//            Knight_black,
//            Knight_white,
//            Pawn_black,
//            Pawn_white,
//            Queen_black,
//            Queen_white,
//            Rook_black,
//            Rook_white;;
//
//    public ReadImageFromFile(){
//        Bishop_black = readPieceFromFile(ChessPiece.Bishop_black);
//        Bishop_white = readPieceFromFile(ChessPiece.Bishop_white);
//        King_black = readPieceFromFile(ChessPiece.King_black);
//        King_white = readPieceFromFile(ChessPiece.King_white);
//        Knight_black = readPieceFromFile(ChessPiece.Knight_black);
//        Knight_white = readPieceFromFile(ChessPiece.Knight_white);
//        Pawn_black = readPieceFromFile(ChessPiece.Pawn_black);
//        Pawn_white = readPieceFromFile(ChessPiece.Pawn_white);
//        Queen_black = readPieceFromFile(ChessPiece.Queen_black);
//        Queen_white = readPieceFromFile(ChessPiece.Queen_white);
//        Rook_black = readPieceFromFile(ChessPiece.Rook_black);
//        Rook_white = readPieceFromFile(ChessPiece.Rook_white);
//    }

    public Image readPieceFromFile(ChessPiece chessPiece){
        try {
            String imageResource = chessPiece + ".png";
            Image image = ImageIO.read(getClass().getResourceAsStream(imageResource));
            File file = new File("piece_images/" + chessPiece + ".png");
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

    public Image readImageFromMemory(ChessPiece chessPiece){
        return null;
    }
}
