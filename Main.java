import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static final HashMap<String, Integer> fileSizeMap = new HashMap<>();

    private static final String[] formats = {".java",".c",".cpp",".glsl",".py", ".js"};
    private static final gfxVessel vessel = new gfxVessel();

    private static final String[] formatsWithIcons = {"Java☕", "C","C++","GLSL","Python\uD83D\uDC0D","JS"};
    public static void main(String[] args)  {
        File folder=null;
        JFileChooser jFileChooser = new JFileChooser();
        try {
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jFileChooser.showOpenDialog(vessel);
            File f = jFileChooser.getSelectedFile();


            listFilesForFolder(f);
            printMap();
        }catch (FileNotFoundException e){
            JOptionPane.showMessageDialog(vessel,"IncorrectPath");
        }
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
        int[] formatFiles = new int[formats.length];
        Iterator<String> iterator = (fileSizeMap.keySet()).iterator();

        int tmp;
        Iterator<Integer> iterator1 = fileSizeMap.values().iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()){
            String temp;
            files++;

            String str = String.format("%4d\t |%s\n",(tmp = iterator1.next()),(temp =iterator.next()));
            sb.append(str);
            System.out.print(str);

            total +=tmp;
            int i = 0;

            for (int j = 0; j < formats.length; j++) {
                if(temp.contains(formats[j])){
                    formatFiles[j]+=tmp;
                    break;
                }
            }

        }

        System.out.println("---------------------");
        System.out.println(files+ ((files!=1)?"\tFiles":" File")+ ",\t" + total +" Lines of code");
        sb.append(files).append((files != 1) ? " Files" : " File").append(",\t").append(total).append(" Lines of code");

        sb.append('\n').append('\n');

        for(int i = 0; i< formatFiles.length; i++){
            if(formatFiles[i] == 0){continue;}
            sb.append(formatsWithIcons[i]).append("\t").append(formatFiles[i]).append('\t');
                    sb.append((float) ((int)((100f*(float) formatFiles[i]/(float) total)*100))/100f).append("%").append("\n");

        }




        vessel.setText(sb.toString());
    }


    private static final class gfxVessel extends JFrame{
        private final JTextArea tf;
        public gfxVessel(){
            ScrollPane scrollPane = new ScrollPane();

            setBounds(0,0,640,480);

            JTextArea textField = new JTextArea();
            tf = textField;
            textField.setFont(new Font("Bahnscrift",Font.BOLD, 14));
            tf.setSelectionColor(new Color(66, 135, 245));
            tf.setBounds(0,0,640,480);
            scrollPane.add(textField);
            add(scrollPane);
            setVisible(true);
            tf.setEditable(false);
            setTitle("FileAnalyzer");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        }

        void setText(String Text){
            tf.setText(Text);




        }

    }


}

