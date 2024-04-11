package vttp.batch4.csf.toiletnearme.configs;

import java.time.Duration;
import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients.ElasticsearchHttpClientConfigurationCallback;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.support.HttpHeaders;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Override
	public ClientConfiguration clientConfiguration() {

		return ClientConfiguration.builder()           
            .connectedTo("localhost:9200", "localhost:9291")                                                                              
            .withProxy("localhost:8888")                                          
            .withPathPrefix("ela")                                                
            .withConnectTimeout(Duration.ofSeconds(5))                            
            .withSocketTimeout(Duration.ofSeconds(3))                                                                      
            .withHeaders(() -> {                                                  
                HttpHeaders headers = new HttpHeaders();
                headers.add("currentTime", new Date().toString());
                // TODO: JWT Token, if not, label as unauthorised
            return headers;
            })

            .withClientConfigurer(                                                
            ElasticsearchHttpClientConfigurationCallback.from(clientBuilder -> {
                // ...
                return clientBuilder;
                }))
            .build();
	}
    
}
