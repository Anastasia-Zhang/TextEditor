/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor;


import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.List;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

/**
 *
 * @author Annie
 */
public class AppFrame extends JFrame//Can not be Frame
{

    private static void setEnable(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    JTextPane tp=new JTextPane();//area can only change the color for whole fill
    String word="讍齎蘄";
    String path=null;
    int mark=0;
    String fontName;
    int styleName,sizeName;
    StyledDocument sd=tp.getStyledDocument();
    AppFrame()
    {
        tp.getDocument().addDocumentListener(new highLight(tp));
        setSize(1000,800);
        add(tp);
        JMenuBar mb=new JMenuBar();
        //File
        JMenu file=new JMenu("File(F)");
        mb.add(file);
        JMenuItem xnew=new JMenuItem("New");
        JMenuItem open=new JMenuItem("Open");
        JMenuItem save=new JMenuItem("Save");
        JMenuItem saveAs=new JMenuItem("Save As");
        JMenuItem exit=new JMenuItem("Exit");
        
        file.add(xnew);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.add(exit);
        //Edit
        JMenu edit=new JMenu("Edit(E)");
        mb.add(edit);
        JMenuItem cut=new JMenuItem("Cut");
        JMenuItem copy=new JMenuItem("Copy");
        JMenuItem paste=new JMenuItem("Paste");
        JMenuItem delete=new JMenuItem("Delete");
        JMenuItem find=new JMenuItem("Find");
        JMenuItem replace=new JMenuItem("Replace");
        
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(delete);
        edit.add(find);
        edit.add(replace);
        
        //Format
        JMenu format=new JMenu("Format(O)");
        mb.add(format);
        JMenuItem font=new JMenuItem("Font");
        JMenuItem color=new JMenuItem("Color");
        
        format.add(font);
        format.add(color);
        //View
        /*JMenu view=new JMenu("View");
        mb.add(view);
        JMenuItem status=new JMenuItem("Status Bar");
        
        view.add(status);
        */
        //Help
        JMenu help=new JMenu("Help(H)");
        JMenuItem xhelp=new JMenuItem("Help");
        JMenuItem sv=new JMenuItem("Software Version");
        help.add(xhelp);
        help.add(sv);
        
        mb.add(help);
        //
        setJMenuBar(mb);
        //Shortcut Key
        file.setMnemonic('F');
        edit.setMnemonic('E');
        format.setMnemonic('O');
        help.setMnemonic('H');
        xnew.setAccelerator(KeyStroke.getKeyStroke('N',InputEvent.CTRL_MASK));
        open.setAccelerator(KeyStroke.getKeyStroke('O',InputEvent.CTRL_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke('S',InputEvent.CTRL_MASK));
        cut.setAccelerator(KeyStroke.getKeyStroke('X',InputEvent.CTRL_MASK));
        copy.setAccelerator(KeyStroke.getKeyStroke('C',InputEvent.CTRL_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke('V',InputEvent.CTRL_MASK));
        find.setAccelerator(KeyStroke.getKeyStroke('F',InputEvent.CTRL_MASK));
        replace.setAccelerator(KeyStroke.getKeyStroke('H',InputEvent.CTRL_MASK));
        font.setAccelerator(KeyStroke.getKeyStroke('F',InputEvent.CTRL_MASK));
        //New Listener
        xnew.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                newDocument();
            }
        });
        open.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               openOptions();
            }
        });
        
        //Exit Listener
        exit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                System.exit(0);
            }
            
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                saveDocument();
            }
        });
        saveAs.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAsDocument();
            }
            
        });
        copy.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Clipboard cb=getToolkit().getSystemClipboard();//Use the system clipboard
                if(e.getSource()==copy)//when you click the copy
                {
                    String temp=tp.getSelectedText();//get the word you choose
                    StringSelection text=new StringSelection(temp);//send it as a no format style
                    cb.setContents(text, null);//transfer contents&owner   
                    //put the data into the clipboard
                }
            }
        });
        paste.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard cb=getToolkit().getSystemClipboard();
                if(e.getSource()==paste)
                {
                    Transferable con=cb.getContents(this);//get the data from the clipboard and send it to con
                    DataFlavor flavor=DataFlavor.stringFlavor;//connect Java with my computer
                    //Judge if the con is the data we want
                    if(con.isDataFlavorSupported(flavor))
                    {
                        try
                        {
                            String str;              
                            str=(String)con.getTransferData(flavor); //send the data we judged into str
                            append(str);//put it in TextEditor
                        } catch (UnsupportedFlavorException ex) {
                            Logger.getLogger(AppFrame.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(AppFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        cut.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard cb=getToolkit().getSystemClipboard();
                if(e.getSource()==cut)
                {
                    String temp=tp.getSelectedText();
                    StringSelection text=new StringSelection(temp);
                    cb.setContents(text,null);
                    //get the position of the word
                    tp.replaceSelection("");
                }
                
            }
            
        });
        delete.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==delete)
                {
                    String temp=tp.getSelectedText();
                    tp.replaceSelection("");
                }
            }
            
        });



        find.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                mark=0;
                JFrame jf=new JFrame("Find");
                jf.setBounds(200,200,600,300);
                jf.setLayout(null);
                jf.setVisible(true);
                
                JLabel find=new JLabel("Find");
                find.setBounds(20,20, 50,50);
                find.setFont(new Font("宋体",Font.BOLD,16));
                jf.add(find);
                
                JTextField tf=new JTextField();
                tf.setBounds(90,35,180,25);
                jf.add(tf);
                
                JButton jb1=new JButton("Find Next");
                jb1.setBounds(300,35,90,25);
                jf.add(jb1);
                JButton jb2=new JButton("Cancel");
                jb2.setBounds(300,75,90,25);
                jf.add(jb2);
                
                jb1.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        String key=tf.getText();
                        find(key);
                    }             
                });           
            }
            
        });
        replace.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jf=new JFrame("Replace");
                jf.setBounds(200,200,600,300);
                jf.setVisible(true);
                jf.setLayout(null);
                
                JLabel find=new JLabel("Find");
                find.setBounds(20,20, 80,50);
                find.setFont(new Font("宋体",Font.BOLD,16));
                jf.add(find);
                JLabel replace=new JLabel("Replace");
                replace.setBounds(20,90,80,50);
                replace.setFont(new Font("宋体",Font.BOLD,16));
                jf.add(replace);
             
                JTextField tf1=new JTextField();
                tf1.setBounds(110,35,180,25);
                jf.add(tf1);
                JTextField tf2=new JTextField();
                tf2.setBounds(110,105,180,25);
                jf.add(tf2);
                
                JButton jb1=new JButton("Find Next");
                jb1.setBounds(300,35,90,25);
                jf.add(jb1);
                JButton jb2=new JButton("Replace");
                jb2.setBounds(300,105,90,25);
                jf.add(jb2);
                
                jb1.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        String key=tf1.getText();
                        find(key);
                    }             
                });
                
                jb2.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Pattern find=Pattern.compile(tf1.getText());
                        Matcher replace=find.matcher(tp.getText());
                        tp.setText(replace.replaceAll(tf2.getText()));
                    }
                    
                });
  
            }
            
        });
        /*************************************************************/
        font.addActionListener(new ActionListener()
        {
            
            @Override
            @SuppressWarnings("empty-statement")
            public void actionPerformed(ActionEvent e) 
            {
                JFrame jf=new JFrame("Font");
                jf.setLayout(null);
                jf.setBounds(200,200,500,500);
                jf.setVisible(true);
                JLabel l1=new JLabel("Font");
                JLabel l2=new JLabel("Style");
                JLabel l3=new JLabel("Size");
                l1.setBounds(30,10,40,30);
                l2.setBounds(180,10,40,30);
                l3.setBounds(330,10,40,30);
                
                JButton b1=new JButton("OK");
                JButton b2=new JButton("Cancel");
                b1.setBounds(330,280,95,45);
                b2.setBounds(330,340,95,45);
                
                JTextField t1=new JTextField("Arial");
                JTextField t2=new JTextField("BOLD");
                JTextField t3=new JTextField("10");
                t1.setBounds(10,40,130,25);
                t2.setBounds(160,40,130,25);
                t3.setBounds(310,40,130,25);
                
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                String[] fontNames = ge.getAvailableFontFamilyNames();
                JList font=new JList(fontNames);
                JScrollPane sfont=new JScrollPane(font);
                sfont.setBounds(10,70,130,180);
                font.setSelectedValue("Arial", true);
       
                String []styleList={"PLAIN","BOLD","ITALIC","BOLD+ITALIC"};
                JList style=new JList(styleList);
                JScrollPane sstyle=new JScrollPane(style);
                sstyle.setBounds(160,70,130,180);
                
                String[] sizeList = new String[]{"8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72",
            "初号", "小初", "一号", "小一", "二号", "小二", "三号", "小三", "四号", "小四", "五号", "小五", "六号", "小六", "七号", "八号"};
                JList size=new JList(sizeList);
                JScrollPane ssize=new JScrollPane(size);
                ssize.setBounds(310,70,130,180);
                
                JPanel show=new JPanel();
                show.setBorder(javax.swing.BorderFactory.createTitledBorder("示例"));
                show.setBounds(10,280,280,120);
                JLabel mark=new JLabel("From BiuBiuBiu");
                mark.setBackground(Color.white);
                show.add(mark);
               
                int sizeVal[] = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72, 42, 36, 26, 24, 22, 18, 16, 15, 14, 12, 11, 9, 8, 7, 6, 5};
                Map sizeM=new HashMap();
                for (int i = 0; i < sizeList.length; ++i)
                {
                        sizeM.put(sizeList[i], sizeVal[i]);
                }
                
                jf.add(l1);
                jf.add(l2);
                jf.add(l3);
                jf.add(t1);
                jf.add(t2);
                jf.add(t3);
                jf.add(sfont);
                jf.add(sstyle);
                jf.add(ssize);
                jf.add(show);
                jf.add(b1);
                jf.add(b2);
                
                font.addListSelectionListener(new ListSelectionListener()
                {
                    @Override
                    public void valueChanged(ListSelectionEvent e)
                    {
                        fontName =(String) font.getSelectedValue();
                        t1.setText(fontName);
                        mark.setFont(new Font(fontName, styleName, sizeName));
                    }
                });
                
                style.addListSelectionListener(new ListSelectionListener()
                {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        String value = (String) ((JList) e.getSource()).getSelectedValue();
                        if (value.equals("PLAIN")) 
                        {
                            styleName = Font.PLAIN;
                        }
                        if (value.equals("ITALIC")) 
                        {
                            styleName= Font.ITALIC;
                        }
                        if (value.equals("BOLD"))
                        {
                            styleName = Font.BOLD;
                        }
                        if (value.equals("BOLD+ITALIC")) 
                        {
                            styleName = Font.BOLD | Font.ITALIC;
                        }
                        t2.setText(value);
                        mark.setFont(new Font(fontName, styleName, sizeName));

                    }
                    
                });
                
                size.addListSelectionListener(new ListSelectionListener()
                { 
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        sizeName = (Integer) sizeM.get(size.getSelectedValue());
                        t3.setText(String.valueOf(sizeName));
                        mark.setFont(new Font(fontName, styleName, sizeName));
                    }
                    
                });
                b1.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        tp.setFont(new Font(fontName, styleName, sizeName));
                        jf.dispose();
                    }
                    
                });
                b2.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        jf.dispose();
                    }
                    
                });
                        
            }
        });
              
        color.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                JColorChooser cc=new JColorChooser();
                Color c=cc.showDialog(AppFrame.this,"Color",tp.getSelectionColor());
                new StyledEditorKit.ForegroundAction("",c).actionPerformed(e);
            }
            
        });
        sv.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String path="1.jpg";
                JDialog jd=new JDialog();
                jd.setTitle("Software Version");
                jd.setSize(500,450);
                jd.setLocation(450, 200);
                jd.setVisible(true);
                JLabel jl=new JLabel();
                ImageIcon ii=new ImageIcon(path);
                jl.setIcon(ii);
                jd.add(jl);
            }
            
        });
         AppFrame.this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindowInfo();
            }
        });
        
        
    }
    void closeWindowInfo()
    {
         setDefaultCloseOperation(EXIT_ON_CLOSE);
        if(!word.equals(tp.getText())&&!tp.getText().equals("")){
    
            int option=JOptionPane.showConfirmDialog(null,
                "This txt didn`t be saved.Do you want to save it?", 
                "Warning",
                JOptionPane.YES_NO_CANCEL_OPTION);
            
            switch(option){
                case JOptionPane.YES_OPTION:saveAsDocument();break;
                case JOptionPane.NO_OPTION:
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                    break;
                case JOptionPane.CANCEL_OPTION:
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    break;
            }
                 
    }
    }

    void newDocument()
    {
        int n=JOptionPane.showConfirmDialog(null,"Save it or not", null, 
                JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
        switch(n)
        {
            case 0:
                saveDocument();
                tp.setText("");
                break;
            case 1:
                tp.setText("");
                break;
            case 2:
                break;
       }
    }
    void openOptions()
    {
        int n;
        if(!tp.getText().equals("")&&!word.equals(tp.getText()))
        {
            n=JOptionPane.showConfirmDialog(null,"Save it or not", null, 
                JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
            switch(n)
           {
                case 0:
                    saveDocument();
                    openFile();
                    break;
                case 1:
                    openFile();
                    break;
                case 2:
                    word=tp.getText();
                    break;
                }
            }
        else
        {
            openFile();
        }
    }
    void append(String insert)
    {
        Document doc=tp.getDocument();
        if(doc!=null)
            try {
                doc.insertString(doc.getLength(), insert, null);
        } catch (BadLocationException ex) {
                System.out.println("Can't find the location");
        }
    }
  void openFile()
    {
        tp.setText("");
        FileDialog fd=new FileDialog(this,"Open",FileDialog.LOAD);
        fd.setVisible(true);
        path=fd.getDirectory()+fd.getFile();
        if(!path.equals("nullnull"))//very important
        {
            try {
                FileReader fr = new FileReader(path);
                BufferedReader br=new BufferedReader(fr);
                String input="";//very important
                while((input=br.readLine())!=null)
                {
                    append(input+"\n");
                }
                word=tp.getText();
            } catch (FileNotFoundException ex) {
                System.out.println("Can't find the file!");
            } catch (IOException ex) {
                System.out.println("Can't read from the file!");
            }
           
        }
    }
    void saveDocument()
    {
        try {
            if(path!=null)
            {
                FileWriter fw=new FileWriter(path);
                BufferedWriter bw=new BufferedWriter(fw);
                bw.write(tp.getText());
                bw.flush();
                bw.close();
                word=tp.getText();
            }
            else
            {
                saveAsDocument();
            }
        } catch (IOException ex) {
            System.out.println("Can;t save the file");
        }
        word=tp.getText();
    }
    void saveAsDocument()
    {
        FileDialog fd=new FileDialog(this,"Save As",FileDialog.SAVE);
        fd.setFile(".txt");
        fd.setVisible(true);
        try {
            FileWriter fw=new FileWriter(fd.getDirectory()+fd.getFile());
            BufferedWriter bw=new BufferedWriter(fw);
            bw.write(tp.getText());
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            System.out.println("Can't save the file");
        }
        word=tp.getText();
        
    }
    void find(String key)
    {
        int head=tp.getText().indexOf(key,mark);
        mark=head+key.length();
        tp.setSelectionStart(head);
        tp.setSelectionEnd(mark);
    }
            }
class highLight implements DocumentListener
{
    Style  key;
    Style  normal;
    Set <String> table;
    highLight(JTextPane tp)
    {
        String []KeyWords={"abstract" ,"assert" ,"boolean","break" ,"byte","case" ,"catch" ,"char" ,"class" ,
"const" ,"continue" ,"default" ,"do","double","else" ,"enum" ,"extends" ,"final" ,"finally" ,
"float" ,"for" ,"goto" ,"if" ,"implements" ,"import" ,"instanceof" ,"int" ,"interface" ,"long" ,
"native" ,"new" ,"package" ,"private" ,"protected" ,"public" ,"return" ,"strictfp" ,"short" ,"static",
"super" ,"switch" ,"synchronized" ,"this" ,"throw" ,"throws","transient" ,"try" ,"void" ,"volatile","while"};
        table= new HashSet<String>();
        for(int i=0;i<50;i++)
        {
            table.add(KeyWords[i]);
        }
        key=((StyledDocument) tp.getDocument()).addStyle("Key", null);
        normal=((StyledDocument)tp.getDocument()).addStyle("Normal",null);
        StyleConstants.setForeground(key, Color.BLUE);
        StyleConstants.setForeground(normal,Color.BLACK);
    }
   public char getCursor(Document doc, int cursor) throws BadLocationException 
   {
        return doc.getText(cursor,1).charAt(0);
    }
 
    public boolean wordOrNot(Document doc, int cursor) 
    {
        char c = ' ';
        try {
            c = getCursor(doc, cursor);
        } catch (BadLocationException ex) {
            System.out.println(ex);
        }
        if (Character.isLetter(c) )
           return true; 
        else
            return false;
    }
        public int start(Document doc,int cursor)
        {
            while(cursor>0&&wordOrNot(doc,cursor-1))
                --cursor;
            return cursor;
        }
        public int stop(Document doc,int cursor)
        {
            while(wordOrNot(doc,cursor))
                ++cursor;
            return cursor;
        }
        public int changeColor(StyledDocument sd,int cursor)
        {
            String mark=" ";
            int end=stop(sd,cursor);
            try {
                mark=sd.getText(cursor, end-cursor);
             } catch (BadLocationException ex) {
            System.out.println(ex);
        }
            if(table.contains(mark))
            {
                SwingUtilities.invokeLater(new ColouringTask(sd, cursor, end - cursor, key));
            } else {
                SwingUtilities.invokeLater(new ColouringTask(sd,cursor,end-cursor,normal));
            }
            return end;
        }
             
        class ColouringTask implements Runnable
        {
            StyledDocument sd;
            Style style;
            int length;
            int location;
            public ColouringTask(StyledDocument sd,int location,int length,Style style)
            {
                this.sd = sd;
                this.style =style;
                this.length = length;
                this.location=location;
            }
            @Override
        public void run() {
            sd.setCharacterAttributes(location, length,style, true);
        }  
        }
         void colorOrNot(StyledDocument sd,int cursor,int length)
            {
                int first=start(sd,cursor);
                int end=stop(sd,cursor+length);
                char c=' ';
                while(first<end)
                {
                    try {
                        c=getCursor(sd,first);
                    } catch (BadLocationException ex) {
                        System.out.println("c");
                    }
                    if(Character.isLetter(c))
                    {
                        first=changeColor(sd,first);
                    }
                    else
                        ++first;
                }
            }
    
    @Override
    public void insertUpdate(DocumentEvent e) {
        colorOrNot((StyledDocument)e.getDocument(),e.getOffset(),e.getLength());
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        colorOrNot((StyledDocument)e.getDocument(),e.getOffset(),0);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        
}

            
            

             
        }