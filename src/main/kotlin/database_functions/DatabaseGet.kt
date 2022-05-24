package database_functions

import attributePictures
import attributes
import gui.controllers.controller_DB.ControllerDBCrutch
import malfunctions


fun getMalfunctions(): List<ControllerDBCrutch> {
    val result: ArrayList<ControllerDBCrutch> = ArrayList()

    for (malfunction in malfunctions) {
        result.add(ControllerDBCrutch(malfunction.name, "", ""))
    }

    return result
}


fun getAttributes(): List<ControllerDBCrutch> {
    val result: ArrayList<ControllerDBCrutch> = ArrayList()

    for (attribute in attributes) {
        result.add(ControllerDBCrutch(attribute.name, "", ""))
    }

    return result
}


fun getAvailableValues(): List<ControllerDBCrutch> {
    val result: ArrayList<ControllerDBCrutch> = ArrayList()

    for (attribute in attributes) {
        for (attributeValue in attribute.availableValues)
            result.add(ControllerDBCrutch(attribute.name, attributeValue.value, ""))
    }

    return result
}

fun getNormalValues(): List<ControllerDBCrutch> {
    val result: ArrayList<ControllerDBCrutch> = ArrayList()

    for (attribute in attributes) {
        for (attributeValue in attribute.normalValues)
            result.add(ControllerDBCrutch(attribute.name, attributeValue.value, ""))
    }

    return result
}

fun getAttributePicture(): List<ControllerDBCrutch> {
    val result: ArrayList<ControllerDBCrutch> = ArrayList()

    for (picture in attributePictures) {
        for (attribute in picture.valuesByAttributes)
            result.add(ControllerDBCrutch(picture.malfunction.name, attribute.attribute.name, ""))
    }

    return result
}

fun getValuesByMalfunctions(): List<ControllerDBCrutch> {
    val result: ArrayList<ControllerDBCrutch> = ArrayList()

    for (picture in attributePictures) {
        for (valuesByMalfunction in picture.valuesByAttributes) {
            for (attributeValue in valuesByMalfunction.values)
                result.add(
                    ControllerDBCrutch(
                        picture.malfunction.name,
                        valuesByMalfunction.attribute.name,
                        attributeValue.value
                    )
                )
            if (valuesByMalfunction.values.size == 0) {
                result.add(
                    ControllerDBCrutch(
                        picture.malfunction.name,
                        valuesByMalfunction.attribute.name,
                        ""
                    )
                )
            }
        }
    }

    return result
}
