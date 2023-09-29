package microservice.read.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Service {

    @RequestMapping(value = "/service3", method = RequestMethod.GET)
    public Object listDevices() {
        return new ResponseEntity<>(getListFromPostres(), HttpStatus.OK);
    }

    private Object getListFromPostres() {
        List all = new ArrayList<>();
        for (int i=1;i<=10;i++) {
            Map map = new HashMap<>();
            map.put("id", "id_" + i);
            map.put("name", "name_" + i);
            all.add(map);
        }
        System.out.println("read list from postregs: "+all.toString());
        return all;
    }

}
