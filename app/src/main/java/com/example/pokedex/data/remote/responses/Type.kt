package com.example.pokedex.data.remote.responses

data class Type(
  val slot: Int,
  val type: TypeX
)

data class TypeX(
  val name: String,
  val url: String
)