package mxryan.msscbreweryclient.web.config;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


//@Component
public class NIORestTemplateCustomizer implements RestTemplateCustomizer {

    @Override
    public void customize(RestTemplate restTemplate) {

        final DefaultConnectingIOReactor ioreactor;
        try {
            ioreactor = new DefaultConnectingIOReactor(IOReactorConfig.custom().
                    setConnectTimeout(3000).
                    setIoThreadCount(4).
                    setSoTimeout(3000).
                    build());
        } catch (IOReactorException e) {
            e.printStackTrace();
            return;
        }

        final PoolingNHttpClientConnectionManager connectionManager =
                new PoolingNHttpClientConnectionManager(ioreactor);

        connectionManager.setDefaultMaxPerRoute(100);
        connectionManager.setMaxTotal(1000);

        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        restTemplate.setRequestFactory(
                new HttpComponentsAsyncClientHttpRequestFactory(httpAsyncClient)
        );
    }
}
