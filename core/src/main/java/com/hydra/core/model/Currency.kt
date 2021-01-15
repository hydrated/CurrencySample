package com.hydra.core.model

data class Currency (
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val currencies: Map<String, String>
) {
    companion object {
        public fun fromJson(json: String) = {
            //TODO:
        }
    }
}