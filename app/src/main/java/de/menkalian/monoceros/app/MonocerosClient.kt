package de.menkalian.monoceros.app

import android.util.Log
import de.menkalian.monoceros.app.printing.PrintInformation
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.android.Android
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MonocerosClient {
    private val serverUrl = "192.168.178.24"
    private val serverPort = "8080"
    private val httpBase = "http://$serverUrl:$serverPort"

    private val client = HttpClient(Android) {}

    suspend fun checkHealth(): Boolean {
        try {
            Log.i("MonocerosClient", "Prüfe Serverstatus")
            val response = client.request<HttpResponse>("${httpBase}/actuator/health")
            response.receive<Any>()
            return response.status == HttpStatusCode.OK
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }
    }

    suspend fun printData(printInformation: PrintInformation): Boolean {
        try {
            Log.i("MonocerosClient", "Drucke $printInformation")
            val response = client.post<HttpResponse>("$httpBase/print") {
                this.body = Json.encodeToString(printInformation)
                contentType(ContentType.Application.Json)
            }
            response.receive<Any>()
            return response.status == HttpStatusCode.OK
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }
    }
}