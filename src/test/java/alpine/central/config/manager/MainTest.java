package alpine.central.config.manager;

import org.junit.jupiter.api.Test;
import java.util.Properties;

class MainTest {

    @Test
    void main() {
        Properties properties = new GmailEmailConfig().getServerProperties();
        System.out.println(properties);

    }

    @Test
    void loadPropertiesFromCustomPath() {
        Properties properties = new GmailEmailConfig("file.lua")
                .getServerProperties();

        System.out.println(properties);

    }
}