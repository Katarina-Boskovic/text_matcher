package com.textmatcher

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import kotlinx.css.*
import kotlin.test.*
import io.ktor.server.testing.*

class ApplicationTest {
    @Test
    fun testApplication() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/match-text") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                setBody("Sample email contains Biscuit International SAS\r\nhas 3 lines and contains\r\nCompanyB in the text")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(
                    "{\"matchedCompanies\":[\"Biscuit International SAS\"],\"matchedLines\":[\"Sample email contains Biscuit International SAS\"]}",
                    response.content
                )
            }
        }
    }
}
