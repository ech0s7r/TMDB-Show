package com.ech0s7r.android.skeletonapp.model

import java.io.Reader

/**
 *
 * @author ech0s7r
 */
class ModelResponse {

    companion object {
        fun parse(reader: Reader): Model? {
            reader.forEachLine { }
            return null
        }
    }

}