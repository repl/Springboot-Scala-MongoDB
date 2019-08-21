package org.repl.poc.lmsdata.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.{Bean, Configuration, Primary}
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.{EnableWebSecurity, WebSecurityConfigurerAdapter}
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.provider.token.{AccessTokenConverter, DefaultTokenServices, TokenStore}
import org.springframework.security.oauth2.provider.token.store.{JwtAccessTokenConverter, JwtTokenStore}
import org.springframework.web.cors.{CorsConfiguration, UrlBasedCorsConfigurationSource}
import org.springframework.web.filter.CorsFilter
import javax.annotation.Resource

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Bean
  def accessTokenConverter(): AccessTokenConverter = {
    val jatc = new JwtAccessTokenConverter()
    jatc.setSigningKey("123")
    jatc.setVerifierKey("123")
    return jatc
  }

  @Bean
  def tokenStore(converter: AccessTokenConverter): TokenStore = new JwtTokenStore(converter.asInstanceOf[JwtAccessTokenConverter])

  @Bean
  @Primary
  def tokenServices(tokenStore: TokenStore): DefaultTokenServices = {
    val dts = new DefaultTokenServices()
    dts.setTokenStore(tokenStore)
    dts.setSupportRefreshToken(true)
    return dts
  }

  @Bean
  @throws[Exception]
  override def authenticationManagerBean: AuthenticationManager = super.authenticationManagerBean

  @Bean def encoder = new BCryptPasswordEncoder

  @Resource(name = "userService")
  override val userDetailsService: UserDetailsService = null

  @Autowired
  @throws[Exception]
  def globalUserDetails(auth: AuthenticationManagerBuilder): Unit = {
    auth.userDetailsService(userDetailsService).passwordEncoder(encoder)
  }

  /*@Autowired
  def configureGlobal(auth: AuthenticationManagerBuilder): Unit = {
    auth.inMemoryAuthentication().withUser("bugbug0102").password("0102").roles("USER", "ADMIN", "BUGBUG")
  }

  @throws[Exception]
  override protected def configure(auth: AuthenticationManagerBuilder): Unit = {
    //@formatter:off
    auth.inMemoryAuthentication.withUser("habuma")
      .password("password")
      .authorities("ROLE_USER", "ROLE_ADMIN")
      .and
      .withUser("izzy")
      .password("password")
      .authorities("ROLE_USER")
    //@formatter:on
  }*/

  @throws[Exception]
  override protected def configure(http: HttpSecurity): Unit = {
    http
      .csrf.disable
      .anonymous.disable
      //.sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .authorizeRequests
      .antMatchers(HttpMethod.GET, "/api/v1/movies", "/error").permitAll
      .antMatchers(HttpMethod.POST, "/api/v1/login", "/api/v1/register", "/api/v1/tickets").permitAll
  }

  /*@Bean def corsFilter: FilterRegistrationBean[CorsFilter] = {
    val source = new UrlBasedCorsConfigurationSource
    val config = new CorsConfiguration
    config.setAllowCredentials(true)
    config.addAllowedOrigin("*")
    config.addAllowedHeader("*")
    config.addAllowedMethod("*")
    source.registerCorsConfiguration("/**", config)
    val bean = new FilterRegistrationBean(new CorsFilter(source))
    bean.setOrder(0)
    bean
  }*/
   */

}
