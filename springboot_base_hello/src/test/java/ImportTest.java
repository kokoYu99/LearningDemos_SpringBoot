import com.koko.config.AAA;
import com.koko.config.BBB;
import com.koko.config.MyConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(value= MyConfig.class)
public class ImportTest {

    @Autowired
    private AAA aaa;

    @Autowired
    private BBB bbb;

    @Autowired
    private ApplicationContext context;

    @Test
    public void test(){
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }
}
