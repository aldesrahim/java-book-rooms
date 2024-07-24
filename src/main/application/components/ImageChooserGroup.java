package main.application.components;

import com.formdev.flatlaf.util.UIScale;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import main.util.Dialog;
import main.util.ImageHelper;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.VerticalLayout;

/**
 *
 */
public class ImageChooserGroup extends JPanel {

    private Dimension panelSize = new Dimension(280, 250);
    
    private JLabel lbTitle;
    private JLabel lbPreview;
    private JPanel mainPanel;
    private JPanel btnPanel;

    private Button btnChoose;
    private Button btnDelete;
    private Button btnCancel;

    private String currentPath;
    private File selectedFile;

    private ButtonEvent btnDeleteEvent;
    private boolean isReadOnly = false;

    public ImageChooserGroup() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 7));
        setOpaque(false);
        //setPreferredSize(UIScale.scale(panelSize));

        lbTitle = new JLabel();
        lbPreview = new JLabel();
        mainPanel = new JPanel();
        btnPanel = new JPanel();
        
        mainPanel.setOpaque(false);
        btnPanel.setOpaque(false);

        btnChoose = new Button();
        btnChoose.setText("Pilih");
        
        btnDelete = new Button();
        btnDelete.setText("Hapus");
        
        btnCancel = new Button();
        btnCancel.setText("Batal");

        lbPreview.setVisible(false);
        btnDelete.setVisible(false);
        btnCancel.setVisible(false);

        btnChoose.addActionListener(new ChooseButtonListener());
        btnDelete.addActionListener((ae) -> {
            btnDeleteEvent.onClick(ae);
        });
        btnCancel.addActionListener((ae) -> {
            cancelSelected();
        });
        
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 3, 3));
        btnPanel.add(btnChoose);
        btnPanel.add(btnDelete);
        btnPanel.add(btnCancel);        

        mainPanel.setLayout(new BorderLayout(0, 5));
        mainPanel.add(btnPanel, BorderLayout.PAGE_START);
        mainPanel.add(lbPreview, BorderLayout.CENTER);

        lbTitle.setText("Title");
        add(lbTitle, BorderLayout.PAGE_START);
        add(mainPanel, BorderLayout.CENTER);
    }

    public void setIsReadOnly(boolean isReadOnly) {
        btnChoose.setEnabled(!isReadOnly);
        btnDelete.setEnabled(!isReadOnly);
        btnCancel.setEnabled(!isReadOnly);
    }

    public void setTitleText(String titleText) {
        lbTitle.setText(titleText);
    }

    public void setBtnDeleteEvent(ButtonEvent btnDeleteEvent) {
        this.btnDeleteEvent = btnDeleteEvent;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String path) {
        if (path == null) {
            return;
        }
        
        currentPath = path;
        btnChoose.setEnabled(true);
        btnDelete.setVisible(true);
        btnCancel.setVisible(false);
        
        setPreview(new File(path));
    }

    public void setSelectedFile(File file) {
        selectedFile = file;        
        btnChoose.setEnabled(false);
        btnCancel.setVisible(true);
        btnDelete.setVisible(false);
        
        setPreview(file);
    }
    
    public void setPreview(File file) {
        lbPreview.setVisible(true);

        try {
            BufferedImage image = ImageHelper.scaleImageMaxHeight(200, new File(file.getAbsolutePath()));
            lbPreview.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelSelected() {
        cancelSelected(false);
    }
    
    public void cancelSelected(boolean force) {
        selectedFile = null;
        btnCancel.setVisible(false);
        btnDelete.setVisible(false);
        btnChoose.setEnabled(true);
        lbPreview.setIcon(null);
        
        if (currentPath != null && !force) {
            setCurrentPath(currentPath);
        }

        if (currentPath != null && force) {
            currentPath = null;
        }
    }

    public void setPanelSize(Dimension defaultSize) {
        setPreferredSize(UIScale.scale(defaultSize));

        this.panelSize = defaultSize;

        repaint();
        revalidate();
    }
    
    public File getSelectedFile() {
        return selectedFile;
    }

    private class ChooseButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    } else {
                        String filename = f.getName().toLowerCase();
                        return filename.endsWith(".jpg")
                                || filename.endsWith(".jpeg")
                                || filename.endsWith(".png")
                                || filename.endsWith(".bmp");
                    }
                }

                @Override
                public String getDescription() {
                    return "Image Files (*.jpg, *.jpeg, *.png, *.bmp)";
                }
            });

            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                setSelectedFile(fileChooser.getSelectedFile());
            }
        }
    }
}
