package alpine.central.config.manager;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.Properties;

public class LuaFileReader {
    private static LuaFileReader instance = null;

    private static Globals globals;
    private static LuaValue chunk;
    private static LuaTable mainTable;

    public static LuaFileReader getInstance(){

        if(instance == null){
            instance = new LuaFileReader(LuaFileReader.class.getClassLoader().getResource("email_props.lua").getPath());
        }
        return instance;
    }

    public static LuaFileReader getInstance(String luaFilePath){

        if(instance == null){
            instance = new LuaFileReader(luaFilePath);
        }
        return instance;
    }

    private LuaFileReader(String luaFilePath) throws RuntimeException{
        try{
            globals = JsePlatform.standardGlobals();
            chunk = globals.loadfile(luaFilePath);
            chunk.call();
        } catch (Exception e) {
            throw new RuntimeException("error on loading lua file");
        }
    }

    public static Properties initReader(String mainTableName)
    throws IllegalArgumentException{
        mainTable = (LuaTable) globals.get(mainTableName);

        if(mainTable.equals(LuaTable.NIL)) throw new IllegalArgumentException("main table not found");

        Properties props = new Properties();
        for(LuaValue key: mainTable.keys()){
            props.put(key,mainTable.get(key).toString());
        }
        return props;
    }


}
