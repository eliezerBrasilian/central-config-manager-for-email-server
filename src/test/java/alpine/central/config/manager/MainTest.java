package alpine.central.config.manager;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

class MainTest {

    @Test
    void loadPropertiesFromDefaultLuaFile() {
        Properties properties = new GmailEmailConfig().getServerProperties();
        System.out.println(properties);
    }

    @Test
    void loadPropertiesFromCustomPath() throws FileNotFoundException {

        Properties properties = new GmailEmailConfig(new FileInputStream("file.lua"))
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