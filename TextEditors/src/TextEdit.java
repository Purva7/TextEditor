/**
 * 
 */

/**
 * @author Purva
 *
 */
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class TextEdit extends JFrame implements ActionListener {
  private JTextArea textArea = new JTextArea();
  private JMenu fileMenu = new JMenu("File");
  private JMenuBar menuBar = new JMenuBar();
  private JMenuItem newItem = new JMenuItem("New");
  private JMenuItem openItem = new JMenuItem("Open");
  private JMenuItem saveItem = new JMenuItem("Save");
  private JMenuItem saveAsItem = new JMenuItem("Save As");
  private JMenuItem exitItem = new JMenuItem("Exit");
  private String filename = null;  // set by "Open" or "Save As"

  public static void main(String args[]) {
    new TextEdit();
  }

  // Constructor: create a text editor with a menu
  public TextEdit() {
    super("Text Editor");

    // Create menu and add listeners
    fileMenu.add(newItem);
    fileMenu.add(openItem);
    fileMenu.add(saveItem);
    fileMenu.add(saveAsItem);
    fileMenu.add(exitItem);
    newItem.addActionListener(this);
    openItem.addActionListener(this);
    saveItem.addActionListener(this);
    saveAsItem.addActionListener(this);
    exitItem.addActionListener(this);
    menuBar.add(fileMenu);
    setJMenuBar(menuBar);

    // Create and display rest of GUI
    add(new JScrollPane(textArea));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(300, 300);
    setVisible(true);
  }

  // Handle menu events
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == newItem)
      textArea.setText("");
    else if (e.getSource() == openItem)
      loadFile();
    else if (e.getSource() == saveItem)
      saveFile(filename);
    else if (e.getSource() == saveAsItem)
      saveFile(null);
    else if (e.getSource() == exitItem)
      System.exit(0);
  }

  // Prompt user to enter filename and load file.  Allow user to cancel.
  // If file is not found, pop up an error and leave screen contents
  // and filename unchanged.
  private void loadFile() {
    JFileChooser fc = new JFileChooser();
    String name = null;
    if (fc.showOpenDialog(null) != JFileChooser.CANCEL_OPTION)
      name = fc.getSelectedFile().getAbsolutePath();
    else
      return;  // user cancelled
    try {
      Scanner in = new Scanner(new File(name));  // might fail
      filename = name;
      textArea.setText("");
      while (in.hasNext())
        textArea.append(in.nextLine() + "\n");
      in.close();
    }
    catch (FileNotFoundException e) {
      JOptionPane.showMessageDialog(null, "File not found: " + name,
        "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  // Save named file.  If name is null, prompt user and assign to filename.
  // Allow user to cancel, leaving filename null.  Tell user if save is
  // successful.
  private void saveFile(String name) {
    if (name == null) {  // get filename from user
      JFileChooser fc = new JFileChooser();
      if (fc.showSaveDialog(null) != JFileChooser.CANCEL_OPTION)
        name = fc.getSelectedFile().getAbsolutePath();
    }
    if (name != null) {  // else user cancelled
      try {
        Formatter out = new Formatter(new File(name));  // might fail
        filename = name;
        out.format("%s", textArea.getText());
        out.close();
        JOptionPane.showMessageDialog(null, "Saved to " + filename,
          "Save File", JOptionPane.PLAIN_MESSAGE);
      }
      catch (FileNotFoundException e) {
        JOptionPane.showMessageDialog(null, "Cannot write to file: " + name,
          "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
