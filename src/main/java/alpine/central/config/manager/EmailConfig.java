package alpine.central.config.manager;

import java.util.Properties;

public abstract class EmailConfig {
    protected abstract String getMainTableTable();

    public EmailConfig(){
        readEmailFile();
    }

    public EmailConfig(String luaFilePath){
        if(getMainTableTable().isEmpty()) throw new IllegalArgumentException("table name is empty");
        LuaFileReader.getInstance(luaFilePath);
        props = LuaFileReader.initReader(getMainTableTable());
    }

    private void readEmailFile() throws IllegalArgumentException{
        if(getMainTableTable().isEmpty()) throw new IllegalArgumentException("table name is empty");
        LuaFileReader.getInstance();
        props = LuaFileReader.initReader(getMainTableTable());
    }

    private Properties props;
    protected Properties getServerProperties(){return props;};
}

