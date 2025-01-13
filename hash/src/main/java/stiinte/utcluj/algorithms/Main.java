package stiinte.utcluj.algorithms;

import stiinte.utcluj.algorithms.data.Cnp;
import stiinte.utcluj.algorithms.service.CnpGeneratorService;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) throws Exception {
        CnpGeneratorService service = new CnpGeneratorService();

        List<Cnp> cnpList = service.getIdentityNumbers(1_000_000);
        System.out.println("CNP list size: " + cnpList.size());
        
        int mapMaxSize = 1000;

        Map<String, Cnp> cnpMap = new LinkedHashMap<>() {
            
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Cnp> eldest) {
                return size() > mapMaxSize;
            }
        };
        
        cnpList.forEach(cnp -> cnpMap.put(
                "" + ThreadLocalRandom.current().nextInt(1, 1000), cnp));

        System.out.println("CNP map size: " + cnpMap.size());
        System.out.println(cnpMap);
    }
}