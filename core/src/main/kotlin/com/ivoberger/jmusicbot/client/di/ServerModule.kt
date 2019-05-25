/*
* Copyright 2019 Ivo Berger
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.ivoberger.jmusicbot.client.di

import com.ivoberger.jmusicbot.client.api.MusicBotService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
internal class ServerModule(private val hostAddress: String, private val port: Int) {

    @Provides
    @Named(NameKeys.BASE_URL)
    fun baseUrl(): String = "http://$hostAddress:$port/"

    @Provides
    @Named(NameKeys.PORT)
    fun port(): Int = port

    @Provides
    @Named(NameKeys.BUILDER_RETROFIT_URL)
    fun retrofitBuilder(
        @Named(NameKeys.BUILDER_RETROFIT_BASE) retrofitBuilder: Retrofit.Builder,
        @Named(NameKeys.BASE_URL) baseUrl: String
    ) =
        retrofitBuilder.baseUrl(baseUrl)

    @Provides
    @Named(NameKeys.SERVICE_BASE)
    fun musicBotService(@Named(NameKeys.BUILDER_RETROFIT_URL) retrofitBuilder: Retrofit.Builder): MusicBotService =
        retrofitBuilder.build().create(MusicBotService::class.java)
}
