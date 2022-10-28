package crazykwak.lotto.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Slf4j
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {

        HttpStatus.Series series = response.getStatusCode().series();

        return series == HttpStatus.Series.CLIENT_ERROR
                || series == HttpStatus.Series.SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus.Series series = response.getStatusCode().series();
        if (series == HttpStatus.Series.SERVER_ERROR) {
            log.error("server error = {}", response);
            return;
        }

        if (series == HttpStatus.Series.CLIENT_ERROR) {
            log.error("client error = {}", response);
            return;
        }

    }
}
