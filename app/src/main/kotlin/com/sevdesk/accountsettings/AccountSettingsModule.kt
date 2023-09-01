package com.sevdesk.accountsettings

import com.sevdesk.persistence.EventRepository
import org.koin.dsl.module

fun accountSettingsModule() = module {
    single { AccountSettingsEventStore(EventRepository.instance()) }
    single { AccountSettingsWriteService(get()) }
    single { AccountSettingsReadService(get()) }
}