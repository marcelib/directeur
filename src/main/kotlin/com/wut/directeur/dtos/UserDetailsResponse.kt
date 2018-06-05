package com.wut.directeur.dtos

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class UserDetailsResponse @JsonCreator constructor(
  @JsonProperty("email") val email: String,
  @JsonProperty("name") val name: String,
  @JsonProperty("picture") val picture: String,
  @JsonProperty("locale") val locale: String
)
