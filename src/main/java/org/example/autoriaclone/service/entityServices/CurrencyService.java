package org.example.autoriaclone.service.entityServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.example.autoriaclone.dto.CurrencyDto;
import org.example.autoriaclone.dto.responses.CurrencyResponse;
import org.example.autoriaclone.entity.Currency;
import org.example.autoriaclone.mapper.CurrencyMapper;
import org.example.autoriaclone.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;
    private final ObjectMapper objectMapper;
    public List<CurrencyResponse> transferToAllCurrencies(String ccy, String value){
        List<Currency> allCurrencies = currencyRepository.findAll();
        String sale = currencyRepository.findByCcy(ccy).getSale();

        Map<String, String> collect = allCurrencies.stream().collect(Collectors.toMap(Currency::getCcy, Currency::getSale));
        List<CurrencyResponse> convertedCurrenciesList = new ArrayList<>(collect.entrySet().stream().map(e -> {
            Double i = (Double.parseDouble(sale) / Double.parseDouble(e.getValue())  * Double.parseDouble(value));
            return new CurrencyResponse(e.getKey(), Double.toString(i));
        }).toList());
        convertedCurrenciesList.removeIf(e->e.getCurrencyName().equals(ccy));
        return convertedCurrenciesList;
    }

    public void isValidCurrencyName(String currencyName){
        List<Currency> allCurrencies = currencyRepository.findAll();
        List<String> list = allCurrencies.stream().map(Currency::getCcy).toList();
        if (!list.contains(currencyName)){
            throw new IllegalArgumentException("Not valid currency name. Currency name could be: "+list.toString().replace("[","").replace("]",""));
        }
    }
    public Integer transferToCcy(String finalCcy, String transferedCcy, Integer value){
        String CcyCurrency = currencyRepository.findByCcy(finalCcy).getSale();
        String sale = currencyRepository.findByCcy(transferedCcy).getSale();
        double submit = Double.parseDouble(sale) * Double.parseDouble(Integer.toString(value))/Double.parseDouble(CcyCurrency);
        System.out.println(submit);
        return Math.toIntExact(Math.round(submit));
    }
    public void uploadCurrencies() throws IOException {
        URL url = new URL("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5");
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            String string = json.toString();
            CurrencyDto[] list = objectMapper.readValue(string, CurrencyDto[].class);
            CurrencyDto eurValue = Arrays.stream(list).toList().get(0);
            CurrencyDto usdValue = Arrays.stream(list).toList().get(1);

            Currency usd = new Currency();
            Currency eur = new Currency();
            Currency uah = new Currency();
            if(currencyRepository.findByCcy("EUR")==null){
                eur.setBuy(eurValue.getBuy()).setSale(eurValue.getSale()).setCcy(eurValue.getCcy());
            }else{
                eur = currencyRepository.findByCcy("EUR");
                eur.setBuy(eurValue.getBuy()).setSale(eurValue.getSale()).setCcy(eurValue.getCcy());
            }
            if(currencyRepository.findByCcy("USD")==null){
                usd.setBuy(usdValue.getBuy()).setSale(usdValue.getSale()).setCcy(usdValue.getCcy());
            }else{
                usd = currencyRepository.findByCcy("USD");
                usd.setBuy(usdValue.getBuy()).setSale(usdValue.getSale()).setCcy(usdValue.getCcy());
            }
            if(currencyRepository.findByCcy("UAH")==null){
                uah.setBuy("1.00000").setSale("1.00000").setCcy("UAH");
            }else{
                uah = currencyRepository.findByCcy("UAH");
                uah.setBuy("1.00000").setSale("1.00000").setCcy("UAH");
            }

            currencyRepository.save(eur);
            currencyRepository.save(usd);
            currencyRepository.save(uah);

        }
    }
}
