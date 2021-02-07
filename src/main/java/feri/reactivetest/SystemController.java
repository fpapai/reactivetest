package feri.reactivetest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class SystemController {

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
    
    @GetMapping("/call/{host}/{port}/{path}")
    public Mono<String> call( @PathVariable String host, @PathVariable int port,
            @PathVariable String path) {
        return WebClient.create( "http://" + host + ":" + port )
                    .get()
                    .uri( path )
                    .retrieve()
                    .bodyToMono( String.class )
                    .map( v -> generateCallResponse( host, port, path, v ) );
    }
    
    private String generateCallResponse( String host, int port, String path, String response ) {
        return String.format( "[service '%s' calling '%s:%d/%s', received=%s]",
                name, host, port, path, response );
    }
}
