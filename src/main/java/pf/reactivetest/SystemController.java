package pf.reactivetest;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class SystemController {
    
    private static final Logger LOG = LoggerFactory.getLogger( MethodHandles.lookup().lookupClass() );

    @Autowired
    private AppConfiguration appCfg;
    
    @GetMapping("/version")
    public Mono<String> getVersion(){
        return Mono.just( appCfg.getVersion() );
    }
    
    @GetMapping("/name")
    public Mono<String> getAppName(){
        return Mono.just( appCfg.getName() );
    }
    
    @GetMapping("/date")
    public Mono<String> date(){
        String v = String.format( "[%s] date=%s", appCfg.getName(), new Date().toString() );
        LOG.info( v );
        return Mono.just( v );
    }
    
    @GetMapping("/random")
    public Mono<String> random(){
        String v = String.format( "[%s] random=%d", appCfg.getName(), ThreadLocalRandom.current().nextInt() );
        LOG.info( v );
        return Mono.just( v );
    }
    
    @GetMapping("/service/{hostPort}/{path}")
    public Mono<String> service( @PathVariable String hostPort, @PathVariable String path) {
        String url = createAddress( hostPort );
        Mono<String> res = WebClient.create( url )
                    .get()
                    .uri( path )
                    .retrieve()
                    .bodyToMono( String.class )
                    .map( v -> generateServiceCallResponse( url, path, v ) );
        res.subscribe( LOG::info );
        return res;
    }
    
    private String generateServiceCallResponse( String url, String path, String response ) {
        return String.format( "[service '%s' calling '%s/%s', received=%s]",
                appCfg.getName(), url, path, response );
    }
    
    private static String createAddress( String hostPort ) {
        return "http://" + hostPort.replace( '_', ':' );
    }
}
