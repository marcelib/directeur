package com.wut.directeur.config

enum class AppParam(val key: String, val defaultValue: Any? = null) {

  AUTHORIZATION_LOGIN_CALLBACK_URL("auth.login.callback.url"),
  AUTHORIZATION_LOGOUT_CALLBACK_URL("auth.logout.callback.url"),
}
