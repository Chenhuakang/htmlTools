import java.io.*;
import java.util.List;

class HTMLFilterAll implements FilenameFilter {
    public boolean accept(File directory, String name) {
        if (name.endsWith(".html")) return true;
        if (name.endsWith(".htm")) return true;
        if (name.endsWith(".HTML")) return true;
        if (name.endsWith(".HTM")) return true;
        return false;
    }
}

class BatchEncoderAll {
    String oldFile = null;

    public BatchEncoderAll(String oldFile)  {
        this.oldFile = oldFile;
        File f = new File(oldFile);
        traverse(f);

//        List<File> myfile = new ArrayList<File>();
//        //开始遍历
//        try {
//            listDirectory(new File(f.getCanonicalPath()), myfile);
//        }catch (IOException ioException){
//
//        }
//        if(myfile.isEmpty()){
//            return;
//        }
//        for(int i = 0;i< myfile.size();i++ ){
//            treeFile(myfile.get(i));
//        }
    }

    public static void traverse(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    traverse(f);
                }
            }
        } else {
            treeFile(file);
//            try {
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    /** *//**
     * 遍历目录及其子目录下的所有文件并保存
     * @param path 目录全路径
     * @param myfile 列表：保存文件对象
     */
    public static void listDirectory(File path, List<File> myfile){
        if (!path.exists()){
            System.out.println("文件名称不存在!");
        }
        else
        {
            if (path.isFile()){
                myfile.add(path);
            } else{
                File[] files = path.listFiles();
                for (int i = 0; i < files.length; i++  ){
                    listDirectory(files[i], myfile);
                }
            }
        }
    }

    public static void treeFile(File f) {
        if (f.getName().endsWith(".html") || f.getName().endsWith(".htm")
                || f.getName().endsWith(".HTML") ||  f.getName().endsWith(".HTM")){
            if(f.isFile()){
                parse(f);
            }else {
                f.mkdirs();
            }
        }
    }

    public void tree(File f) {
        File[] childs = f.listFiles(new HTMLFilterAll());
        System.out.println("childs null treexx" +f.getName());
        if (childs == null) {
            return;
        }
        System.out.println("childs null tree" + childs.length );
        for (int i = 0; i < childs.length; i++) {
            if (childs[i].isDirectory()) {
                System.out.println("childs " + childs[i]);
                tree(childs[i]);
            } else if (childs[i].isFile()) {
                parse(childs[i]);
                System.out.println("childsxxx " + childs[i]);
            }
        }
    }

    public static void parse(File f) {
        try {
//           BufferedReader reader = new BufferedReader(new FileReader(f, UTF_8)); // 打开原始文件进行读取
//           BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f),UTF_8));
             java.nio.charset.Charset defaultCharset = defaultCharset = java.nio.charset.Charset.forName("GBK");
            BufferedReader reader = new BufferedReader(new FileReader(f, defaultCharset));
            StringBuilder contentBuilder = new StringBuilder(); // 构造新的文件内容字符串
            String line;

            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
            String oldString = Main.oldString;
            String newString = Main.newString;

            String modifiedContent = contentBuilder.toString().replace(oldString, newString);
            reader.close(); // 关闭原始文件

            f.deleteOnExit();

            String directory = f.getParent().replace(Main.executeDirectory + "\\","");
            File parentFile = null;
            if(!directory.isEmpty()){
                parentFile = new File(directory);
                parentFile.mkdirs();
            }
            File file = null;
            if(parentFile!=null){
                file = new File(parentFile, f.getName());
            }else {
                file = new File(f.getName());
            }
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

//            FileWriter fileWriter = new  FileWriter(f.getName(),UTF_8);
//            BufferedWriter writer = new BufferedWriter(fileWriter); // 重写同名文件
            writer.write(modifiedContent.toString()); // 将修改后的内容写入文件

            writer.flush(); // 清空输出流
            writer.close(); // 关闭文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }


//    public static void copyDirectory(String sourceFolderPath, String destinationFolderPath) throws IOException {
//        File sourceFolder = new File(sourceFolderPath);
//        if (!sourceFolder.exists()) {
//            throw new IllegalArgumentException("Source folder does not exist.");
//        }
//
//        File destinationFolder = new File(destinationFolderPath);
//        if(!destinationFolder.exists()){
//            destinationFolder.mkdirs();
//        }
//        if (destinationFolder.isFile() || !destinationFolder.getParentFile().exists()) {
//            throw new IllegalArgumentException("Destination is a file or parent directory does not exist.");
//        }
//
//        for (File file : sourceFolder.listFiles()) {
//            if (file.isDirectory()) {
//                copyDirectory(file.getAbsolutePath(), destinationFolder.getAbsolutePath());
//            } else {
//                copyFile(file.getAbsolutePath(), destinationFolder.getAbsolutePath());
//            }
//        }
//    }
//
//    private static void copyFile(String sourceFilePath, String destinationFolderPath) {
//        File sourceFile = new File(sourceFilePath);
//        if (!sourceFile.exists()) {
//            throw new IllegalArgumentException("Source file does not exist.");
//        }
//        if (sourceFile.getName().endsWith("  .html") || sourceFile.getName().endsWith(".htm") ||
//                sourceFile.getName().endsWith(".HTML") ||  sourceFile.getName().endsWith(".HTM")){
//            File destinationFile = new File(destinationFolderPath + "/" + sourceFile.getName());
//            try (InputStream inStream = new FileInputStream(sourceFile); OutputStream outStream = new FileOutputStream(destinationFile)) {
//                byte[] buffer = new byte[1024];
//                int length;
//
//                while ((length = inStream.read(buffer)) > 0) {
//                    outStream.write(buffer, 0, length);
//                }
//            } catch (IOException e) {
//                System.out.println("Error copying file: " + e.getMessage());
//            }
//            sourceFile.deleteOnExit();
//        }
//    }


    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 复制整个文件夹内容
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyDirectory(String oldPath, String newPath) {
        try {
            if(oldPath.contains(".git")){
                return;
            }
            if(oldPath.contains(".idea")){
                return;
            }
            if(oldPath.contains("out")){
                return;
            }
            if(oldPath.contains("src")){
                return;
            }
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a=new File(oldPath);
            String[] file=a.list();
            File temp=null;
            for (int i = 0; i < file.length; i++) {
                if(oldPath.endsWith(File.separator)){
                    temp=new File(oldPath+file[i]);
                }
                else{
                    temp=new File(oldPath+File.separator+file[i]);
                }

                if(temp.isFile()){
                    if (temp.getName().endsWith(".html") || temp.getName().endsWith(".htm") ||
                            temp.getName().endsWith(".HTML") ||  temp.getName().endsWith(".HTM")){
                        try {
                            FileInputStream input = new FileInputStream(temp);
                            FileOutputStream output = new FileOutputStream(newPath + "/" +
                                    (temp.getName()).toString());
                            byte[] b = new byte[1024 * 5];
                            int len;
                            while ( (len = input.read(b)) != -1) {
                                output.write(b, 0, len);
                            }
                            output.flush();
                            output.close();
                            input.close();
                        }catch (Exception exception){

                        }finally {
                            temp.deleteOnExit();
                        }
                    }

                }
                if(temp.isDirectory()){//如果是子文件夹
                    copyDirectory(oldPath+"/"+file[i],newPath+"/"+file[i]);
                }
            }
        }
        catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        }
    }
}

