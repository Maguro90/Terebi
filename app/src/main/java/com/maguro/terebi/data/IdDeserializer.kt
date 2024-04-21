package com.maguro.terebi.data

import com.maguro.terebi.data.model.Id

interface IdDeserializer {
    operator fun invoke(serialized: String): Id
}