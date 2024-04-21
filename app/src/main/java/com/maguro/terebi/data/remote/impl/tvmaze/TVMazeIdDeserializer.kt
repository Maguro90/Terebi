package com.maguro.terebi.data.remote.impl.tvmaze

import com.maguro.terebi.data.IdDeserializer
import com.maguro.terebi.data.model.Id
import com.maguro.terebi.data.model.LongId

class TVMazeIdDeserializer : IdDeserializer {

    override fun invoke(serialized: String): Id {
        return LongId(serialized.toLong())
    }

}