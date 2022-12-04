package hello;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * Created by yangwenjun on 2021/10/26 16:25
 */
@Configuration
@Import(Person.class)
public class MyConfig {

}
