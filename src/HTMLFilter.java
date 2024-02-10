import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

class HTMLFilter implements FilenameFilter {
    public boolean accept(File directory, String name) {
        if (name.endsWith(".html")) return true;
        if (name.endsWith(".htm")) return true;
        if (name.endsWith(".HTML")) return true;
        if (name.endsWith(".HTM")) return true;
        return false;
    }
}

class BatchEncoder {
    String oldFile = null;

    public BatchEncoder(String oldFile) {
        this.oldFile = oldFile;
        File f = new File(oldFile);
        tree(f);
    }

    public void tree(File f) {
        File[] childs = f.listFiles(new HTMLFilter());
        if (childs == null) {
            return;
        }
        for (int i = 0; i < childs.length; i++) {
            if (childs[i].isDirectory()) {
                if (!childs[i].exists()) {
                    childs[i].mkdirs();
                }
                tree(childs[i]);
            } else if (childs[i].isFile()) {
                parse(childs[i]);
            }
        }
    }

    public void parse(File f) {
        try {
//           BufferedReader reader = new BufferedReader(new FileReader(f, UTF_8)); // 打开原始文件进行读取
//           BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f),UTF_8));
            java.nio.charset.Charset defaultCharset = java.nio.charset.Charset.forName("GBK");
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

            FileWriter fileWriter = new  FileWriter(f.getName(),UTF_8);
            BufferedWriter writer = new BufferedWriter(fileWriter); // 重写同名文件
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


    public static void copyDirectory(String sourceFolderPath, String destinationFolderPath) throws IOException {
        File sourceFolder = new File(sourceFolderPath);
        if (!sourceFolder.exists()) {
            throw new IllegalArgumentException("Source folder does not exist.");
        }

        File destinationFolder = new File(destinationFolderPath);
        if(!destinationFolder.exists()){
            destinationFolder.mkdirs();
        }
        if (destinationFolder.isFile() || !destinationFolder.getParentFile().exists()) {
            throw new IllegalArgumentException("Destination is a file or parent directory does not exist.");
        }

        for (File file : sourceFolder.listFiles()) {
            if (file.isDirectory()) {
                copyDirectory(file.getAbsolutePath(), destinationFolder.getAbsolutePath());
            } else {
                copyFile(file.getAbsolutePath(), destinationFolder.getAbsolutePath());
            }
        }
    }

    private static void copyFile(String sourceFilePath, String destinationFolderPath) throws IOException {
        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists()) {
            throw new IllegalArgumentException("Source file does not exist.");
        }
        if (sourceFile.getName().endsWith(".html") || sourceFile.getName().endsWith(".htm") ||
                sourceFile.getName().endsWith(".HTML") ||  sourceFile.getName().endsWith(".HTM")){
            File destinationFile = new File(destinationFolderPath + "/" + sourceFile.getName());
            try (InputStream inStream = new FileInputStream(sourceFile); OutputStream outStream = new FileOutputStream(destinationFile)) {
                byte[] buffer = new byte[1024];
                int length;

                while ((length = inStream.read(buffer)) > 0) {
                    outStream.write(buffer, 0, length);
                }
            } catch (IOException e) {
                System.out.println("Error copying file: " + e.getMessage());
            }
            sourceFile.deleteOnExit();
        }
    }
}

