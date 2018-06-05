package com.wut.directeur.rest.controller

import com.wut.directeur.service.OauthService
import com.wut.directeur.dtos.UserDetailsResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("users")
class UserController
@Autowired constructor(
  private val oauthService: OauthService
) {

  @GetMapping
  @RequestMapping("current")
  fun getLoggedUserDetails(@AuthenticationPrincipal principal: Principal): UserDetailsResponse {
    LOG.info("Receiver getLoggedUserDetails request")

    return oauthService.extractAuthenticationDetails(principal)
  }

  companion object {
    private val LOG = LoggerFactory.getLogger(UserController::class.java)
  }
}
