package project;

import java.util.function.Function;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());
    
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired private DB db;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            final List<GeoLocation> geoLocations =
                this.db.getGeoLocations(32.891998291015625, -117.14399719238281);
            final DataModel data = new DataModel(geoLocations);
            final TSPResult res = TSPSolver.solve(data);

            List<Integer> ids = res
                .getRoute()
                .stream()
                .map(i -> geoLocations.get(i.intValue()).id)
                .collect(Collectors.toList());

            List<Brewery> breweries = db.getBreweries(ids);
            printVisitedBreweries(breweries);
        };
    }
      
    public static void printVisitedBreweries(List<Brewery> breweries) {
        String joined = breweries.stream().map(b -> b.name).collect(Collectors.joining(" -> "));
        logger.info(joined);
    }
}
