package co.nimblehq.surveys.domain.errors.mappers.remote

import co.nimblehq.surveys.domain.errors.exceptions.network.NetworkCaughtException
import co.nimblehq.surveys.domain.errors.mappers.ErrorMapper

interface RemoteErrorMapper : ErrorMapper<NetworkCaughtException>