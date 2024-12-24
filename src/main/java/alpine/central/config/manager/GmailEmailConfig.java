package alpine.central.config.manager;

/**
 * Classe que representa uma configuração de servidor de e-mail para o Gmail.
 * Esta classe estende {@link EmailConfig} e implementa o método abstrato {@link #getMainTableTable()}
 * para retornar o nome da tabela principal do arquivo Lua relacionada ao Gmail.
 *
 * <p>
 * Esta classe pode ser usada para carregar configurações específicas de e-mail
 * para servidores Gmail a partir de um arquivo Lua.
 * </p>
 *
 * <h3>Uso:</h3>
 * <ul>
 *   <li>Crie uma instância usando o construtor padrão para carregar o arquivo Lua padrão.</li>
 *   <li>Use o construtor que aceita um caminho para carregar um arquivo Lua personalizado.</li>
 * </ul>
 */
public class GmailEmailConfig extends EmailConfig{
    /**
     * Construtor padrão.
     * <p>
     * Inicializa a configuração de e-mail para o Gmail utilizando o arquivo Lua padrão
     * definido no projeto.
     * </p>
     */
    public GmailEmailConfig(){}

    /**
     * Construtor que permite especificar um caminho personalizado para o arquivo Lua.
     * <p>
     * Inicializa a configuração de e-mail para o Gmail utilizando o arquivo Lua fornecido.
     * </p>
     *
     * @param luaFilePath O caminho para o arquivo Lua que contém as configurações do Gmail.
     */
    public GmailEmailConfig(String luaFilePath){
        super(luaFilePath);
    }
    @Override
    public String getMainTableTable() {
        return "Gmail";
    }
}
