package alpine.central.config.manager;

public class GmailEmailConfig extends EmailConfig{
    public GmailEmailConfig(){}

    public GmailEmailConfig(String luaFilePath){
        super(luaFilePath);
    }
    @Override
    public String getMainTableTable() {
        return "Gmail";
    }
}
