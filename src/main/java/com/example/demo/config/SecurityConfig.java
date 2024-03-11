//package com.example.demo.config;
//
//import com.example.demo.Security.CustomUserDetailServioce;
//import com.example.demo.Security.JwtAuthenticationEntryPoint;
//import com.example.demo.Security.JwtAuthenticationFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//@Service
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Autowired
//    private CustomUserDetailServioce customUserDetailServioce;
//
//
//    @Autowired
//    private JwtAuthenticationEntryPoint point;
//    @Autowired
//    private JwtAuthenticationFilter filter;
//
//
////    @Autowired
////    private PasswordEncoder passwordEncoder;
//    private final PasswordEncoder passwordEncoder;
//    public SecurityConfig(PasswordEncoder passwordEncoder){
//        this.passwordEncoder=passwordEncoder;
//    }
//
//    @Bean
//
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//       http.csrf(csrf->csrf.disable())
//               .cors(cors->cors.disable())
//               .authorizeHttpRequests(auth-> auth.requestMatchers("/home/**")
//                       .authenticated()
//                       .requestMatchers("/auth/login")
//                       .permitAll()
//                       .anyRequest().authenticated())
//               .exceptionHandling(ex->ex.authenticationEntryPoint(point))
//               .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//       ;
//       http.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//    @Bean
//    public DaoAuthenticationProvider doDaoAuthenticationProvider(){
//        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
//        provider.setUserDetailsService(customUserDetailServioce);
//      provider.setPasswordEncoder(passwordEncoder);
//        return provider;
//
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
//        return builder.getAuthenticationManager();
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//
//
//
//
//
//
//
//
//
//}

package com.example.demo.config;
import com.example.demo.Security.CustomUserDetailServioce;
import com.example.demo.Security.JwtAuthenticationEntryPoint;
import com.example.demo.Security.JwtAuthenticationFilter;
import jakarta.servlet.FilterRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Service
@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    public static final String[] PUBLIC_URLS={
            "/api/v1/auth/**",
            "/api/users/",
            "/v3/api-docs",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**"


    };
    private final CustomUserDetailServioce customUserDetailServioce;
    private final JwtAuthenticationEntryPoint point;
    private final JwtAuthenticationFilter filter;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public SecurityConfig(
            CustomUserDetailServioce customUserDetailServioce,
            JwtAuthenticationEntryPoint point,
            JwtAuthenticationFilter filter) {
        this.customUserDetailServioce = customUserDetailServioce;
        this.point = point;
        this.filter = filter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/home/**").authenticated()
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .requestMatchers("/v3/api-docs").permitAll()
                        .requestMatchers(HttpMethod.GET).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public DaoAuthenticationProvider doDaoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailServioce);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        if (passwordEncoder == null) {
//            passwordEncoder = new BCryptPasswordEncoder();
//        }
//        return passwordEncoder;
//    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public FilterRegistrationBean coresFiltere(){

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource=new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("Authoraization");
        corsConfiguration.addAllowedHeader("Content-Type");
        corsConfiguration.addAllowedHeader("Accept");
        corsConfiguration.addAllowedHeader("POST");
        corsConfiguration.addAllowedHeader("GET");
        corsConfiguration.addAllowedHeader("DELETE");
        corsConfiguration.addAllowedHeader("PUT");
        corsConfiguration.addAllowedHeader("OPTIONS");
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setMaxAge(3600L);


        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        FilterRegistrationBean bean=new FilterRegistrationBean(new CorsFilter(urlBasedCorsConfigurationSource));
        bean.setOrder(-110);

        return bean;

    }
}






