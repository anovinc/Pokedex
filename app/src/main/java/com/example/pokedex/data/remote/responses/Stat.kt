package com.example.pokedex.data.remote.responses

import com.google.gson.annotations.SerializedName


data class Stats(
  @SerializedName("base_stat")
  val value: Int,
  val stat: Stat
  
)
data class Stat(
  val name: String
)
