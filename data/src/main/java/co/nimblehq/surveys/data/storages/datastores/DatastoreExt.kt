package co.nimblehq.surveys.data.storages.datastores

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import co.nimblehq.surveys.domain.extensions.default
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T> DataStore<Preferences>.get(
    key: Preferences.Key<T>,
    default: T,
): Flow<T> = data.map { prefs -> prefs[key].default(default) }

suspend fun <T> DataStore<Preferences>.set(
    key: Preferences.Key<T>,
    value: T,
) {
    edit { prefs -> prefs[key] = value }
}

suspend fun DataStore<Preferences>.clearAll() {
    edit { prefs -> prefs.clear() }
}