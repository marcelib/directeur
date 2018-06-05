package com.wut.directeur.rest.configuration

import com.wut.directeur.config.AppConfig
import com.wut.directeur.config.AppParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Modifying or overriding the default spring boot security.
 */
@Configurable
@EnableWebSecurity
open class OAuthSecurityContext : WebSecurityConfigurerAdapter() {

  private var oauth2ClientContext: OAuth2ClientContext? = null
  private var authorizationCodeResourceDetails: AuthorizationCodeResourceDetails? = null
  private var resourceServerProperties: ResourceServerProperties? = null

  @Autowired
  fun setOauth2ClientContext(oauth2ClientContext: OAuth2ClientContext) {
    this.oauth2ClientContext = oauth2ClientContext
  }

  @Autowired
  fun setAuthorizationCodeResourceDetails(authorizationCodeResourceDetails: AuthorizationCodeResourceDetails) {
    this.authorizationCodeResourceDetails = authorizationCodeResourceDetails
  }

  @Autowired
  fun setResourceServerProperties(resourceServerProperties: ResourceServerProperties) {
    this.resourceServerProperties = resourceServerProperties
  }

  /* This method is for overriding the default AuthenticationManagerBuilder.
     We can specify how the user details are kept in the application. It may
     be in a database, LDAP or in memory.*/
  @Throws(Exception::class)
  override fun configure(auth: AuthenticationManagerBuilder?) {
    super.configure(auth)
  }

  /* This method is for overriding some configuration of the WebSecurity
     If you want to ignore some request or request patterns then you can
     specify that inside this method.*/
  @Throws(Exception::class)
  override fun configure(web: WebSecurity?) {
    super.configure(web)
  }

  /*This method is used for override HttpSecurity of the web Application.
    We can specify our authorization criteria inside this method.*/
  @Throws(Exception::class)
  override fun configure(http: HttpSecurity) {

    http
        // Starts authorizing configurations.
        .authorizeRequests()
        .antMatchers(
            *NOT_LOGGED_USER_UNRESTRICTED_PREFIXES
        ).permitAll()
        // Authenticate all remaining URLs.
        .anyRequest().fullyAuthenticated()
        .and()
        // Setting the logout URL "/google/logout"
        .logout()
        .logoutRequestMatcher(AntPathRequestMatcher("/google/logout"))
        // After successful logout the application will redirect to "/" path.
        .logoutSuccessUrl(AppConfig.getString(AppParam.AUTHORIZATION_LOGOUT_CALLBACK_URL))
        .permitAll()
        .and()
        // Setting the filter for the URL "/google/login".
        .addFilterAt(filter(), BasicAuthenticationFilter::class.java)
        .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
  }

  /*This method for creating filter for OAuth authentication.*/
  private fun filter(): OAuth2ClientAuthenticationProcessingFilter {
    //Creating the filter for "/google/login" url
    val oAuth2Filter = OAuth2ClientAuthenticationProcessingFilter("/google/login")
    oAuth2Filter.setAuthenticationSuccessHandler(object : SimpleUrlAuthenticationSuccessHandler() {
      @Throws(IOException::class, ServletException::class)
      override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        this.defaultTargetUrl = AppConfig.getString(AppParam.AUTHORIZATION_LOGIN_CALLBACK_URL)
        super.onAuthenticationSuccess(request, response, authentication)
      }
    })
    //Creating the rest template for getting connected with OAuth service.
    //The configuration parameters will inject while creating the bean.
    val oAuth2RestTemplate = OAuth2RestTemplate(authorizationCodeResourceDetails!!,
        oauth2ClientContext)
    oAuth2Filter.setRestTemplate(oAuth2RestTemplate)

    // Setting the token service. It will help for getting the token and
    // user details from the OAuth Service.
    oAuth2Filter.setTokenServices(UserInfoTokenServices(resourceServerProperties!!.userInfoUri,
        resourceServerProperties!!.clientId))

    return oAuth2Filter
  }

  companion object {
    private val NOT_LOGGED_USER_UNRESTRICTED_PREFIXES = arrayOf(
        "/",
        "/swagger-ui.html",
        "/internalUtils/**",
        "/webjars/**",
        "/swagger-resources/**",
        "/v2/**")
  }
}
