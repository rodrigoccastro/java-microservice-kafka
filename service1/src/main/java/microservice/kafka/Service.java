package microservice.kafka;

import microservice.kafka.queue.KafkaDispatcher;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;

@RestController
public class Service {

    private final KafkaDispatcher kafkaClient = new KafkaDispatcher<Client>();

    @RequestMapping(value = "/service1", method = RequestMethod.GET)
    public Object execute(@RequestParam(value="id", required=false) String id,
                          @RequestParam(value="name", required=false) String name) {

        var mapAll = new HashMap<>();
        try {
            Client client = new Client(id,name);
            kafkaClient.send("CLIENT_SAVE", id, client);
            System.out.println("New client sent successfully");
            mapAll.put("message", "Data sent to message queue!");
            return new ResponseEntity<>(mapAll, HttpStatus.OK);
        } catch (Exception ex) {
            mapAll.put("message", "Data not sent to message queue!");
            mapAll.put("error",  ExceptionUtils.getStackTrace(ex));
            return new ResponseEntity<>(mapAll, HttpStatus.OK);
        }
    }

}
