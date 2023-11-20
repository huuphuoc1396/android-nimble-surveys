package co.nimblehq.surveys.data.storages.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T> DataStore<Preferences>.get(
    key: Preferences.Key<T>,
    default: T,
): Flow<T> = data.map { prefs -> prefs[key] ?: default }

suspend fun <T> DataStore<Preferences>.set(
    key: Preferences.Key<T>,
    value: T,
) {
    edit { prefs -> prefs[key] = value }
}