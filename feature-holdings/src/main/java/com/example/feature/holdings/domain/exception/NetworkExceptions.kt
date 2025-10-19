package com.example.feature.holdings.domain.exception

/**
 * Exception thrown when there's no internet connectivity
 */
class NoInternetException : Exception("No internet connection available")

/**
 * Exception thrown when there's a network error
 */
class NetworkException(message: String) : Exception(message)
