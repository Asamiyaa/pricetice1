package component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by yangwenjun on 2021/10/26 19:55
 */
@Autowired
@Qualifier("testAnnA")
public @interface TestAnn {

}
