import com.google.gson.Gson;
import pojo.JsonRootBean;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

class GsonFilterGift implements FilenameFilter {
    public GsonFilterGift() {
    }

    public boolean accept(File directory, String name) {
        if (name.endsWith(".json")) return true;
        if (name.endsWith(".txt")) return true;
        return false;
    }
}

class TextFilterGift {
    String oldFile = null;

    public TextFilterGift(String oldFile)  {
        this.oldFile = oldFile;
        File f = new File(oldFile);
        traverse(f);

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
        if (f.getName().endsWith(".json") || f.getName().endsWith(".txt")){
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
            // 读取txt文件内容
            Charset defaultCharset = Charset.forName("GBK");
            BufferedReader reader = new BufferedReader(new FileReader(f, defaultCharset));
            StringBuilder contentBuilder = new StringBuilder(); // 构造新的文件内容字符串
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
//            System.out.println("parse" + contentBuilder);
            String modifiedContent = contentBuilder.toString();

//            File giftParent = new File(f.getParent() + "/gift");
//            if(!giftParent.exists()){
//                giftParent.mkdirs();
//            }
            JsonRootBean rootBean = new Gson().fromJson(modifiedContent, JsonRootBean.class);

//            System.out.println(" " + rootBean.getData().getGoods().size()  + " " +  rootBean.getData().getKnapsack().size() + " " + rootBean.getData().getMk_gift().size()
//                    + " " + rootBean.getData().getFanclub_gift().size());

            for (int i = 0 ;i<rootBean.getData().getGoods().size(); i ++ ){
                String name = unicodeToCN(rootBean.getData().getGoods().get(i).getGift_name());
                File parentFile = new File( f.getParent() + "/goods" + "/" + name);
                if(!parentFile.exists()){

                    int startIndex = rootBean.getData().getGoods().get(i).getGift_image().lastIndexOf("/");
                    int endIndex = rootBean.getData().getGoods().get(i).getGift_image().lastIndexOf("png");
                    if(endIndex > 0){
                        String picName = rootBean.getData().getGoods().get(i).getGift_image().substring(startIndex,endIndex + 3);
                        downloadByIO(rootBean.getData().getGoods().get(i).getGift_image(),parentFile.getPath(),picName);
                    }

                    int startSvga = rootBean.getData().getGoods().get(i).getGift_svgaurl().lastIndexOf("/");
                    int endSvga = rootBean.getData().getGoods().get(i).getGift_svgaurl().lastIndexOf("svga");
                    if(endSvga > 0){
                        String svgaName = rootBean.getData().getGoods().get(i).getGift_svgaurl().substring(startSvga,endSvga + 4);
                        downloadByIO(rootBean.getData().getGoods().get(i).getGift_svgaurl(),parentFile.getPath(),svgaName);
                    }
                }
            }

            for (int i = 0 ;i<rootBean.getData().getKnapsack().size(); i ++ ){
                String name = unicodeToCN(rootBean.getData().getKnapsack().get(i).getGift_name());
                File parentFile = new File( f.getParent() + "/knapsack" + "/" + name);
                if(!parentFile.exists()){

                    int startIndex = rootBean.getData().getKnapsack().get(i).getGift_image().lastIndexOf("/");
                    int endIndex = rootBean.getData().getKnapsack().get(i).getGift_image().lastIndexOf("png");
                    if(endIndex > 0){
                        String picName = rootBean.getData().getKnapsack().get(i).getGift_image().substring(startIndex,endIndex + 3);
                        downloadByIO(rootBean.getData().getKnapsack().get(i).getGift_image(),parentFile.getPath(),picName);
                    }

                    int startSvga = rootBean.getData().getKnapsack().get(i).getGift_svgaurl().lastIndexOf("/");
                    int endSvga = rootBean.getData().getKnapsack().get(i).getGift_svgaurl().lastIndexOf("svga");
                    if(endSvga > 0){
                        String svgaName = rootBean.getData().getKnapsack().get(i).getGift_svgaurl().substring(startSvga,endSvga + 4);
                        downloadByIO(rootBean.getData().getKnapsack().get(i).getGift_svgaurl(),parentFile.getPath(),svgaName);
                    }

                }
            }


            for (int i = 0 ;i<rootBean.getData().getMk_gift().size(); i ++ ){
                String name = unicodeToCN(rootBean.getData().getMk_gift().get(i).getGift_name());
                File parentFile = new File( f.getParent()  + "/mkgift" + "/" + name);
                if(!parentFile.exists()){

                    int startIndex = rootBean.getData().getMk_gift().get(i).getGift_image().lastIndexOf("/");
                    int endIndex = rootBean.getData().getMk_gift().get(i).getGift_image().lastIndexOf("png");
                    if(endIndex > 0){
                        String picName = rootBean.getData().getMk_gift().get(i).getGift_image().substring(startIndex,endIndex + 3);
                        downloadByIO(rootBean.getData().getMk_gift().get(i).getGift_image(),parentFile.getPath(),picName);
                    }


                    int startSvga = rootBean.getData().getMk_gift().get(i).getGift_svgaurl().lastIndexOf("/");
                    int endSvga = rootBean.getData().getMk_gift().get(i).getGift_svgaurl().lastIndexOf("svga");
                    if(endSvga > 0){
                        String svgaName = rootBean.getData().getMk_gift().get(i).getGift_svgaurl().substring(startSvga,endSvga + 4);
                        downloadByIO(rootBean.getData().getMk_gift().get(i).getGift_svgaurl(),parentFile.getPath(),svgaName);
                    }
                }
            }

            for (int i = 0 ;i<rootBean.getData().getFanclub_gift().size(); i ++ ){
                String name = unicodeToCN(rootBean.getData().getFanclub_gift().get(i).getGift_name());
                File parentFile = new File( f.getParent() + "/fanclubgift" + "/" + name);
                if(!parentFile.exists()){

                    int startIndex = rootBean.getData().getFanclub_gift().get(i).getGift_image().lastIndexOf("/");
                    int endIndex = rootBean.getData().getFanclub_gift().get(i).getGift_image().lastIndexOf("png");
                    if(endIndex > 0){
                        String picName = rootBean.getData().getFanclub_gift().get(i).getGift_image().substring(startIndex,endIndex + 3);
                        downloadByIO(rootBean.getData().getFanclub_gift().get(i).getGift_image(),parentFile.getPath(),picName);
                    }

                    int startSvga = rootBean.getData().getFanclub_gift().get(i).getGift_svgaurl().lastIndexOf("/");
                    int endSvga = rootBean.getData().getFanclub_gift().get(i).getGift_svgaurl().lastIndexOf("svga");
                    if(endSvga > 0){
                        String svgaName = rootBean.getData().getFanclub_gift().get(i).getGift_svgaurl().substring(startSvga,endSvga + 4);
                        downloadByIO(rootBean.getData().getFanclub_gift().get(i).getGift_svgaurl(),parentFile.getPath(),svgaName);
                    }
                }
            }
            reader.close();
            // 输出数组
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("parsexxxx" );
        }
    }

    public static void downloadByIO(String url, String saveDir, String fileName) {
        BufferedOutputStream bos = null;
        InputStream is = null;
        try {
            byte[] buff = new byte[8192];
            is = new URL(url).openStream();
            File file = new File(saveDir, fileName);
            file.getParentFile().mkdirs();
            bos = new BufferedOutputStream(new FileOutputStream(file));
            int count = 0;
            while ((count = is.read(buff)) != -1) {
                bos.write(buff, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String unicodeToCN(String str){
        /**
         if(str == null)
         return null;
         if(str.indexOf("u") == -1){
         return str;
         }else{
         str = str.replace("u", "\\\\u");
         }
         */
        Pattern pattern = Pattern.compile("\\\\u([0-9a-fA-F]{4})");
        Matcher matcher = pattern.matcher(str);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String unicode = matcher.group(1);
            //同样也是通过 Integer.parseInt(String str, radix) 方式把 十六进制 转化成 十进制
            int codePoint = Integer.parseInt(unicode, 16);
            //转换成中文
            String chinese = new String(new char[] { (char) codePoint });
            //Unicode替换中文
            matcher.appendReplacement(result, Matcher.quoteReplacement(chinese));
        }
        matcher.appendTail(result);
        return result.toString();
    }

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
            if(oldPath.contains("lib")){
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

