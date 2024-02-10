import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.File;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;


public class Main {

    public static  String oldString = "";
    //newString  替换成新的文字
    public static  String newString = "";

    // executeDirectory  需要操作的文件夹  不要放多级目录，只支持一级目录
    public static  String executeDirectory ="";

    //destinationDirectory 替换成功后的文件夹，不要放executeDirectory里面，会死循环
    private static  String destinationDirectory ="";

    private static  int type = 0;

    public static void main(String[] args) {
        File file = new File("");
        try {
            readJsonData(file.getCanonicalPath() + File.separatorChar+ "config.json");
            if(type == 0){
                return;
            }
            if(type == 1){
                new BatchEncoder(executeDirectory);
                try {
                    BatchEncoder.copyDirectory(file.getCanonicalPath(), destinationDirectory);
                }catch (IOException exception){
                }
                return;
            }
            if(type > 1){
                new BatchEncoderAll(executeDirectory);
                try {
                    BatchEncoderAll.copyDirectory(file.getCanonicalPath(), destinationDirectory);
                }catch (IOException exception){
                }

            }
        }catch (Exception e){

        }
    }

    /**
     * 读取json文件并且转换成字符串
     * @param filePath文件的路径
     * @return
     * @throws IOException
     */
    public static String readJsonData(String pactFile) {
        // 读取文件数据
        //System.out.println("读取文件数据util");

        StringBuffer strbuffer = new StringBuffer();
        File myFile = new File(pactFile);
        if (!myFile.exists()) {
            System.err.println("Can't Find " + pactFile);
        }
        try {
            FileInputStream fis = new FileInputStream(pactFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader in  = new BufferedReader(inputStreamReader);

            String str;
            while ((str = in.readLine()) != null) {
                strbuffer.append(str);  //new String(str,"UTF-8")
            }
            Gson gson = new Gson();
            JsonBean jsonBean = gson.fromJson(strbuffer.toString(), JsonBean.class);

            if(jsonBean!=null){
                oldString = jsonBean.getOldString();
                newString = jsonBean.getNewString();
                executeDirectory = jsonBean.getExecuteDirectory();
                destinationDirectory = jsonBean.getDestinationDirectory();
                type = jsonBean.getType();
            }else {
                System.out.println("无法读取到配置文件");
            }
//            JsonElement jsonElement = gson.fromJson(strbuffer.toString(), JsonElement.class);

            in.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        //System.out.println("读取文件结束util");
        return strbuffer.toString();
    }
}