package microservice.read.client;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceConsumer {

    @Scheduled(fixedDelay = 60_000)
    public void execute() {

        //read mongo
        List listMongo = readMongo();

        //save in database postgres
        saveDatabase(listMongo);

        //save in database postgres
        removeMongo(listMongo);

    }

    private void saveDatabase(List listMongo) {
        System.out.println("read mongo...");
    }

    private List readMongo() {
        System.out.println("save postregs...");
        return null;
    }

    private void removeMongo(List listMongo) {
        System.out.println("remove from mongo...");
    }

}

