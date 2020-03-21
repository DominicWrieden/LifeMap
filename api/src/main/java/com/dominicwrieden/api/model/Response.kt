package com.dominicwrieden.api.model

import okhttp3.ResponseBody
import java.io.IOException
import java.net.UnknownHostException

/**
 * Represents the result of making a network request.
 *
 * @param T success body type for 2xx response.
 * @param U error body type for non-2xx response.
 */
sealed class Response<out T : Any> {

    /**
     * A request that resulted in a response with a 2xx status code that has a body.
     */
    data class Success<T : Any>(val body: T) : Response<T>()

    /**
     * A request that resulted in a response with a non-2xx status code.
     */
    data class ServerError(val code: Int, val body: ResponseBody?) : Response<Nothing>()

    /**
     * A request, that resulted in a response with status code 403
     */
    data class AuthenticationError(val body: ResponseBody?) : Response<Nothing>()

    /**
     * A request that didn't result in a response.
     */
    data class NetworkError(val error: IOException) : Response<Nothing>()


    /**
     * Every other error. E.g. parsing error
     */
    data class UnknownError(val error: Throwable) : Response<Nothing>()
}


enum class StatusCode(val code: Int) {
    Continue(100),
    SwitchingProtocols(101),
    Processing(102),

    OK(200),
    Created(201),
    Accepted(202),
    NonAuthoritativeInformation(203),
    NoContent(204),
    ResetContent(205),
    PartialContent(206),
    MultiStatus(207),
    AlreadyReported(208),
    IMUsed(226),

    MultipleChoices(300),
    MovedPermanently(301),
    Found(302),
    SeeOther(303),
    NotModified(304),
    UseProxy(305),
    TemporaryRedirect(307),
    PermanentRedirect(308),

    BadRequest(400),
    Unauthorized(401),
    PaymentRequired(402),
    Forbidden(403),
    NotFound(404),
    MethodNotAllowed(405),
    NotAcceptable(406),
    ProxyAuthenticationRequired(407),
    RequestTimeout(408),
    Conflict(409),
    Gone(410),
    LengthRequired(411),
    PreconditionFailed(412),
    PayloadTooLarge(413),
    URITooLong(414),
    UnsupportedMediaType(415),
    RangeNotSatisfiable(416),
    ExpectationFailed(417),
    IAmATeapot(418),
    MisdirectedRequest(421),
    UnprocessableEntity(422),
    Locked(423),
    FailedDependency(424),
    UpgradeRequired(426),
    PreconditionRequired(428),
    TooManyRequests(429),
    RequestHeaderFieldsTooLarge(431),
    UnavailableForLegalReasons(451),

    InternalServerError(500),
    NotImplemented(501),
    BadGateway(502),
    ServiceUnavailable(503),
    GatewayTimeout(504),
    HTTPVersionNotSupported(505),
    VariantAlsoNegotiates(506),
    InsufficientStorage(507),
    LoopDetected(508),
    NotExtended(510),
    NetworkAuthenticationRequired(511),

    Unknown(0)
}

fun Int.codeStatus(): StatusCode =
    StatusCode.values().find { it.code == this } ?: StatusCode.Unknown

//TODO in der neuen API ist Token invalid nicht 403 oder 401 sondern 500
fun <T : Any, U: Any> evaluateResponse(response: retrofit2.Response<T>, transform: (input:T) -> U): Response<U> {
    return if (response.isSuccessful && response.body() != null) {
        Response.Success(transform(response.body()!!))
    } else if (response.code().codeStatus() == StatusCode.Forbidden) {
        Response.AuthenticationError(response.errorBody())
    } else {
        Response.ServerError(response.code(),response.errorBody())
    }
}

fun <T : Any> evaluateErrorResponse(exception: Throwable): Response<T> {
    return if (exception is UnknownHostException) {
        Response.NetworkError(exception)
    } else {
        Response.UnknownError(exception)
    }
}