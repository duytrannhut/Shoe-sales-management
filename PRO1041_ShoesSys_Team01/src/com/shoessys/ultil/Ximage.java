/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.ultil;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author trann
 */
public class Ximage {
    public static final Image APP_ICON;
    static{
        String file = "/com/shoessys/icon/icons8_bee_32px.png";
        APP_ICON = new ImageIcon(Ximage.class.getResource(file)).getImage();
    }
    
    public static void save(File src, String folder){
        File dst = new File("image/"+folder, src.getName());
        if(!dst.getParentFile().exists()){
            dst.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static ImageIcon read(String filename,int w, int h, String folder){
        File path = new File("image/"+folder, filename);
        ImageIcon imgIcon = new ImageIcon(path.getAbsolutePath());
        Image img = imgIcon.getImage();
        return new ImageIcon(img.getScaledInstance(w, h, 0));
    }
    
    public static void getImageRound(String file, int w, int h, String folder, JLabel lbl){
        try {
            ImageIcon imgIcon = Ximage.read(file, w, h, folder);
            int diameter = Math.min(imgIcon.getIconWidth(), imgIcon.getIconHeight());
            BufferedImage mask = new BufferedImage(imgIcon.getIconWidth(),imgIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = mask.createGraphics();
            applyQualityRenderingHints(g2d);
            g2d.fillOval(0, 0, diameter - 1, diameter - 1);
            g2d.dispose();

            BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            g2d = masked.createGraphics();
            applyQualityRenderingHints(g2d);
            int x = (diameter - imgIcon.getIconWidth()) / 2;
            int y = (diameter - imgIcon.getIconHeight()) / 2;
            g2d.drawImage(imgIcon.getImage(), x, y, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
            g2d.drawImage(mask, 0, 0, null);
            g2d.dispose();
            
            lbl.setIcon(new ImageIcon(masked));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void applyQualityRenderingHints(Graphics2D g2d) {

        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

    }
}
