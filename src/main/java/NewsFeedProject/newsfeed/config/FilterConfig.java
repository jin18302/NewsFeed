package NewsFeedProject.newsfeed.config;


import NewsFeedProject.newsfeed.filter.JwtFliter;
import NewsFeedProject.newsfeed.filter.JwtTokenProvider;
import jakarta.servlet.Filter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@AllArgsConstructor
public class FilterConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public FilterRegistrationBean jwtFilterRegistrationBean() {
        log.info("JWT Filter 등록 중...");
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new JwtFliter(jwtTokenProvider)); //
        filterFilterRegistrationBean.setOrder(1);
        filterFilterRegistrationBean.addUrlPatterns("/*");
        log.info("JWT Filter 등록 완료.");
        return filterFilterRegistrationBean;
    }
}
