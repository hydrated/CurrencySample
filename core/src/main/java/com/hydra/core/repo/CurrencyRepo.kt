package com.hydra.core.repo

import com.hydra.core.db.CurrencyDb
import com.hydra.core.network.HttpClient

class CurrencyRepo constructor(
    private val api: HttpClient,
    private val db: CurrencyDb
){

}