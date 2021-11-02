package spring.cloud.kafka.BI.services;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;

import spring.cloud.kafka.BI.models.OrderEvent;
import spring.cloud.kafka.BI.models.QuantitySummary;

@Configuration
public class StreamProcessing 
{    
	public final static String productName = "PRODUCT";
	
	//Stream processing - Generate the total order quantity per product
    @Bean
    public Function<KStream<?, OrderEvent>, KStream<String, QuantitySummary>> process() {
        return inputStream -> {
            inputStream.map((k, v) -> {
            	String productName = v.getProduct();
                int orderQuantity = v.getQuantity();
                return KeyValue.pair(productName, orderQuantity);
            }); //get the product name and its order quantity
            
            //group product name and order quantity and map them into a table
            KTable<String, Long> KTable = inputStream.
                    mapValues(OrderEvent::getProduct).
                    groupBy((keyIgnored, value) -> value).
                    count(
                    		Materialized.<String, Long, KeyValueStore<Bytes, byte[]>> as (productName).
                                    withKeySerde(Serdes.String()).
                                    withValueSerde(Serdes.Long())
                    );
            //store the mapped keys and values into stream topic Quantity Summary
            KStream<String, QuantitySummary> stream = KTable.
                    toStream().
                    map((k, v) -> KeyValue.pair(k, new QuantitySummary(k, v)));
            
            // console output
            stream.print(Printed.<String, QuantitySummary>toSysOut().withLabel("Console Output:"));

            return stream;
        };
    }
}
