package com.shop.config;

import com.shop.constant.Role;
import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    private final CustomOAuth2UserService customOAuth2UserService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
// aws 책 추가 코드
//        http
//                .csrf().disable()
//                .headers().frameOptions().disable()// .csrf() ~ frameOption.disable() 까지 h2-console 화면을 사용하기 위해 옵션들을 disable한다.
//                .and()
//                .authorizeRequests()
//                .antMatchers("/", "/css/**","/images/**","/js/**","h2-console/**").permitAll()
//                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
//                .anyRequest().authenticated() //나머지 url 들은 로그인된 사람만이 허용하게 한다.
//                .and()
//                .logout()
//                .logoutSuccessUrl("/")
//                .and()
//                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(customOAuth2UserService);

        http.formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/members/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/")
                ;

        //httpServletRequest를 처리하겠습니다를 알려줌.
        http.authorizeRequests()
                //permitAll() 모든 사용자가 인증없이 해당 경로를 접근할 수 있도록 설정한다.
                .mvcMatchers("/","/members/**","/item/**", "/images/**").permitAll()
                .mvcMatchers("/admin/**").hasRole(Role.USER.name())
                .anyRequest().authenticated() // 위 과정이 아닌 경우 나머지 경로들은 모두 인증해 달라는 code
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
        ;

        //인증되지 않은 사용자가 리소스에 접근하였을때 수행되는 핸들러를 등록한다.
        //CustomAuthenticationEntryPoint에 response.sendError()를 함축해 놓았다.
        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
