package database_functions

import attributes
import malfunctions

private fun localGetMalfunctions(): List<String> {
    val lMalfunctions = ArrayList<String>()

    for (malfunction in malfunctions) {
        lMalfunctions.add(malfunction.name)
    }

    return lMalfunctions
}

private fun localGetAttributes(): List<String> {
    val lAttribute = ArrayList<String>()

    for (attribute in attributes) {
        lAttribute.add(attribute.name)
    }

    return lAttribute
}

private fun localGetAvailableValues(): List<String> {
    val lValues = ArrayList<String>()

    for (attribute in attributes) {
        for (attributeValue in attribute.availableValues) {
            var isFound = false

            for (lValue in lValues) {
                if (attributeValue.value == lValue) {
                    isFound = true
                    break
                }
            }

            if (!isFound)
                lValues.add(attributeValue.value)
        }
    }

    return lValues
}

fun getElementsByIndex(index: Int): List<String> {
    return when(index) {
        1 -> localGetAttributes()
        2 -> localGetAvailableValues()
        else -> localGetMalfunctions()
    }

}