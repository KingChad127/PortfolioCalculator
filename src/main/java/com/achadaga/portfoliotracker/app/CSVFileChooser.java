package com.achadaga.portfoliotracker.app;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class CSVFileChooser extends JFrame {

  private final JFileChooser fileChooser;

  public CSVFileChooser() {
    fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.setDialogTitle("Select a .csv file");
    FileNameExtensionFilter restrict = new FileNameExtensionFilter(".csv", "csv");
    fileChooser.addChoosableFileFilter(restrict);
  }

  public File openFile() {
    int response = fileChooser.showOpenDialog(null);
    if (response == JFileChooser.APPROVE_OPTION) {
      return new File(String.valueOf(fileChooser.getSelectedFile().getAbsoluteFile()));
    }
    return null;
  }

  public static void main(String[] args) {
    CSVFileChooser CSVFileChooser = new CSVFileChooser();
    File f = CSVFileChooser.openFile();
    System.out.println(f);
  }
}
