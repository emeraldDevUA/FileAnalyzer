import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    String path = "";
    static HashMap<String, Integer> fileSizeMap = new HashMap<>();
    static String[] formats = {".java",".c",".cpp",".glsl",".py", ".js"};
    static gfxVessel vessel = new gfxVessel();

    public static void main(String[] args) throws FileNotFoundException {
         File folder=null;
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jFileChooser.showOpenDialog(vessel);
        File f = jFileChooser.getSelectedFile();


        listFilesForFolder(f);
        printMap();

    }

    public static void listFilesForFolder(File folder) throws FileNotFoundException {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                //System.out.println(fileEntry.getName());
                for (int i = 0; i <formats.length ; i++) {
                    if(formats[i].contains(fileEntry.getName()
                            .substring(fileEntry.getName().lastIndexOf(".")))){

                        Scanner sc = new Scanner(new FileInputStream(fileEntry.getAbsolutePath()));
                        int value = 0;
                        while (sc.hasNext()){
                            value +=1;
                           // System.err.println();
                            sc.nextLine();
                        }

                        fileSizeMap.putIfAbsent(fileEntry.getName(),value);
                    }
                }

            }
        }
    }

    public static void printMap(){
        int total = 0;
        int files = 0;
        Iterator<String> iterator = fileSizeMap.keySet().iterator();
        int tmp;
        Iterator<Integer> iterator1 = fileSizeMap.values().iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()){
            files++;

            String str = String.format("%20s\t%4d\n",iterator.next(),(tmp = iterator1.next()));
            sb.append(str);
            System.out.print(str);

            total +=tmp;
        }

        System.out.println("---------------------");
        System.out.println(files+ ((files!=1)?" Files":" File")+ ",\t" + total +" Lines of code");
        sb.append(files).append((files != 1) ? " Files" : " File").append(",\t").append(total).append(" Lines of code");

        vessel.setText(sb.toString());
    }


    private static final class gfxVessel extends JFrame{
        JTextArea tf;
        public gfxVessel(){
            ScrollPane scrollPane = new ScrollPane();

            setBounds(0,0,640,480);

            JTextArea textField = new JTextArea();
            tf = textField;
            textField.setFont(new Font("Bahnscrift",Font.BOLD, 14));

            tf.setBounds(0,0,640,480);
            scrollPane.add(textField);
            add(scrollPane);
            setVisible(true);





        }

        void setText(String Text){
            tf.setText(Text);




        }

    }


}


