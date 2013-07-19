package generalLogic;

import imageStegoLib.common.BitBooleanArraysConverter;
import imageStegoLib.imageProcessor.ImageEmbed;
import imageStegoLib.message.AuthenticatorContainer;
import imageStegoLib.message.MessageContainer;
import imageUtil.Picture;
import stegoWebAdmin.models.Employee;
import stegoWebAdmin.models.PassEmployee;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class PassImageGenerator {
    public static Picture resizeInputImageToParameters(Picture input, int width, int height) {
        Picture newPic = input.getResizedToWidth(width); //TODO
        return newPic.crop(0,0,width,height);
    }

    public static byte[] getPassImageForEmployee(Employee employee, PassEmployee pass, URL backgroundLocation) throws IOException {
        final BufferedImage passBackground = ImageIO.read(new File(backgroundLocation.getPath()));
        Graphics g = passBackground.getGraphics();
        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(50f));
        g.drawString(employee.getName() + " " + employee.getSurname(), 80, 80);

        g.setFont(g.getFont().deriveFont(30f));
        g.drawString("mob. " + employee.getPhone(), 50, 500);
        g.drawString("email: " + employee.getEmail(), 50, 600);

        InputStream in = new ByteArrayInputStream(employee.getPhoto());
        BufferedImage image = ImageIO.read(in);

        ImageEmbed ie = new ImageEmbed();
        SecretStorage ss = new SecretStorage();
        MessageContainer mc = new AuthenticatorContainer();
        mc.setMessageAndComputeChecksum(BitBooleanArraysConverter.byteArray2BitArray(pass.getAuthenticationId()));
        ie.embedIntoImage(image, ss, mc);

        g.drawImage(image, 620, 70, null);

        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(passBackground, "png", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }
}
