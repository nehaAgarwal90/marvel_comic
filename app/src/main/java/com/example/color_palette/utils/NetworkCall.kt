package com.example.color_palette.utils

import com.example.color_palette.constants.Constants.ERROR_MESSAGE_DEFAULT
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

inline fun <reified T> execute(
    apiCall: () -> Response<T>
): ApiResponse<T> {
    return try {
        val response = apiCall()
        val code = response.code()
        val body: T? = response.body()
        val errorBody = response.errorBody()

        if (response.isSuccessful) {
            if (body != null) {
                Success(body)
            } else { //No exception && response is null.
                NetworkError(ERROR_MESSAGE_DEFAULT)
            }
        } else {
            if (errorBody != null) {

                val jsonObject = JSONObject(errorBody.string())
                ApiError(jsonObject.optString("message"), jsonObject.optString("code"))
//                TODO("Error parsing is pending.")
            } else { //No exception && response and error response both are null.
                NetworkError(ERROR_MESSAGE_DEFAULT)
            }
        }
    } catch (httpException: HttpException) {
        val errorBody = httpException.response()?.errorBody()
        val errorCode = httpException.response()?.code()
        if (errorBody != null) {
            val jsonObject = JSONObject(errorBody.string())
            ApiError(jsonObject.optString("message"), jsonObject.optString("code"))
            //            TODO("Error parsing is pending.")
        } else { //No exception && response and error response both are null.
            NetworkError(ERROR_MESSAGE_DEFAULT)
        }
    } catch (exception: Exception) {
        NetworkError(exception.localizedMessage)
    }
}