package fi.ficora.lippu;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.map.repository.config.EnableMapRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring boot Application settings.
 * @author markkuko
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = { "fi.ficora.lippu",
                                "fi.ficora.lippu.controller" })
@EnableMapRepositories
public class Application implements CommandLineRunner {


    @Override
    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new Application.ExitException();
        }
    }

    public static void main(String[] args) throws Exception {
        new SpringApplication(Application.class).run(args);
    }

    static class ExitException extends RuntimeException
            implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
