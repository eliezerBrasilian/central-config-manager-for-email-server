package alpine.central.config.manager;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.Properties;

/**
 * Classe responsável por carregar e interpretar arquivos de configuração escritos em Lua.
 * Permite inicializar uma instância singleton e ler as propriedades definidas em tabelas Lua.
 *
 * Esta classe utiliza a biblioteca LuaJ para executar e processar scripts Lua.
 */
public class LuaFileReader {
    // Instância única da classe (Singleton)
    private static LuaFileReader instance = null;

    // Variáveis globais do ambiente Lua
    private static Globals globals;
    // Chunk de código carregado do arquivo Lua
    private static LuaValue chunk;
    // Tabela principal no arquivo Lua
    private static LuaTable mainTable;

    /**
     * Obtém a instância única de LuaFileReader.
     * O arquivo Lua utilizado será o padrão, localizado no classpath como "email_props.lua".
     *
     * @return A instância única de LuaFileReader.
     */
    public static LuaFileReader getInstance() {
        if (instance == null) {
            instance = new LuaFileReader(LuaFileReader.class.getClassLoader().getResource("email_props.lua").getPath());
        }
        return instance;
    }

    /**
     * Obtém a instância única de LuaFileReader, inicializando-a com um caminho customizado para o arquivo Lua.
     *
     * @param luaFilePath O caminho para o arquivo Lua a ser carregado.
     * @return A instância única de LuaFileReader.
     */
    public static LuaFileReader getInstance(String luaFilePath) {
        if (instance == null) {
            instance = new LuaFileReader(luaFilePath);
        }
        return instance;
    }

    /**
     * Construtor privado que carrega o arquivo Lua fornecido e inicializa o ambiente.
     *
     * @param luaFilePath O caminho para o arquivo Lua a ser carregado.
     * @throws RuntimeException Se houver erros ao carregar ou executar o arquivo Lua.
     */
    private LuaFileReader(String luaFilePath) throws RuntimeException {
        try {
            globals = JsePlatform.standardGlobals();
            chunk = globals.loadfile(luaFilePath);
            chunk.call();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o arquivo Lua: " + luaFilePath, e);
        }
    }

    /**
     * Lê as propriedades de uma tabela principal especificada no arquivo Lua e as retorna como um objeto Properties.
     *
     * @param mainTableName O nome da tabela principal a ser lida no arquivo Lua.
     * @return Um objeto {@link Properties} contendo as chaves e valores da tabela Lua.
     * @throws IllegalArgumentException Se a tabela principal não for encontrada no arquivo Lua.
     */
    public static Properties initReader(String mainTableName) throws IllegalArgumentException {
        mainTable = (LuaTable) globals.get(mainTableName);

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
