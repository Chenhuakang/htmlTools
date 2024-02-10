public class JsonBean {

    private String oldString;

    private String newString;

    private String executeDirectory;

    private String destinationDirectory;

    private int type;

    public JsonBean(String oldString, String newString, String executeDirectory, String destinationDirectory, int type) {
        this.oldString = oldString;
        this.newString = newString;
        this.executeDirectory = executeDirectory;
        this.destinationDirectory = destinationDirectory;
        this.type = type;
    }

    public String getOldString() {
        return oldString;
    }

    public void setOldString(String oldString) {
        this.oldString = oldString;
    }

    public String getNewString() {
        return newString;
    }

    public void setNewString(String newString) {
        this.newString = newString;
    }

    public String getExecuteDirectory() {
        return executeDirectory;
    }

    public void setExecuteDirectory(String executeDirectory) {
        this.executeDirectory = executeDirectory;
    }

    public String getDestinationDirectory() {
        return destinationDirectory;
    }

    public void setDestinationDirectory(String destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
