package com.achadaga.portfoliocalculator.app;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class CSVFileChooser extends JFrame {

  private final JFileChooser fileChooser;

  /**
   * construct a CSVFileChooser to open and save CSV files
   */
  public CSVFileChooser() {
    fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.setDialogTitle("Select a .csv file");
    FileNameExtensionFilter restrict = new FileNameExtensionFilter(".csv", "csv");
    fileChooser.addChoosableFileFilter(restrict);
  }

  /**
   * Open the user selected CSV file
   * @return the file that the user selected
   */
  public File openFile() {
    int response = fileChooser.showOpenDialog(null);
    if (response == JFileChooser.APPROVE_OPTION) {
      return new File(String.valueOf(fileChooser.getSelectedFile().getAbsoluteFile()));
    }
    return null;
  }

  /**
   * Save the file in the selected directory
   * @return the file that the user is going to save
   */
  public File saveFile() {
    int response = fileChooser.showSaveDialog(null);
    if (response == JFileChooser.APPROVE_OPTION) {
      return new File(fileChooser.getSelectedFile().getAbsolutePath());
    }
    return null;
  }
}
