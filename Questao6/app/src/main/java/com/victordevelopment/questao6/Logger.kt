package com.victordevelopment.questao6

import android.util.Log

open class Logger {

    /**
     * This class is a non-static logger
     */
    class Logger {
        fun e(tag: String?, message: String?) {
            Log.e(tag, message)
        }

        fun w(tag: String?, message: String?) {
            Log.w(tag, message)
        }

        fun v(tag: String?, message: String?) {
            Log.v(tag, message)
        }

        fun d(tag: String?, message: String?) {
            Log.d(tag, message)
        }
    }

}
