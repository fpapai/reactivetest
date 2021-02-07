package pf.reactivetest;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class SystemController {
    
    private static final Logger LOG = LoggerFactory.getLogger( MethodHandles.lookup().lookupClass() );

    @Value("${app.name}")
    private String name;
    
    @Value("${app.version}")
    private String version;

    @GetMapping("/version")
    public Mono<String> getVersion(){
        return Mono.just( version );
    }
    
    @GetMapping("/name")
    public Mono<String> getAppName(){
        return Mono.just( name );
    }
    
    @GetMapping("/date")
    public Mono<String> date(){
        String v = String.format( "[%s] date=%s", name, new Date().toString() );
        LOG.info( v );
        return Mono.just( v );
    }
    
    @GetMapping("/random")
    public Mono<String> random(){
        String v = String.format( "[%s] random=%d", name, ThreadLocalRandom.current().nextInt() );
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
                name, url, path, response );
    }
    
    private static String createAddress( String hostPort ) {
        return "http://" + hostPort.replace( '_', ':' );
    }
}
