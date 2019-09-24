package com.textmatcher

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import kotlinx.coroutines.io.ByteReadChannel

class Company(val name: String)
class Deal(val targets:Array<Company>)
class Deals(val deals:Array<Deal>)

class DealResponse(val data: Deals)

suspend fun getDeals(): Array<String> {
    val client = HttpClient()
    val htmlContent = client.post<String>{
        url("http://127.0.0.1:8080/graphql")
        body = "{\"query\":\"query FindAllDeals {\\n  deals(where:{stateIn:[CONFIRMED]}){\\n    targets{\\n      name\\n    }\\n  }\\n}\\n\\n\",\"variables\":null,\"operationName\":\"FindAllDeals\"}"
    }
    println(htmlContent)

    client.close()
    val response: DealResponse? = Klaxon().parse<DealResponse>(htmlContent)
    val companiesList: Array<String>? = response?.data?.deals?.map { deal -> deal.targets[0].name }?.toTypedArray()
    if(companiesList.isNullOrEmpty()){
        return arrayOf()
    }
    return companiesList
}