package com.wut.directeur.service

import com.wut.directeur.dtos.UserDetailsResponse
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class OauthService {

  fun extractAuthenticationDetails(principal: Principal): UserDetailsResponse {
    LOG.info("Start extracting authentication details")
    val oAuth2Authentication = principal as OAuth2Authentication
    val authentication = oAuth2Authentication.userAuthentication

    val authenticationDetails = authentication.details as Map<*, *>

    val response = UserDetailsResponse(
      email = authenticationDetails["email"] as String,
      picture = authenticationDetails["picture"] as String,
      name = authenticationDetails["name"] as String,
      locale = authenticationDetails["locale"] as String
    )

    LOG.info("Returning userDetailsResponse {}", response)
    return response
  }

  fun extractOauthEmail(principal: Principal): String? {
    LOG.info("Start extracting authentication details")
    val oAuth2Authentication = principal as OAuth2Authentication
    val authentication = oAuth2Authentication.userAuthentication
    val details = authentication.details as Map<*, *>

    val email = details["email"] as String
    LOG.info("Returning oauth email {}", email)
    return email
  }

  companion object {
    private val LOG = LoggerFactory.getLogger(OauthService::class.java)
  }
}
