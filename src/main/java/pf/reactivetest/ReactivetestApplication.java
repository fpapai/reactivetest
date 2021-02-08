package pf.reactivetest;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactivetestApplication {

    private static final Logger LOG = LoggerFactory.getLogger( MethodHandles.lookup().lookupClass() );

    @Autowired
    private AppConfiguration appCfg;
    
	public static void main(String[] args) {
		SpringApplication.run(ReactivetestApplication.class, args);
	}

    @PostConstruct
    public void startupApplication() {
        LOG.info( "App started {}", appCfg );
    }

    @PreDestroy
    public void shutdownApplication() {
        LOG.info( "App stopped {}", appCfg );
    }
}
