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

  val ADMIN_ROLE: Any = "ROLE_ADMIN"

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
    auth.userDetailsService(userDetailsService).passwordEncoder(encoder)
  }

  @throws[Exception]
  override protected def configure(auth: AuthenticationManagerBuilder): Unit = {
    auth.userDetailsService(userDetailsService).passwordEncoder(encoder)
  }*/

  @throws[Exception]
  override protected def configure(http: HttpSecurity): Unit = {
    //@formatter:off
    http.csrf().disable()
      .authorizeRequests()
      .antMatchers(HttpMethod.GET, "/echo").permitAll()
      .antMatchers(HttpMethod.POST, "/oauth/token").permitAll()
      .antMatchers(HttpMethod.GET, "/api/v1/books/**").permitAll()
      .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
      .antMatchers("/api/v1/admin/**").access("hasRole('" + ADMIN_ROLE + "')")
      //.anyRequest().authenticated().and().httpBasic()

    //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    //@formatter:on
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
