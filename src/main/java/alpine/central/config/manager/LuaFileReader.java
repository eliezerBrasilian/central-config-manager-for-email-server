package alpine.central.config.manager;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.InputStream;
import java.util.Properties;

public class LuaFileReader {
    // Instância única da classe (Singleton)
    private static LuaFileReader instance = null;

    // Variáveis globais do ambiente Lua
    final private Globals globals;

    /**
     * Obtém a instância única de LuaFileReader.
     * O arquivo Lua utilizado será o padrão, localizado no classpath como "email_props.lua".
     *
     * @return A instância única de LuaFileReader.
     */
    public static LuaFileReader getInstance() {
        if (instance == null) {
            InputStream luaFileStream = LuaFileReader.class.getClassLoader().getResourceAsStream("email_props.lua");
            if (luaFileStream == null) {
                throw new RuntimeException("Arquivo Lua não encontrado: email_props.lua");
            }
            instance = new LuaFileReader(luaFileStream);
        }
        return instance;
    }

    /**
     * Cria uma nova instância de LuaFileReader com um InputStream personalizado.
     *
     * @param luaFileStream O fluxo de entrada para o arquivo Lua a ser carregado.
     * @return Uma nova instância de LuaFileReader.
     */
    public static LuaFileReader createInstance(InputStream luaFileStream) {
        if (luaFileStream == null) {
            throw new RuntimeException("O InputStream fornecido é nulo.");
        }
        return new LuaFileReader(luaFileStream);
    }

    /**
     * Construtor privado que carrega o arquivo Lua fornecido e inicializa o ambiente.
     *
     * @param luaFileStream O fluxo de entrada para o arquivo Lua a ser carregado.
     * @throws RuntimeException Se houver erros ao carregar ou executar o arquivo Lua.
     */
    private LuaFileReader(InputStream luaFileStream) throws RuntimeException {
        try {
            globals = JsePlatform.standardGlobals();
            // Chunk de código carregado do arquivo Lua
            LuaValue chunk = globals.load(luaFileStream, "script.lua", "t", globals);
            chunk.call();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o arquivo Lua", e);
        }
    }

    /**
     * Lê as propriedades de uma tabela principal especificada no arquivo Lua e as retorna como um objeto Properties.
     *
     * @param mainTableName O nome da tabela principal a ser lida no arquivo Lua.
     * @return Um objeto {@link Properties} contendo as chaves e valores da tabela Lua.
     * @throws IllegalArgumentException Se a tabela principal não for encontrada no arquivo Lua.
     */
    public Properties initReader(String mainTableName) throws IllegalArgumentException {
        // Tabela principal no arquivo Lua
        LuaTable mainTable = (LuaTable) globals.get(mainTableName);

        if (mainTable.equals(LuaValue.NIL)) {
            throw new IllegalArgumentException("Tabela principal não encontrada: " + mainTableName);
        }

        Properties props = new Properties();
        for (LuaValue key : mainTable.keys()) {
            props.put(key.tojstring(), mainTable.get(key).tojstring());
        }
        return props;
    }
}
