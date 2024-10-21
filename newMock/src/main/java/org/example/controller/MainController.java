package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.RequestDTO;
import org.example.model.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

//@Slf4j - логи через аннотацию
@RestController
public class MainController {
    private Logger log = LoggerFactory.getLogger(MainController.class);
    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxLimit;
            String currency;

            if (firstDigit == '8') {
                maxLimit = new BigDecimal(2000);
                currency = "US";
            } else if (firstDigit == '9') {
                maxLimit = new BigDecimal(1000);
                currency = "EU";
            } else {
                maxLimit = new BigDecimal(10000);
                currency = "RUB";
            }
            int randomBalance = (int) (Math.random() * maxLimit.intValue());
            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(requestDTO.getRqUID());
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(new BigDecimal(randomBalance));
            responseDTO.setMaxLimit(maxLimit);

            log.info("********** RequestDTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.info("********** ResponseDTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
