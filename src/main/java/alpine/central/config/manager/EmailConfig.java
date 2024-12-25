package alpine.central.config.manager;

import java.util.Properties;

/**
 * Classe abstrata que define a configuração base para serviços de e-mail.
 * Esta classe gerencia a leitura de configurações de arquivos `.lua` e armazena
 * as propriedades do servidor de e-mail em um objeto {@link Properties}.
 *
 * <p>
 * A classe utiliza o padrão de design Template Method para delegar a definição
 * do nome da tabela principal no arquivo Lua para suas subclasses.
 * As subclasses devem implementar o método {@link #getMainTableTable()} para
 * fornecer o nome da tabela específica de configuração de e-mail.
 * </p>
 *
 * <h3>Uso:</h3>
 * <ul>
 *   <li>Herança: Crie uma classe que estenda `EmailConfig` e implemente o método
 *       {@link #getMainTableTable()} para especificar o nome da tabela principal do arquivo Lua.</li>
 *   <li>Inicialização: Utilize um dos construtores para criar instâncias com ou sem
 *       um caminho personalizado para o arquivo Lua.</li>
 *   <li>Acessar Propriedades: Use o método {@link #getServerProperties()} para acessar
 *       as configurações do servidor.</li>
 * </ul>
 *
 * <p>
 * Nota: A classe assume que a biblioteca {@link LuaFileReader} é utilizada para
 * processar os arquivos Lua e carregar as tabelas de configuração.
 * </p>
 */
public abstract class EmailConfig {

    /**
     * Define o nome da tabela principal no arquivo Lua que contém as configurações
     * do servidor de e-mail. Este método deve ser implementado pelas subclasses.
     *
     * @return O nome da tabela principal no arquivo Lua.
     */
    protected abstract String getMainTableTable();

    /**
     * Construtor padrão. Inicializa a leitura de configurações de um arquivo Lua
     * padrão, cujo caminho está embutido no projeto.
     *
     * @throws IllegalArgumentException Se o nome da tabela principal estiver vazio.
     */
    public EmailConfig() {
        readEmailFile();
    }

    /**
     * Construtor que permite especificar um caminho personalizado para o arquivo Lua.
     * Utiliza o arquivo fornecido para carregar as configurações do servidor de e-mail.
     *
     * @param luaFilePath O caminho para o arquivo Lua que contém as configurações.
     * @throws IllegalArgumentException Se o nome da tabela principal estiver vazio.
     */
    public EmailConfig(String luaFilePath) {
        if (getMainTableTable().isEmpty()) {
            throw new IllegalArgumentException("table name is empty");
        }
        LuaFileReader.getInstance(luaFilePath);
        props = LuaFileReader.initReader(getMainTableTable());
    }

    /**
     * Lê as configurações do arquivo Lua padrão e inicializa as propriedades do servidor.
     *
     * @throws IllegalArgumentException Se o nome da tabela principal estiver vazio.
     */
    private void readEmailFile() throws IllegalArgumentException {
        if (getMainTableTable().isEmpty()) {
            throw new IllegalArgumentException("table name is empty");
        }
        LuaFileReader.getInstance();
        props = LuaFileReader.initReader(getMainTableTable());
    }

    /**
     * Propriedades que armazenam as configurações do servidor de e-mail.
     */
    private Properties props;

    /**
     * Retorna as propriedades do servidor de e-mail carregadas do arquivo Lua.
     *
     * @return Um objeto {@link Properties} contendo as configurações do servidor.
     */
    public Properties getServerProperties() {
        return props;
    }
}
