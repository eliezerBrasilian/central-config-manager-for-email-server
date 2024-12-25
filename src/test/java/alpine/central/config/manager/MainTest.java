package alpine.central.config.manager;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

class MainTest {

    @Test
    void loadPropertiesFromDefaultLuaFile() {
        Properties properties = new GmailEmailConfig().getServerProperties();
        System.out.println(properties);

    }

    @Test
    void loadPropertiesFromCustomPath() {
        Properties properties = new GmailEmailConfig("file.lua")
                .getServerProperties();

        System.out.println(properties);
    }

    @Test
    void generateJarFile(){

    }

    @Test
    void findMvn() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd", "/c", "mvn", "--version");
        processBuilder.inheritIO();
        Process process = processBuilder.start();
        process.waitFor();

    }
}