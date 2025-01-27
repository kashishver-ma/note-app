import javax.swing.text.AttributeSet;

import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.undo.UndoManager;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

//import javafx.scene.image.Image;
//import javafx.scene.text.Font;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.security.KeyStore;
import java.io.*;

public class Noteapp extends JFrame implements MouseListener, ActionListener {

    JTextPane textpane;
    String text="";
    JButton imagebutton; 
    JFileChooser fileChooser; 
    JToggleButton themeToggleButton; // Toggle button for theme
    boolean isDarkTheme = false;
    JMenuBar menubar;
    JLabel headerLabel;
    JSlider zoomSlider;
    JPanel southPanel;
    Color selectedColor ;
    private UndoManager undoManager;
    String lighttheme ;


    Noteapp(){
        setTitle("NoteApp");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon noteappi=new ImageIcon(ClassLoader.getSystemResource("myicon.jpeg"));
        Image icon=noteappi.getImage();
        setIconImage(icon);

      
        //JPanel bottomPanel = new JPanel();
        //bottomPanel.setPreferredSize(new Dimension(15, 15));

        //bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Align button to the right
        //bottomPanel.setBackground(null); // Set background color

        /*imagebutton = new JButton("ADD Image");
        ImageIcon imicon=new ImageIcon("nai.png");
        imagebutton.setIcon(imicon);
        int buttonwidth=imicon.getIconWidth();
        int buttonheight=imicon.getIconHeight();
        imagebutton.setPreferredSize(new Dimension(buttonwidth-20, buttonheight)); // Set preferred size for the button
        imagebutton.setBounds(1100,500,buttonwidth-40,buttonheight-40);
        imagebutton.addActionListener(this);
        add(imagebutton);
*/
        textpane=new JTextPane();
        textpane.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
        

        textpane.setFont(new Font("SAN_SERIF",Font.PLAIN,20));
        JScrollPane scrollpane=new JScrollPane(textpane);
        add(scrollpane,BorderLayout.CENTER);

        /* 
        area=new JTextArea();
        setFont(new java.awt.Font("SAN_SERIF",Font.PLAIN,20));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        add(area);
      
        add(pane,BorderLayout.CENTER);
        */
        
        headerLabel = new JLabel("......Collect Your Thoughts......");
        Font font = new Font("Blackadder ITC", Font.BOLD, 24);
        headerLabel.setFont(font);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(221,221,245));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(headerLabel, BorderLayout.NORTH);
        
        // Create toggle button for theme
       
      //  setLayout((new FlowLayout()));
    //  themeToggleButton.setOpaque(false);
      themeToggleButton = new JToggleButton();
        themeToggleButton.setOpaque(false);
        themeToggleButton.setContentAreaFilled(false);
        themeToggleButton.setBorderPainted(false);
        lighttheme="dark.png";
        ImageIcon icontheme = new ImageIcon(lighttheme);
        themeToggleButton.setIcon(icontheme);

        // Get the size of the button
        //Dimension buttonSize = themeToggleButton.getPreferredSize();

        // Scale the icon to match the button's size
        Image scaledIcon = icontheme.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH);
        ImageIcon scaledIconImage = new ImageIcon(scaledIcon);

        // Set the scaled icon for the button
        themeToggleButton.setIcon(scaledIconImage);
        
        themeToggleButton.addActionListener(this);
        themeToggleButton.setFocusPainted(false);
      //  themeToggleButton.setPreferredSize(new Dimension(40,10));
        //toggle button to the header label
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.add(themeToggleButton, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);





        setLocationRelativeTo(null);

        southPanel = new JPanel(new BorderLayout());
        zoomSlider = new JSlider(JSlider.HORIZONTAL, 10, 60, 20);
        zoomSlider.setMajorTickSpacing(10);
        zoomSlider.setMinorTickSpacing(5);
        zoomSlider.setPaintTicks(true);
       // zoomSlider.setPaintLabels(true);
        southPanel.setOpaque(true);

        southPanel.setBackground(new Color(221,221,255));
        southPanel.add(zoomSlider, BorderLayout.EAST);
        
        add(southPanel, BorderLayout.SOUTH);
     
        ChangeListener zoomChangeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                /*int value = zoomSlider.getValue();
                if (value == zoomSlider.getMinimum()) {
                    zoomSlider.setLabelTable(zoomSlider.createStandardLabels(1)); // Show tick at the minimum value
                } else if (value == zoomSlider.getMaximum()) {
                    zoomSlider.setLabelTable(zoomSlider.createStandardLabels(1)); // Show tick at the maximum value
                } else if  (value == 20) 
                    {zoomSlider.setLabelTable(zoomSlider.createStandardLabels(2));
                     // Remove tick when value is not at minimum or maximum
                } else {
               zoomSlider.setLabelTable(null);
                }*/
                int zoomLevel = zoomSlider.getValue();
                textpane.setFont(textpane.getFont().deriveFont((float) zoomLevel));
            }
        };
        
        zoomSlider.addChangeListener(zoomChangeListener);



        menubar=new JMenuBar(); //create menu
        menubar.setBackground(new Color(221,221,255));
        menubar.setPreferredSize(new Dimension(10, 20));

        JMenu file=new JMenu("File"); //menu item 1
        //file.setFont(new Font("AERIAL",Font.PLAIN,14));//cfont fmily,type,size
        file.addMouseListener(this);
        
        
        menubar.add(file); //add menu in menubar

        JMenuItem New=new JMenuItem("New");
        New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
        New.addActionListener(this);
        JMenuItem Open=new JMenuItem("Open");
        Open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        Open.addActionListener(this);
        JMenuItem Save=new JMenuItem("Save");
        Save.addActionListener(this);
        Save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        JMenuItem filemenu4=new JMenuItem("Exit");
        filemenu4.addActionListener(this);
        filemenu4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK));

        

        file.add(New);
        file.add(Open);
        file.add(Save);
        file.add(filemenu4);
        

        JMenu edit=new JMenu("Edit"); //menu item 2
        menubar.add(edit); //add menu in menubar
        edit.addMouseListener(this);

        JMenuItem undo=new JMenuItem("Undo");
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));
        undoManager = new UndoManager();
        undo.addActionListener(this);

        JMenuItem redo=new JMenuItem("Redo");
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,ActionEvent.CTRL_MASK));
        redo.addActionListener(this);
   

        JMenuItem select=new JMenuItem("Select All");
        select.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
        select.addActionListener(this);
    
        JMenuItem copy=new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
        copy.addActionListener(this);        

        JMenuItem paste=new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
        paste.addActionListener(this);

        edit.add(undo);
        edit.add(redo);
        edit.add(select);
        edit.add(copy);
        edit.add(paste);

        JMenu insert=new JMenu("Insert"); //menu item 3
        menubar.add(insert);
        insert.addMouseListener(this);

        JMenuItem snap=new JMenuItem("Snap");
        snap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.CTRL_MASK));
        snap.addActionListener(this);
        insert.add(snap);

        JMenuItem notes=new JMenuItem("Stickie");
        notes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,ActionEvent.CTRL_MASK));
        notes.addActionListener(this);
        insert.add(notes);

       
        JMenuItem colour = new JMenuItem("Color");
        colour.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));

        colour.addActionListener(new ActionListener() {
            JFrame colourframe = new JFrame();
            {
                colourframe.setUndecorated(true);
                colourframe.setIconImage(null);
                ImageIcon noteappi=new ImageIcon(ClassLoader.getSystemResource("myicon.jpeg"));
                Image icon=noteappi.getImage();
                colourframe.setIconImage(icon);
            
            }
            public void actionPerformed(ActionEvent e) {
                selectedColor = JColorChooser.showDialog(colourframe , "Color chooser", Color.BLACK);
                if (selectedColor != null) {
                    textpane.setForeground(selectedColor);
                }
            }
        });
        insert.add(colour);
        
        JMenu help=new JMenu("Help");
        JMenuItem about=new JMenuItem("About");
        about.addActionListener(this);
        help.add(about);
        menubar.add(help);
        
        setJMenuBar(menubar);//add menubar in screen
        
        setVisible(true);

     }
  

     @Override
     public void mouseReleased(MouseEvent e) {}

     @Override
     public void mouseClicked(MouseEvent e) {}
    
     @Override
     public void mousePressed(MouseEvent e) {}
    
     
     @Override
     public void mouseEntered(MouseEvent e) {
         JMenuItem item = (JMenuItem) e.getSource();
         item.setFont(item.getFont().deriveFont(Font.BOLD)); // Change font style
     }
 
     @Override
     public void mouseExited(MouseEvent e) {
         JMenuItem item = (JMenuItem) e.getSource();
         item.setForeground(Color.BLACK); // Reset text color
         item.setFont(item.getFont().deriveFont(Font.PLAIN)); // Reset font style
     } private void savetofile() {
        JFileChooser saveChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Styled Document Files", "ser");
        saveChooser.setFileFilter(filter);
        int option = saveChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File fileToSave = saveChooser.getSelectedFile();
            if (!fileToSave.getName().endsWith(".ser")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".ser");
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                oos.writeObject(textpane.getStyledDocument());
                JOptionPane.showMessageDialog(this, "File saved successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadfromfile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Styled Document Files", "ser");
        chooser.setFileFilter(filter);
        int action = chooser.showOpenDialog(this);
        if (action != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            StyledDocument doc = (StyledDocument) ois.readObject();
            textpane.setStyledDocument(doc);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error opening file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




     @Override
        public void actionPerformed(ActionEvent ae){
            if (ae.getActionCommand().equals("New")){

                  if (!textpane.getText().isEmpty()) {
        // Prompt the user with a dialog box to confirm if they want to save existing data
        int option = JOptionPane.showConfirmDialog(this, "Do you want to save existing data?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // If the user chooses to save, trigger the "Save" action
            savetofile();
        } else if (option == JOptionPane.NO_OPTION) {
            // If the user chooses not to save, clear the text area
            textpane.setText("");
        } else {
            // If the user cancels, do nothing
            return;
        }
    } else {
        // If there is no existing data, simply clear the text area
        textpane.setText("");
    }
}   else if (ae.getActionCommand().equals("Save")){
   savetofile();
}
            else if (ae.getActionCommand().equals("Open")){
                loadfromfile();
         }else if (ae.getActionCommand().equals("Paste")){
        if (!text.isEmpty()) {
            int caretPosition = textpane.getCaretPosition();
            textpane.replaceSelection(text);
            textpane.setCaretPosition(caretPosition + text.length());
        }

    } else if (ae.getActionCommand().equals("Undo")) {
        if (undoManager.canUndo()) {
            System.out.println("undo");
            undoManager.undo();
        }
    } else if (ae.getActionCommand().equals("Redo")) {
        System.out.println("redo");
        if (undoManager.canRedo()) {
            undoManager.redo();
        }
    }
    else if  (ae.getActionCommand().equals("Select All"))
        {System.out.println("Select All");
    }  else if (ae.getActionCommand().equals("Snap")){
            fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image Files", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);
    
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                ImageIcon imageiccon=new ImageIcon(selectedFile.getAbsolutePath());
                textpane.insertIcon(imageiccon);
            }
        }else if  (ae.getActionCommand().equals("Stickie")){
            
    JTextPane notePane = new JTextPane();
    notePane.setFont(new Font("SansSerif", Font.BOLD, 25)); // Set initial font style and size
    notePane.setBackground(new Color(214, 205, 234));
    
    // Add custom document filter for bold text and size increase
    ((AbstractDocument) notePane.getDocument()).setDocumentFilter(new DocumentFilter() {
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            super.insertString(fb, offset, string, attr);
            if (string.equals("\n")) {
                notePane.setCaretPosition(offset);
            }
        }

        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            super.replace(fb, offset, length, text, attrs);
            notePane.setCaretPosition(offset + text.length());
        }
    });

    // Add Enter key listener to jump out of the text field
    notePane.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                textpane.requestFocusInWindow();
            }
        }
    });

    /*notePane.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("clicked in stickie");
            if (e.getClickCount() == 2) { // Detect double-click
                int choice = JOptionPane.showConfirmDialog(Noteapp.this, "Delete this sticky note?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    // Delete the sticky note if confirmed
                    Container parent = notePane.getParent();
                    if (parent instanceof JTextPane) {
                        JTextPane textPaneParent = (JTextPane) parent;
                        textPaneParent.remove(notePane);
                       // textPaneParent.revalidate();
                        textPaneParent.repaint();
                }
            }
        }
    });
*/
    textpane.insertComponent(notePane);


        }else if (ae.getActionCommand().equals("About")){
            System.out.println("in about");
            new noteabut();

        }else if (ae.getSource() == themeToggleButton) {
                isDarkTheme = !isDarkTheme;
                System.out.println("Toggle button clicked");
                 updateTheme();
            }
        }
        
        private void updateTheme() {
            Color backgroundColor;
            Color textColor;
            Color labells;
            Color labell;
            Color menuc;
            Color southpan;
            

            if (isDarkTheme) {
                backgroundColor = new Color(10,10,10);
                textColor = selectedColor;
                labells =Color.WHITE ;
                labell=new Color(166,166,227) ;
                menuc = new Color(166,166,227);
                textpane.setCaretColor(Color.WHITE);
                southpan= new Color(166,166,227);
                lighttheme="suny.png";
               
            } else {
                backgroundColor = Color.WHITE;
                textColor = selectedColor;
                labells =Color.BLACK;
                labell=new Color(221,221,245) ;
                menuc=new Color(221,221,255);
                textpane.setCaretColor(Color.BLACK);
                southpan= new Color(221,221,255);
                lighttheme="dark.png";
         
            }
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(lighttheme));
            Image scaledIcon = icon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH);
            ImageIcon scaledIconImage = new ImageIcon(scaledIcon);
            themeToggleButton.setIcon(scaledIconImage);
            getContentPane().setBackground(backgroundColor);
            textpane.setForeground(textColor);
            textpane.setBackground(backgroundColor);
            headerLabel.setForeground(labells);
            headerLabel.setBackground(labell);
            menubar.setBackground(menuc);
            southPanel.setBackground(southpan);
        }
        
    
         public static void main(String[] args){
            new Noteapp();   
           }
       }
    
        
    
