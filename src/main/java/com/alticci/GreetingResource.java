package com.alticci;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

@Path("/")
public class GreetingResource {

    Map<Integer, List<Integer>>  series = new HashMap<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("alticci/{n}")
    public List<Integer> hello(@PathParam("n") Integer n) {


        IntStream stream = IntStream.generate(new AlticciSequence());
        if (!series.containsKey(n)){
            List<Integer> list = new ArrayList<>();
            stream.limit(n).forEach(v -> list.add(v));
            series.put(n,list);
            System.out.println("cache created");
        } else {
            System.out.println("using cache");
        }
        return series.get(n);

    }
    /*
    public static void main(String args[]){
        IntStream stream = IntStream.generate(new AlticciSequence());
        stream.limit(20).forEach(System.out::println);
    }
    */

    static class AlticciSequence implements IntSupplier {
        int current = 0;
        int previous = 0;
        int previousMinusTwo = 0;
        int previousMinusThree = 0;
        int result = 0;
        int n = 0;
        @Override
        public int getAsInt() {
            if (n ==0){
                result = 0;
            } else if (n== 1) {
                result = 1;
            }else if (n==2){
                result = 1;
            }else if (n==3){
                result = 1;
                current = 1;
                previous = 1;
                previousMinusTwo = 1;
                previousMinusThree = 1;

            } else {
                current = previousMinusThree + previousMinusTwo;//0+0
                result = current;
                previousMinusThree = previousMinusTwo;//0
                previousMinusTwo = previous ;//0
                previous = result;
            }
            n++;
            return result;
        }
    }
}

