package ribbonconfigration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 随机轮询
 */

@Configuration
public class RibbonConfigration {
    @Bean
    public IRule ribbonRule(){
        return new RandomRule();
    }

}
