package co.nimblehq.surveys.data.services.providers

import co.nimblehq.surveys.data.services.adapters.MappingApiErrorCallAdapterFactory

object CallAdapterFactoryProvider {
    fun getMappingApiErrorCallAdapterFactory(): MappingApiErrorCallAdapterFactory {
        return MappingApiErrorCallAdapterFactory.create()
    }
}
