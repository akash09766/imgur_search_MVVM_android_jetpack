package com.axxess.android.axxesschallenge.app.utils

object MConstants {

    const val SEARCH_LIST_LANDSCAPE_MODE_ROW_ITEM_COUNT = 5
    const val SEARCH_LIST_PORTRAIT_MODE_ROW_ITEM_COUNT = 3

    const val START_PAGE_NUMBER = 1
    const val PAGINATION_INITIAL_PAGE_NUMBER = 0

    const val CONNECTION_TIME_OUT = 25L

    const val IMAGE_BASE_URL = "https://i.imgur.com/"
    const val IMAGE_EXTENSION = ".jpg"

    // server response type
    const val SUCCESS = "SUCCESS"
    const val FAILED = "FAILED"
    const val ERROR = "ERROR"

    // network error message
    const val NOT_CONNECTED_TO_INTERNET = "You are not connected to internet."
    const val SERVER_NOT_RESPONDING =
        "Somethings wrong with our servers at the moment. Our best minds are on it. Please try again after some time."
    const val INVALID_SERVER_RESPONSE =
        "Somethings wrong with our servers at this movement.Our best minds are on it.\n Please try after some time."

    const val NO_INTERNET =
        "The internet connection you are connected to is not working please check."

    const val TRY_AGAIN = "Something went wrong please try again!"
    const val SNACK_BAR_OKAY_BTN_TEXT = "Okay"


    const val WENT_WRONG_ID = 1
    const val BACKEND_ERROR__ID = 2
    const val NETWORK_ERROR__ID = 3


    const val SEARCH_ITEM_DETAILS_INTENT_FILTER_NAME = "search_item_details_"
}