package com.achadaga.portfoliotracker.app;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

public class FileChooser extends JFrame {

  JFileChooser j;

  public FileChooser() {
    j = new JFileChooser(FileSystemView.getFileSystemView());
    j.setFileSelectionMode(JFileChooser.FILES_ONLY);
  }

  public File openFile() {
    int response = j.showOpenDialog(null);
    if (response == JFileChooser.APPROVE_OPTION) {
      File f = new File(String.valueOf(j.getSelectedFile().getAbsoluteFile()));
      System.out.println(f);
    }
    return null;
  }
}
