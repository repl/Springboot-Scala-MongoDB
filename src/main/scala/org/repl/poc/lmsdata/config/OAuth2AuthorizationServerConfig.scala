package org.repl.poc.lmsdata.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.{AuthorizationServerConfigurerAdapter, EnableAuthorizationServer}
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.{AccessTokenConverter, TokenEnhancer, TokenStore}

@Configuration
@EnableAuthorizationServer
class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private var tokenStore: TokenStore = _

  @Autowired
  private var accessTokenConverter: AccessTokenConverter = _

  @Autowired
  private var authenticationManager: AuthenticationManager = _

  val CLIENT_ID: String = "devglan-client";
  val CLIENT_SECRET = "$2a$04$e/c1/RfsWuThaWFCrcCuJeoyvwCV0URN/6Pn9ZFlrtIWaU/vj/BfG"; //devglan-secret
  val GRANT_TYPE_PASSWORD = "password";
  val AUTHORIZATION_CODE = "authorization_code";
  val REFRESH_TOKEN = "refresh_token";
  val IMPLICIT = "implicit";
  val SCOPE_READ = "read";
  val SCOPE_WRITE = "write";
  val TRUST = "trust";
  val ACCESS_TOKEN_VALIDITY_SECONDS: Int = 1 * 60 * 60;
  val REFRESH_TOKEN_VALIDITY_SECONDS: Int = 6 * 60 * 60;

  override def configure(clients: ClientDetailsServiceConfigurer): Unit = {
    clients
      .inMemory
      .withClient(CLIENT_ID)
      .secret(CLIENT_SECRET)
      .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
      .scopes(SCOPE_READ, SCOPE_WRITE)
      .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS).
      refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS)
  }

  override def configure(endpoints: AuthorizationServerEndpointsConfigurer): Unit = {
    endpoints
      .tokenStore(tokenStore)
      .accessTokenConverter(accessTokenConverter)
      .authenticationManager(authenticationManager)
  }
}

/*class CustomTokenEnhancer extends TokenEnhancer {
  override def enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken = {
    return accessToken
  }
}*/
