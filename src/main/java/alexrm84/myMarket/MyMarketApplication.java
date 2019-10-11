package alexrm84.myMarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("alexrm84")
public class MyMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyMarketApplication.class, args);
	}

}
